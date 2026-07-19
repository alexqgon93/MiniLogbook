package com.alexdev.minilogbook.testing

import com.alexdev.minilogbook.domain.model.BgEntry

object ReadingFixtures {
    val fixedTimestamp: Long = 1_717_000_000_000L

    fun reading(
        id: Long = 1L,
        valueInMmol: Double = 5.5,
        timestamp: Long = fixedTimestamp,
    ) = BgEntry(
        id = id,
        valueInMmol = valueInMmol,
        timestamp = timestamp,
    )

    fun readings(vararg valuesInMmol: Double): List<BgEntry> =
        valuesInMmol.mapIndexed { index, value ->
            reading(
                id = index.toLong() + 1L,
                valueInMmol = value,
                timestamp = fixedTimestamp + index,
            )
        }
}
