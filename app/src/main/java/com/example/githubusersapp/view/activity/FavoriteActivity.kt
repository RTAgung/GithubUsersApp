package com.example.githubusersapp.view.activity

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapp.R
import com.example.githubusersapp.adapter.ListAdapter
import com.example.githubusersapp.data.database.DatabaseContract.FavoriteColumn.Companion.CONTENT_URI
import com.example.githubusersapp.helper.MappingHelper
import com.example.githubusersapp.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val titleActionBar = getString(R.string.title_action_bar_favorite)
        supportActionBar?.title = titleActionBar

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadData()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, observer)

        listAdapter = ListAdapter()
        listAdapter.notifyDataSetChanged()
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detailIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_PARCEL_DATA, data)
                startActivity(detailIntent)
                finish()
            }
        })

        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings){
            val settingsIntent = Intent(this@FavoriteActivity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                cursor?.let { MappingHelper.mapCursorToArrayList(it) }
            }
            val users = deferred.await()
            Log.d("DATA", "users: " + users.toString())
            users?.let {
                if (it.size > 0) {
                    listAdapter.setData(it)
                } else {
                    listAdapter.setData(ArrayList())
                    showSnackbarMessage(getString(R.string.snackbar_no_data))
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_favorite, message, Snackbar.LENGTH_INDEFINITE).show()
    }
}