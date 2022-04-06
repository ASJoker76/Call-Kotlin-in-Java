package com.app.test.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.test.connection.RetroInstance
import com.app.test.connection.RetroService
import com.app.test.connection.RxService
import com.app.test.model.Evolution
import com.app.test.model.detailRecylerlist
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    lateinit var pokemonLiveData: MutableLiveData<detailRecylerlist>
    lateinit var evolutionLiveData: MutableLiveData<Evolution>

    init {
        pokemonLiveData = MutableLiveData<detailRecylerlist>()
        evolutionLiveData = MutableLiveData<Evolution>()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<detailRecylerlist>? {
        return pokemonLiveData
    }

    fun getRecycler2ListDataObserver(): MutableLiveData<Evolution>? {
        return evolutionLiveData
    }

    fun panggildetailapi(nama : String) {

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RxService.buildService().getDetailPokemon(nama)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> onResponse(response)}, {t -> onFailure(t) }
                )
        )
    }

    private fun onFailure(t: Throwable) {
        pokemonLiveData?.postValue(null)
    }

    private fun onResponse(response: detailRecylerlist) {
        pokemonLiveData?.postValue(response)
    }

    fun panggilevolution(id : Int) {

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RxService.buildService().getEvolutionPokemon(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> onResponse(response)}, {t -> onFailure2(t) }
                )
        )
    }

    private fun onFailure2(t: Throwable) {
        evolutionLiveData?.postValue(null)
    }

    private fun onResponse(response: Evolution) {
        evolutionLiveData?.postValue(response)
    }
}