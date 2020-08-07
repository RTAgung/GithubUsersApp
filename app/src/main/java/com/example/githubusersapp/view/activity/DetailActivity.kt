package com.example.githubusersapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersapp.R
import com.example.githubusersapp.adapter.SectionsPagerAdapter
import com.example.githubusersapp.data.database.FavoriteDatabase
import com.example.githubusersapp.data.database.UserFavoriteDao
import com.example.githubusersapp.model.User
import com.example.githubusersapp.viewmodel.MyViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.loading_bar_layout

class DetailActivity : AppCompatActivity() {

    private lateinit var titleActionBar: String
    private lateinit var myViewModel: MyViewModel
    private lateinit var userFavoriteDao: UserFavoriteDao
    private lateinit var menu: Menu
    private lateinit var user: User
    private lateinit var warning_bar: View
    private lateinit var progress_bar: View
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        userFavoriteDao = FavoriteDatabase.init(this).userFavoriteDao()
        user = intent.getParcelableExtra(EXTRA_PARCEL_DATA) as User

        titleActionBar = user.login.toString()
        supportActionBar?.title = titleActionBar
        supportActionBar?.elevation = 0F
        warning_bar = loading_bar_layout.findViewById<View>(R.id.warning_bar)
        progress_bar = loading_bar_layout.findViewById<View>(R.id.progress_bar)

        isFavorite = userFavoriteDao.exists(user.id)
        showLoading(true)

        myViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MyViewModel::class.java)

        setData(user.login.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        if (menu != null) this.menu = menu
        changeFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                val settingsIntent = Intent(this@DetailActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
            R.id.menu_favorite -> {
                onClickFavorite()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickFavorite() {
        if (isFavorite) {
            userFavoriteDao.delete(user)
            Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show()
        } else {
            userFavoriteDao.insert(user)
            Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
        }
        isFavorite = !isFavorite
        changeFavorite()
    }

    private fun changeFavorite() {
        if (isFavorite)
            menu.findItem(R.id.menu_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        else
            menu.findItem(R.id.menu_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
    }

    private fun setData(username: String) {
        myViewModel.setUserDetail(username)
        loadData()
    }

    private fun loadData() {
        if (!myViewModel.isLoading) {
            if (myViewModel.getError() == null) {
                user = myViewModel.getUserDetail()
                loadView(user)
                showLoading(false)
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

    private fun loadView(user: User) {
        Picasso.get()
            .load(user.avatar_url)
            .placeholder(R.drawable.ic_baseline_person_24)
            .error(R.drawable.ic_baseline_person_24)
            .into(iv_avatar)
        tv_name.text = user.name
        tv_login.text = user.login
        tv_repos.text = resources.getString(R.string.user_repos, user.public_repos)
        tv_repos.visibility = View.VISIBLE
        if (user.company != null) {
            tv_company.visibility = View.VISIBLE
            tv_company.text = user.company
        } else tv_company.visibility = View.GONE
        if (user.location != null) {
            tv_location.visibility = View.VISIBLE
            tv_location.text = user.location
        } else tv_location.visibility = View.GONE
        if (user.bio != null) {
            tv_bio.visibility = View.VISIBLE
            tv_bio.text = user.bio
        } else tv_bio.visibility = View.GONE

        val username = user.login.toString()
        val followers = user.followers
        val following = user.following
        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager, username, followers, following)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
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
        const val EXTRA_PARCEL_DATA = "extra_parcel_data"
        const val REQUEST_INTERVAL_TIME = 100L
    }
}