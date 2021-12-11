package com.soyeb.zerohoursmedicalservice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
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

class Home : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

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

        model.title = "1"
        model.description = ""+edtEditPost.text.toString()
        model.user_id = ""+PreferenceUtility.instance.getUserId(this)

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
                            it[i].error,
                            it[i].id,
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
                    recyclerView.setItemViewCacheSize(20)
                    recyclerView.isDrawingCacheEnabled = true
                    recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

                    val linearLayoutManager = LinearLayoutManager(this)
                    recyclerView.layoutManager = linearLayoutManager

                    adapter = PostListAdapter(postList,this)

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
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()

//                i = Intent(this,Profile::class.java)
//                startActivity(i)
            }

            R.id.help ->{
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()

//                i = Intent(this,Messaging::class.java)
//                startActivity(i)
            }

            R.id.about ->{
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()
//                i = Intent(this,About::class.java)
//                startActivity(i)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}