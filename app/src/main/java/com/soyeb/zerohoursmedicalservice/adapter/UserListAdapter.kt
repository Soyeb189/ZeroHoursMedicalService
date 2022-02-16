package com.soyeb.zerohoursmedicalservice.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.github.chrisbanes.photoview.PhotoView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.data_model.UserListResponseModel
import com.soyeb.zerohoursmedicalservice.local.User
import com.soyeb.zerohoursmedicalservice.local.UserDataBase
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility.Companion.instance
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter(
    private val postList : ArrayList<UserListResponseModel.UserListResponseModelItem>,
    private val context: Context

) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    companion object {
        private lateinit var clickListener: OnItemClick
    }

    fun setClickListener(clickListener: OnItemClick?) {
        Companion.clickListener = clickListener!!
    }



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var profileImage: CircleImageView
        var name: TextView

        var lyt : ConstraintLayout
        init {
            profileImage = itemView.findViewById(R.id.profileImage)
            name = itemView.findViewById(R.id.tvPosterName)
            lyt = itemView.findViewById(R.id.lyt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postList = postList[position]



        val sharedPreferences : SharedPreferences = context.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

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



        holder.name.text = postList.name

        Glide.with(context)
            .load(postList.image)
            .centerCrop()                        //optional
            .into(holder.profileImage)


        holder.lyt.setOnClickListener {
            clickListener.onPostClick(postList)
        }



    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface OnItemClick{
        fun onPostClick(postItem : UserListResponseModel.UserListResponseModelItem)
    }
}