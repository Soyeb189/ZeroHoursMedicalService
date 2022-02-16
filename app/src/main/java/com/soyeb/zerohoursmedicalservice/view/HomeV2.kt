package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.google.android.material.navigation.NavigationView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.adapter.PostListAdapter
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.data_model.UserDetails
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.view_model.PostListViewM
import com.soyeb.zerohoursmedicalservice.view_model.PostWithoutImageVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect

class HomeV2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //*********** Toolbar ************//
    private lateinit var toolbar: Toolbar

    //*********** Sweet Alert *********//
    private lateinit var pDialog: SweetAlertDialog

    //*********** Drawer *************//
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var globalVariable: GlobalVariable

    private lateinit var postListViewModel : PostListViewM
    private lateinit var postWithoutImageVM : PostWithoutImageVM

    private lateinit var adapter : PostListAdapter

    //************ button ************//
    private lateinit var btnPost : Button

    //************ Edit Text *************//
    private lateinit var edtEditPost : AppCompatEditText

    //*********** Menu List *************//
    private lateinit var postList : ArrayList<PostListResponseModel.PostListResponseModelItem>


    lateinit var userDetails: UserDetails


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        userDetails = UserDetails(this)

        initialization()

        getSharedData()



        setSupportActionBar(toolbar)
        supportActionBar?.title = "Zero Hour Medical Service"

        //************ For Drawer *****************///
        toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
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
    }

    private fun initialization() {
        //************ Toolbar ****************//
        toolbar = findViewById(R.id.toolbar)

        //************ Alert Dialog **********//
        pDialog = Custom_alert.showProgressDialog(this)

        //************ Drawer ****************//
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        postListViewModel = ViewModelProvider(this).get(PostListViewM::class.java)
        postWithoutImageVM = ViewModelProvider(this).get(PostWithoutImageVM::class.java)

        postList = ArrayList()

        //******** button ***************//
        btnPost = findViewById(R.id.btnPost)
        edtEditPost = findViewById(R.id.edtEditPost)



        globalVariable = this.applicationContext as GlobalVariable

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

//                i = Intent(this,Messaging::class.java)
//                startActivity(i)
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
                PreferenceUtility.instance.setUserId(this,"")
                PreferenceUtility.instance.setUserName(this,"")
                PreferenceUtility.instance.setUserEmail(this,"")
                PreferenceUtility.instance.setDoctor(this,"")
                PreferenceUtility.instance.setApprove(this,"")

                PreferenceUtility.instance.setIsLogin(this,"0")

                i = Intent(this,Login::class.java)
                startActivity(i)
                finish()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}