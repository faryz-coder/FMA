package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.FixNotation
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentAdminViewStudentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminViewStudentFragment : Fragment(), UserListener {

    private var _binding : FragmentAdminViewStudentBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var myAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminViewStudentBinding.inflate(inflater, container, false)

        val listStudent = mutableListOf<UserList>()
        myAdapter = UserListAdapter(listStudent, this@AdminViewStudentFragment)

        val doc = db.collection("Login").whereEqualTo("type", "student")
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(FixNotation.LOG, "document: ${document.documents}")
                    document.documents.forEach { profile ->
                        Log.d(FixNotation.LOG, "document: ${profile["name"]}")
                        listStudent.add(
                            UserList(
                                profile["name"].toString(),
                                profile["email"].toString(),
                                profile.id
                            )
                        )
                    }
                    binding.listStudentRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@AdminViewStudentFragment.context)
                        adapter = myAdapter
                    }
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selectUser(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_adminViewStudentFragment_to_adminStudentInfo, bundle)
    }
}
