package com.kath.randomapiconsumer.ui.utils.downloader

interface Downloader {
    fun downloadFile(url: String,namefile:String): Long
}