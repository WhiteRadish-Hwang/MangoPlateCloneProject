package com.example.mangoplate_mock_aos_radi.src.main

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentFirebaseTestBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
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
            val msg = token.toString()
            Log.d(TAG, "msg: $msg")
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })

    }

    fun onMessageReceived(remoteMessage: RemoteMessage) {
        var message: String? = ""
        var title: String? = ""

        // Notifition 항목이 있을때.
        if (remoteMessage.notification != null) {
            message = remoteMessage.notification!!.body
            title = remoteMessage.notification!!.title
        }

        // Data 항목이 있을때.
        val data = remoteMessage.data
        val messageData: String = data["message"]!!
        val titleData: String = data["title"]!!
        val nameData: String = data["name"]!!

//        //저는 포그라운드 백그라운드 동일하게 컨트롤하기 위해 Data항목에 푸쉬 Title, Body 모두 넣어서 구현하였습니다.
//        sendNotification(titleData, messageData, nameData)
    }

//    private fun sendNotification(title: String, message: String, name: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("name", name) //push 정보중 name 값을 mainActivity로 넘김
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder: NotificationCompat.Builder
//        val notificationManager = getSystemService<Any>(Context.NOTIFICATION_SERVICE as Context) as NotificationManager?
//
//        //SDK26부터 푸쉬에 채널항목에 대한 세팅이 필요하다.
//        if (Build.VERSION.SDK_INT >= 26) {
//            val channelId = "test push"
//            val channelName = "test Push Message"
//            val channelDescription = "New test Information"
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            channel.description = channelDescription
//            //각종 채널에 대한 설정
//            channel.enableLights(true)
//            channel.lightColor = Color.RED
//            channel.enableVibration(true)
//            channel.vibrationPattern = longArrayOf(100, 200, 300)
//            notificationManager!!.createNotificationChannel(channel)
//            //channel이 등록된 builder
//            notificationBuilder = Builder(this, channelId)
//        } else {
//            notificationBuilder = Builder(this)
//        }
//        notificationBuilder.setSmallIcon(R.drawable.ic_stat_ic_notification)
//                .setContentTitle(title)
//                .setStyle(BigTextStyle().bigText(message))
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//                .setContentText(message)
//        val localTime: Int = TimeUtil().getuniqTime()
//        notificationManager!!.notify(localTime /* ID of notification */, notificationBuilder.build())
//    }

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