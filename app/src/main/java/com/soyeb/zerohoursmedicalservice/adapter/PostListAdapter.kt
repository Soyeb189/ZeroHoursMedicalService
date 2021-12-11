package com.soyeb.zerohoursmedicalservice.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.github.chrisbanes.photoview.PhotoView
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
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

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var profileImage: CircleImageView
        var name: TextView
        var postDate: TextView
        var description : TextView
        var postImage : PhotoView
        var messageButton : Button
        var footerV : View
        var footer : LinearLayout
        init {
            profileImage = itemView.findViewById(R.id.profileImage)
            name = itemView.findViewById(R.id.tvPosterName)
            postDate = itemView.findViewById(R.id.tvPostDate)
            description = itemView.findViewById(R.id.tvPostDetails)
            postImage = itemView.findViewById(R.id.postImage)
            messageButton = itemView.findViewById(R.id.messageButton)
            footerV = itemView.findViewById(R.id.footerV)
            footer = itemView.findViewById(R.id.footer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postList = postList[position]

        var i = 2
        var doctor = PreferenceUtility.instance.getDoctor(context)

        val dtStart = postList.createdAt
        Log.d("date",postList.createdAt)
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

        if (doctor=="0"){
            holder.messageButton.visibility = View.GONE
            holder.footer.visibility = View.GONE
            holder.footerV.visibility = View.GONE
        }else{
            holder.messageButton.visibility = View.GONE
            holder.footer.visibility = View.VISIBLE
            holder.footerV.visibility = View.GONE
        }



    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface OnItemClick{
        fun onPostClick(postItem : PostListResponseModel)
    }
}