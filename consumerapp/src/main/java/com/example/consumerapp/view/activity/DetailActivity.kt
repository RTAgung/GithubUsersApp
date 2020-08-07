package com.example.consumerapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.consumerapp.R
import com.example.consumerapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var titleActionBar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra(EXTRA_PARCEL_DATA) as User

        titleActionBar = user.login.toString()
        supportActionBar?.title = titleActionBar
        supportActionBar?.elevation = 0F

        loadView(user)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_language -> {
                val settingsIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(settingsIntent)
            }
        }
        return super.onOptionsItemSelected(item)
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
        tv_followers.text = resources.getString(R.string.followers, user.followers)
        tv_following.text = resources.getString(R.string.following, user.following)
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
    }

    companion object {
        const val EXTRA_PARCEL_DATA = "extra_parcel_data"
    }
}