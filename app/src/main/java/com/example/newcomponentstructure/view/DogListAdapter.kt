package com.example.newcomponentstructure.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.newcomponentstructure.R
import com.example.newcomponentstructure.databinding.ItemDogBinding
import com.example.newcomponentstructure.model.DogBreed
import com.example.newcomponentstructure.utils.getProgressCircle
import com.example.newcomponentstructure.utils.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter (val dogList: ArrayList<DogBreed>):RecyclerView.Adapter<DogListAdapter.DogListViewHolder> (), DogClickListener{

    fun updateDogList(newDogList: ArrayList<DogBreed>){
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.item_dog,parent,false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater,R.layout.item_dog, parent, false)
        return DogListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onBindViewHolder(holder: DogListViewHolder, position: Int) {
        holder.view.dog = dogList[position]
        holder.view.listener = this
       /* holder.view.title.text = dogList[position].dogBreed
        holder.view.description.text = dogList[position].lifeSpan
        holder.view.imageView.loadImage(dogList[position].imageURL, getProgressCircle(holder.view.imageView.context))
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            action.dogId = dogList[position].uuid!!.toInt()
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    override fun onDogClick(view: View) {
        val uuid = view.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogId = uuid
        Navigation.findNavController(view).navigate(action)
    }


    inner class DogListViewHolder(var view:ItemDogBinding): RecyclerView.ViewHolder(view.root){

    }
}