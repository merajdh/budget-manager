package com.example.managebudget.feature.CryptoCurrency

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.palette.graphics.Palette
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.managebudget.R
import com.example.managebudget.extensions.NetworkChecker
import com.example.managebudget.extensions.formatNumberWithCurrency
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.LightDarkColor
import com.example.managebudget.ui.theme.LightGray
import com.example.managebudget.ui.theme.ManageBudgetTheme
import com.example.managebudget.ui.theme.PrimaryLight
import com.example.managebudget.ui.theme.Secondary
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.core.parameter.parametersOf
import java.util.Locale
import kotlin.math.absoluteValue


@Composable
fun CryptoCurrencyScreen(
) {
    val context = LocalContext.current

    val viewModel = getNavViewModel<CryptoCurrencyViewModel>(parameters = {
        parametersOf(NetworkChecker(context).isInternetConnected)
    })
    viewModel.refreshDollarData(NetworkChecker(context).isInternetConnected)
    viewModel.refreshCryptoData(NetworkChecker(context).isInternetConnected)
    val dollar = viewModel.dollarData.observeAsState()
    val crypto = viewModel.cryptoData.observeAsState()
    ManageBudgetTheme(color = MaterialTheme.colorScheme.secondary, isDark = false) {

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


                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.secondary),

                                ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .align(Alignment.End)
                                        .padding(bottom = 20.dp, top = 25.dp),
                                    text = "قیمت ارزهای دیجیتال",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = PrimaryLight
                                )
                                Card(
                                    shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(25.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                                ) {

                                }

                            }

                        }
                    }
                    if (crypto.value != null && dollar.value != null && NetworkChecker(context).isInternetConnected) {
                        val itemCount = crypto.value!!.data.size


                        items(itemCount) {
                            val data = crypto.value!!.data[it]


                            if (data.rAW != null) {

                                val persianDigits =
                                    arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

                                val cryptoPrice =
                                    String.format(
                                        locale = Locale.ENGLISH,
                                        "%.15f",
                                        data.rAW.uSD.pRICE
                                    ).toDouble()

                                val price =
                                    cryptoPrice * dollar.value!!.p.replace(",", "").dropLast(1)
                                        .toDouble()
                                val fullPrice =
                                    String.format(locale = Locale.ENGLISH, "%.15f", price)
                                val fullConverted = if (fullPrice.toDouble() >= 1000) {
                                    fullPrice.toDouble().toUInt().toString()

                                } else {
                                    fullPrice.substring(0, 6)

                                }
                                val convertedPrice =
                                    formatNumberWithCurrency(
                                        fullConverted.toDouble(),
                                        if (fullConverted.toDouble() >= 100) {
                                            ""

                                        } else {
                                            "0.00"

                                        }
                                    )
                                Log.v("datas", data.coinInfo.id)
                                val resultColor = when {
                                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                        .toDouble() < 0 -> {
                                        ExpanseColor
                                    }

                                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                        .toDouble() > 0 -> {
                                        IncomingColor
                                    }

                                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                        .toDouble() == 0.0 -> {
                                        Color(0xFFAAADB1)
                                    }

                                    else -> {
                                        LightGray
                                    }
                                }


                                val textChange =
                                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4).map {
                                        if (it != '.' && it != '-') {
                                            persianDigits[it.toString().toInt()]
                                        } else {
                                            if (it == '.') {
                                                '.'

                                            } else {
                                                '-'
                                            }
                                        }
                                    }.joinToString("")


                                CryptoPriceItems(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    cryptoName = data.coinInfo.fullName,
                                    cryptoNickname = data.coinInfo.name,
                                    cryptoIcon = "https://www.cryptocompare.com" + data.coinInfo.imageUrl,
                                    price = convertedPrice,
                                    top24Hour = if (textChange.endsWith('-')) {

                                        textChange.substring(0, 5)
                                    } else {
                                        textChange.substring(0, 4)

                                    },
                                    changeColor = resultColor
                                )
                                if (it != 14) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .height(2.5.dp)
                                            .align(Alignment.CenterHorizontally),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                                        shape = RoundedCornerShape(100)
                                    ) {

                                    }
                                }

                            }
                        }
                    } else {
                        item {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.crypto_data
                                )
                            )
                            val lottieAnimatable = rememberLottieAnimatable()

                            LaunchedEffect(Unit) {
                                lottieAnimatable.animate(
                                    composition,
                                    iterations = LottieConstants.IterateForever,
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                LottieAnimation(
                                    modifier = Modifier
                                        .size(60.dp),
                                    composition = composition,
                                    iterations = LottieConstants.IterateForever,


                                    )
                            }

                        }
                    }

                }

            }

        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun CryptoPriceItems(
    modifier: Modifier,
    cryptoName: String,
    cryptoNickname: String,
    changeColor: Color,
    cryptoIcon: String,
    price: String,
    top24Hour: String?
) {
    val text = remember {
        mutableStateOf("")
    }
    text.value = cryptoName

    var dominantColor by remember { mutableStateOf(Color.White) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = cryptoIcon) {
        val loadedBitmap = withContext(Dispatchers.IO) {
            BitmapFactory.decodeStream(java.net.URL(cryptoIcon).openStream())
        }

        bitmap.value = loadedBitmap
        val palette = Palette.from(loadedBitmap).generate()
        val dominantSwatch =
            palette.dominantSwatch ?: palette.vibrantSwatch ?: palette.lightVibrantSwatch
            ?: palette.darkVibrantSwatch
        dominantColor = dominantSwatch?.rgb?.let { Color(it) } ?: Color.White
    }


    Card(

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .padding(vertical = 12.dp)
                        .size(50.dp)
                ) {
                    bitmap.value?.asImageBitmap()?.let {

                        Box(

                            modifier = Modifier
                                .background(dominantColor, shape = CircleShape)
                                .size(50.dp)
                        ) {


                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(dominantColor, CircleShape)
                                    .clip(
                                        CircleShape
                                    )
                                    .padding(5.dp),
                                bitmap = it,
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }
                    }
                }

                Column {
                    Text(
                        text = cryptoName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = cryptoNickname,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = TextUnit(13f, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.onPrimary.copy(0.7f)
                    )


                }

            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = price.toString(),
                    fontSize = TextUnit(16f, TextUnitType.Sp),

                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                top24Hour?.let {
                    Text(
                        text = if (it == "-۰.۰") {
                            "-"
                        } else {
                            "$it%"

                        },
                        color = changeColor,
                        fontSize = TextUnit(14f, TextUnitType.Sp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
