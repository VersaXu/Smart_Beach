package com.example.myhelloworldapplication.SVG

import android.graphics.Path

data class MapData(
        val name: String = "",
        val fillColor: String = "",
        val strokeColor: String = "",
        val strokeWidth: String = "",
        val pathData: Path,
        var isSelect: Boolean = false
){}
