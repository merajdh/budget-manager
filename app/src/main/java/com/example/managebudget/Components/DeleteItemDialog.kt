package com.example.managebudget.Components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.managebudget.R
import com.example.managebudget.feature.Wallet.DeleteItemViewModel
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.ui.theme.PrimaryLight
import dev.burnoo.cokoin.navigation.getNavViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalUnitApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DeleteDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit
) {

    val deleteDialog = getNavViewModel<DeleteItemViewModel>()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.anim_delete
        )
    )
    val animProgress by animateLottieCompositionAsState(composition)

    ManageBudgetTheme {


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
                    .fillMaxHeight(0.35f)
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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(25.dp))


                    LottieAnimation(
                        applyOpacityToLayers = true,
                        clipToCompositionBounds = true,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxSize(),
                        composition = composition,
                        progress = { animProgress })
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "آیا می خواهید این تراکنش را حذف کنید ؟",
                        modifier = Modifier.weight(0.5f),
                        color = MaterialTheme.colorScheme.onPrimary.copy(0.9f),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W400,
                        fontSize = TextUnit(16f , TextUnitType.Sp)
                        )
                    Row(
                        Modifier
                            .weight(0.3f)
                            .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.width(30.dp))
                        Button(
                            onClick = {
                                onDismiss.invoke()
                                deleteDialog.onDismiss()
                            },
                            modifier = Modifier.weight(0.55f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp, pressedElevation = 12.dp
                            )

                        ) {
                            Text(text = "انصراف", style = MaterialTheme.typography.bodyMedium)

                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = {
                                deleteDialog.onClick()
                                onConfirm.invoke()
                                onDismiss.invoke()

                            },
                            modifier = Modifier.weight(0.45f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp, pressedElevation = 12.dp
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
                }
            }
        }
    }
}