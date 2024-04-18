package com.sedooj.api.domain.data.resume.entity

import kotlinx.serialization.Serializable

@Serializable
class Resume (
    var resumeId: Long,
    var title: String,
    var vacancyInformation: CreateResume.VacancyInformation,
    var personalInformation: CreateResume.PersonalInformation,
    var workExperienceInformation: List<CreateResume.WorkExperienceInformation>?,
    var skillsInformation: CreateResume.SkillsInformation,
    var url: String?
)