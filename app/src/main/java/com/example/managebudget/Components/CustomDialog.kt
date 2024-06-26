package com.example.managebudget.Components

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.managebudget.R
import com.example.managebudget.data.RadioItems
import com.example.managebudget.extensions.formatTomanWithCommas
import com.example.managebudget.feature.Wallet.WalletDialogViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.PrimaryLight
import dev.burnoo.cokoin.navigation.getNavViewModel
import java.time.LocalDate
import java.util.Calendar



@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalUnitApi::class)
@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val walletViewModel = getNavViewModel<WalletViewModel>()
    val dialogViewModel = getNavViewModel<WalletDialogViewModel>()

    val context = LocalContext.current
    var colorStateInCome by remember {
        mutableStateOf(IncomingColor)
    }
    var colorStateExpanse by remember {
        mutableStateOf(ExpanseColor.copy(alpha = 0.2f))
    }
    var radiobuttonType by remember {
        mutableStateOf(false)
    }
    val radioState = remember {
        mutableStateListOf(
            RadioItems(
                true,
                "خرج",
                R.drawable.ic_down_growth,
            ),
            RadioItems(
                false,
                "درآمد",
                R.drawable.ic_up_growth,
            )
        )

    }

    val transactionCount by walletViewModel.transactionCount.observeAsState()
    val transactionName by walletViewModel.transactionName.observeAsState()
    val transactionType by walletViewModel.transactionType.observeAsState()
    val transactionTime by walletViewModel.transactionTime.observeAsState()


    Dialog(
        onDismissRequest = { onDismiss.invoke() }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(

            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(
                                0.7f
                            ), MaterialTheme.colorScheme.primary
                        )
                    ), shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            0.0f to MaterialTheme.colorScheme.primary,
                            700.0f to MaterialTheme.colorScheme.onBackground.copy(
                                0.9f
                            ), start = Offset.Zero, end = Offset.Infinite
                        ), shape = RoundedCornerShape(16.dp)
                    )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f)
                        .padding(top = 5.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "تراکنش جدید",
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .padding(vertical = 5.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        letterSpacing = TextUnit(3f, TextUnitType.Sp)
                    )
                }



                Spacer(modifier = Modifier.weight(0.02f))
                Text(
                    text = "نوع تراکنش",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 30.dp, bottom = 5.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 10.dp
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .weight(0.09f)
                        .padding(end = 30.dp)
                        .fillMaxWidth(0.34f)
                ) {


                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        radioState.forEachIndexed { index, info ->
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        brush = if (index == 0) {
                                            Brush.horizontalGradient(
                                                listOf(
                                                    colorStateExpanse,
                                                    ExpanseColor.copy(alpha = 0.03f)
                                                )
                                            )
                                        } else {
                                            Brush.horizontalGradient(
                                                listOf(
                                                    IncomingColor.copy(alpha = 0.03f),
                                                    colorStateInCome,
                                                )
                                            )
                                        },
                                        shape = if (index == 0) {
                                            RoundedCornerShape(
                                                topStart = 10.dp,
                                                bottomStart = 10.dp
                                            )
                                        } else {
                                            RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                                        }
                                    )
                                    .weight(0.3f)

                                    .clickable {

                                        radioState.replaceAll {
                                            it.copy(
                                                isSelected = it.title == info.title
                                            )
                                        }
                                    }
                            ) {

                                RadioButton(
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.Transparent,
                                        unselectedColor = Color.Transparent
                                    ), selected = info.isSelected == transactionType, onClick = {

                                        walletViewModel.transactionType.value = info.isSelected
                                        radioState.replaceAll {
                                            it.copy(
                                                isSelected = it.title == info.title
                                            )

                                        }

                                        if (index == 0) {
                                            radiobuttonType = true
                                            colorStateExpanse = ExpanseColor
                                            colorStateInCome = IncomingColor.copy(alpha = 0.3f)
                                        } else {
                                            radiobuttonType = false

                                            colorStateInCome = IncomingColor
                                            colorStateExpanse = ExpanseColor.copy(alpha = 0.3f)
                                        }
                                    })
                                if (index == 0) {
                                    Row {
                                        Icon(
                                            painter = painterResource(id = info.icon),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                } else {
                                    Row {
                                        Icon(
                                            painter = painterResource(id = info.icon),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(20.dp)


                                        )

                                    }
                                }


                            }

                        }
                    }
                }
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = "اطلاعات تراکنش",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 30.dp, bottom = 5.dp)
                )

                MainTextField(
                    icon = R.drawable.ic_transaction_name,
                    modifier = Modifier.weight(0.1f),
                    keyboardType = KeyboardType.Text,
                    edtName = "نام تراکنش",
                    supportText = null,
                    textValue = transactionName.toString()
                ) {
                    walletViewModel.transactionName.value = it
                }
                Spacer(modifier = Modifier.weight(0.02f))

                MainTextField(
                    icon = R.drawable.ic_wallet_unselected,
                    modifier = Modifier.weight(0.1f),
                    keyboardType = KeyboardType.NumberPassword,
                    supportText = "تومان",
                    edtName = "مقدار تراکنش",
                    textValue =
                    transactionCount.toString()
                ) {
                    walletViewModel.transactionCount.value = it

                }
                if (transactionCount != "") {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                        Text(
                            text = formatTomanWithCommas(transactionCount?.toLong() ?: 0),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = TextUnit(14f, TextUnitType.Sp),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(start = 32.dp, top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(0.13f))


                Row(
                    Modifier
                        .weight(0.1f)
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = {
                            onDismiss.invoke()
                            dialogViewModel.onDismiss()
                        },
                        modifier = Modifier.weight(0.4f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 12.dp
                        )

                    ) {
                        Text(text = "انصراف", style = MaterialTheme.typography.bodyMedium)

                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            val formattedDate = Calendar.getInstance()
                            if (transactionCount != "" && transactionName != "") {
                                val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    LocalDate.now()
                                } else {
                                    Calendar.getInstance()
                                }
                                walletViewModel.transactionTime.value = today.toString()
                            walletViewModel.getAll()
                            dialogViewModel.onClick()
                            onConfirm.invoke()
                            onDismiss.invoke()
                        } else {
                        Toast.makeText(
                            context,
                            "لطفا اطلاعات خواسته شده را وارد کنید ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.weight(0.6f),
                shape = RoundedCornerShape(14.dp),
                colors =
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 16.dp
                )
                ) {

                Text(
                    text = "تایید",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryLight
                )

            }
                Spacer(modifier = Modifier.width(30.dp))

            }

            Spacer(modifier = Modifier.weight(0.05f))
        }
    }
}


}


