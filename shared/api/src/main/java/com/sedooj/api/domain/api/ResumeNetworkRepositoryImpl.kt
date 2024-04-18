package com.sedooj.api.domain.api

import com.sedooj.api.domain.NetworkConfig
import com.sedooj.api.domain.data.resume.entity.CreateResume
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.api.domain.util.convert
import com.sedooj.localstorage.dao.AuthUserDao
import io.ktor.client.HttpClient
import io.ktor.client.request.basicAuth
import io.ktor.client.request.post
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
                        dateOfBirth = input.personalInformation.dateOfBirth.convert(),
                        city = input.personalInformation.city,
                        residenceCountry = input.personalInformation.residenceCountry,
                        genderType = input.personalInformation.genderType,
                        maritalStatus = input.personalInformation.maritalStatus,
                        education = input.personalInformation.education.map {
                            CreateResume.PersonalInformation.Education(
                                educationStage = it.educationStage,
                                title = it.title,
                                locationCity = it.locationCity,
                                enterDate = it.enterDate.convert(),
                                graduatedDate = it.graduatedDate.convert(),
                                faculty = it.faculty,
                                speciality = it.speciality
                            )
                        },
                        hasChild = input.personalInformation.hasChild,
                        socialMedia = input.personalInformation.socialMedia.map {
                            CreateResume.PersonalInformation.SocialMedia(
                                type = it.type, url = it.url
                            )
                        },
                        aboutMe = input.personalInformation.aboutMe,
                        personalQualities = input.personalInformation.personalQualities

                    ),
                    workExperienceInformation = input.workExperienceInformation.map {
                        CreateResume.WorkExperienceInformation(
                            companyName = it.companyName,
                            kindOfActivity = it.kindOfActivity,
                            gotJobDate = it.gotJobDate.convert(),
                            quitJobDate = it.quitJobDate?.convert(),
                            isCurrentlyWorking = it.isCurrentlyWorking
                        )
                    },
                    skillsInformation = CreateResume.SkillsInformation(
                        softSkillsInformation = input.skillsInformation.softSkillsInformation,
                        hardSkillsInformation = input.skillsInformation.hardSkillsInformation,
                        workedFrameworksInformation = input.skillsInformation.workedFrameworksInformation,
                        languagesSkillsInformation = input.skillsInformation.languagesSkillsInformation.map {
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
        TODO("Not yet implemented")
    }

    override suspend fun getResumeList(): List<Resume>? {
        TODO("Not yet implemented")
    }

    override suspend fun getResume(resumeId: Long): Resume? {
        TODO("Not yet implemented")
    }

    override suspend fun dropResume(resumeId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun downloadResume(resumeId: Long): ByteArray? {
        TODO("Not yet implemented")
    }
}