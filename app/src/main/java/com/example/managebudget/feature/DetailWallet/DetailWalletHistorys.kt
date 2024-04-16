package com.example.managebudget.feature.DetailWallet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.managebudget.Components.DeleteDialog
import com.example.managebudget.R
import com.example.managebudget.data.WalletData
import com.example.managebudget.extensions.formatNumberWithCurrency
import com.example.managebudget.feature.AddFloatingButton
import com.example.managebudget.feature.TransactionItems
import com.example.managebudget.feature.TransactionsLazyColumn
import com.example.managebudget.feature.Wallet.DeleteItemViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.utils.Screens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

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
                    navController.popBackStack(route = Screens.WalletScreen.route, inclusive = false)
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
        Column(modifier = modifier.fillMaxSize()) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

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


            }
            TransactionsLazyColumns() {
                items.invoke(it)
            }

        }

    }
}

@Composable
fun TransactionsLazyColumns(item: (WalletData) -> Unit) {
    val walletViewModel = getNavViewModel<WalletViewModel>()
    walletViewModel.getAll()
    val walletHistory = walletViewModel.transactionData.observeAsState()
    val deleteItemDialog = getNavViewModel<DeleteItemViewModel>()

    LazyColumn(
        modifier = Modifier.height(1000.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (walletHistory.value != null) {
            items(walletHistory.value!!.reversed()) { items ->
                Spacer(modifier = Modifier.height(8.dp))
                items.Count?.let { formatNumberWithCurrency(it.toDouble()) }?.let {
                    TransactionItems(
                        items.name, it, items.type
                    ) {
                        item.invoke(items)
                        deleteItemDialog.onClick()
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }
}