package com.mrk.example.compose.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.preferredSize
import androidx.ui.unit.Dp
import com.squareup.picasso.Picasso

@Composable
fun ImageNetwork(url: String?, width: Dp, height: Dp) {
    val image = state<Bitmap?> { null }

    onCommit(url) {
        val picasso = Picasso.get()
        val target = object : com.squareup.picasso.Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image.value = bitmap
            }
        }
        picasso
            .load(url)
            .into(target)

        onDispose {
            image.value = null
            picasso.cancelRequest(target)
        }
    }

    Box(modifier = Modifier.preferredSize(width, height)) {
        if (image.value != null) {
            Image(image.value!!.asImageAsset())
        }
    }
}
