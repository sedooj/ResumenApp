package com.sedooj.app_ui.pages.resume.create.components

import com.ramcosta.composedestinations.generated.destinations.EducationDestination
import com.ramcosta.composedestinations.generated.destinations.PersonalMainDestination
import com.ramcosta.composedestinations.generated.destinations.PersonalSecondaryDestination
import com.ramcosta.composedestinations.generated.destinations.SkillsDestination
import com.ramcosta.composedestinations.generated.destinations.VacancyDestination
import com.ramcosta.composedestinations.generated.destinations.WorkDestination
import com.ramcosta.composedestinations.spec.Direction
import com.sedooj.ui_kit.R

enum class ComponentList(
    val title: Int,
    val subTitle: Int?,
    val icon: Int,
    val direction: Direction,
) {
    VACANCY(
        title = R.string.vacancy,
        R.string.type_vacancy,
        icon = R.drawable.bag,
        direction = VacancyDestination
    ),
    PERSONAL_MAIN(
        title = R.string.personal_information,
        subTitle = R.string.type_personal_information_main,
        icon = R.drawable.profile,
        direction = PersonalMainDestination
    ),
    PERSONAL_SECONDARY(
        title = R.string.personal_information,
        subTitle = R.string.type_personal_information_second,
        icon = R.drawable.profile,
        direction = PersonalSecondaryDestination
    ),
    EDUCATION(
        title = R.string.education,
        subTitle = R.string.type_education,
        icon = R.drawable.education,
        direction = EducationDestination
    ),
    WORK(
        title = R.string.work_experience,
        subTitle = null,
        icon = R.drawable.work_experience,
        direction = WorkDestination
    ),
    SKILLS(
        title = R.string.skills,
        subTitle = R.string.type_skills,
        icon = R.drawable.skills,
        direction = SkillsDestination
    )
}