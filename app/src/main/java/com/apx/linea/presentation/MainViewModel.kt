package com.apx.linea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.domain.usecase.LineaUseCase
import com.apx.linea.preference.PrefManager
import com.apx.linea.presentation.state.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val lineaUseCase: LineaUseCase,
    private val prefManager: PrefManager
): ViewModel() {

    private val _isFirstRun: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFirstRun: StateFlow<Boolean> = _isFirstRun

    private val _lineaState = MutableStateFlow<CommonState<List<LineaModel>>>(CommonState.Loading)
    val lineaState: StateFlow<CommonState<List<LineaModel>>> = _lineaState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        getIsFirstRun()
        getAllLineas()
    }

    fun setIsFirstRun() {
        viewModelScope.launch {
            prefManager.setBoolean("IsFirstRun", false)
        }
    }
    private fun getIsFirstRun() {
        _isFirstRun.value = prefManager.getBoolean("IsFirstRun")
    }

    fun getAllLineas() {
        viewModelScope.launch {
            lineaUseCase.getAllLineas()
                .onStart { _lineaState.value = CommonState.Loading }
                .catch { e -> _lineaState.value = CommonState.Error(e.message?: "Unknown Error") }
                .collect { list -> _lineaState.value = CommonState.Success(list) }
        }
    }

    fun searchLinea(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            lineaUseCase.searchLineaByName(query)
                .onStart { _lineaState.value = CommonState.Loading }
                .catch { e -> _lineaState.value = CommonState.Error(e.message ?: "Unknown error") }
                .collect { list -> _lineaState.value = CommonState.Success(list) }
        }
    }

    fun insertLinea(linea: LineaModel) {
        viewModelScope.launch {
            lineaUseCase.insertLinea(linea)

            _lineaState.value = CommonState.Loading
            lineaUseCase.getAllLineas()
                .collect {
                    _lineaState.value = CommonState.Success(it)
                }
        }
    }

    fun updateLinea(linea: LineaModel) {
        viewModelScope.launch {
            lineaUseCase.updateLinea(linea)
        }
    }

    fun deleteLinea(linea: LineaModel) {
        viewModelScope.launch {
            lineaUseCase.deleteLinea(linea)
        }
    }

    fun getLineaById(id: Long, onResult: (LineaModel?) -> Unit) {
        viewModelScope.launch {
            val result = lineaUseCase.getLineaById(id)
            onResult(result)
        }
    }

    fun insertDummyLineas(count: Int = 30) {
        val photoList = listOf(
            "/storage/emulated/0/Download/allen.png",
            "/storage/emulated/0/Download/myface.png",
            "/storage/emulated/0/Download/doosan.png",
            ""
        )

        viewModelScope.launch {
            repeat(count) { index ->
                val startDate = LocalDate.of(2024, 1, 1)
                val endDate = LocalDate.of(2025, 7, 1)
                val randomDays = (0..ChronoUnit.DAYS.between(startDate, endDate)).random()
                val randomDate = startDate.plusDays(randomDays)

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val linea = LineaModel(
                    name = "테스트 제품 $index",
                    number = "SN2025-${1000 + index}",
                    memo = "자동 삽입된 테스트",
                    mydate = randomDate,
                    photoPath = photoList.random(),
                    createdAt = LocalDateTime.now().format(formatter)
                )
                lineaUseCase.insertLinea(linea)
            }
        }
    }
}