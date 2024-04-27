//package com.sedooj.app_ui.pages.resume.create.components.tabs
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
//import com.sedooj.app_ui.pages.resume.create.components.MainComponent
//import com.sedooj.app_ui.pages.resume.create.components.SecondComponent
//import com.sedooj.app_ui.pages.resume.create.components.vacancy.VacancyComponent
//import com.sedooj.arch.viewmodel.auth.model.TabsModel
//import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
//
//@Composable
//fun TabContent(
//    selectedTab: TabsModel.Tabs,
//    createResumeViewModel: CreateResumeViewModel,
//) {
//    // Resume Options
//    val title = rememberSaveable { mutableStateOf("") }
//    // Vacancy information
//
//    // Personal information
//
//    when (selectedTab) {
//        TabsModel.Tabs.PERSONAL_MAIN -> MainComponent(
//            firstName = firstName.value,
//            secondName = secondName.value,
//            thirdName = thirdName.value,
//            dateOfBirth = dateOfBirth.value,
//            city = city.value,
//            residenceCountry = residenceCountry.value,
//            genderType = genderType.value,
//            maritalStatus = maritalStatus.value,
//
//            onDate = {
//                dateOfBirth.value = it
//            },
//            onGenderType = {
//                genderType.value = it
//                if (maritalStatus.value != null) {
//                    if (maritalStatus.value?.genderType != genderType.value) {
//                        maritalStatus.value = null
//                    }
//                }
//            },
//            onMaritalType = {
//                maritalStatus.value = it
//            }
//        )
//
//        TabsModel.Tabs.PERSONAL_SECONDARY -> SecondComponent(
//            education = education,
//            hasChild = hasChild.value,
//            socialMedia = socialMedia.value,
//            aboutMe = aboutMe.value,
//            personalQualities = personalQualities.value,
//            onEducation = { i, s ->
//                if (i >= education.size)
//                    education.add(s)
//                else
//                    education[i] = s
//            },
//            onDropEducation = {
//                education.removeAt(it)
//            }
//        )
//
//        TabsModel.Tabs.WORK -> {
//
//
//        }
//
//        TabsModel.Tabs.SKILLS -> {
//
//
//        }
//    }
//}