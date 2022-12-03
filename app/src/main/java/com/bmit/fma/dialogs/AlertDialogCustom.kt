package com.bmit.fma.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.util.Log.d
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bmit.fma.R
import com.bmit.fma.student.SessionViewModel

class AlertDialogCustom {

    private var activity: Activity
    private var sessionViewModel : SessionViewModel

    constructor(myActivity: Activity, mySessionViewModel: SessionViewModel) {
        activity = myActivity
        sessionViewModel = mySessionViewModel
    }

    fun showQuantitySelectionDialog(itemId: String, itemBox: ConstraintLayout) {
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
                        sessionViewModel.addItem(itemId, itemQuantity?.text.toString())
                        d("sessionViewModel", "${sessionViewModel.getItemOrder()}")
                        itemBox.setBackgroundColor(Color.GRAY)
                    } else {
                        sessionViewModel.removeItem(itemId)
                        d("sessionViewModel", "${sessionViewModel.getItemOrder()}")
                        itemBox.setBackgroundColor(Color.WHITE)
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
        itemQuantity = dialog.findViewById<TextView>(R.id.itemQuantity)
        dialog.findViewById<Button>(R.id.quantityAdd).setOnClickListener {
            itemQuantity.text = (itemQuantity.text.toString().toInt() + 1).toString()
        }
        dialog.findViewById<Button>(R.id.quantitySubstract).setOnClickListener {
            itemQuantity.text = if (itemQuantity.text.toString().toInt() - 1 < 0) "0" else (itemQuantity.text.toString().toInt() - 1).toString()
        }
    }


}