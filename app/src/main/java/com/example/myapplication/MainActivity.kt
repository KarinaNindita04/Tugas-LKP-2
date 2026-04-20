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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.PraktiktamTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktiktamTheme {
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
                style = MaterialTheme.typography.titleLarge
            )
        }

        // REKOMENDASI
        item {
            Text(
                text = "Rekomendasi Populer",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(dummyFood) { food ->
                    FoodRowItem(food)
                }
            }
        }

        // LIST
        item {
            Text(
                text = "Daftar Pengeluaran",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(moneyList) { item ->
            DetailItem(item)
        }
    }
}

// -----------------------------------------------------------
// CARD HORIZONTAL
// -----------------------------------------------------------

@Composable
fun FoodRowItem(food: Food) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
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

            Text(
                text = food.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = food.price,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// -----------------------------------------------------------
// DETAIL ITEM
// -----------------------------------------------------------

@Composable
fun DetailItem(data: MoneyTrack) {

    var isFavorite by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxWidth()) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

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
                                if (isFavorite)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = data.nama,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = data.deskripsi,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = data.harga,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            delay(2000)

                            snackbarHostState.showSnackbar(
                                "Pengeluaran ${data.nama} berhasil dicatat!"
                            )

                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {
                        Text("Catat Pengeluaran")
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}