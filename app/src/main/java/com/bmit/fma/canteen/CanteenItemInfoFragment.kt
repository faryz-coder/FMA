package com.bmit.fma.canteen

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.FixNotation
import com.bmit.fma.databinding.FragmentCanteenItemInfoBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.utils.Validation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import java.util.concurrent.Executors

class CanteenItemInfoFragment: Fragment(), ItemCallback, View.OnClickListener {

    private var _binding: FragmentCanteenItemInfoBinding? = null
    private val binding get() = _binding!!
    private val getData = GetData()
    private var updateData = UpdateData()
    private var imageUri : Uri? = null
    private var type = ""
    private var status = ""
    private var validate = Validation()
    private lateinit var itemId : String
    lateinit var itemInfo : DocumentSnapshot


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanteenItemInfoBinding.inflate(inflater, container,false)
        itemId = arguments?.getString("itemId").toString()
        getData.getItemInfo(itemId, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            resultLauncher.launch(gallery)
        }

        // Radio Button
        binding.choiceActive.setOnClickListener(this)
        binding.choiceDisabled.setOnClickListener(this)
        binding.choiceDrink.setOnClickListener(this)
        binding.choiceFood.setOnClickListener(this)
        binding.choiceSnack.setOnClickListener(this)

        // submit
        binding.updateBtn.setOnClickListener {
            Toast.makeText(requireContext(), "selected: $type, $status", Toast.LENGTH_SHORT).show()
            val name = binding.inputItemName.editText?.text.toString()
            val calories = binding.inputItemCalories.editText?.text.toString()
            val price = binding.inputItemPrice.editText?.text.toString()
            //validate the form
            if (validate.validateToUpdateItem(itemInfo, name, calories, price, type, status)) {
                // upload data
                updateData.updateItemInfo(itemId,imageUri, name, calories, price, type, status, this)
            } else {
                Snackbar.make(requireView(), "Nothing to update", Snackbar.LENGTH_SHORT).show()
            }

        }

        binding.backBtn7.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            imageUri = data?.data
            Log.d(FixNotation.LOG, "imageUri: $imageUri")
            binding.uploadImage.setImageURI(imageUri)
        }
    }

    override fun onItemUpdated() {
        super.onItemUpdated()
        Snackbar.make(requireView(), "Updated Successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemInfo(item: DocumentSnapshot) {
        super.onItemInfo(item)
        itemInfo = item
        binding.inputItemName.editText?.setText(item["name"].toString())
        binding.inputItemCalories.editText?.setText(item["calories"].toString())
        binding.inputItemPrice.editText?.setText(item["price"].toString())
        val imageUrl = item["imageUrl"].toString()

        when (item["type"].toString()) {
            "Food" -> {
                binding.choiceFood.isChecked = true
                type = "Food"
            }
            "Drink" -> {
                binding.choiceDrink.isChecked = true
                type = "Drink"
            }
            "Snack" -> {
                binding.choiceSnack.isChecked = true
                type = "Drink"
            }
        }
        when (item["status"].toString()) {
            "Active" -> {
                binding.choiceActive.isChecked = true
                status = "Active"
            }
            "Disabled" -> {
                binding.choiceDisabled.isChecked = true
                status = "Disabled"
            }
        }

        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()

        // Grab Image online and display
        executor.execute {
            try {
                val `in` = java.net.URL(imageUrl).openStream()
                val img = BitmapFactory.decodeStream(`in`)

                handler.post{
                    binding.uploadImage.setImageBitmap(img)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v is RadioButton) {
            // is the button checked
            val checked = v.isChecked

            // check which radio button is clicked
            when (v.id) {
                binding.choiceActive.id -> if (checked) {
                    status = binding.choiceActive.text.toString()
                }
                binding.choiceDisabled.id -> if (checked) {
                    status = binding.choiceDisabled.text.toString()
                }
                binding.choiceDrink.id -> if (checked) {
                    type = binding.choiceDrink.text.toString()
                }
                binding.choiceFood.id -> if (checked) {
                    type = binding.choiceFood.text.toString()
                }
                binding.choiceSnack.id -> if (checked) {
                    type = binding.choiceSnack.text.toString()
                }
            }
        }
    }
}