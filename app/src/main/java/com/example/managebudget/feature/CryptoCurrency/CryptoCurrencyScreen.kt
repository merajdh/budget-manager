package com.example.managebudget.feature.CryptoCurrency

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.palette.graphics.Palette
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.managebudget.extensions.formatNumberWithCurrency
import com.example.managebudget.ui.theme.ExpanseColor
import com.example.managebudget.ui.theme.IncomingColor
import com.example.managebudget.ui.theme.LightGray
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun CryptoCurrencyScreen(
) {

    val viewModel = getNavViewModel<CryptoCurrencyViewModel>()
    viewModel.getDollarData()
    viewModel.getCryptoData()
    val dollar = viewModel.dollarData.observeAsState()
    val crypto = viewModel.cryptoData.observeAsState()

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

                        Text(
                            modifier = Modifier
                                .wrapContentSize(Alignment.CenterEnd)
                                .padding(end = 15.dp, bottom = 20.dp),
                            text = "قیمت ارزهای دیجیتال",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                if (crypto.value != null && dollar.value != null) {

                    items(crypto.value!!.data) {

                        if (it.rAW != null) {

                            val persianDigits =
                                arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

                            val cryptoPrice =
                                String.format("%.15f", it.rAW.uSD.pRICE).toDouble()

                            val price =
                                cryptoPrice * dollar.value!!.p.replace(",", "").dropLast(1)
                                    .toInt()
                            val fullPrice = String.format("%.15f", price)
                            var fullConverted = if (fullPrice.toDouble() >= 10000) {
                                fullPrice.toDouble().toUInt().toString()

                            } else {
                                fullPrice.substring(0, 6)

                            }
                            val convertedPrice =
                                formatNumberWithCurrency(fullConverted.toDouble())
                            Log.v("datas", fullPrice.toString())
                            val resultColor = when {
                                it.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                    .toDouble() < 0 -> {
                                    ExpanseColor
                                }

                                it.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                    .toDouble() > 0 -> {
                                    IncomingColor
                                }

                                it.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                                    .toDouble() == 0.0 -> {
                                    LightGray
                                }

                                else -> {
                                    LightGray
                                }
                            }


                            val textChange =
                                it.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4).map {
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
                                cryptoName = it.coinInfo.fullName,
                                cryptoNickname = it.coinInfo.name,
                                cryptoIcon = "https://www.cryptocompare.com" + it.coinInfo.imageUrl,
                                price = convertedPrice,
                                top24Hour = textChange.substring(0, 4),
                                changeColor = resultColor


                            )
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

@Preview()
@Composable
fun pre() {
}

