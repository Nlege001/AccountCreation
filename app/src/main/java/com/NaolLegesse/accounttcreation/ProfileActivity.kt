package com.NaolLegesse.accounttcreation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.change_password
import kotlinx.android.synthetic.main.activity_profile.image_view
import kotlinx.android.synthetic.main.activity_profile.profile_email_textView
import kotlinx.android.synthetic.main.activity_profile.progressbar
import kotlinx.android.synthetic.main.nav_profile_header.*
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val request_image_capture = 1
    companion object {private val request_select_image = 2}
    private lateinit var imageUri : Uri
    private lateinit var url : String
    private lateinit var uid : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storage = FirebaseStorage.getInstance().reference.child("pics/$uid")
        storage.downloadUrl.addOnSuccessListener { result ->
            url = result.toString()
            Glide.with(this).load(url).into(image_view)
        }

        text_email.setOnClickListener{
            ChangeProfilePictureDialog()
        }


        profile_backbtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        change_password.setOnClickListener {
            startActivity(Intent(this, ResetPassword::class.java))
        }


        image_view.setOnClickListener {
            //takePictureIntent()
            ChangeProfilePictureDialog()
            //chooseFromGallery.launch("image/*")

        }

        val user = FirebaseAuth.getInstance().currentUser
        profile_email_textView.text = user?.email.toString()
    }




    private fun takePictureIntent(){
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(pictureIntent, request_image_capture)
        }

    val chooseFromGallery = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            uploadFromGallery(it)
            //image_view.setImageURI(it)

        }
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            when (requestCode) {
                request_image_capture -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    uploadImageAndSaveUrl(imageBitmap)
                }
            }
        }
    }

    private fun uploadImageAndSaveUrl(bitmap: Bitmap){
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        val upload = storageRef.putBytes(image)

        progressbar.visibility = VISIBLE

        upload.addOnCompleteListener{uploadTask ->
            progressbar.visibility = INVISIBLE
            if(uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener { urltask ->
                    urltask.result?.let {
                        imageUri = it
                        Toast.makeText(this@ProfileActivity, "Profile Picture Set Successfully", Toast.LENGTH_SHORT).show()
                        image_view.setImageBitmap(bitmap)
                        }
                    }
                }else{
                Toast.makeText(
                    this@ProfileActivity,
                    uploadTask.exception!!.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                    }

                }
            }


    private fun uploadFromGallery(uri: Uri){
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")

        storageRef.putFile(imageUri).addOnCompleteListener{uploadTask ->
            progressbar.visibility = VISIBLE
            if(uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        Toast.makeText(this@ProfileActivity, imageUri.toString(), Toast.LENGTH_SHORT).show()
                        image_view.setImageURI(imageUri)
                    }
                }
            }
        }
    }










    private fun ChangeProfilePictureDialog(){
        val listItems = arrayOf("Open Camera","Choose from Gallery")
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle("Change Profile Picture")
        builder.setSingleChoiceItems(listItems, -1){dialogInterface, i->
            listItems[i]
            if (listItems[i] == "Open Camera"){
                takePictureIntent() // if open camera is chosen,camera is opened for picture change
            }else if (listItems[i] == "choose from Gallery"){
                chooseFromGallery.launch("image/*")
            }
            dialogInterface.dismiss()
            }
        builder.setNeutralButton("Cancel"){dialog, which ->
            dialog.cancel()
            }

        val dialog = builder.create()
        dialog.show()

            }
        }






