package com.sedooj.app_ui.pages.resume.create.components.tabs

import androidx.compose.runtime.Composable
import com.sedooj.app_ui.pages.resume.create.components.ResumeOptionsComponent
import com.sedooj.app_ui.pages.resume.create.components.VacancyComponent
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Composable
fun TabContent(
    selectedTab: TabsModel.Tabs,
    createResumeViewModel: CreateResumeViewModel,
) {
    when (selectedTab) {
        TabsModel.Tabs.RESUME -> ResumeOptionsComponent(createResumeViewModel = createResumeViewModel)
        TabsModel.Tabs.VACANCY -> VacancyComponent(createResumeViewModel = createResumeViewModel)

        TabsModel.Tabs.PERSONAL -> {


        }

        TabsModel.Tabs.WORK -> {


        }

        TabsModel.Tabs.SKILLS -> {


        }
    }
}