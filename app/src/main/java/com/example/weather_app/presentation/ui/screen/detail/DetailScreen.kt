package com.example.weather_app.presentation.ui.screen.detail


import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather_app.R
import com.example.weather_app.data.remote.api.weaterapi
import com.example.weather_app.domain.model.MainViewModel
import com.example.weather_app.domain.repoistory.Repository
import com.example.weather_app.domain.usecase.ResultState
import com.example.weather_app.presentation.navigation.Screens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController) {
    val context = LocalContext.current
    val locationHelper = remember { LocationHelper(context) }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val brush = Brush.verticalGradient(
        listOf(Color(0XFF1a2340), Color(0XFF4d3d99), Color(0XFF874ead))
    )

    val repository = remember { Repository() }
    val viewModel = remember { MainViewModel(repository) }
    val state by viewModel.allWeatherReport.collectAsState()
    var allWeatherData by remember { mutableStateOf<weaterapi?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showSearchBox by remember { mutableStateOf(false) }
    var searchCity by remember { mutableStateOf("") }

    LaunchedEffect(permissionsState.allPermissionsGranted) {
        if (permissionsState.allPermissionsGranted) {
            locationHelper.getCurrentLocation(
                onLocationFound = { city ->
                    searchCity = city
                    viewModel.getWeather(city)
                },
                onError = {
                    viewModel.getWeather(searchCity)
                }
            )
        } else {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Text(text = "Error: $error", color = Color.Red)
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Succses -> {
            isLoading = false
            val succses = (state as ResultState.Succses).response
            allWeatherData = succses
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {

        val weatherData = allWeatherData
        if (weatherData != null) {

            val date = weatherData.forecast.forecastday.getOrNull(0)?.date
            val formattedDate = date?.let { formatDate(it) } ?: "--"

            val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AnimatedVisibility(visible = showSearchBox) {
                        OutlinedTextField(
                            value = searchCity,
                            onValueChange = { searchCity = it },
                            placeholder = {
                                Text(
                                    text = "Search city...",
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            },
                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        searchCity = ""
                                        showSearchBox = false
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(50),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (searchCity.isNotBlank()) {
                                        viewModel.getWeather(searchCity.trim())
                                        showSearchBox = false
                                    }
                                }
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White.copy(alpha = 0.1f),
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        )
                    }

                    if (!showSearchBox || searchCity.isBlank()) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.End)
                                .size(28.dp)
                                .clickable {
                                    showSearchBox = true
                                }
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.weather),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${weatherData.location.name}", fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "${weatherData.current.tempC}°C",
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Current Condition",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = weatherData.current.condition.text ?: "Condition",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Max: ${weatherData.forecast.forecastday[0].day.maxtempC}°C",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Min: ${weatherData.forecast.forecastday[0].day.mintempC}°C",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Humidity: ${weatherData.current.humidity ?: "--"}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )

                        Text(
                            text = "Wind: ${weatherData.current.windKph ?: "--"} km/h",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "Pressure: ${weatherData.current.pressureMb ?: "--"} mb",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
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
                                Text(
                                    text = "Today",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Text(
                                    text = formattedDate,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Divider(modifier = Modifier.fillMaxWidth(), color = Color.White)

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (i in 0 until 4) {
                                    val forecastHour =
                                        weatherData.forecast.forecastday[0].hour.getOrNull(
                                            currentTime + i
                                        )
                                    forecastHour?.let {
                                        WeatherReportItem(
                                            degree = "${it.tempC}°C",
                                            image = R.drawable.weather,
                                            time = "${currentTime + i}.00"
                                        )
                                    }
                                }
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

        } else {
            Text(text = "No weather data available.", color = Color.White)
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
            fontSize = 14.sp,
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
            fontSize = 14.sp
        )
    }
}

fun formatDate(date: String?): String {
    if (date.isNullOrBlank()) return "--"

    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = formatter.parse(date)
        val outputFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        outputFormatter.format(parsedDate ?: Date())
    } catch (e: Exception) {
        "--"
    }
}


