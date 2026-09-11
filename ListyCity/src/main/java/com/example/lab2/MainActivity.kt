package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab2.ui.theme.Lab2Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()

        setContent {
            Lab2Theme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it)},
                        onDeleteCity = {cityRepository.deleteCity(it)},
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

//Following code is from Anthropic, Claude (Sonnet 5) "Why is my cities turning into
// items (count = cities) instead of items(cities)"
//Debugging & implementation, 2026-09-10
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity : (String) -> Unit,
    onDeleteCity : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf(value = "") }
    var selectedCity by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(all = 16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }

        Button(
            onClick = {
                if (selectedCity.isNotBlank()) {
                    onDeleteCity(selectedCity)
                    selectedCity = ""
                }
            }, modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Delete City")
        }
        }
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
                CityRow(
                    city = city,
                    onDelete = { selectedCity = city})
            }
        }
    }
}


@Composable

fun CityRow (city : String, onDelete:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ){
        Text(
            text = city,
            fontSize = 28.sp,
            modifier = Modifier
        )
        Button (onClick = onDelete) {
            Text("Select")
        }

    }
}


class CityRepository {
    private val _cities = mutableStateListOf("Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin",
        "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi")

    val cities : List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }
    fun deleteCity(city: String) {
        _cities.remove(city)
    }
}

