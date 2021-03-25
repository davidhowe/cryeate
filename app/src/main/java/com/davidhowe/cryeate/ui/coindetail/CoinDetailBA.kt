package com.davidhowe.cryeate.ui.coindetail
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.utils.CustomFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import org.joda.time.DateTime
import timber.log.Timber
import java.text.DecimalFormat

@BindingAdapter("bindChartData")
fun bindChartData(chart: LineChart, stateUI: CoinDetailStateUI?) {

    Timber.d("stateUI=$stateUI")

    if (stateUI != null && stateUI is CoinDetailStateUI.ChartData) {
        Timber.d("chartData!=null")
        val xPoints = stateUI.priceData.map { DateTime(it[0].toLong()).toLocalDate().toString() }
        val lineData = CoinDetailHelper.getStartingData(chart.context, stateUI.priceData)

        chart.description.isEnabled = false
        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.setBorderColor(Color.TRANSPARENT)
        chart.setGridBackgroundColor(Color.TRANSPARENT)
        //chart.drawingCacheBackgroundColor = Color.TRANSPARENT
        chart.setDrawGridBackground(false)
        chart.isAutoScaleMinMaxEnabled = true
        chart.setPinchZoom(false)
        chart.isClickable = false
        chart.legend.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.setScaleEnabled(true)

        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        //leftAxis.axisMinimum = stateUI.priceData.minOf { it[1].toFloat() }
        //leftAxis.axisMaximum = stateUI.priceData.maxOf { it[1].toFloat() }
        val minY = stateUI.priceData.minOf { it[1] }
        leftAxis.setDrawTopYLabelEntry(true)
        leftAxis.textColor = chart.resources.getColor(R.color.blue_gray_50)

        val decimalFormatter = CustomFormatter.getPriceDecimalFormatter(minY)

        leftAxis.valueFormatter =
                IAxisValueFormatter { value: Float, axis: AxisBase? -> stateUI.currencySymbol + decimalFormatter.format(value).toString() }

        val xAxis = chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //xAxis.setAxisMinimum(0f);
        //xAxis.setAxisMinimum(0f);
        xAxis.granularity = 1f
        xAxis.textColor = chart.resources.getColor(R.color.blue_gray_50)
        xAxis.labelCount = xPoints.size
        xAxis.labelRotationAngle = 90.0f
        xAxis.setCenterAxisLabels(false)
        xAxis.spaceMin = 0.3f

        xAxis.xOffset = 50f
        xAxis.valueFormatter = IAxisValueFormatter { value: Float, axis: AxisBase? ->
            if (value >= 0 && value < xPoints.size)
                xPoints[value.toInt()]
            else
                ""
        }

        chart.data = lineData
        chart.invalidate()
    }
}

@BindingAdapter("showIfLoading")
fun View.showIfLoading(stateUI: CoinDetailStateUI?) {
    if(stateUI!=null && stateUI is CoinDetailStateUI.Loading) {
        visibility = if (stateUI.loading) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("hideIfLoading")
fun View.hideIfLoading(stateUI: CoinDetailStateUI?) {
    if(stateUI!=null && stateUI is CoinDetailStateUI.Loading) {
        visibility = if (stateUI.loading) View.GONE else View.VISIBLE
    }
}