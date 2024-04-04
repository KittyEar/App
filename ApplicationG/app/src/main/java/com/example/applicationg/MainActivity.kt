package com.example.applicationg

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.applicationg.ui.theme.ApplicationGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationGTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    var ganZhiDateTime by remember { mutableStateOf(MyAppWidget().dealFontColor(MyAppWidget().getGanZhiDateTime())) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = ganZhiDateTime,
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                // 点击事件处理程序
                // 这里可以添加您的点击逻辑
                ganZhiDateTime = MyAppWidget().dealFontColor(MyAppWidget().getGanZhiDateTime())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApplicationGTheme {
        Greeting()
    }
}
