package com.sedooj.api.domain.api

import com.sedooj.api.domain.NetworkConfig
import com.sedooj.api.domain.data.resume.entity.CreateResume
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.basicAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ResumeNetworkRepositoryImpl(
    private val client: HttpClient,
) : ResumeNetworkRepository {

    override suspend fun createResume(input: CreateResume): Int {
        val response = client.post("${NetworkConfig.API_RESUME}createResume") {
            contentType(ContentType.Application.Json)
            basicAuth(
                username = "",
                password = ""
            )
            setBody(
                CreateResume(
                    title = input.title,
                    vacancyInformation = input.vacancyInformation,
                    personalInformation = input.personalInformation,
                    workExperienceInformation = input.workExperienceInformation,
                    skillsInformation = input.skillsInformation,
                    resumeOptions = input.resumeOptions
                )
            )
        }
        return response.status.value
    }

    override suspend fun updateResume(resumeId: Long, input: CreateResume): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getResumeList(): List<Resume> {
        TODO("Not yet implemented")
    }

    override suspend fun getResume(resumeId: Long): Resume {
        TODO("Not yet implemented")
    }

    override suspend fun dropResume(resumeId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun downloadResume(resumeId: Long): ByteArray {
        TODO("Not yet implemented")
    }
}