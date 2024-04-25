package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.ui_kit.R

interface TabsModel {

    enum class Tabs(val title: Int, val subTitle: Int?, val icon: Int) {
        VACANCY(title = R.string.vacancy,R.string.type_vacancy, icon = R.drawable.bag),
        PERSONAL_MAIN(title = R.string.personal_information, subTitle = R.string.type_personal_information_main, icon = R.drawable.profile),
        PERSONAL_SECONDARY(title = R.string.personal_information, subTitle = R.string.type_personal_information_second, icon = R.drawable.profile),
        WORK(title = R.string.work_experience,subTitle = null, icon = R.drawable.work_experience),
        SKILLS(title = R.string.skills, subTitle = R.string.type_skills,icon = R.drawable.skills),
    }

    fun setTab(tab: Tabs, id: Int)
}