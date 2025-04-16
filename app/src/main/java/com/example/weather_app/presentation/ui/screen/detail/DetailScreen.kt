package com.example.weather_app.presentation.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.weather_app.presentation.navigation.Screens

@Composable
fun DetailScreen(navController: NavController) {
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1a2340),
            Color(0xFF4d3d99),
            Color(0xFF874ead)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(40.dp)) // iOS notch spacing

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.weather),
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "19°C",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Precipitations",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.house),
            contentDescription = "City Image",
            modifier = Modifier
                .height(180.dp)
                .width(320.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(246.dp),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            val box = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF874ead),
                    Color(0xFF874ead),
                    Color(0xFF1a2340),
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(box)
            ) {
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Today", fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(
                            text = "July, 21",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Divider(modifier = Modifier.fillMaxWidth(), color = Color.White)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherReportItem(
                            degree = "19°C",
                            image = R.drawable.weather,
                            time = "15.00"
                        )
                        WeatherReportItem(
                            degree = "18°C",
                            image = R.drawable.mooncloud,
                            time = "16.00"
                        )
                        WeatherReportItem(
                            degree = "18°C",
                            image = R.drawable.mooncloud,
                            time = "17.00"
                        )
                        WeatherReportItem(
                            degree = "18°C",
                            image = R.drawable.mooncloud,
                            time = "18.00"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.navigate(Screens.LocationScreen.route) }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun WeatherReportItem(degree: String, image: Int, time: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = degree,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .height(66.dp)
                .width(66.dp)
        )
        Text(
            text = time,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
