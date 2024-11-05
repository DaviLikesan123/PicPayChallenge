package com.picpay.desafio.android.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.recyclerview.UserListAdapter
import com.picpay.desafio.android.viewmodel.ResponseViewModel


class MainActivity : AppCompatActivity(R.layout.activity_main) {

        private lateinit var viewModel: ResponseViewModel

        private lateinit var recyclerView: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var adapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ResponseViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        observeViewModel()

        viewModel.fetchUsers()
    }

    private fun observeViewModel() {
        viewModel.users.observe(this, Observer { users ->
            users?.let {
                adapter.users = it
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
    }

