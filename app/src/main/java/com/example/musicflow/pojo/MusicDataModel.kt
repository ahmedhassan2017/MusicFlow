package com.example.musicflow.pojo

import com.google.gson.annotations.SerializedName

class MusicDataModel {
    val id: Int? = null
    val type: String? = null
    val title: String? = null
    val publishingDate: String? = null
    val duration: Int? = null
    val mainArtist: MainArtist? = null
    val release: Release? = null
    val cover: Cover? = null

    inner class MainArtist {
        val id: Int? = null
        val name: String? = null

    }

    inner class Release {
        val id: Int? = null
        val title: String? = null

    }

    inner class Cover {
        val small: String? = null
        val large: String? = null

        @SerializedName("default")
        val default: String? = null

    }
}