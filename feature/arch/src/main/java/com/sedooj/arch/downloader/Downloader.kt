package com.sedooj.arch.downloader

interface Downloader {

    fun downloadFile(fileName: String, url: String, auth: String): Long

    fun openDownloadFile(id: Long)
}