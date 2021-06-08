# MusicFlow
mondia Company Task

# Features
App Built with :
* MVVM Design Architecture
* RX Java
* Kotlin language
* Object oriented programming
* Simple clean Code

The android app lets you:
* View songs,atrists and publishing dates

# Screenshots
SplashScreen              |  MainActivity         |  DetailedActivity
:-------------------------:|:-------------------------: |:-------------------------:
![](https://github.com/ahmedhassan2017/MusicFlow/blob/master/app/src/main/res/drawable-v24/screen1.jpeg)  |  ![](https://github.com/ahmedhassan2017/MusicFlow/blob/master/app/src/main/res/drawable-v24/screen2.jpeg)  |  ![](https://github.com/ahmedhassan2017/MusicFlow/blob/master/app/src/main/res/drawable-v24/screen3.jpeg)

# Permissions
* Network Access state.
* internet permission.

# Code Example
 Code snippet checks the internet connection
 ```
 fun InternetConnected(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    ) {
        //we are connected to a network
        true
    } else {
        false
    }
}
```
 Code snippet of getToken fun that gets the access token using Observation (RXjava)
 ```
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
```

libiraries and dependancies that used
```
// Retrofit
implementation "com.squareup.retrofit2:retrofit:2.6.2"
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
// LiveData
implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
//recyclerview
implementation 'com.android.support:recyclerview-v7:30.0.0'
//cardview
implementation 'com.android.support:cardview-v7:30.0.0'
implementation 'de.hdodenhof:circleimageview:3.1.0'
// picasso
implementation 'com.squareup.picasso:picasso:2.71828'

// RxJava
implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
implementation 'io.reactivex.rxjava3:rxjava:3.0.0'
implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'

```
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.


