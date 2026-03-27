package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MoneyTrackScreen()
            }
        }
    }
}

// -----------------------------------------------------------
// DATA
// -----------------------------------------------------------

data class MoneyTrack(
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val gambar: Int
)

val moneyList = listOf(
    MoneyTrack("Makan Siang", "Makan di kantin kampus", "Rp15.000", R.drawable.makan),
    MoneyTrack("Transport", "Naik ojek ke kampus", "Rp10.000", R.drawable.transport),
    MoneyTrack("Jajan", "Beli kopi dan snack", "Rp20.000", R.drawable.jajan),
    MoneyTrack("Internet", "Beli paket data", "Rp30.000", R.drawable.internet),
    MoneyTrack("Tabungan", "Menabung harian", "Rp10.000", R.drawable.tabungan)
)

// Dummy untuk rekomendasi food
data class Food(val name: String, val price: String, val photo: Int)

val dummyFood = listOf(
    Food("Ayam Geprek", "Rp12.000", R.drawable.makan),
    Food("Burger", "Rp18.000", R.drawable.jajan),
    Food("Seblak", "Rp10.000", R.drawable.makan)
)

// -----------------------------------------------------------
// UI SCREEN
// -----------------------------------------------------------

@Composable
fun MoneyTrackScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {
            Text(
                text = "MoneyTrack Student",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }

        // -------------------------
        // REKOMENDASI (HORIZONTAL)
        // -------------------------
        item {
            Text(
                text = "Rekomendasi Populer",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(dummyFood) { food ->
                    FoodRowItem(food)
                }
            }
        }

        // -------------------------
        // DAFTAR MONEYTRACK (VERTIKAL)
        // -------------------------
        item {
            Text(
                text = "Daftar Pengeluaran",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(moneyList) { item ->
            DetailItem(item)
        }
    }
}

// -----------------------------------------------------------
// ITEM Rekomendasi (Horizontal)
// -----------------------------------------------------------

@Composable
fun FoodRowItem(food: Food) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = food.photo),
                contentDescription = food.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(food.name, fontWeight = FontWeight.Bold)
            Text(food.price)
        }
    }
}

// -----------------------------------------------------------
// DETAIL ITEM (Vertikal)
// -----------------------------------------------------------

@Composable
fun DetailItem(data: MoneyTrack) {
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
                    .height(150.dp),
                contentScale = ContentScale.Crop
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

        Text(text = data.nama, fontWeight = FontWeight.Bold)
        Text(text = data.deskripsi)
        Text(text = data.harga, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { }) {
            Text("Catat Pengeluaran")
        }
    }
}

// -----------------------------------------------------------
// PREVIEW
// -----------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun PreviewMoneyTrack() {
    MyApplicationTheme {
        MoneyTrackScreen()
    }
}