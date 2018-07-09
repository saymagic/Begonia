package cn.saymagic.begonia.fundamental

import android.widget.ImageView
import cn.saymagic.begonia.R
import com.squareup.picasso.Picasso


inline fun ImageView.into(url: String, circle : Boolean = false){
    if ("" != url) {
        if (circle) {
            Picasso.get().load(url).transform(CircleTransform()).placeholder(R.drawable.circle).into(this)
        } else {
            Picasso.get().load(url).placeholder(R.drawable.rectangle).into(this)
        }
    }
}

