package com.example.brokenhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.brokenhub.ui.theme.BrokenHubTheme
import com.example.brokenhub.data.AppDatabase
import com.example.brokenhub.data.DatabaseSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Initialize Room database
        val db = AppDatabase.getDatabase(this)

        // ✅ Seed 50 listings (only once)
        DatabaseSeeder.seed(db)

        setContent {
            BrokenHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListingScreen(db = db, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListingScreen(db: AppDatabase, modifier: Modifier = Modifier) {
    val dao = db.listingDao()
    var listings by remember { mutableStateOf(listOf<`ListingEntity.kt`>()) }

    // Load listings from DB
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            listings = dao.getAllListings()
        }
    }

    LazyColumn(modifier = modifier) {
        items(listings) { listing ->
            Text(text = "${listing.title} - BWP ${listing.price} - ${listing.location}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListingScreen() {
    BrokenHubTheme {
        Text(text = "Preview Listings")
    }
}
