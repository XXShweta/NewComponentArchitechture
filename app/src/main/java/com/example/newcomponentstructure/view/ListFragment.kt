package com.example.newcomponentstructure.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newcomponentstructure.R
import com.example.newcomponentstructure.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListFragmentViewModel
    private val dogListAdapter: DogListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListFragmentViewModel::class.java)
        dogListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }
        viewModel.refresh()
        observingLivedata()
        swipeRfreshLayout.setOnRefreshListener {
            errorText.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            swipeRfreshLayout.isRefreshing = false
        }
    }

    private fun observingLivedata(){
        viewModel.dogList.observe(this, Observer { it->
            it.let {
                dogListAdapter.updateDogList(it)
            }
        })

        viewModel.isLoading.observe(this, Observer { it->
            it.let {
                if(it){
                    loadingView.visibility = View.VISIBLE
                }else{
                    loadingView.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.apiError.observe(this, Observer { it->
            it.let {
                if(it){
                    errorText.visibility = View.VISIBLE
                }else{
                    errorText.visibility = View.INVISIBLE
                }
            }
        })
    }


    private fun goToDetail(it: View) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogId =100
        Navigation.findNavController(it).navigate(action)
        //findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }

}
