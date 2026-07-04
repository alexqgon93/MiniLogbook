# Keep Room schema metadata and generated implementations discoverable in release builds.
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Database class * { *; }

# Keep Hilt-generated application/component classes used by generated entry points.
-keep class dagger.hilt.** { *; }
-keep class hilt_aggregated_deps.** { *; }
