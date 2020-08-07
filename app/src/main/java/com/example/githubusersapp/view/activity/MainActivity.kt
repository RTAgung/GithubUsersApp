package com.example.githubusersapp.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusersapp.R
import com.example.githubusersapp.adapter.GridAdapter
import com.example.githubusersapp.model.User
import com.example.githubusersapp.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var gridAdapter: GridAdapter
    private lateinit var myViewModel: MyViewModel
    private lateinit var warning_bar: View
    private lateinit var progress_bar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleActionBar = resources.getString(R.string.title_action_bar)
        supportActionBar?.title = titleActionBar
        loading_bar_layout.setBackgroundColor(Color.argb(180, 240, 240, 240))
        warning_bar = loading_bar_layout.findViewById<View>(R.id.warning_bar)
        progress_bar = loading_bar_layout.findViewById<View>(R.id.progress_bar)

        showLoading(true)

        gridAdapter = GridAdapter()
        gridAdapter.notifyDataSetChanged()
        rv_github_users.setHasFixedSize(true)
        rv_github_users.layoutManager = GridLayoutManager(this, 2)
        rv_github_users.adapter = gridAdapter

        myViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MyViewModel::class.java)

        gridAdapter.setOnItemClickCallback(object : GridAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_PARCEL_DATA, data)
                startActivity(detailIntent)
            }
        })

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setData(s.toString())
            }
        })

        if (!myViewModel.haveMainData)
            setData(null)
        else
            loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                val settingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
            R.id.menu_refresh -> {
                et_search.setText("")
                showLoading(true)
                setData(null)
            }
            R.id.menu_favorite -> {
                val favoriteIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        if (!myViewModel.isLoading) {
            if (myViewModel.getError() == null) {
                myViewModel.getUsers().observe(this, Observer { userItems ->
                    if (userItems != null) {
                        gridAdapter.setData(userItems)
                        showLoading(false)
                    }
                })
            } else {
                Log.d("Error", myViewModel.getError().toString())
                showWarning(myViewModel.getError().toString())
            }
        } else {
            Handler().postDelayed({
                loadData()
            }, REQUEST_INTERVAL_TIME)
        }
    }

    private fun setData(search: String?) {
        if (search == null) {
            myViewModel.setUsers()
        } else {
            myViewModel.setUsers(search)
        }
        loadData()
        myViewModel.haveMainData = true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            warning_bar.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE
            loading_bar_layout.visibility = View.VISIBLE
        } else
            loading_bar_layout.visibility = View.GONE
    }

    private fun showWarning(message: String) {
        progress_bar.visibility = View.GONE
        warning_bar.visibility = View.VISIBLE
        val warning = warning_bar.findViewById<TextView>(R.id.text_error_message)
        warning.text = message
    }

    companion object {
        const val REQUEST_INTERVAL_TIME = 100L
    }
}