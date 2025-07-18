package com.apx.linea.data.remote

import com.squareup.moshi.Json

data class LineaResponseDto(
    @field:Json(name = "resp")
    val weatherData: String,

//    @field:Json(name = "weather")
//    val weatherData: List<WeatherDto>,
//
//    @field:Json(name = "main")
//    val main: WeatherMainDto,
//
//    @field:Json(name = "name")
//    val name: String,
//
//    @field:Json(name = "cod")
//    val cod: Int
)
