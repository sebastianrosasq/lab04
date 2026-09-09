package com.example.moviecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecounter.ui.theme.MovieCounterTheme

// 1. Modelo de datos para el reto
data class MovieTask(
    val id: Int,
    val label: String,
    var initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WellnessMovieScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 2. Stateful Composable (Administra la lista y el estado)
@Composable
fun WellnessMovieScreen(modifier: Modifier = Modifier) {
    var movieName by rememberSaveable { mutableStateOf("") }
    val movieList = remember { mutableStateListOf<MovieTask>() }
    var idCounter by rememberSaveable { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Películas registradas: ${movieList.size}")
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = movieName,
            onValueChange = { movieName = it },
            label = { Text("Nombre de la película") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (movieName.isNotBlank()) {
                    movieList.add(MovieTask(id = idCounter++, label = movieName.trim()))
                    movieName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar a la lista")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Renderizado de la lista con elevación de estado
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                items = movieList,
                key = { task -> task.id }
            ) { task ->
                MovieTaskItem(
                    taskName = task.label,
                    checked = task.checked,
                    onCheckedChange = { isChecked -> task.checked = isChecked },
                    onClose = { movieList.remove(task) }
                )
            }
        }
    }
}

// 3. Stateless Composable (Elemento individual reutilizable)
@Composable
fun MovieTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Eliminar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessMovieScreenPreview() {
    MovieCounterTheme {
        WellnessMovieScreen()
    }
}