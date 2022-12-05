package com.bmit.fma

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.databinding.ActivityStudentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class StudentActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(LOG, "Key: $key Value: $value")
            }
        }

        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.login_graph_fragment_activity_student)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(LOG, "fetching fcm token failed")
                return@OnCompleteListener
            }

            // Get New FCM registration token
            val token = task.result

            // Log and toast
            val msg = "FCM Token: $token"
            Log.d(LOG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.login_graph_fragment_activity_admin)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}