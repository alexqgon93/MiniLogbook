package com.alexdev.minilogbook.domain.time

interface TimeProvider {
    fun now(): Long
}
