package com.example.managebudget.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun MainTextField(
    icon: Int,
    modifier: Modifier,
    supportText : String?,
    keyboardType: KeyboardType,
    edtName: String,
    textValue: String?,
    onValueChange: (String) -> Unit
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            TextField(colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
            ),
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .border(
                        1.5.dp,
                        MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(12.dp)
                    ),

                shape = RoundedCornerShape(12.dp),
                value = textValue ?: "0 ",
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = keyboardType, imeAction = ImeAction.Next
                ),
                singleLine = true,

                leadingIcon = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    if (supportText != null) {
                        Text(
                            text = supportText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = TextUnit(12f, TextUnitType.Sp),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            modifier = Modifier.padding(end = 7.dp)
                        )
                    }
                },
                placeholder = {
                    Text(text = edtName, style = MaterialTheme.typography.bodyMedium)
                })

    }
}