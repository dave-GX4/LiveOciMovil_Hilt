package com.updavid.liveoci_hilt.features.activity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.updavid.liveoci_hilt.features.activity.domain.usecases.ActivityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityUseCases: ActivityUseCases
): ViewModel() {

}