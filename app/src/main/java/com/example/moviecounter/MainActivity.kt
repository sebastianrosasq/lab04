package com.example.moviecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecounter.ui.theme.MovieCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StatefulMovieCounter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Componente Stateful (Gestiona el estado)
@Composable
fun StatefulMovieCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    var movieName by rememberSaveable { mutableStateOf("") }

    StatelessMovieCounter(
        count = count,
        movieName = movieName,
        onMovieNameChange = { movieName = it },
        onAddMovie = {
            if (movieName.isNotBlank()) {
                count++
                movieName = ""
            }
        },
        modifier = modifier
    )
}

@Composable
fun StatelessMovieCounter(
    count: Int,
    movieName: String,
    onMovieNameChange: (String) -> Unit,
    onAddMovie: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (count > 0) {
            Text("Has agregado $count películas.")
        } else {
            Text("No has agregado películas aún.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = movieName,
            onValueChange = onMovieNameChange,
            label = { Text("Nombre de la película") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddMovie,
            enabled = count < 10,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar película")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieCounter() {
    MovieCounterTheme {
        StatefulMovieCounter()
    }
}