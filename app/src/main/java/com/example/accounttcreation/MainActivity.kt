package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val nav = findViewById<NavigationView>(R.id.navView)


        toggle = ActionBarDrawerToggle(this,drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext, "Clicked Item 1", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(applicationContext, "Clicked Item 2", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(applicationContext, "Clicked Item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }




        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")




        val emailText = findViewById<TextView>(R.id.MainEmail)
        val passwordText = findViewById<TextView>(R.id.MainPassword)
        val mainButton = findViewById<Button>(R.id.logoutButton)
        val dataButton = findViewById<Button>(R.id.DatabaseButton)




        emailText.text = "User ID :: $userId"
        passwordText.text = "Email ID :: $emailId"





        mainButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            finish()
        }

        dataButton.setOnClickListener {
            startActivity(Intent(this, RecycleViewDB::class.java))
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}