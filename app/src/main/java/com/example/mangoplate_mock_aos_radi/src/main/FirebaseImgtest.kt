package com.example.mangoplate_mock_aos_radi.src.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentFirebaseTestBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class FirebaseImgtest: BaseFragment<FragmentFirebaseTestBinding>(FragmentFirebaseTestBinding::bind, R.layout.fragment_firebase_test) {

    var pickImageFromAlbum = 0
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null
    var viewProfile: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewProfile = view

        fbStorage = FirebaseStorage.getInstance()
        Log.d(TAG, "fbStorage: $fbStorage")

        binding.button2.setOnClickListener {
            val photoPickIntent = Intent(Intent.ACTION_PICK)
            photoPickIntent.type = "image/*"
            startActivityForResult(photoPickIntent, pickImageFromAlbum)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d(TAG, "token: $token")

            // Log and toast
            val msg = token.toString();
            Log.d(TAG, "msg: $msg")
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "requestCode: $requestCode, pickImageFromAlbum: $pickImageFromAlbum")
        Log.d(TAG, "resultCode: $resultCode, resultCode: ${Activity.RESULT_OK}")
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data
                binding.imageView6.setImageURI(uriPhoto)
                Log.d(TAG, "uriPhoto: $uriPhoto")
                if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    funImageUpload(viewProfile!!)
                }
            }
        }

    }

    private fun funImageUpload(view: View) {
        val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        val imgFileName = "IMAGE_${timeStamp}_.png"
        val storageRef = fbStorage?.reference?.child("images")?.child(imgFileName)



        Log.d(TAG, "imgFileName: $imgFileName  storageRef: $storageRef")

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            Log.d(TAG, "funImageUpload: uploadSuccess")
            Toast.makeText(view.context, "Image Uploaded", Toast.LENGTH_SHORT).show()
        }
        storageRef?.putFile(uriPhoto!!)?.addOnFailureListener{
            Log.d(TAG, "funImageUpload: uploadFailure")
            Toast.makeText(view.context, "Image Uploaded Failure", Toast.LENGTH_SHORT).show()
        }

    }




}