package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.ui_kit.R

interface TabsModel {

    enum class Tabs(val title: Int, val icon: Int) {
        RESUME(title = R.string.resume, icon = R.drawable.resume),
        VACANCY(title = R.string.vacancy, icon = R.drawable.bag),
        PERSONAL(title = R.string.personal_information, icon = R.drawable.profile),
        WORK(title = R.string.work_experience, icon = R.drawable.work_experience),
        SKILLS(title = R.string.skills, icon = R.drawable.skills),
    }

    enum class PersonalTabs(val title: Int) {
        MAIN(title = R.string.personal_main),
        SECONDARY(title = R.string.personal_secondary)
    }

    fun setTab(tab: Tabs, id: Int)
    fun setPersonalTab(tab: PersonalTabs, id: Int)
}