package com.example.musicflow.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicflow.data.MusicClient
import com.example.musicflow.pojo.MusicDataModel
import com.example.musicflow.pojo.TokenModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MusicViewModel : ViewModel() {
    var listMutableLiveData = MutableLiveData<List<MusicDataModel?>>()

    fun getMusic(query: String?, token: String?) {
        val observable: @NonNull Observable<List<MusicDataModel>?>? =
            MusicClient.iNSTANCE?.getMusic(query, token)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
        val observer: Observer<List<MusicDataModel>?> =
            object : Observer<List<MusicDataModel>?> {
                override fun onSubscribe(d: @NonNull Disposable?) {}
                override fun onNext(@NonNull t: @NonNull List<MusicDataModel>?) {
                    listMutableLiveData.setValue(t)
                }

                override fun onError(e: @NonNull Throwable?) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {}
            }
        if(observable!=null)
        observable.subscribe(observer)
    }

    var tokenMutableLiveData = MutableLiveData<TokenModel?>()
    fun getToken (){
            val observable: @NonNull Observable<TokenModel?>? =
                MusicClient.iNSTANCE?.getToken()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
            val observer: Observer<TokenModel?> =
                object : Observer<TokenModel?> {
                    override fun onSubscribe(d: @NonNull Disposable?) {}
                    override fun onNext(articlesModels: @NonNull TokenModel?) {
                        tokenMutableLiveData.setValue(articlesModels)
                    }

                    override fun onError(e: @NonNull Throwable?) {
                        Log.d(TAG, "onError: $e")
                    }

                    override fun onComplete() {}
                }
            if (observable != null) {
                observable.subscribe(observer)
            }
        }

    companion object {
        private const val TAG = "ArticleViewModel"
    }
}



