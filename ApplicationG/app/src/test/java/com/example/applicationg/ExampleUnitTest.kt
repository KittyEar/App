package com.example.applicationg

import kotlinx.coroutines.delay
import org.hothub.calendarist.Calendarist
import org.hothub.calendarist.pojo.CycleDate
import org.hothub.calendarist.pojo.LunarDate
import org.hothub.calendarist.pojo.SolarDate
import org.junit.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun main() {
        // 计算距离下一个奇数小时的时间
        val currentTime = LocalDateTime.now()
        var nextOddHour = currentTime.plusHours(1)
        if (nextOddHour.hour % 2 == 0) { // 如果下一个小时是偶数小时，跳过一个小时
            nextOddHour = nextOddHour.plusHours(1)
        }

        nextOddHour = nextOddHour.withMinute(0).withSecond(0).withNano(0)
        val delayMillis = Duration.between(currentTime, nextOddHour).toMillis()

        // 延迟执行到下一个奇数小时
        println(delayMillis / 1000 / 60)
        println(nextOddHour.hour)
    }

}