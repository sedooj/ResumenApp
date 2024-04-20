package com.sedooj.app_ui.pages.resume.create.components.tabs.personal

import androidx.compose.runtime.Composable
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.app_ui.pages.resume.create.components.MainComponent
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel


@Composable
fun PersonalTabContent(
    selectedTab: TabsModel.PersonalTabs,
    createResumeViewModel: CreateResumeViewModel,
    firstName: String?,
    secondName: String?,
    thirdName: String?,
    dateOfBirth: String?,
    city: String?,
    residenceCountry: String?,
    genderType: GenderType?,
    maritalStatus: MaritalStatus?,
    onDate: (String?) -> Unit,
    onGenderType: (GenderType) -> Unit,
    onMaritalType: (MaritalStatus)-> Unit
) {
    when (selectedTab) {
        TabsModel.PersonalTabs.MAIN -> MainComponent(
            firstName = firstName,
            secondName = secondName,
            thirdName = thirdName,
            dateOfBirth = dateOfBirth,
            city = city,
            residenceCountry = residenceCountry,
            genderType = genderType,
            maritalStatus = maritalStatus,
            onDate = onDate,
            onGenderType = onGenderType,
            onMaritalType = onMaritalType

        )
        TabsModel.PersonalTabs.SECONDARY -> TODO()
    }
}