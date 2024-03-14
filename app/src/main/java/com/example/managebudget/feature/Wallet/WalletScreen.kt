package com.example.managebudget.feature

import android.view.SurfaceControl.Transaction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.LightDarkColor
import com.example.managebudget.ui.theme.LightGray
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.ui.theme.PrimaryLight

@Composable
fun WalletScreen() {

    val currentPrice = remember {
        mutableStateOf("۱,۰۰۰,۰۰۰")
    }
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


                Column(modifier = Modifier.fillMaxSize()) {
                    InComeExpanse(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.4f),
                        currentPrice = currentPrice.value

                    )

                    LastTransactions(
                        modifier = Modifier
                            .weight(0.6f)
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun InComeExpanse(modifier: Modifier, currentPrice: String) {

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally ) {

        Text(
            text = "باقی مانده فعلی",
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 35.dp )
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                letterSpacing = TextUnit(3f, TextUnitType.Sp),
                text = "$currentPrice تومان ",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
        Row() {
            InComeExpanseCard(
                backColor = ExpanseColor,
                titleText = "هزینه ها",
                countText = "۱,۰۰۰,۰۰۰",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 25.dp, end = 8.dp)
            )
            InComeExpanseCard(
                backColor = IncomingColor,
                titleText = "درآمد",
                countText = "۱۰۰,۰۰۰,۰۰۰,۰۰۰",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, end = 25.dp)
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
                letterSpacing = TextUnit(2f , TextUnitType.Sp),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 15.dp)

            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = "$countText تومان",
                    letterSpacing = TextUnit(3f , TextUnitType.Sp),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 17.dp)
                )
            }
        }
    }
}

@Composable
fun LastTransactions(modifier: Modifier) {

    Box(modifier = modifier) {


        Column(modifier = modifier.fillMaxSize()) {
            TransactionsLazyColumn()

        }
        AddFloatingButton(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 25.dp))
    }
}

@Composable
fun TransactionsLazyColumn() {
    LazyColumn(){
        item {
            TransactionItems()
        }
    }
}

@Composable
fun TransactionItems(){

    Row(Modifier.fillMaxWidth()){
    }
}
@Composable
fun AddFloatingButton(modifier: Modifier) {
    FloatingActionButton(onClick = { /*TODO*/ },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(50),
        modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(vertical = 10.dp),
            text = "اضافه کردن تراکنش",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryLight
        )
    }
}


