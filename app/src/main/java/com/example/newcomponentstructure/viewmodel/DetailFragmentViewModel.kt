package com.example.newcomponentstructure.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newcomponentstructure.model.DogBreed
import com.example.newcomponentstructure.model.DogDatabase
import kotlinx.coroutines.launch

class DetailFragmentViewModel(application: Application): BaseViewModle(application) {

    var dog = MutableLiveData<DogBreed>()
    var uuid : Int?=null

    fun fetch(){
        launch {
            val dao = DogDatabase(getApplication()).dogDAO()
            val dogBreed1 = uuid?.let { dao.getDog(it) }
            dog.value = dogBreed1
        }
    }
}