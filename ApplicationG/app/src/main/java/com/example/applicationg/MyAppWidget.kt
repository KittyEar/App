package com.example.applicationg


import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.state.updateAppWidgetState

import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import org.hothub.calendarist.Calendarist
import org.hothub.calendarist.pojo.CycleDate
import org.hothub.calendarist.pojo.SolarDate
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

// 定义一个 StateFlow 来保存状态
val ganZhiDateTimeState = MutableStateFlow("")

class MyAppWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            // create your AppWidget here
            myContent(context, id)
        }

    }
    @Composable
    private fun myContent(context: Context, id: GlanceId) {
        val context1 = context
        //val ganZhiDateTime = remember { mutableStateOf(getGanZhiDateTime()) }
        // 观察状态以更新 UI
        val ganZhiDateTime = ganZhiDateTimeState.collectAsState()
        // 读取状态

        //val ganZhiDateTime = rememberUpdatedState(getGanZhiDateTime())

//        // 设置每隔奇数小时执行
//        LaunchedEffect(id) {
//            while (true) {
//                // 计算距离下一个奇数小时的时间
//                val currentTime = LocalDateTime.now()
//                var nextOddHour = currentTime.plusHours(1)
//                if (nextOddHour.hour % 2 == 0) { // 如果下一个小时是偶数小时，跳过一个小时
//                    nextOddHour = nextOddHour.plusHours(1)
//                }
//
//                nextOddHour = nextOddHour.withMinute(0).withSecond(0).withNano(0)
//                val delayMillis = Duration.between(currentTime, nextOddHour).toMillis()
//
//                // 延迟执行到下一个奇数小时
//                delay(delayMillis)//挂起
//
//                // 更新干支时间
//                ganZhiDateTime.value = getGanZhiDateTime()
//            }
//        }
        Column(
            modifier = GlanceModifier.fillMaxSize().clickable { ganZhiDateTime.value },
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            //val ganZhiDateTime = getGanZhiDateTime()
            Text(text = ganZhiDateTime.value, style = TextStyle(fontSize = 24.sp,fontFamily = FontFamily.SansSerif, color = ColorProvider(
                Color(247, 246, 245)
            ), fontWeight = FontWeight.Bold), modifier = GlanceModifier.padding(horizontal = 8.dp))
        }
    }

    // 示例：这里只是一个占位函数，请替换为你的干支时间计算逻辑
   fun getGanZhiDateTime(): String {
        // 实现干支时间计算逻辑
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 月份从1开始计数
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY) // 24小时制
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val mills = calendar.get(Calendar.MILLISECOND)
        //阳历日期
        val solarDate = SolarDate(year, month, day, hour, minute, second, mills)

        //设置要转换的阳历日期
        val calendarist: Calendarist = Calendarist.fromSolar(solarDate)
        val cycleDate: CycleDate = calendarist.toCycle()


        return dealString(cycleDate) + second
    }
    private fun countTime() : Long{
        val currentTime = LocalDateTime.now()
        var nextOddHour = currentTime.plusHours(1)
        if (nextOddHour.hour % 2 == 0) { // 如果下一个小时是偶数小时，跳过一个小时
            nextOddHour = nextOddHour.plusHours(1)
        }

        nextOddHour = nextOddHour.withMinute(0).withSecond(0).withNano(0)
        return Duration.between(currentTime, nextOddHour).toMillis()
    }
    //让甲子竖着排
    private fun dealString(cycleDate: CycleDate): String {
        //天干
        val heavenlyStems = cycleDate.eraYear[0] + " " + cycleDate.eraMonth[0] + " " + cycleDate.eraDay[0] + " " + cycleDate.eraHour[0]
        //地支
        val earthlyBranches = cycleDate.eraYear[1] + " " + cycleDate.eraMonth[1] + " " + cycleDate.eraDay[1] + " " + cycleDate.eraHour[1]

        return  heavenlyStems + "\n" + earthlyBranches
    }

    //处理字的颜色,可惜，Glance库不支持AnnotatedString

    fun dealFontColor(afterDealString: String): AnnotatedString {

        val coloredText = buildAnnotatedString {
            afterDealString.forEach { char ->
                when (char) {
                    '甲' , '乙' , '寅', '卯' -> {
                        // 如果字符是甲，设置为绿色
                        withStyle(style = SpanStyle(color = Color.Green)) {
                            append(char)
                        }
                    }
                    '丙', '丁' , '巳', '午'  -> {
                        // 如果字符是丙，设置为红色
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(char)
                        }
                    }
                    '戊', '己', '丑', '辰', '未', '戌' -> {
                        withStyle(style = SpanStyle(color = Color.DarkGray)) {
                            append(char)
                        }
                    }
                    '庚', '辛' , '申', '酉'  -> {
                        withStyle(style = SpanStyle(color = Color.Yellow)) {
                            append(char)
                        }
                    }
                    else -> {
                        // 其他字符保持默认颜色
                        append(char)
                    }
                }
            }
        }
        return coloredText
    }
}
