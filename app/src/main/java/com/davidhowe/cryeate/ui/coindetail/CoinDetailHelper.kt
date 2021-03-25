package com.davidhowe.cryeate.ui.coindetail

import android.content.Context
import android.graphics.Color
import com.davidhowe.cryeate.R
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.ArrayList

object CoinDetailHelper {

    fun getStartingData(context: Context, priceData: List<List<Double>>) : LineData {
        val lineData = LineData()
        val entries = ArrayList<Entry>()
        for (index in priceData.indices) entries.add(Entry(index.toFloat(), priceData[index][1].toFloat()))
        val lineDataSet = LineDataSet(entries, "Dates")
        lineDataSet.color = context.resources.getColor(R.color.teal_200)
        lineDataSet.setCircleColorHole(Color.TRANSPARENT)
        lineDataSet.lineWidth = 4f
        lineDataSet.fillColor = context.resources.getColor(R.color.teal_200)
        lineDataSet.circleRadius = 8f
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawValues(false)
        lineDataSet.valueTextSize = 12f
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.isHighlightEnabled = false
        lineDataSet.setCircleColor(context.resources.getColor(R.color.teal_200))
        lineData.addDataSet(lineDataSet)
        return lineData
    }
}