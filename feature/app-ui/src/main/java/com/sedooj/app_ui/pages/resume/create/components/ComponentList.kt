package com.sedooj.app_ui.pages.resume.create.components

import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEPERSONALMAINDestination
import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEPERSONALSECONDARYDestination
import com.ramcosta.composedestinations.generated.destinations.EDUCATIONDestination
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.spec.Direction
import com.sedooj.ui_kit.R

enum class ComponentList(val title: Int, val subTitle: Int?, val icon: Int, val direction: Direction) {
    VACANCY(
        title = R.string.vacancy,
        R.string.type_vacancy,
        icon = R.drawable.bag,
        direction = HOMEDestination
    ),
    PERSONAL_MAIN(
        title = R.string.personal_information,
        subTitle = R.string.type_personal_information_main,
        icon = R.drawable.profile,
        direction = CREATERESUMEPERSONALMAINDestination
    ),
    PERSONAL_SECONDARY(
        title = R.string.personal_information,
        subTitle = R.string.type_personal_information_second,
        icon = R.drawable.profile,
        direction = CREATERESUMEPERSONALSECONDARYDestination
    ),
    EDUCATION(
        title = R.string.education,
        subTitle = R.string.type_education,
        icon = R.drawable.education,
        direction = EDUCATIONDestination
    ),
    WORK(
        title = R.string.work_experience,
        subTitle = null,
        icon = R.drawable.work_experience,
        direction = CREATERESUMEPERSONALMAINDestination
    ),
    SKILLS(
        title = R.string.skills,
        subTitle = R.string.type_skills,
        icon = R.drawable.skills,
        direction = CREATERESUMEPERSONALMAINDestination
    )
}