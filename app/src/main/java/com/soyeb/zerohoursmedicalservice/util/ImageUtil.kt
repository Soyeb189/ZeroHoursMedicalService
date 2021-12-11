package com.soyeb.zerohoursmedicalservice.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageUtil {

    companion object {


        fun loadUri(context: Context, imageView: ImageView, uri: Uri) {
            /*try {
                Picasso.get()
                    .load(uri)
                    .placeholder(droidninja.filepicker.R.drawable.image_placeholder)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .centerCrop()
                    .into(imageView)
            }catch (e:Exception){}*/
        }


        fun glidLoadUri(context: Context, imageView: ImageView, uri: Uri) {
            Glide.with(context)
                .load(uri)
                .apply(
                    RequestOptions.centerCropTransform()
                        .dontAnimate()
                        //.override(imageSize, imageSize)
                        .placeholder(droidninja.filepicker.R.drawable.image_placeholder)
                )
                .thumbnail(0.5f)
                .into(imageView)
        }
    }
}