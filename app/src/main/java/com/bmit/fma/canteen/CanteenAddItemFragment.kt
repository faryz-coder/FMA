package com.bmit.fma.canteen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.databinding.FragmentCanteenAddItemBinding
import com.bmit.fma.firebaseUtils.UploadData
import com.bmit.fma.utils.Common
import com.bmit.fma.utils.Validation
import com.bmit.fma.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class CanteenAddItemFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCanteenAddItemBinding? = null
    private val binding get() = _binding!!

    private var imageUri : Uri? = null
    private var type = ""
    private var status = ""
    private var validate = Validation()
    private lateinit var uploadData : UploadData
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanteenAddItemBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadData = UploadData(loginViewModel, requireView())

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
        binding.submitBtn.setOnClickListener {
            Toast.makeText(requireContext(), "selected: $type, $status", Toast.LENGTH_SHORT).show()
            val name = binding.inputItemName.editText?.text.toString()
            val calories = binding.inputItemCalories.editText?.text.toString()
            val price = binding.inputItemPrice.editText?.text.toString()
            //validate the form
            if (validate.validateAddItem(imageUri, name, calories,price,type,status)) {
                // upload data
                uploadData.uploadItem(imageUri, name, calories,price,type,status)
            } else {
                Snackbar.make(requireView(), "Please complete the form", Snackbar.LENGTH_SHORT).show()
            }

        }

        // back btn
        binding.backBtn7.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            imageUri = data?.data
            Log.d(LOG, "imageUri: $imageUri")
            binding.uploadImage.setImageURI(imageUri)
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