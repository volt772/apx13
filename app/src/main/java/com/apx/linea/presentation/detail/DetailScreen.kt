package com.apx.linea.presentation.detail

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apx.linea.R
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.presentation.ui.theme.AxGray100
import com.apx.linea.presentation.ui.theme.AxText
import com.apx.linea.presentation.ui.theme.AxThird

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    linea: LineaModel,
    onBack: () -> Unit,
    onEdit: (LineaModel) -> Unit,
    onDelete: (LineaModel) -> Unit
) {
    val scrollState = rememberScrollState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm") },
            text = { Text("정말 삭제하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(linea)
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Go Back",
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val imageUri = linea.photoPath
                ?.takeIf { it.isNotBlank() }
                ?.let { Uri.parse(it) }

            // 이미지
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                fallback = painterResource(R.drawable.bg_no_img),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AxGray100)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LabelWithValue(label = "NAME", value = linea.name)
            dividerBetweenItem()
            LabelWithValue(label = "NUMBER", value = linea.number)
            dividerBetweenItem()
            LabelWithValue(label = "MEMO", value = linea.memo?: "")

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        showDeleteDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "삭제",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onEdit(linea) },
                    colors = ButtonDefaults.buttonColors(containerColor = AxThird),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "수정",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun dividerBetweenItem() {
    Spacer(modifier = Modifier.height(8.dp))
    Divider( color = Color.LightGray, thickness = 1.dp, modifier = Modifier .fillMaxWidth() .padding(vertical = 8.dp) )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LabelWithValue(label: String, value: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}