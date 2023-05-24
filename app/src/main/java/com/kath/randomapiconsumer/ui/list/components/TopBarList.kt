package com.kath.randomapiconsumer.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kath.randomapiconsumer.R

@Composable
fun TopBarList(
    orderByLastName: () -> Unit,
    orderByAge: () -> Unit,
    orderByDefault: () -> Unit,
) {

    var taskMenuOpen by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colors.primary)

    ) {
        Text(text = stringResource(id = R.string.title_list),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterStart),
            color = Color.White)

        IconButton(
            onClick = { taskMenuOpen = true },
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Acciones para tarea", tint = Color.White
            )

            DropdownMenu(
                expanded = taskMenuOpen,
                onDismissRequest = {
                    taskMenuOpen = false
                }
            ) {
                DropdownMenuItem(
                    onClick = {
                        orderByLastName.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.by_lastname))
                }

                DropdownMenuItem(
                    onClick = {
                        orderByAge.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.by_age))
                }

                DropdownMenuItem(
                    onClick = {
                        orderByDefault.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.by_default))
                }
            }
        }
    }
}