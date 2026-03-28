package com.updavid.liveoci_hilt.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.ScheduleUseCases
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases
): ViewModel() {
}