package com.bmit.fma.admin

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentAdminViewStaffBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminViewStaffFragment : Fragment(), UserListener {

    private var _binding: FragmentAdminViewStaffBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminViewStaffBinding.inflate(inflater, container, false)

        val listStaff = mutableListOf<UserList>()

        val doc = db.collection("Login").whereEqualTo("type", "staff")
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(LOG, "document: ${document.documents}")
                    document.documents.forEach { profile ->
                        Log.d(LOG, "document: ${profile["name"]}")
                        listStaff.add(
                            UserList(
                                profile["name"].toString(),
                                profile["email"].toString(),
                                profile.id
                            )
                        )
                    }
                    binding.listStaffRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@AdminViewStaffFragment.context)
                        adapter = UserListAdapter(listStaff, this@AdminViewStaffFragment)
                    }
                }
            }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn4.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selectUser(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_adminViewStaffFragment_to_adminStaffInfo, bundle)
    }
}
