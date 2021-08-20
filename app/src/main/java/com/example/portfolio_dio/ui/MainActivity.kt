package com.example.portfolio_dio.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import com.example.portfolio_dio.R
import com.example.portfolio_dio.data.remote.GithubApi
import com.example.portfolio_dio.databinding.ActivityMainBinding
import com.example.portfolio_dio.ui.adapters.RepoAdapter
import com.example.portfolio_dio.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: AppCompatActivity(), SearchView.OnQueryTextListener {
    @Inject
    lateinit var githubApi: GithubApi
    private val mMainViewModel by viewModels<MainViewModel>()
    private val adapter by lazy { RepoAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.recyclerView.adapter = adapter

        mMainViewModel.getRepoList("edufelip")
        mMainViewModel.repoList.observe(this, {
            when(it) {
                //MainViewModel.State.Loading -> TODO()
                is MainViewModel.State.Error -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setPositiveButton("Yes") { _, _ ->

                    }
                    builder.setTitle("Erro")
                    builder.setMessage(it.error.message)
                    builder.create().show()
                }
                is MainViewModel.State.Success -> {
                    adapter.submitList(it.list)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { mMainViewModel.getRepoList(it) }
        binding.root.hideKeyboard()
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("Not yet implemented")
        //return false
    }

    companion object {
        private const val TAG = "TAG"
    }
}