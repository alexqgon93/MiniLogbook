package com.alexdev.minilogbook.domain.model

data class BgEntry(
    val id: Long = 0,
    val valueInMmol: Double,
    val timestamp: Long,
)
