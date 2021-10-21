package com.example.accounttcreation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val request_image_capture = 1
    private val request_select_image = 2
    private lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profile_backbtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        change_password.setOnClickListener {
            startActivity(Intent(this, ResetPassword::class.java))
        }


        image_view.setOnClickListener {
            //takePictureIntent()
            ChangeProfilePictureDialog()

        }

        val user = FirebaseAuth.getInstance().currentUser
        profile_email_textView.text = user?.email.toString()
    }




    private fun takePictureIntent(){
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(pictureIntent, request_image_capture)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == request_image_capture && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUrl(imageBitmap)
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
                        Toast.makeText(this@ProfileActivity, imageUri.toString(), Toast.LENGTH_SHORT).show()
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
    private fun selectImage(){
        val intent = Intent()
        intent.type = "images/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, request_select_image)

    }

    



    private fun uploadFromGallery(){
        val fileName = FirebaseAuth.getInstance().currentUser?.email.toString()
        val storageRef = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageRef.putFile(imageUri).
                addOnSuccessListener {
                    Toast.makeText(this, "Upload Complete", Toast.LENGTH_SHORT).show()
                    image_view.setImageURI(imageUri)
                }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()

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





