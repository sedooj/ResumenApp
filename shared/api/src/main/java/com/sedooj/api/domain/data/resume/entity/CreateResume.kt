package com.sedooj.api.domain.data.resume.entity

import com.sedooj.api.domain.data.resume.generator.Templates
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType
import kotlinx.serialization.Serializable

@Serializable
data class CreateResume(
    var title: String,
    var vacancyInformation: VacancyInformation,
    var personalInformation: PersonalInformation,
    var workExperienceInformation: List<WorkExperienceInformation>?,
    var skillsInformation: SkillsInformation,
    var resumeOptions: ResumeOptionsComponent,
) {

    @Serializable
    data class VacancyInformation(
        var stackType: StackType,
        var platformType: PlatformType,
        var desiredRole: String,
        var desiredSalaryFrom: String?,
        var desiredSalaryTo: String?,
        var busynessType: BusynessType?,
        var scheduleType: ScheduleType,
        var readyForTravelling: Boolean,
    )

    @Serializable
    data class PersonalInformation(
        var firstName: String,
        var secondName: String,
        var thirdName: String?,
        var dateOfBirth: String,
        var city: String,
        var residenceCountry: String,
        var genderType: GenderType,
        var maritalStatus: MaritalStatus?,
        var education: List<Education>?,
        var hasChild: Boolean,
        var socialMedia: List<SocialMedia>?,
        var aboutMe: String?,
        var personalQualities: String?,
    ) {
        @Serializable
        class Education(
            var educationStage: EducationStage,
            var title: String,
            var locationCity: String,
            var enterDate: String,
            var graduatedDate: String,
            var faculty: String,
            var speciality: String,
        )

        @Serializable
        data class SocialMedia(
            var type: String,
            var url: String,
        )
    }

    @Serializable
    data class WorkExperienceInformation(
        var companyName: String,
        var kindOfActivity: String,
        var gotJobDate: String,
        var quitJobDate: String?,
        var isCurrentlyWorking: Boolean,
    )

    @Serializable
    data class SkillsInformation(
        var softSkillsInformation: List<String>?,
        var hardSkillsInformation: List<String>?,
        var workedFrameworksInformation: List<String>?,
        var languagesSkillsInformation: List<LanguageSkillsInformation>?,
        var workedProgrammingLanguageInformation: List<String>?,
    ) {
        @Serializable
        data class LanguageSkillsInformation(
            var languageName: String,
            var knowledgeLevel: String,
        )
    }

    @Serializable
    data class ResumeOptionsComponent(
        var generatePreview: Boolean,
        var generateFinalResult: Boolean,
        var generationTemplate: Templates?
    )
}
