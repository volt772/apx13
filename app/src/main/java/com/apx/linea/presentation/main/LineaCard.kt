package com.apx.linea.presentation.main

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apx.linea.R
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.presentation.ui.theme.AxCardBackground
import com.apx.linea.presentation.ui.theme.AxGray100
import com.apx.linea.presentation.ui.theme.AxGray500
import com.apx.linea.presentation.ui.theme.AxGray700
import com.apx.linea.presentation.ui.theme.AxThird

@Composable
fun LineaCard(
    linea: LineaModel,
    onClick: (LineaModel) -> Unit,
    onDelete: (LineaModel) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("삭제 확인") },
            text = { Text("이 제품을 삭제할까요?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(linea)
                    showDeleteDialog = false
                }) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    (Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onClick(linea) },
                onLongClick = { showDeleteDialog = true }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AxCardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = linea.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = linea.number,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = AxGray700
            )

//            // 메모
//            linea.memo?.takeIf { it.isNotBlank() }?.let {
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = it,
//                    style = MaterialTheme.typography.bodySmall,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
//                    color = AxGray500
//                )
//            }
        }
    })
}
