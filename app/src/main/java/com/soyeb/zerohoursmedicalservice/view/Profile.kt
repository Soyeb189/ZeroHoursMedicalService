package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.local.User
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility

class Profile : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    //*********** Toolbar ************//
    private lateinit var toolbar: Toolbar

    //*********** Drawer *************//
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    //*********** Text View **************//
    private lateinit var tvName : TextView
    private lateinit var tvEmail : TextView

    private lateinit var user:User


    private lateinit var globalVeriable: GlobalVariable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_drawer)

        initialization()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "প্রোফাইল"


        //************ For Drawer *****************///
        toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        getSharedData()




//        tvName.text = user.name
//        tvEmail.text = user.email
    }

    private fun getSharedData() {
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

        val id : String? = sharedPreferences.getString("ID",null)
        Log.d("SSS", "ID: $id")

        val name : String? = sharedPreferences.getString("NAME",null)
        Log.d("SSS", "Doctor: $name")

        val email : String? = sharedPreferences.getString("EMAIL",null)
        Log.d("SSS", "Doctor: $email")

        val phone : String? = sharedPreferences.getString("PHONE",null)
        Log.d("SSS", "Doctor: $phone")

        val doctor : String? = sharedPreferences.getString("DOCTOR",null)
        Log.d("SSS", "Doctor: $doctor")

        val approve : String? = sharedPreferences.getString("APPROVE",null)
        Log.d("SSS", "Approve: $approve")

        tvName.text = name
        tvEmail.text = email
    }

    private fun initialization(){
        //************ Toolbar ****************//
        toolbar = findViewById(R.id.toolbar)

        //************ Drawer ****************//
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        //*********** Text View **************//
        tvName  = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)

        globalVeriable = this.applicationContext as GlobalVariable


    }

    override fun onBackPressed() {
        super.onBackPressed()
        var i : Intent
        i = Intent(this,Home::class.java)
        startActivity(i)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId
        var i : Intent

        when(id){
            R.id.account ->{
                i = Intent(this,Profile::class.java)
                startActivity(i)
                finish()
            }

            R.id.help ->{
                Toast.makeText(this,"Coming Soon", Toast.LENGTH_SHORT).show()

            }

            R.id.about ->{
                i = Intent(this,About::class.java)
                startActivity(i)
                finish()
            }

            R.id.home ->{
                i = Intent(this,Home::class.java)
                startActivity(i)
                finish()
            }

            R.id.logout ->{
                saveSharedData("0","","","","","","0")

                PreferenceUtility.instance.setIsLogin(this,"0")

                i = Intent(this,Login::class.java)
                startActivity(i)
                finish()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun saveSharedData(
        id: String,
        name: String,
        email: String,
        phone: String,
        doctor: String,
        approve: String,
        isLogin: String
    ) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putString("ID", id)
            putString("NAME", name)
            putString("EMAIL", email)
            putString("PHONE", phone)
            putString("DOCTOR", doctor)
            putString("APPROVE", approve)
            putString("ISLOGIN",isLogin)
        }.apply()
    }
}