package com.example.managebudget.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(modifier : Modifier  , onChanged  : (String) -> Unit) {

    val chipList: List<String> = listOf(
       "هزینه ها","درآمد ها", "هفتگی", "ماهانه",  "کلی"
    )

    var selected by remember {
        mutableStateOf("کلی")
    }

    Row(
        modifier
    ) {

        chipList.reversed().forEach { it ->

            Chip(title = it, selected = selected, onSelected =
            {

                onChanged.invoke(it)
                selected = it
            })
        }
    }

}

@Composable
fun Chip(
    title: String, selected: String, onSelected: (String) -> Unit
) {
    val isSelected = selected == title
    val background = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    val onBackground = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background,)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(12.dp))
            .clickable(
                onClick = {
                    onSelected(title)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 5.dp , bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
                Text(text = title, style = MaterialTheme.typography.bodyMedium ,  color =onBackground )


        }
    }
}