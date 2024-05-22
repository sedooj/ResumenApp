package com.sedooj.arch.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sedooj.arch.viewmodel.auth.model.UserModel
import com.sedooj.localstorage.dao.AuthUserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProfileUiState(
    var username: String? = null,
    var isPageLoaded: Boolean = false
)

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authUserDao: AuthUserDao,
) : ViewModel(), UserModel {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    override suspend fun loadPage() {
        if (!_uiState.value.isPageLoaded) {
            val username = authUserDao.getAuthorizationData()?.username
            _uiState.update {
                it.copy(
                    username = username,
                    isPageLoaded = true
                )
            }
        }
    }

    override suspend fun logout(): Boolean {
        val credentials = authUserDao.getAuthorizationData()
        if (credentials != null)
            try {
                authUserDao.delete(credentials)
                return true
            } catch (e: Exception) {
                Log.d("Error while logout", e.toString())
                return false
            }
        return false
    }

    override suspend fun getCredentials(): String? {
        val auth = authUserDao.getAuthorizationData()
        return auth?.let { "${auth.username}:${auth.password}" }
    }

}