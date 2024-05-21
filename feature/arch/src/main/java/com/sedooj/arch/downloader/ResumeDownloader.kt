package com.sedooj.arch.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.sedooj.api.domain.getBasicHeader

class ResumeDownloader(
    context: Context,
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(fileName: String, url: String, auth: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/pdf")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("${fileName}(${System.currentTimeMillis()})-resume.pdf")
            .addRequestHeader("Authorization", getBasicHeader(auth))
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${fileName}(${System.currentTimeMillis()})-resume.pdf"
            )
        val enqueue = downloadManager.enqueue(request)
        return enqueue
    }

    override fun openDownloadFile(id: Long) {
        try {
            downloadManager.openDownloadedFile(id)
        } catch (e: Exception) {
            throw e
        }
    }
}