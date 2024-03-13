package com.example.managebudget.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.managebudget.ui.theme.LightDarkColor
import com.example.managebudget.ui.theme.ManageBudgetTheme

@Composable
fun WalletScreen() {
    ManageBudgetTheme() {
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

}