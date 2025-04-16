package com.example.weather_app.presentation.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.weather_app.presentation.navigation.Screens

@Composable
fun HomeScreen(navController: NavController) {
    val brush = Brush
        .verticalGradient(
            listOf(
                Color(0XFF1a2340),
                Color(0XFF4d3d99),
                Color(0XFF874ead)
            )
        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = com.example.weather_app.R.drawable.weather),
            contentDescription = "", modifier = Modifier
                .height(428.dp)
                .width(428.dp)
        )
        Text(
            text = "Weather", fontSize = 64.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ForeCasts",
            fontSize = 64.sp,
            color = Color(0XFFDDB130),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Screens.DetailScreen.route) }, modifier = Modifier
                .height(72.dp)
                .width(304.dp),
            colors = ButtonDefaults
                .buttonColors(containerColor = Color(0XFFDDB130))
        ) {
            Text(
                text = "Get Start",
                color = Color(0XFF4b3b7a),
                fontSize = 20.sp
            )
        }


    }
}