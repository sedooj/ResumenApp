package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.arch.Routes
import com.sedooj.ui_kit.R

interface TabsModel {

    enum class Tabs(val title: Int, val subTitle: Int?, val icon: Int,val route: String) {
        VACANCY(title = R.string.vacancy,R.string.type_vacancy, icon = R.drawable.bag, route = Routes.CREATE_RESUME_VACANCY),
        PERSONAL_MAIN(title = R.string.personal_information, subTitle = R.string.type_personal_information_main, icon = R.drawable.profile, route = Routes.CREATE_RESUME_PERSONAL_MAIN),
        PERSONAL_SECONDARY(title = R.string.personal_information, subTitle = R.string.type_personal_information_second, icon = R.drawable.profile, route = Routes.CREATE_RESUME_PERSONAL_SECONDARY),
        EDUCATION(title = R.string.education, subTitle = R.string.type_education, icon = R.drawable.education, route = Routes.CREATE_RESUME_EDUCATION),
        WORK(title = R.string.work_experience,subTitle = null, icon = R.drawable.work_experience, route = Routes.CREATE_RESUME_WORK),
        SKILLS(title = R.string.skills, subTitle = R.string.type_skills,icon = R.drawable.skills, route = Routes.CREATE_RESUME_SKILLS),
    }

    fun setTab(tab: Tabs, id: Int)
}