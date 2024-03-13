package com.example.managebudget.feature.Main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.managebudget.R
import com.example.managebudget.data.BottomNavigationItems
import com.example.managebudget.di.Modules
import com.example.managebudget.feature.Navigation
import com.example.managebudget.ui.theme.LightDarkColor
import com.example.managebudget.ui.theme.LightGray
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.utils.Screens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.getNavController
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(Modules)
            }) {
                ManageBudgetTheme() {
                    Surface {
                            Navigation()

                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(currentdest : NavDestination? ) {
    val navController = rememberNavController()





}