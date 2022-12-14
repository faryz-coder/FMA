package com.bmit.fma.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log.d
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.student.ListMenu
import com.bmit.fma.student.SessionViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors

class AlertDialogCustom {

    private var activity: Activity
    private var sessionViewModel : SessionViewModel

    constructor(myActivity: Activity, mySessionViewModel: SessionViewModel = SessionViewModel()) {
        activity = myActivity
        sessionViewModel = mySessionViewModel
    }

    fun showQuantitySelectionDialog(
        itemId: String,
        itemBox: MaterialCardView,
        listMenu: ListMenu,
        callback: ItemCallback
    ) {
        var itemQuantity: TextView? = null

        val dialog = activity.let {
            val builder = AlertDialog.Builder(it)
            val inflater = activity.layoutInflater

            builder.setView(inflater.inflate(R.layout.dialogs_quantity_selection, null))
                .setPositiveButton(
                    "Confirm"
                ) { dialog, id ->
                    Toast.makeText(activity, "Confirm", Toast.LENGTH_SHORT).show()
                    if (itemQuantity?.text.toString().toInt() > 0) {
                        sessionViewModel.addItem(
                            itemId,
                            itemQuantity?.text.toString(),
                            listMenu
                        )
                        d("sessionViewModel", "${sessionViewModel.getItemOrder()}")
                        itemBox.setCardBackgroundColor(MaterialColors.getColor(itemBox, com.google.android.material.R.attr.colorSurfaceVariant))
                        callback.onItemUpdated()
                    } else {
                        sessionViewModel.removeItem(itemId)
                        d("sessionViewModel", "${sessionViewModel.getItemOrder()}")
                        itemBox.setCardBackgroundColor(MaterialColors.getColor(itemBox, com.google.android.material.R.attr.colorSurface))
                        callback.onItemUpdated()

                    }
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, id ->
                    Toast.makeText(activity, "Cancel!", Toast.LENGTH_SHORT).show()
                }

            builder.create()

        } ?: throw java.lang.IllegalStateException("Activity cannot be null")
        dialog.show()
        itemQuantity = dialog.findViewById(R.id.itemQuantity)
        dialog.findViewById<Button>(R.id.quantityAdd).setOnClickListener {
            itemQuantity.text = (itemQuantity.text.toString().toInt() + 1).toString()
        }
        dialog.findViewById<Button>(R.id.quantitySubstract).setOnClickListener {
            itemQuantity.text = if (itemQuantity.text.toString().toInt() - 1 < 0) "0" else (itemQuantity.text.toString().toInt() - 1).toString()
        }
    }


}