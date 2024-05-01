package com.example.widgetkullanimi

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.widgetkullanimi.ui.theme.WidgetKullanimiTheme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WidgetKullanimiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Gecis()
                }
            }
        }
    }
}

@Composable
fun Gecis(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SayfaFab"){
        composable("SayfaFab"){
            SayfaFab(navController = navController)
        }
        composable("SayfaButtonTextTf"){
            SayfaButtonTextTf(navController = navController)
        }
        composable("ProgressSayfa"){
            ProgressVeSliderSayfa(navController = navController)
        }
        composable("WebviewSayfa"){
            WebviewSayfa(navController = navController)
        }
        composable("ImageSayfa"){
            ImageSayfa(navController = navController)
        }
        composable("DropDownMenu"){
            DropDownMenuSayfa(navController = navController)
        }
        composable("DynamicDropDownMenu"){
            DynamicDropDownMenu(navController = navController)
        }
    }
}
@Composable
fun DynamicDropDownMenu (navController: NavController) {
    val nationalList = listOf(
        "Turkish",
        "Spanish",
        "English",
        "German",
        "French",
        "Russian")
    val menuDrumu = remember {
        mutableStateOf(false)
    }
    val selected_index = remember {
        mutableStateOf(0)
    }
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Box {
            Row (horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        menuDrumu.value = true
                    }
            ) {
                Text(text = nationalList[selected_index.value])
                Image(painter = painterResource(id = R.drawable.dropdown), contentDescription = "")

            }

            DropdownMenu(
                expanded = menuDrumu.value,
                onDismissRequest = {
                    menuDrumu.value=false
                }) {
                nationalList.forEachIndexed { index, national ->
                    DropdownMenuItem(onClick = {
                        selected_index.value=index
                        Log.e("Menu seçildi","National selected : $national")
                        menuDrumu.value=false },
                        text = { Text(text = national ) },
                        )
                }

            }
        }

    }

}
@Composable
fun DropDownMenuSayfa (navController: NavController){
    val menuDurumu= remember {
        mutableStateOf(false)
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box{
            Button(onClick = {
                menuDurumu.value = true
            }) {
                Text(text = "View")
            }
            DropdownMenu(

                expanded = menuDurumu.value ,
                onDismissRequest = { menuDurumu.value = false }) {
                DropdownMenuItem(text = { Text(text = "Delete") },
                    onClick = {
                        Log.e("Menu","Delete seçildi")
                        menuDurumu.value = false
                    })
                DropdownMenuItem(text = { Text(text = "Update") },
                    onClick = {
                        Log.e("Menu","Update seçildi")
                        menuDurumu.value = false
                    })
            }
        }
    }
}

@Composable
fun ImageSayfa(navController: NavController){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
        ) {
        val activity = (LocalContext.current as Activity)
        Image(bitmap = ImageBitmap.imageResource(
            id = activity.resources.getIdentifier(
                "jc",
                "drawable",
                activity.packageName)
        ), contentDescription = "")
        
        Image(painter = painterResource(id = R.drawable.resim), contentDescription ="" )

    }
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebviewSayfa(navController: NavController){
    // Firstly Go Manifest
    val url ="https://gelecegiyazanlar.turkcell.com.tr/"
    AndroidView(factory = {
                          WebView(it).apply {
                              layoutParams=ViewGroup.LayoutParams(
                                  ViewGroup.LayoutParams.MATCH_PARENT,
                                  ViewGroup.LayoutParams.MATCH_PARENT
                              )
                              webViewClient = WebViewClient()
                              loadUrl(url)
                          }

    }, update = {
        it.loadUrl(url)
    })
}
@Composable
fun ProgressVeSliderSayfa (navController: NavController){
    val progressDurum = remember {
        mutableStateOf(false)
    }
    val sliderDeger = remember {
        mutableStateOf(0f)
    }
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if (progressDurum.value){
            CircularProgressIndicator(color = Color.Red)
        }
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = {
                progressDurum.value = true
            }
            ) {
                Text(text = "Başla")
            }
            Button(onClick = {
                progressDurum.value = false
            }
            ) {
                Text(text = "Dur")
            }
        }

        Text(text = "Sonuç : ${sliderDeger.value.toInt()}")

        Slider(value = sliderDeger.value,
            onValueChange = {sliderDeger.value = it},
            valueRange = 0f..100f,
            modifier = Modifier.padding(all = 20.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.LightGray,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Black
            )
        )

        Button(onClick = {
            Log.e("Slider",sliderDeger.value.toString())
        }
        ) {
          Text(text = "Göster")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SayfaFab(navController: NavController){
    val switchDurum = remember {
        mutableStateOf(false)
    }

    Scaffold (
        content = {
                  //Sayfa içeriği
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                    navController.navigate("SayfaButtonTextTf")
                }, text = {
                    Text(
                        text = "Add",
                        color = Color.White
                    )
                },
                containerColor = Color.Red,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_img),
                        contentDescription = "",
                        tint = Color.White
                    )
                })
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Switch(checked = switchDurum.value,
            onCheckedChange ={
                switchDurum.value = it
                Log.e("Switch seçildi.",it.toString())
            } ,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.Gray,
                checkedThumbColor = Color.Black,
                uncheckedTrackColor = Color.White,
                uncheckedThumbColor = Color.Gray
            )
        )
        Button(onClick = {
            Log.e("Switch son durum ->",switchDurum.value.toString())
        }) {
            Text(text = "Switch Durumunu Log'da göster")
        }
        Row (modifier =  Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
            ){
            Button(modifier = Modifier.padding(all = 15.dp),
                onClick = {
                navController.navigate("WebviewSayfa")
            }) {
                Text(text = "Webview Subject")
            }
            Button(modifier = Modifier.padding(all = 15.dp),
                onClick = {
                navController.navigate("ImageSayfa")
            }) {
                Text(text = "Image Page")
            }
        }
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            ){
            Button(
                onClick = {
                navController.navigate("DropDownMenu")
            }) {
                Text(text = "Drop Down Menu is here")
            }
            Button(
                onClick = {
                navController.navigate("DynamicDropDownMenu")
            }) {
                Text(text = "Dynamic Drop Down Menu is here ! ")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SayfaButtonTextTf(navController: NavController) {
  val tf = remember {
      mutableStateOf("")
  }
    val tfoutline = remember {
        mutableStateOf("")
    }
  val alinanVeri = remember {
      mutableStateOf("")
  }
    Column (modifier =  Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Gelen veri : ${alinanVeri.value}")
        TextField(
            value = tf.value,
            onValueChange = {tf.value = it},
            label = {Text(text = "Veri Giriniz...")},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray,
                focusedTextColor = Color.Red,
                focusedIndicatorColor = Color.Cyan,
                focusedLabelColor = Color.Blue
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number)
        )
        Button(onClick = {
            alinanVeri.value=tf.value
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Cyan,
                contentColor = Color.White,
            ),
            border = BorderStroke(1.dp,Color.Blue),
            shape = RoundedCornerShape(50)

            ) {
            Text(text = "Veriyi Gönder")
        }
        OutlinedTextField(
            value = tfoutline.value,
            onValueChange = {tfoutline.value = it},
            label = {Text(text = "Veri Giriniz...")}
        )
        OutlinedButton(onClick = {
            alinanVeri.value=tfoutline.value
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            border = BorderStroke(1.dp,Color.Cyan),
            shape = RoundedCornerShape(50)
            ) {
            Text(text = "Veriyi Gönder")
        }
        Button(onClick = {
            navController.navigate("ProgressSayfa")
        }) {
            Text(text = "Progress Subject")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WidgetKullanimiTheme {
    }
}