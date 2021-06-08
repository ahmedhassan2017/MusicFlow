package com.example.musicflow.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicflow.R
import com.example.musicflow.pojo.MusicDataModel
import com.example.musicflow.pojo.TokenModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import java.util.*


class MainActivity : AppCompatActivity(), OnItemListener {
    var progressBar: ProgressBar? = null
    var checkConnection: LinearLayout? = null
    var searchButton: ImageView? = null
    var searchET: EditText? = null
    var musicViewModel: MusicViewModel? = null
    var musicModelArrayList: ArrayList<MusicDataModel?> =
        ArrayList<MusicDataModel?>()
    var adapter: MusicAdapter? = null
    var appBarLayout: AppBarLayout? = null
    var accessToken: String? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar_cyclic)
        searchButton = findViewById(R.id.search_btn)
        searchET = findViewById(R.id.search_edit_text)
        appBarLayout = findViewById(R.id.appbar)
        checkConnection = findViewById(R.id.linear1)
        recyclerView = findViewById(R.id.recycler)

        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        musicViewModel!!.getToken()       // ask for token

        musicViewModel!!.tokenMutableLiveData.observe(
            this,
            Observer<TokenModel?> { tokenModel ->
                accessToken = "Bearer " + tokenModel?.accessToken
                musicViewModel!!.getMusic("All", accessToken)
            })

        searchButton?.setOnClickListener(View.OnClickListener {
            if (accessToken != null) musicModelArrayList.clear()
            musicViewModel!!.getMusic(searchET?.getText().toString(), accessToken)
        })

        adapter = MusicAdapter(this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        musicViewModel!!.listMutableLiveData.observe(
            this,
            object : Observer<List<MusicDataModel?>> {


                override fun onChanged(t: List<MusicDataModel?>) {
                    musicModelArrayList.addAll(t)
                    adapter!!.setList(t as List<MusicDataModel>)
                    progressBar?.setVisibility(View.GONE)
                    recyclerView?.visibility = View.VISIBLE
                }
            })
    }

    override fun onItemClicked(position: Int) {
        val intent = Intent(this@MainActivity, DetailedActivity::class.java)


        if (musicModelArrayList[position]?.cover == null) {
            intent.putExtra("image", "no image")
        } else {
            intent.putExtra("image", musicModelArrayList[position]?.cover?.large)
        }
        if (musicModelArrayList[position]?.mainArtist != null) intent.putExtra(
            "artist",
            musicModelArrayList[position]!!.mainArtist?.name
        ) else intent.putExtra("artist", "Error fetching data")
        if (musicModelArrayList[position]?.publishingDate != null) intent.putExtra(
            "date",
            musicModelArrayList[position]?.publishingDate
        ) else intent.putExtra("date", "Error fetching data")

        if (musicModelArrayList[position]!!.title != null) intent.putExtra(
            "title",
            musicModelArrayList[position]?.title
        ) else intent.putExtra("title", "error fetching data")

        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        if (!InternetConnected()) {
            showInternetSnackBar()
            checkConnection?.visibility = View.VISIBLE
            progressBar?.visibility = View.VISIBLE
            appBarLayout?.visibility = View.GONE
            recyclerView?.visibility = View.GONE
        } else {
            checkConnection?.setVisibility(View.GONE)
            progressBar?.setVisibility(View.GONE)
        }
        checkConnection?.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        })
    }


    fun InternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            !!.getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!
                .getState() == NetworkInfo.State.CONNECTED
        ) {
            //we are connected to a network
            true
        } else {
            false
        }
    }

    fun showInternetSnackBar() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Please, Check your internet connection",
            Snackbar.LENGTH_LONG
        )
            .setAction("Open") {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }
            .setActionTextColor(resources.getColor(R.color.white))
            .show()
    }

    private var lastPressedTime: Long = 0
    private val PERIOD = 2000
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getKeyCode() === KeyEvent.KEYCODE_BACK) {
            when (event.getAction()) {
                KeyEvent.ACTION_DOWN -> {
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        val a = Intent(Intent.ACTION_MAIN)
                        a.addCategory(Intent.CATEGORY_HOME)
                        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(a)
                    } else {
                        Toast.makeText(
                            applicationContext, "Press again to exit.",
                            Toast.LENGTH_SHORT
                        ).show()
                        lastPressedTime = event.getEventTime()
                    }
                    return true
                }
            }
        }
        return false
    }
}