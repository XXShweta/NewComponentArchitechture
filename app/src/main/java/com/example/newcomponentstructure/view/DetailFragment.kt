package com.example.newcomponentstructure.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.newcomponentstructure.R
import com.example.newcomponentstructure.databinding.FragmentDetailBinding
import com.example.newcomponentstructure.utils.getProgressCircle
import com.example.newcomponentstructure.utils.loadImage
import com.example.newcomponentstructure.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel : DetailFragmentViewModel
    private lateinit var dataBinding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel::class.java)
        arguments?.let {
            viewModel.uuid = DetailFragmentArgs.fromBundle(it).dogId
        }
        viewModel.fetch()
        observingLiveData()

    }

    private fun observingLiveData(){
        viewModel.dog.observe(this, Observer { it->
            it.let {
                dataBinding.dog = it
                /*dogname.text = it.dogBreed
                description.text = it.lifeSpan
                imageView.loadImage(it.imageURL, getProgressCircle(imageView.context))*/
            }
        })
    }

    private fun goToList(it:View){
        val action = DetailFragmentDirections.actionDetailFragmentToListFragment()
            Navigation.findNavController(it).navigate(action)
        //findNavController().navigateUp()
    }


}
