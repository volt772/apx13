package com.apx.linea.data.mappers

import com.apx.linea.data.local.entity.Linea
import com.apx.linea.domain.model.LineaModel

fun Linea.toModel(): LineaModel = LineaModel(
    id = id,
    name = name,
    number = number,
    memo = memo,
    mydate = mydate,
    photoPath = photoPath,
    createdAt = createdAt
)

fun LineaModel.toEntity(): Linea = Linea(
    id = id,
    name = name,
    number = number,
    memo = memo,
    mydate = mydate,
    photoPath = photoPath,
    createdAt = createdAt
)