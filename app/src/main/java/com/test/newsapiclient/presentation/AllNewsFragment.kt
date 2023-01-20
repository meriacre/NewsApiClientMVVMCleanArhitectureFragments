package com.test.newsapiclient.presentation

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapiclient.R
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.databinding.FragmentAllNewsBinding
import com.test.newsapiclient.presentation.adapter.NewsAdapter
import com.test.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AllNewsFragment : Fragment(R.layout.fragment_all_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentAllNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var language = "bitcoin"
    private var page = 1
    private var isLoading = false
    private var isScrolling = false
    private var isLastPage = false
    private var pages = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllNewsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        viewNewsList()
        setSearchView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_allNewsFragment_to_infoFragment, bundle)
        }

    }

    private fun viewNewsList() {
        viewModel.getAllNews(language, page)
        viewModel.allNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0){
                            pages = it.totalResults / 20
                        }else{
                            pages = it.totalResults/20+1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun initRecyclerView() {
//        newsAdapter = NewsAdapter()
        binding.rvAll.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@AllNewsFragment.onScollListener)
        }
    }

    private fun showProgressBar(){
        isLoading = true
        binding.pbMain.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.pbMain.visibility = View.GONE
    }

    private val onScollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvAll.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate){
                page++
                viewModel.getAllNews(language, page)
                isScrolling = false
            }
        }
    }

    private fun setSearchView(){
        binding.swNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews("us", query.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.searchNews("us", newText.toString(), page)
                    viewSearchedNews()
                }
                return false
            }

        })

        binding.swNews.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                initRecyclerView()
                viewNewsList()
                return false
            }

        })
    }

    private fun viewSearchedNews(){
        if (view != null){
            viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            if (it.totalResults % 20 == 0){
                                pages = it.totalResults / 20
                            }else{
                                pages = it.totalResults/20 + 1
                            }
                            isLastPage = page == pages
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                        }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }
    }
}