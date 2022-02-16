package com.soyeb.zerohoursmedicalservice.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.github.chrisbanes.photoview.PhotoView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
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

class PostListAdapter(
    private val postList : ArrayList<PostListResponseModel.PostListResponseModelItem>,
    private val context: Context

) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    companion object {
        private lateinit var clickListener: OnItemClick
    }

    fun setClickListener(clickListener: OnItemClick?) {
        Companion.clickListener = clickListener!!
    }



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var profileImage: CircleImageView
        var name: TextView
        var postDate: TextView
        var description : TextView
        var postImage : PhotoView
        var messageButton : Button
        var message : Button
        var call : Button
        var footerV : View
        var footer : LinearLayout
        var edtMessage : EditText
        var sendImage : ImageView
        init {
            profileImage = itemView.findViewById(R.id.profileImage)
            name = itemView.findViewById(R.id.tvPosterName)
            postDate = itemView.findViewById(R.id.tvPostDate)
            description = itemView.findViewById(R.id.tvPostDetails)
            postImage = itemView.findViewById(R.id.postImage)
            messageButton = itemView.findViewById(R.id.messageButton)
            message = itemView.findViewById(R.id.message)
            call = itemView.findViewById(R.id.call)
            footerV = itemView.findViewById(R.id.footerV)
            footer = itemView.findViewById(R.id.footer)
            edtMessage = itemView.findViewById(R.id.edtMessage)
            sendImage = itemView.findViewById(R.id.sendImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postList = postList[position]



        val dtStart = postList.createdAt


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

        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date: Date = df.parse(dtStart)
            val format = SimpleDateFormat("yyyy-MM-dd")
            System.out.println(date)
            holder.postDate.text = date.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        holder.name.text = postList.name
        holder.description.text = postList.description

        Glide.with(context)
            .load(postList.userImage)
            .centerCrop()                        //optional
            .into(holder.profileImage)

        if (postList.image != "http://dump.shaikot.com/default-image/default.jpg"){
            Glide.with(context)
                .load(postList.image)
                .centerCrop()                        //optional
                .into(holder.postImage)

        }else{
            holder.postImage.visibility = View.GONE
        }

        if (doctor.equals("1")){
            holder.messageButton.visibility = View.GONE
            holder.footer.visibility = View.VISIBLE
            holder.footerV.visibility = View.GONE
            holder.sendImage.visibility = View.GONE
            holder.edtMessage.visibility = View.GONE
            holder.message.visibility = View.VISIBLE
            holder.call.visibility = View.VISIBLE

        }else{
            holder.messageButton.visibility = View.GONE
            holder.footer.visibility = View.GONE
            holder.footerV.visibility = View.GONE
            holder.sendImage.visibility = View.GONE
            holder.edtMessage.visibility = View.GONE
        }

        holder.profileImage.setOnClickListener {
            clickListener.onPostClick(postList)
        }

        holder.message.setOnClickListener {
            clickListener.onPostClick(postList)
        }
        holder.call.setOnClickListener {
            clickListener.onPostCallClick(postList)
        }



    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface OnItemClick{
        fun onPostClick(postItem : PostListResponseModel.PostListResponseModelItem)
        fun onPostCallClick(postItem : PostListResponseModel.PostListResponseModelItem)
    }
}