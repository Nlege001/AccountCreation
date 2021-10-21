package com.example.accounttcreation

import android.app.usage.UsageEvents
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val nav = findViewById<NavigationView>(R.id.navView)
        //val navController = findNavController()

        // for displaying email on navigation view






        toggle = ActionBarDrawerToggle(this,drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav.setNavigationItemSelectedListener {

            it.isChecked = true
            when(it.itemId){
                R.id.home_item -> Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                R.id.account_item -> startActivity(Intent(this, ProfileActivity::class.java))  //replaceFragment(ProfileFragment(), it.title.toString())
                R.id.settings_item -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
                R.id.logout_item -> showAlertDialog()
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



        ProvideInput.setOnClickListener {
            startActivity(Intent(this, UserInput::class.java ))
        }


        AssessmentView.setOnClickListener {
            startActivity(Intent(this, DisplayUserInput::class.java))
        }





        mainButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            finish()
        }

        dataButton.setOnClickListener {
            startActivity(Intent(this, RecycleViewDB::class.java))
        }




    }




    private fun replaceFragment(fragment : Fragment, title : String){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        //fragmentManager.popBackStackImmediate()
        fragmentTransaction.commit()
        drawer.closeDrawers()
        setTitle(title)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes"){dialog, id ->
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            finish()
        }
        builder.setNegativeButton("No"){dialog, id ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }

}