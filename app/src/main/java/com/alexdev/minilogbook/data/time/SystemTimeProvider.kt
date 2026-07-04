package com.alexdev.minilogbook.data.time

import com.alexdev.minilogbook.domain.time.TimeProvider
import javax.inject.Inject

class SystemTimeProvider
    @Inject
    constructor() : TimeProvider {
        override fun now(): Long = System.currentTimeMillis()
    }
