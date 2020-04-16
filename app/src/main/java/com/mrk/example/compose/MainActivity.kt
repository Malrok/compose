package com.mrk.example.compose

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.navigation.NavigationStatus
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.navigation.navigateTo
import com.mrk.example.compose.viewmodels.MainViewModel
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


const val PICK_IMAGE = 5732

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                Providers(
                    ViewModelAmbient provides mainViewModel
                ) {
                    Root.Content()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (NavigationStatus.currentScreen is Root.Routing.Detail) {
            navigateTo(Root.Routing.List)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            var imageStream: InputStream? = null
            try {
                //Let's read the picked image -its URI
                val pickedImage: Uri? = data?.data

                if (pickedImage != null) {
                    imageStream = contentResolver.openInputStream(pickedImage)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                if (imageStream != null) {
                    try {
                        imageStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
