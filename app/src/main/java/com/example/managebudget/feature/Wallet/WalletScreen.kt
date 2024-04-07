package com.example.managebudget.feature

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.managebudget.Components.CustomDialog
import com.example.managebudget.R
import com.example.managebudget.extensions.changeCharAtIndex
import com.example.managebudget.extensions.formatNumberWithCurrency
import com.example.managebudget.extensions.removeZeros
import com.example.managebudget.feature.Wallet.WalletDialogViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.ui.theme.PrimaryLight
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlin.math.abs

@OptIn(ExperimentalUnitApi::class)
@Composable
fun WalletScreen() {
    val dialogViewModel = getNavViewModel<WalletDialogViewModel>()
    val walletViewMode = getNavViewModel<WalletViewModel>()

    val totalExpenses by walletViewMode.expensesData.observeAsState()
    val totalIncome by walletViewMode.incomesData.observeAsState()
    val currentFinance by walletViewMode.currentTotalData.observeAsState()

    walletViewMode.totalExpenses()
    walletViewMode.totalIncomes()
    walletViewMode.totalCurrent()
    ManageBudgetTheme() {

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

                if (dialogViewModel.isDialogOpen) {
                    CustomDialog(onDismiss = {
                        dialogViewModel.onDismiss()

                    }, onConfirm = {
                        walletViewMode.addDataWallet()
                        walletViewMode.getAll()
                        walletViewMode.totalExpenses()
                        walletViewMode.totalIncomes()
                        walletViewMode.totalCurrent()
                        walletViewMode.resetTransaction()
                        Log.v("dataa" , walletViewMode.transactionData.value.toString())

                    })


                }
                Column(modifier = Modifier.fillMaxSize()) {
                    InComeExpanse(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.4f),
                        currentExpense = totalExpenses.toString(),
                        currentIncome = totalIncome.toString(),
                        currentPrice = currentFinance.toString()

                    )

                    LastTransactions(
                        modifier = Modifier
                            .weight(0.6f)
                    ) {
                        dialogViewModel.onClick()
                    }
                }
            }
        }

    }


}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun InComeExpanse(
    modifier: Modifier,
    currentPrice: String,
    currentExpense: String,
    currentIncome: String
) {
    val persianDigits = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "باقی مانده فعلی",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 35.dp)
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val text =
                currentPrice.map {
                    if (it != '-') {
                        persianDigits[
                            it.toString().toInt()]

                    } else {
                        '-'
                    }

                }.joinToString("")
                    .let { removeZeros(formatNumberWithCurrency(it.toInt().toDouble())) }

            Log.v("last", text.first().toString())

            Text(
                letterSpacing = TextUnit(3f, TextUnitType.Sp),
                text = "${
                    if (text.first() == '−') {
                        text.drop(0)
                        Log.v("last",  text.last().toString())
                        Log.v("first",  text.first().toString())
                        "$text-"
                    } else {
                        text
                    }
                } تومان ",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }

        Row() {
            InComeExpanseCard(
                backColor = ExpanseColor,
                titleText = "هزینه ها",
                countText = currentExpense.map { persianDigits[it.toString().toInt()] }
                    .joinToString("").let { removeZeros(formatNumberWithCurrency(it.toInt().toDouble())) },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 25.dp, end = 6.dp)
            )
            InComeExpanseCard(
                backColor = IncomingColor,
                titleText = "درآمد",
                countText = currentIncome.map { persianDigits[it.toString().toInt()] }
                    .joinToString("").let { removeZeros(formatNumberWithCurrency(it.toInt().toDouble())) },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 6.dp, end = 25.dp)
            )


        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun InComeExpanseCard(backColor: Color, titleText: String, countText: String, modifier: Modifier) {
    val brush =
        Brush.linearGradient(listOf(backColor.copy(alpha = 0.9f), backColor.copy(alpha = 0.5f)))

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(brush)
                .fillMaxWidth()

        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                text = titleText,
                maxLines = 1,
                letterSpacing = TextUnit(2f, TextUnitType.Sp),
                style = MaterialTheme.typography.bodyMedium,

                modifier = Modifier.padding(top = 15.dp)

            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = "$countText تومان",
                    letterSpacing = TextUnit(3f, TextUnitType.Sp),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = TextUnit(17f, TextUnitType.Sp),

                    modifier = Modifier.padding(bottom = 17.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LastTransactions(modifier: Modifier, onClick: () -> Unit) {

    Box(modifier = modifier) {
        Column(modifier = modifier.fillMaxSize()) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Text(
                        text = "تراکنش اخیر",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.95f),
                        modifier = Modifier
                            .padding(start = 30.dp)
                    )
                    Text(
                        text = "بیشتر...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        modifier = Modifier
                            .padding(end = 30.dp)
                    )
                }

            }
            TransactionsLazyColumn()

        }
        AddFloatingButton(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        ) {
            onClick.invoke()
        }
    }
}

@Composable
fun TransactionsLazyColumn() {
    val walletViewModel = getNavViewModel<WalletViewModel>()
    walletViewModel.getAll()
    val walletHistory = walletViewModel.transactionData.observeAsState()
    LazyColumn(
        modifier = Modifier.height(1000.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (walletHistory.value != null) {
            items(walletHistory.value!!.reversed().take(4)) { items ->
                Spacer(modifier = Modifier.height(8.dp))
                TransactionItems(
                    items.name, items.Count.toString(), items.type
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TransactionItems(transactionName: String, transactionCount: String, transactionType: Boolean) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.onBackground.copy(0.8f)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transactionName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 15.dp)
                )
                Row(
                    modifier = Modifier.padding(end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transactionCount,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = TextUnit(14f, TextUnitType.Sp),
                        color = if (transactionType) {
                            ExpanseColor
                        } else {
                            IncomingColor
                        },
                        modifier = Modifier.padding(end = 5.dp)
                    )

                    Icon(
                        painter = painterResource(
                            id = if (transactionType) {
                                R.drawable.ic_up_growth
                            } else {
                                R.drawable.ic_down_growth
                            }
                        ), contentDescription = null,
                        modifier = Modifier
                            .rotate(180f)
                            .size(18.dp)
                            .padding(top = 2.dp),
                        tint = if (transactionType) {
                            ExpanseColor
                        } else {
                            IncomingColor
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddFloatingButton(modifier: Modifier, onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        modifier = modifier,

        onClick = { onClick.invoke() },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(45),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = PrimaryLight
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = "تراکنش جدید",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryLight
        )
    }
}


