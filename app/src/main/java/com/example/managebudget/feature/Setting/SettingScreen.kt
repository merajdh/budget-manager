package com.example.managebudget.feature.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.managebudget.ui.theme.LightDarkColor

@Composable
fun SettingScreen() {

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground)) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            shadowElevation = 8.dp,
            tonalElevation = 2.dp,

            modifier = Modifier.weight(0.98f).fillMaxWidth().padding(bottom = 15.dp)

        ) {
            Text(text = "تست", style = MaterialTheme.typography.titleMedium)

        }
    }
}