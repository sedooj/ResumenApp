package com.sedooj.api.domain.api

import com.sedooj.api.domain.NetworkConfig
import com.sedooj.api.domain.data.resume.entity.CreateResume
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.localstorage.dao.AuthUserDao
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.basicAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject


class ResumeNetworkRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val authUserDao: AuthUserDao
) : ResumeNetworkRepository {
    override suspend fun createResume(input: CreateResumeUseCase): Int? {
        val authorizationData = authUserDao.getAuthorizationData() ?: return null
        val response = client.post("${NetworkConfig.API_RESUME}createResume") {
            contentType(ContentType.Application.Json)
            basicAuth(
                username = authorizationData.username,
                password = authorizationData.password
            )
            setBody(
                CreateResume(
                    title = input.title,
                    vacancyInformation = CreateResume.VacancyInformation(
                        stackType = input.vacancyInformation.stackType,
                        platformType = input.vacancyInformation.platformType,
                        desiredRole = input.vacancyInformation.desiredRole,
                        desiredSalary = input.vacancyInformation.desiredSalary,
                        busynessType = input.vacancyInformation.busynessType,
                        scheduleType = input.vacancyInformation.scheduleType,
                        readyForTravelling = input.vacancyInformation.readyForTravelling

                    ),
                    personalInformation = CreateResume.PersonalInformation(
                        firstName = input.personalInformation.firstName,
                        secondName = input.personalInformation.secondName,
                        thirdName = input.personalInformation.thirdName,
                        dateOfBirth = input.personalInformation.dateOfBirth,
                        city = input.personalInformation.city,
                        residenceCountry = input.personalInformation.residenceCountry,
                        genderType = input.personalInformation.genderType,
                        maritalStatus = input.personalInformation.maritalStatus,
                        education = input.personalInformation.education?.map {
                            CreateResume.PersonalInformation.Education(
                                educationStage = it.educationStage,
                                title = it.title,
                                locationCity = it.locationCity,
                                enterDate = it.enterDate,
                                graduatedDate = it.graduatedDate,
                                faculty = it.faculty,
                                speciality = it.speciality
                            )
                        },
                        hasChild = input.personalInformation.hasChild,
                        socialMedia = input.personalInformation.socialMedia?.map {
                            CreateResume.PersonalInformation.SocialMedia(
                                type = it.type, url = it.url
                            )
                        },
                        aboutMe = input.personalInformation.aboutMe,
                        personalQualities = input.personalInformation.personalQualities

                    ),
                    workExperienceInformation = input.workExperienceInformation?.map {
                        CreateResume.WorkExperienceInformation(
                            companyName = it.companyName,
                            kindOfActivity = it.kindOfActivity,
                            gotJobDate = it.gotJobDate,
                            quitJobDate = it.quitJobDate,
                            isCurrentlyWorking = it.isCurrentlyWorking
                        )
                    },
                    skillsInformation = CreateResume.SkillsInformation(
                        softSkillsInformation = input.skillsInformation.softSkillsInformation,
                        hardSkillsInformation = input.skillsInformation.hardSkillsInformation,
                        workedFrameworksInformation = input.skillsInformation.workedFrameworksInformation,
                        languagesSkillsInformation = input.skillsInformation.languagesSkillsInformation?.map {
                            CreateResume.SkillsInformation.LanguageSkillsInformation(
                                languageName = it.languageName,
                                knowledgeLevel = it.knowledgeLevel
                            )
                        },
                        workedProgrammingLanguageInformation = input.skillsInformation.workedProgrammingLanguageInformation
                    ),
                    resumeOptions = CreateResume.ResumeOptionsComponent(
                        generatePreview = input.resumeOptions.generatePreview,
                        generateFinalResult = input.resumeOptions.generateFinalResult,
                        generationTemplate = input.resumeOptions.generationTemplate
                    )
                )
            )
        }
        return response.status.value
    }

    override suspend fun updateResume(resumeId: Long, input: CreateResumeUseCase): Int? {
        val authorizationData = authUserDao.getAuthorizationData() ?: return null
        val request = client.put("${NetworkConfig.API_RESUME}updateResume/${resumeId}") {
            contentType(ContentType.Application.Json)
            basicAuth(
                username = authorizationData.username,
                password = authorizationData.password
            )
            setBody(
                CreateResume(
                    title = input.title,
                    vacancyInformation = CreateResume.VacancyInformation(
                        stackType = input.vacancyInformation.stackType,
                        platformType = input.vacancyInformation.platformType,
                        desiredRole = input.vacancyInformation.desiredRole,
                        desiredSalary = input.vacancyInformation.desiredSalary,
                        busynessType = input.vacancyInformation.busynessType,
                        scheduleType = input.vacancyInformation.scheduleType,
                        readyForTravelling = input.vacancyInformation.readyForTravelling

                    ),
                    personalInformation = CreateResume.PersonalInformation(
                        firstName = input.personalInformation.firstName,
                        secondName = input.personalInformation.secondName,
                        thirdName = input.personalInformation.thirdName,
                        dateOfBirth = input.personalInformation.dateOfBirth,
                        city = input.personalInformation.city,
                        residenceCountry = input.personalInformation.residenceCountry,
                        genderType = input.personalInformation.genderType,
                        maritalStatus = input.personalInformation.maritalStatus,
                        education = input.personalInformation.education?.map {
                            CreateResume.PersonalInformation.Education(
                                educationStage = it.educationStage,
                                title = it.title,
                                locationCity = it.locationCity,
                                enterDate = it.enterDate,
                                graduatedDate = it.graduatedDate,
                                faculty = it.faculty,
                                speciality = it.speciality
                            )
                        },
                        hasChild = input.personalInformation.hasChild,
                        socialMedia = input.personalInformation.socialMedia?.map {
                            CreateResume.PersonalInformation.SocialMedia(
                                type = it.type, url = it.url
                            )
                        },
                        aboutMe = input.personalInformation.aboutMe,
                        personalQualities = input.personalInformation.personalQualities

                    ),
                    workExperienceInformation = input.workExperienceInformation?.map {
                        CreateResume.WorkExperienceInformation(
                            companyName = it.companyName,
                            kindOfActivity = it.kindOfActivity,
                            gotJobDate = it.gotJobDate,
                            quitJobDate = it.quitJobDate,
                            isCurrentlyWorking = it.isCurrentlyWorking
                        )
                    },
                    skillsInformation = CreateResume.SkillsInformation(
                        softSkillsInformation = input.skillsInformation.softSkillsInformation,
                        hardSkillsInformation = input.skillsInformation.hardSkillsInformation,
                        workedFrameworksInformation = input.skillsInformation.workedFrameworksInformation,
                        languagesSkillsInformation = input.skillsInformation.languagesSkillsInformation?.map {
                            CreateResume.SkillsInformation.LanguageSkillsInformation(
                                languageName = it.languageName,
                                knowledgeLevel = it.knowledgeLevel
                            )
                        },
                        workedProgrammingLanguageInformation = input.skillsInformation.workedProgrammingLanguageInformation
                    ),
                    resumeOptions = CreateResume.ResumeOptionsComponent(
                        generatePreview = input.resumeOptions.generatePreview,
                        generateFinalResult = input.resumeOptions.generateFinalResult,
                        generationTemplate = input.resumeOptions.generationTemplate
                    )
                )
            )
        }
        return request.status.value
    }

    override suspend fun getResumeList(): List<Resume>? {
        val authorizationData = authUserDao.getAuthorizationData() ?: return null
        val request = client.get("${NetworkConfig.API_RESUME}list") {
            contentType(ContentType.Application.Json)
            basicAuth(
                username = authorizationData.username,
                password = authorizationData.password
            )
        }
        val response = request.body<List<Resume>>()
        return response
    }

    override suspend fun getResume(resumeId: Long): Resume? {
        val authorizationData = authUserDao.getAuthorizationData() ?: return null
        val response = client.get("${NetworkConfig.API_RESUME}getResume/${resumeId}") {
            contentType(ContentType.Application.Json)
            basicAuth(
                username = authorizationData.username,
                password = authorizationData.password
            )
        }
        return response.body<Resume>()
    }

    override suspend fun dropResume(resumeId: Long) {
        val authorizationData = authUserDao.getAuthorizationData() ?: return
        val request = client.delete("${NetworkConfig.API_RESUME}dropResume/${resumeId}") {
            basicAuth(
                username = authorizationData.username,
                password = authorizationData.password
            )
        }
    }

    override suspend fun downloadResume(resumeId: Long): ByteArray? {
        TODO("Not yet implemented")
    }
}