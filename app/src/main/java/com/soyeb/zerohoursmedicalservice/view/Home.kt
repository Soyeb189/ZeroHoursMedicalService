package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.adapter.PostListAdapter
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVeriable
import com.soyeb.zerohoursmedicalservice.view_model.PostListViewM
import de.hdodenhof.circleimageview.CircleImageView
import java.security.AccessController.getContext

class Home : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    //*********** Toolbar ************//
    private lateinit var toolbar: Toolbar

    //*********** Sweet Alert *********//
    private lateinit var pDialog: SweetAlertDialog

    //*********** Drawer *************//
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var globalVeriable: GlobalVeriable

    private lateinit var postListViewModel : PostListViewM
    private lateinit var adapter : PostListAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //*********** Menu List *************//
    private lateinit var postList : ArrayList<PostListResponseModel.PostListResponseModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        initialization()

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

        doDailyTransactionNoTransfer()
        dailyTransferTransactionListObserver()

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
        
        postList = ArrayList<PostListResponseModel.PostListResponseModelItem>()



        //globalVeriable = this.applicationContext as GlobalVeriable

    }

    private fun doDailyTransactionNoTransfer() {

        pDialog.show()

        this.let { it1 -> postListViewModel.doPostRequest(it1) }
    }

    private fun dailyTransferTransactionListObserver() {

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

                    for (i in 0 until it.size) {

                        val model = PostListResponseModel.PostListResponseModelItem(

                            it.get(i).createdAt,
                            it.get(i).description,
                            it.get(i).email,
                            it.get(i).error,
                            it.get(i).id,
                            it.get(i).image,
                            it.get(i).name,
                            it.get(i).title,
                            it.get(i).userImage


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

            /*        viewManager = LinearLayoutManager(this)
                    viewAdapter =
                        PostListAdapter(
                            postList,
                            this,
                            object :
                                PostListAdapter.OnItemClickListener {
                                override fun onItemClick(item: PostListResponseModel.PostListResponseModelItem?) {

                                }
                            })

                    recyclerView = findViewById<RecyclerView>(R.id.rvPostList).apply {
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        setHasFixedSize(true)

                        viewManager.isAutoMeasureEnabled = false;

                        // use a linear layout manager
                        layoutManager = viewManager

                        // specify an viewAdapter (see also next example)
                        adapter = viewAdapter

                    }*/
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