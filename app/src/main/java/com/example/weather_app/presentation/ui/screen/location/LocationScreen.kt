package com.example.weather_app.presentation.ui.screen.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather_app.R
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@Composable
fun LocationScreen(navController: NavController) {
    val brush = Brush.verticalGradient(
        listOf(
            Color(0XFF1a2340),
            Color(0XFF4d3d99),
            Color(0XFF874ead)
        )
    )

    val forecastList = listOf(
        Forecast("19°C", R.drawable.weather, "Mon"),
        Forecast("18°C", R.drawable.mooncloud, "Tue"),
        Forecast("18°C", R.drawable.mooncloud, "Wed"),
        Forecast("19°C", R.drawable.weather, "Thu"),
        Forecast("20°C", R.drawable.weather, "Fri"),
        Forecast("40°C", R.drawable.mooncloud, "Sat"),
        Forecast("33°C", R.drawable.weather, "Sun")
    )

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 60.dp)
        ) {
            Text(
                text = "North America",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Max: 24°",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Min: 18°",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))


        Text(
            text = "7-Days Forecast",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp),
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_arrowback),
                contentDescription = "Scroll Left",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(32.dp)
                    .clickable {
                        scope.launch {
                            lazyListState.animateScrollToItem(
                                (lazyListState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                            )
                        }
                    }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(172.dp)
            ) {
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    contentPadding = PaddingValues(horizontal = 48.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(forecastList) { forecast ->
                        LocationWeatherReportItem(
                            degree = forecast.degree,
                            image = forecast.image,
                            dayOfWeek = forecast.day
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Scroll Right",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(32.dp)
                    .clickable {
                        scope.launch {
                            lazyListState.animateScrollToItem(
                                (lazyListState.firstVisibleItemIndex + 1).coerceAtMost(forecastList.size - 1)
                            )
                        }
                    }
            )
        }
        val box = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF805BCA),
                Color(0xFF362A84)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .height(174.dp)
                .width(352.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(box)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_crosshairs),
                            contentDescription = "", tint = Color.White
                        )
                        Text(text = "AIR QUALITY", color = Color.White, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "3-Low Health Risk",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    val box1 = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF805BCA),
                            Color(0xFF362A84)
                        )
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(box1),
                        thickness = 0.dp,
                        color = DividerDefaults.color
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "See more", fontSize = 18.sp, color = Color.White)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Scroll Right",
                            tint = Color.White
                        )

                    }


                }


            }

        }
        Spacer(modifier = Modifier.height(40.dp))
        val brush1 = Brush.verticalGradient(
            listOf(
                Color(0XFF1a2340),
                Color(0XFF4d3d99),
                Color(0XFF874ead)
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherCard(
                title = "SUNRISE",
                time = "5:28",
                amPm = "AM",
                bottomText = "Sunset: 7:25PM",
                modifier = Modifier.weight(1f)
            )

            WeatherCard(
                title = "UV INDEX",
                time = "4",
                amPm = "",
                bottomText = "Moderate",
                modifier = Modifier.weight(1f)
            )
        }


    }
}

data class Forecast(val degree: String, val image: Int, val day: String)

@Composable
fun LocationWeatherReportItem(degree: String, image: Int, dayOfWeek: String) {
    val box = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF9A50A1),
            Color(0xFF482EAC)
        )
    )

    Card(
        modifier = Modifier
            .height(140.dp)
            .width(80.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(box)
                .padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = degree,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = dayOfWeek,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun WeatherCard(
    title: String,
    time: String,
    amPm: String,
    bottomText: String,
    modifier: Modifier = Modifier
) {
    val brush = Brush.verticalGradient(
        listOf(
            Color(0XFF1a2340),
            Color(0XFF4d3d99),
            Color(0XFF874ead)
        )
    )

    Card(
        modifier = modifier
            .height(150.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = time,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (amPm.isNotEmpty()) {
                        Text(
                            text = amPm,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Text(
                    text = bottomText,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
