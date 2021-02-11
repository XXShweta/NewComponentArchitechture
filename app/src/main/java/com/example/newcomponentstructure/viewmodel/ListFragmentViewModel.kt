package com.example.newcomponentstructure.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newcomponentstructure.model.DogBreed
import com.example.newcomponentstructure.model.DogDatabase
import com.example.newcomponentstructure.model.Service
import com.example.newcomponentstructure.utils.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListFragmentViewModel(application: Application): BaseViewModle(application) {

    private val service = Service()
    private val disposable = CompositeDisposable()
    private val shareHelper = SharedPreferenceHelper(getApplication())
    private val refreshTime = 5*60*1000*1000*1000L

    var dogList = MutableLiveData<ArrayList<DogBreed>>()
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Boolean>()

    fun refresh(){
        val updateTime = shareHelper.getUpdateTime()
        if(updateTime!=null && updateTime != 0L && System.nanoTime()-updateTime<refreshTime){
            fetchFromDB()
        }else {
            fetchDataFromApi()
        }
    }

    private fun fetchFromDB(){
        isLoading.value = true
        launch {
            val dao = DogDatabase(getApplication()).dogDAO()
            val result =dao.getAllDogs()
            gotdataFromAPI(result as ArrayList<DogBreed>)
            Toast.makeText(getApplication(),"database",Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDataFromApi(){
        isLoading.value = true
        disposable.add(
            service.getDog()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<DogBreed>>(){
                    override fun onSuccess(listDog: ArrayList<DogBreed>) {
                        storeDataToDB(listDog)
                    }
                    override fun onError(e: Throwable) {
                        apiError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                    }
                })
        )

    }

    private fun gotdataFromAPI(listDog: ArrayList<DogBreed>){
        dogList.value = listDog
        isLoading.value = false
        apiError.value = false
    }

    private fun storeDataToDB(listDog: ArrayList<DogBreed>){
        launch {
            val dao = DogDatabase(getApplication()).dogDAO()
            dao.deleteAllDogs()
            val result = dao.insertAll(*listDog.toTypedArray())
            var i=0
            while (i<listDog.size){
                listDog[i].uuid = result[i].toInt()
                ++i
            }
            gotdataFromAPI(listDog)
            Toast.makeText(getApplication(),"api",Toast.LENGTH_SHORT).show()
        }

        shareHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}