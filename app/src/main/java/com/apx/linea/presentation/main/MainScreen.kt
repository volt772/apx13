package com.apx.linea.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx.apx108.presentation.topbar.TopBarWithSearch
import com.apx.linea.BuildConfig
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.presentation.MainViewModel
import com.apx.linea.presentation.ads.BannersAds
import com.apx.linea.presentation.state.CommonState
import com.apx.linea.presentation.ui.theme.AxFabBackground
import com.apx.linea.presentation.ui.theme.AxFabIcon
import com.apx.linea.presentation.ui.theme.AxSecondary
import com.apx.linea.presentation.ui.theme.AxWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onAddClick: () -> Unit,
    onItemClick: (LineaModel) -> Unit,
    onDeleteClick: (LineaModel) -> Unit
) {

    val lineaState by viewModel.lineaState.collectAsStateWithLifecycle()

    var isSearching by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AxWhite)
    ) {
        // 상단 앱바 (Material3 기준)
        TopBarWithSearch(
            isSearching = isSearching,
            query = query,
            onQueryChange = {
                query = it
                viewModel.searchLinea(query)
            },
            onSearchClick = { isSearching = true },
            onCloseClick = {
                isSearching = false
                query = ""
                viewModel.getAllLineas()
            }
        )

        // 아이디어 리스트 + FAB
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (lineaState) {
                is CommonState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is CommonState.Success -> {
                    val lineas = (lineaState as CommonState.Success<List<LineaModel>>).data

                    if (lineas.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "리스트가 없습니다.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentPadding = PaddingValues(2.dp),
                        ) {
                            items(lineas) { linea ->
                                LineaCard(
                                    linea = linea,
                                    onClick = onItemClick,
                                    onDelete = onDeleteClick
                                )
                            }
                        }
                    }
                }

                is CommonState.Error -> {
                    println("probe :: error : ${(lineaState as CommonState.Error).message}")
                    Text(
                        text = "오류 발생",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
            }

            // FAB (Material3 기준)
            FloatingActionButton(
                onClick = { onAddClick() },
                containerColor = AxFabBackground,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "추가", tint = AxFabIcon)
            }
        }

        if (BuildConfig.DEBUG) {
            Button(
                onClick = { viewModel.insertDummyLineas() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text("더미 데이터 30개 삽입")
            }
        }

        // 하단 고정 배너 광고
        BannersAds()
    }
}
