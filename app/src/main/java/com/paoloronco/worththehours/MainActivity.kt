package com.paoloronco.worththehours

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.paoloronco.worththehours.ui.NavGraph
import com.paoloronco.worththehours.ui.theme.WorthTheHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This line allows us to draw behind the system bars.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WorthTheHoursTheme {
                NavGraph()
            }
        }
    }
}
