package com.alexdev.minilogbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexdev.minilogbook.presentation.LogbookScreen
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniLogbookTheme {
                LogbookScreen()
            }
        }
    }
}
