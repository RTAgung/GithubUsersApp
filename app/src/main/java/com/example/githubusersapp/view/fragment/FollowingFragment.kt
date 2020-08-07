package com.example.githubusersapp.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapp.R
import com.example.githubusersapp.adapter.ListAdapter
import com.example.githubusersapp.model.User
import com.example.githubusersapp.view.activity.DetailActivity
import com.example.githubusersapp.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var listAdapter: ListAdapter
    private lateinit var myViewModel: MyViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            myViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(MyViewModel::class.java)
        }
        val username = arguments?.getString(EXTRA_USERNAME)
        setData(username)
    }

    private fun setData(username: String?) {
        myViewModel.setUserFollowing(username.toString())
        loadData()
    }

    private fun loadData() {
        if (myViewModel.getError() == null) {
            if (!myViewModel.isLoading) {
                myViewModel.getUsers().observe(this, Observer { userItems ->
                    if (userItems != null) {
                        listAdapter.setData(userItems)
                    }
                })
            } else {
                Handler().postDelayed({
                    loadData()
                }, REQUEST_INTERVAL_TIME)
            }
        } else {
            Log.d("Error", myViewModel.getError().toString())
            Toast.makeText(context, myViewModel.getError(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = ListAdapter()
        listAdapter.notifyDataSetChanged()
        rv_user_following.setHasFixedSize(true)
        rv_user_following.layoutManager = LinearLayoutManager(context)
        rv_user_following.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detailIntent = Intent(activity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_PARCEL_DATA, data)
                startActivity(detailIntent)
                activity?.finish()
            }
        })
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val REQUEST_INTERVAL_TIME = 100L

        @JvmStatic
        fun newInstance(username: String?) = FollowingFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_USERNAME, username)
            }
        }
    }
}