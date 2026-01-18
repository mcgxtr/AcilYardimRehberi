package com.example.tester

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tester.ui.theme.TesterTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesterTheme {
                // Ekranlar arası geçişi kontrol eden state (0: Ana Ekran, 1: Eczane, 2: İl Kodları)
                var currentScreen by remember { mutableStateOf(0) }

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                val title = when(currentScreen) {
                                    1 -> "NÖBETÇİ ECZANELER"
                                    2 -> "TÜRKİYE İL KODLARI"
                                    else -> "ACİL YARDIM"
                                }
                                Text(title, fontWeight = FontWeight.Bold)
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFFD32F2F),
                                titleContentColor = Color.White
                            )
                        )
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        0 -> AnaEkran(Modifier.padding(innerPadding),
                            onEczaneClick = { currentScreen = 1 },
                            onPlakaClick = { currentScreen = 2 })

                        1 -> EczaneEkrani(Modifier.padding(innerPadding)) { currentScreen = 0 }

                        2 -> PlakaKodlariEkrani(Modifier.padding(innerPadding)) { currentScreen = 0 }
                    }
                }
            }
        }
    }
}

@Composable
fun AnaEkran(modifier: Modifier, onEczaneClick: () -> Unit, onPlakaClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmergencyButton("112 ACİL YARDIM", Color(0xFFD32F2F)) {
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:112")))
        }
        EmergencyButton("155 POLİS İMDAT", Color(0xFF1976D2)) {
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:155")))
        }
        EmergencyButton("110 İTFAİYE", Color(0xFFF57C00)) {
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:110")))
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Nöbetçi Eczane Butonu
        Button(
            onClick = onEczaneClick,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("NÖBETÇİ ECZANELER", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        // YENİ: İl Kodları Butonu
        Button(
            onClick = onPlakaClick,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF607D8B))
        ) {
            Text("TÜRKİYE İL KODLARI", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PlakaKodlariEkrani(modifier: Modifier, onBack: () -> Unit) {
    val plakaListesi = listOf(
        "01 Adana", "02 Adıyaman", "03 Afyonkarahisar", "04 Ağrı", "05 Amasya", "06 Ankara", "07 Antalya", "08 Artvin",
        "09 Aydın", "10 Balıkesir", "11 Bilecik", "12 Bingöl", "13 Bitlis", "14 Bolu", "15 Burdur", "16 Bursa",
        "17 Çanakkale", "18 Çankırı", "19 Çorum", "20 Denizli", "21 Diyarbakır", "22 Edirne", "23 Elazığ", "24 Erzincan",
        "25 Erzurum", "26 Eskişehir", "27 Gaziantep", "28 Giresun", "29 Gümüşhane", "30 Hakkari", "31 Hatay", "32 Isparta",
        "33 Mersin", "34 İstanbul", "35 İzmir", "36 Kars", "37 Kastamonu", "38 Kayseri", "39 Kırklareli", "40 Kırşehir",
        "41 Kocaeli", "42 Konya", "43 Kütahya", "44 Malatya", "45 Manisa", "46 Kahramanmaraş", "47 Mardin", "48 Muğla",
        "49 Muş", "50 Nevşehir", "51 Niğde", "52 Ordu", "53 Rize", "54 Sakarya", "55 Samsun", "56 Siirt", "57 Sinop",
        "58 Sivas", "59 Tekirdağ", "60 Tokat", "61 Trabzon", "62 Tunceli", "63 Şanlıurfa", "64 Uşak", "65 Van", "66 Yozgat",
        "67 Zonguldak", "68 Aksaray", "69 Bayburt", "70 Karaman", "71 Kırıkkale", "72 Batman", "73 Şırnak", "74 Bartın",
        "75 Ardahan", "76 Iğdır", "77 Yalova", "78 Karabük", "79 Kilis", "80 Osmaniye", "81 Düzce"
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("ANA SAYFAYA DÖN") }
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(plakaListesi) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(15.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// Eczane Ekranı (3 İl Kalsın Dediğin İçin Sabitlendi)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EczaneEkrani(modifier: Modifier, onBack: () -> Unit) {
    val sehirler = listOf("Sakarya", "Istanbul", "Kocaeli")
    var secilenSehir by remember { mutableStateOf(sehirler[0]) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("GERİ") }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                OutlinedButton(onClick = { expanded = true }) { Text(secilenSehir.uppercase()) }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    sehirler.forEach { sehir ->
                        DropdownMenuItem(text = { Text(sehir) }, onClick = {
                            secilenSehir = sehir
                            expanded = false
                        })
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listOf(
                Pharmacy("$secilenSehir Nöbetçi Eczanesi", "Merkez", "Atatürk Cad. No:1", "0000", "0,0")
            )) { eczane ->
                PharmacyCard(eczane)
            }
        }
    }
}

@Composable
fun EmergencyButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(80.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PharmacyCard(pharmacy: Pharmacy) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(pharmacy.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Adres: ${pharmacy.address}")
            Button(onClick = { /* Arama kodu buraya */ }, modifier = Modifier.padding(top = 8.dp)) { Text("ARA") }
        }
    }
}

data class Pharmacy(val name: String, val dist: String, val address: String, val phone: String, val loc: String)