package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MaterialTheme {
                    MoneyTrackScreen()
                }
            }
        }
    }
}

data class MoneyTrack(
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val gambar: Int
)

val moneyList = listOf(
    MoneyTrack("Makan Siang", "Makan di kantin kampus", "Rp15000", R.drawable.makan),
    MoneyTrack("Transport", "Naik ojek ke kampus", "Rp10000", R.drawable.transport),
    MoneyTrack("Jajan", "Beli kopi dan snack", "Rp20000", R.drawable.jajan),
    MoneyTrack("Internet", "Beli paket data", "Rp30000", R.drawable.internet),
    MoneyTrack("Tabungan", "Menabung harian", "Rp10000", R.drawable.tabungan)
)

@Composable
fun MoneyTrackScreen() {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "MoneyTrack Student",
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(16.dp))

        moneyList.forEach { item ->
            DetailScreen(item)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailScreen(data: MoneyTrack) {

    var isFavorite by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Box {

            Image(
                painter = painterResource(id = data.gambar),
                contentDescription = data.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {

                Icon(
                    imageVector =
                        if (isFavorite) Icons.Filled.Favorite
                        else Icons.Outlined.FavoriteBorder,

                    contentDescription = "Favorite",

                    tint =
                        if (isFavorite) Color.Red
                        else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = data.nama,
            fontWeight = FontWeight.Bold
        )

        Text(text = data.deskripsi)

        Text(
            text = data.harga,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { }) {
            Text("Catat Pengeluaran")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoneyTrackPreview() {
    MyApplicationTheme {
        MoneyTrackScreen()
    }
}