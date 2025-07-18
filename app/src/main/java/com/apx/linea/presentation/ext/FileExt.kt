package com.apx.linea.presentation.ext

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import kotlin.io.copyTo
import kotlin.io.use

object FileExt {

    /* APX107에 복사*/
    fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
        val fileName = getFileName(context, uri) ?: return null
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.filesDir, fileName)

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath // ✅ 이 경로를 DB에 저장
    }

    /* 진짜 파일이름 뽑아주는 함수*/
    fun getFileName(context: Context, uri: Uri): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        returnCursor?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                return cursor.getString(nameIndex)
            }
        }
        return null
    }

}