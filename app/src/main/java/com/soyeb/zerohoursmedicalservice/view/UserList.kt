package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.google.android.material.navigation.NavigationView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.adapter.PostListAdapter
import com.soyeb.zerohoursmedicalservice.adapter.UserListAdapter
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.data_model.UserListRequestM
import com.soyeb.zerohoursmedicalservice.data_model.UserListResponseModel
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.view_model.PostListViewM
import com.soyeb.zerohoursmedicalservice.view_model.UserListViewM

class UserList : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    UserListAdapter.OnItemClick {

    //*********** Toolbar ************//
    private lateinit var toolbar: Toolbar

    //*********** Drawer *************//
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var userListViewM: UserListViewM

    //*********** Sweet Alert *********//
    private lateinit var pDialog: SweetAlertDialog


    private lateinit var globalVeriable: GlobalVariable

    private lateinit var userList : ArrayList<UserListResponseModel.UserListResponseModelItem>

    private lateinit var adapter : UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_drawer)

        initialization()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "মেসেজ সমূহ"


        //************ For Drawer *****************///
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        getUserList()
        getUserListObserver()
    }

    private fun initialization() {

        //************ Alert Dialog **********//
        pDialog = Custom_alert.showProgressDialog(this)

        //************ Toolbar ****************//
        toolbar = findViewById(R.id.toolbar)

        userListViewM = ViewModelProvider(this).get(UserListViewM::class.java)

        //************ Drawer ****************//
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        globalVeriable = this.applicationContext as GlobalVariable

        userList = ArrayList()


    }



    private fun getUserList() {

        pDialog.show()

        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        val id : String? = sharedPreferences.getString("ID",null)
        Log.d("SSS", "ID: $id")

        var model = UserListRequestM()

        model.user_id = ""+id


        this.let { it1 -> userListViewM.getUserList(model,it1) }
    }

    private fun getUserListObserver() {

        userListViewM.userList.observe(
            this,
            androidx.lifecycle.Observer {
                it?.let {
                    pDialog.dismiss()
                    try {
                        userList.clear()
                    } catch (e: Exception) {
                    }
                    userList = ArrayList<UserListResponseModel.UserListResponseModelItem>()
                    for (i in it.indices) {
                        val model = UserListResponseModel.UserListResponseModelItem(
                            it[i].id,
                            it[i].image,
                            it[i].name
                        )
                        userList.add(model)
                        Log.e("Size",userList.size.toString())
                    }

                    val recyclerView: RecyclerView = findViewById(R.id.rvPostList)
                    recyclerView.setHasFixedSize(true)
                    /* recyclerView.setItemViewCacheSize(20)
                     recyclerView.isDrawingCacheEnabled = true
                     recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH*/

                    val linearLayoutManager = LinearLayoutManager(this)
                    recyclerView.layoutManager = linearLayoutManager

                    adapter = UserListAdapter(userList,this)

                    adapter.setClickListener(this)

                    recyclerView.adapter = adapter

                }

            })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        var i : Intent
        i = Intent(this,Home::class.java)
        startActivity(i)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        var i: Intent

        when (id) {
            R.id.account -> {
                i = Intent(this, Profile::class.java)
                startActivity(i)
                finish()
            }

            R.id.help -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()

//                i = Intent(this,Messaging::class.java)
//                startActivity(i)
            }

            R.id.about -> {
                i = Intent(this, About::class.java)
                startActivity(i)
                finish()
            }

            R.id.home -> {
                i = Intent(this, Home::class.java)
                startActivity(i)
                finish()
            }

            R.id.logout -> {
                saveSharedData("0","","","","","","0")

                PreferenceUtility.instance.setIsLogin(this, "0")

                i = Intent(this, Login::class.java)
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

    override fun onPostClick(postItem: UserListResponseModel.UserListResponseModelItem) {
        val intent = Intent(this,Messaging::class.java)
        intent.putExtra("ID",postItem.id)
        intent.putExtra("Name",postItem.name)
        startActivity(intent)
    }
}