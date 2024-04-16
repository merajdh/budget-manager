package com.example.managebudget.feature

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.managebudget.Components.ClearRippleTheme
import com.example.managebudget.R
import com.example.managebudget.data.BottomNavigationItems
import com.example.managebudget.feature.CryptoCurrency.CryptoCurrencyScreen
import com.example.managebudget.feature.DetailWallet.DetailWalletScreen
import com.example.managebudget.feature.Setting.SettingScreen
import com.example.managebudget.ui.theme.LightDarkColor
import com.example.managebudget.ui.theme.LightGray
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.utils.Screens
import dev.burnoo.cokoin.navigation.KoinNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    ManageBudgetTheme() {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.onBackground,
            bottomBar = {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination
                val items = listOf(

                    BottomNavigationItems(
                        Screens.CryptoCurrencyScreen.route,
                        R.drawable.cryptocurrency,
                        R.drawable.cryptocurrency_unselected

                    ),
                    BottomNavigationItems(
                        Screens.WalletScreen.route,
                        R.drawable.ic_wallet_selected,
                        R.drawable.ic_wallet_unselected

                    ),
                    BottomNavigationItems(
                        Screens.SettingScreen.route,
                        R.drawable.ic_setting_selected,
                        R.drawable.ic_setting_unselected

                    ),

                )

                CompositionLocalProvider(
                    LocalRippleTheme provides ClearRippleTheme
                ) {
                    NavigationBar(

                        modifier = Modifier
                            .height(height = 50.dp)
                            .padding(bottom = 5.dp),
                        containerColor = if (isSystemInDarkTheme()) {
                            LightDarkColor
                        } else {
                            LightGray
                        }
                    ) {
                        items.forEachIndexed { index, items ->
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.onBackground),
                                interactionSource = remember { MutableInteractionSource() },
                                selected = currentDestination?.hierarchy?.any { it.route == items.Title } == true,
                                onClick = {
                                    navController.navigate(items.Title) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
//                        Toast.makeText(context , items.Title , Toast.LENGTH_LONG).show()
                                },

                                icon = {


                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Icon(
                                            painter = painterResource(
                                                id = if (currentDestination?.hierarchy?.any { it.route == items.Title } == true) {
                                                    items.SelectedIcon
                                                } else {
                                                    items.unselectedIcon
                                                }
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )

                                        if (currentDestination?.hierarchy?.any { it.route == items.Title } == true) {
                                            Text(
                                                text = items.Title,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))

                                    }
                                }
                            })
                    }
                }
            }
        }) {
            KoinNavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Screens.WalletScreen.route,
            ) {
                composable(Screens.WalletScreen.route) {
                    WalletScreen()
                }

                composable(Screens.CryptoCurrencyScreen.route) {
                    CryptoCurrencyScreen()
                }
                composable(Screens.SettingScreen.route) {
                    SettingScreen()
                }
                composable(Screens.DetailWalletScreen.route) {
                    DetailWalletScreen()
                }

            }
        }

    }

}
