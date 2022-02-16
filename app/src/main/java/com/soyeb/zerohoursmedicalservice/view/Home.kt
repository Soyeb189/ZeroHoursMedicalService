package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatEditText
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
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.data_model.PostRequestWithoutImageM
import com.soyeb.zerohoursmedicalservice.data_model.PostResponseM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.view_model.PostListViewM
import com.soyeb.zerohoursmedicalservice.view_model.PostWithoutImageVM

class Home : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener ,PostListAdapter.OnItemClick{

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        initialization()

        getSharedData()

        Log.d("SSS","ID"+globalVariable.id)
        Log.d("SSS","ID: "+PreferenceUtility.instance.getUserId(this))


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

        btnPost.setOnClickListener{
            postPost()
        }

        getPostList()
        getPostListObserver()
        observePostWithoutImage()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.message){
            var intent = Intent(this,UserList::class.java)
            startActivity(intent)
            finish()

        }

        return super.onOptionsItemSelected(item)
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

    private fun postPost() {

        pDialog.show()

        var model  = PostRequestWithoutImageM()

        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        val id : String? = sharedPreferences.getString("ID",null)
        Log.d("SSS", "ID: $id")

        model.title = "1"
        model.description = ""+edtEditPost.text.toString()
        model.user_id = ""+id

        this.let { it1 -> postWithoutImageVM.doPost(model,it1) }
    }


    fun observePostWithoutImage(){
        postWithoutImageVM.message_info.observe(this, androidx.lifecycle.Observer {

            it?.let {

                val model = PostResponseM(
                    it.title,
                    it.user_id,
                    it.description,
                    it.updated_at,
                    it.created_at,
                    it.id,
                    it.error,
                    it.message,
                )

                getPostList()
            }
        })
    }

    private fun getPostList() {

        pDialog.show()

        this.let { it1 -> postListViewModel.doPostRequest(it1) }
    }

    private fun getPostListObserver() {

        postListViewModel.add_opt_res.observe(
            this,
            androidx.lifecycle.Observer {
                it?.let {
                    pDialog.dismiss()
                    edtEditPost.setText("")
                    try {
                        postList.clear()
                    } catch (e: Exception) {
                    }
                    postList = ArrayList<PostListResponseModel.PostListResponseModelItem>()
                    for (i in it.indices) {
                        val model = PostListResponseModel.PostListResponseModelItem(
                            it[i].createdAt,
                            it[i].description,
                            it[i].email,
                            it[i].phone_no,
                            it[i].error,
                            it[i].id,
                            it[i].user_id,
                            it[i].image,
                            it[i].name,
                            it[i].title,
                            it[i].userImage
                        )
                        postList.add(model)
                        Log.e("Size",postList.size.toString())
                    }

                    val recyclerView: RecyclerView = findViewById(R.id.rvPostList)
                    recyclerView.setHasFixedSize(true)
                   /* recyclerView.setItemViewCacheSize(20)
                    recyclerView.isDrawingCacheEnabled = true
                    recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH*/

                    val linearLayoutManager = LinearLayoutManager(this)
                    recyclerView.layoutManager = linearLayoutManager

                    adapter = PostListAdapter(postList,this)

                    adapter.setClickListener(this)

                    recyclerView.adapter = adapter

                }

            })
    }

    //***************** Adapter Class start here *********************//

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
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()

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

    override fun onPostClick(postItem: PostListResponseModel.PostListResponseModelItem) {
        Log.d("SSS",postItem.phone_no)
        Log.d("SSS",postItem.user_id)
        val intent = Intent(this,Messaging::class.java)
        intent.putExtra("ID",postItem.user_id)
        intent.putExtra("Name",postItem.name)
        startActivity(intent)
    }

    override fun onPostCallClick(postItem: PostListResponseModel.PostListResponseModelItem) {
        Log.d("SSS",postItem.phone_no)

        val phoneNumber = postItem.phone_no // call center number goes here
        val dial = Intent()
        dial.action = Intent.ACTION_DIAL
        try {
            dial.data = Uri.parse("tel:$phoneNumber")
            startActivity(dial)
        } catch (e: Exception) {
            // Log.e("Calling", "" + e.getMessage());
        }
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

        if (doctor.equals("1")){
            var heading = findViewById<TextView>(R.id.heading)
            edtEditPost.visibility = View.GONE
            btnPost.visibility = View.GONE
            heading.visibility = View.GONE
        }else{
            var heading = findViewById<TextView>(R.id.heading)
            edtEditPost.visibility = View.VISIBLE
            btnPost.visibility = View.VISIBLE
            heading.visibility = View.VISIBLE
        }
    }

}