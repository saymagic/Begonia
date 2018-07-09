package cn.saymagic.begonia.ui.activity

import android.Manifest
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import cn.saymagic.begonia.R
import cn.saymagic.begonia.fundamental.into
import cn.saymagic.begonia.repository.Status
import cn.saymagic.begonia.util.Constants
import cn.saymagic.begonia.viewmodel.PhotoListViewModel
import cn.saymagic.begonia.viewmodel.ViewPhotoModel
import kotlinx.android.synthetic.main.activity_view_photo.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager


class ViewPhotoActivity : AppCompatActivity() {


    lateinit var photoModel: ViewPhotoModel

    lateinit var menu: Menu

    private val REQUEST_EXTERNAL_STORAGE = 1

    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_photo)
        val photoId = intent.getStringExtra(Constants.PHOTO_ID)
        if (photoId == null) {
            finish()
            return
        }
        photoModel = ViewModelProviders.of(this).get(ViewPhotoModel::class.java)
        photoModel.photo.observe(this, Observer {
            photoImg.setImageBitmap(it)
            val inflater = menuInflater
            inflater.inflate(R.menu.view_photo_menu, menu)
            menu.findItem(R.id.set_as_desktop)?.setOnMenuItemClickListener {
                photoModel.setWallpaper()
                true
            }
            menu.findItem(R.id.save_to_system)?.setOnMenuItemClickListener {
                val permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
                } else {
                    photoModel.saveToPhotoAlbum()
                }
                true
            }
        })

        photoModel.networkState.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> viewPhotoProgressLayout.showError(R.drawable.ic_error_black_24dp, getString(R.string.error_title), it?.msg?.message, getString(R.string.constant_retry), {
                    photoModel.rerty?.invoke()
                })
                Status.RUNNING -> viewPhotoProgressLayout.showLoading()
                Status.SUCCESS -> viewPhotoProgressLayout.showContent()
            }
        })

        photoModel.toastTip.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        photoModel.getPhoto(photoId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            photoModel.saveToPhotoAlbum()
        }
    }


}
