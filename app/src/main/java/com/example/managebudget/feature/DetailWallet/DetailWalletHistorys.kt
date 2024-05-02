package com.example.managebudget.feature.DetailWallet

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.managebudget.Components.ChipGroup
import com.example.managebudget.Components.DeleteDialog
import com.example.managebudget.R
import com.example.managebudget.data.WalletData
import com.example.managebudget.extensions.formatNumberWithCurrency
import com.example.managebudget.feature.TransactionItems
import com.example.managebudget.feature.Wallet.DeleteItemViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.utils.Screens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

@Composable
fun DetailWalletScreen() {
    val deleteItemDialog = getNavViewModel<DeleteItemViewModel>()
    val walletViewModel = getNavViewModel<WalletViewModel>()
    val navController = getNavController()
    var item = remember {
        mutableStateOf<WalletData?>(null)
    }
    ManageBudgetTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                shadowElevation = 8.dp,
                tonalElevation = 2.dp,
                modifier = Modifier
                    .weight(0.98f)
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)

            ) {

                if (deleteItemDialog.isDialogOpen) {
                    DeleteDialog(onDismiss = {
                        deleteItemDialog.onDismiss()
                    }) {
                        item.value?.let { walletViewModel.deleteItem(it) }
                    }
                }

                LastTransactions(modifier = Modifier.fillMaxSize(), items = {
                    item.value = it
                }) {
                    navController.popBackStack(
                        route = Screens.WalletScreen.route,
                        inclusive = false
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LastTransactions(
    modifier: Modifier,
    items: (WalletData) -> Unit,
    onBack: () -> Unit
) {

    Box(modifier = modifier) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp)

                        .padding(vertical = 15.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .padding(top = 2.dp)
                            .clickable {
                                onBack.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null
                    )
                    Text(
                        text = "تراکنش ها",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.95f),
                        modifier = Modifier
                            .padding(start = 10.dp)

                    )

                }


                var time by remember {
                    mutableStateOf("کلی")
                }
                ChipGroup(
                    modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp)
                        .weight(0.1f)
                ) {
                    time = it
                }

                TransactionsLazyColumns(
                    Modifier
                        .fillMaxWidth()
                        .weight(0.95f), type = time
                ) {

                    items.invoke(it)
                }

            }
        }

    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionsLazyColumns(modifier: Modifier, type: String, item: (WalletData) -> Unit) {
    val walletViewModel = getNavViewModel<WalletViewModel>()
    walletViewModel.getAll()
    val walletHistory = walletViewModel.transactionData.observeAsState()

    var dateState by remember {
        mutableStateOf(99999)
    }
    LazyColumn(
        modifier = modifier.height(1000.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (walletHistory.value != null) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val today = LocalDate.now()
                val endDate = today
                val startDate = today.minusDays(dateState.toLong())
                walletHistory.value!!.filter { item ->
                    val itemDate = LocalDate.parse(item.time)
                    !itemDate.isBefore(startDate) && !itemDate.isAfter(endDate)
                }
                  walletHistory.value!!.sortedBy { item ->
                    LocalDate.parse(item.time)
                }

            } else {
                Calendar.getInstance()
                 val today = Calendar.getInstance()
                 val endDate = today.clone() as Calendar
                 val startDate = today.clone() as Calendar
                 startDate.add(Calendar.DAY_OF_YEAR, -dateState.toInt())

                 walletHistory.value!!.filter { item ->
                     val itemDate = Calendar.getInstance()
                     itemDate.time = item.time?.let { SimpleDateFormat("yyyy-MM-dd").parse(it) }!!
                     !(itemDate.before(startDate) || itemDate.after(endDate))
                 }.sortedBy { item ->
                     item.time?.let { SimpleDateFormat("yyyy-MM-dd").parse(it) }
                 }

             }


            dateState = when (type) {
                "کلی" -> 99999
                "ماهانه" -> 30
                "هفتگی" -> 7
                "درآمد ها" -> 99999
                "هزینه ها" -> 99999
                else -> {
                    99999
                }
            }

            items(walletHistory.value!!) { items ->
                Spacer(modifier = Modifier.height(8.dp))
                items.count?.let { formatNumberWithCurrency(it.toDouble()) }?.let {
                    if (type == "هزینه ها") {
                        if (items.type) {
                            TransactionItems(
                                items.name, it, items.type, items.time.toString()
                            ) {
                            }
                        }
                    } else {
                        if (type == "درآمد ها") {
                            if (!items.type) {
                                TransactionItems(
                                    items.name, it, items.type, items.time.toString()
                                ) {
                                }
                            }
                        } else {
                            TransactionItems(
                                items.name, it, items.type, items.time.toString()
                            ) {
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }
}