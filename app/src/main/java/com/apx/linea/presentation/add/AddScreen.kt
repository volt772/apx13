package com.apx.linea.presentation.add

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx.linea.R
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.presentation.ApxApp.Companion.appContext
import com.apx.linea.presentation.MainViewModel
import com.apx.linea.presentation.dialog.LineaDateField
import com.apx.linea.presentation.ext.FileExt.copyUriToInternalStorage
import com.apx.linea.presentation.ui.theme.AxGray400
import com.apx.linea.presentation.ui.theme.AxGray500
import com.apx.linea.presentation.ui.theme.AxIconGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddScreen(
    viewModel: MainViewModel,
    linea: LineaModel?= null,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val isEditMode = linea != null

    var name by remember { mutableStateOf(linea?.name.orEmpty()) }
    var number by remember { mutableStateOf(linea?.number.orEmpty()) }
    var memo by remember { mutableStateOf(linea?.memo.orEmpty()) }
    var mydate by remember { mutableStateOf(linea?.mydate?: LocalDate.now()) }
    var photoPath by remember { mutableStateOf(linea?.photoPath.orEmpty()) }

    val outLinedTextRequester = remember { BringIntoViewRequester() }
    val outLinedTextFocusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 이미지 선택 결과 처리
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val savedPath = copyUriToInternalStorage(context, it)
            photoPath = savedPath.orEmpty()  // 👉 DB에 저장될 경로
        }
    }


    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "수정" else "등록",
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "뒤로가기",
                            tint = AxIconGray
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            saveLinea(
                                isEditMode = isEditMode,
                                linea = linea,
                                name = name,
                                number = number,
                                mydate = mydate,
                                memo = memo,
                                photoPath = photoPath,
                                viewModel = viewModel,
                                onSaved = onSaved
                            )
                        })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "저장",
                            tint = AxIconGray
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize() // 중요!
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    if (it.length <= 15) name = it
                },
                label = { Text("FD1") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AxGray400,
                    unfocusedBorderColor = AxGray400
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LineaDateField (
                purchaseDate = mydate,
                onDateSelected = { mydate = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                singleLine = true,
                label = { Text("FD2") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AxGray400,
                    unfocusedBorderColor = AxGray400
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                label = { Text("FD3") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .focusRequester(outLinedTextFocusRequester)
                    .bringIntoViewRequester(outLinedTextRequester)
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            coroutineScope.launch {
                                delay(100) // 키보드 애니메이션 기다림
                                outLinedTextRequester.bringIntoView()
                            }
                        }
                    },
                maxLines = 20,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AxGray400,
                    unfocusedBorderColor = AxGray400
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { imagePickerLauncher.launch("image/*") }
                    .border(1.dp, AxGray400, RoundedCornerShape(4.dp))
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(AxGray500)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = selectedImageUri?.lastPathSegment ?: "사진",
                )
            }
        }
    }
}

fun saveLinea(
    isEditMode: Boolean,
    linea: LineaModel?,
    name: String,
    number: String,
    memo: String?,
    mydate: LocalDate,
    photoPath: String?,
    viewModel: MainViewModel,
    onSaved: () -> Unit
) {
    when {
        name.isBlank() -> {
            Toast.makeText(appContext, "name을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        number.isBlank() -> {
            Toast.makeText(appContext, "number를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
    }

    if (name.isNotBlank() && number.isNotBlank() && mydate != null) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val newLinea = LineaModel(
            id = linea?.id?: 0L,
            name = name,
            number = number,
            memo = memo?.trim().takeIf { !it.isNullOrBlank() } ?: "",
            mydate = mydate,
            photoPath = photoPath?.trim().takeIf { !it.isNullOrBlank() } ?: "",
            createdAt = LocalDateTime.now().format(formatter)
        )

        val message = if (isEditMode) {
            viewModel.updateLinea(newLinea)
            "수정되었습니다."
        } else {
            viewModel.insertLinea(newLinea)
            "추가되었습니다."
        }

        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
        onSaved()
    }
}


