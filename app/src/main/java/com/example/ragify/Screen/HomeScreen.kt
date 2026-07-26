package com.example.ragify.Screen

import com.example.ragify.R
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){

    val pdflauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ){ uri ->

        if(uri != null){

        }

    }
    Scaffold(
        topBar = { TopBar() }
    ){
            innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text("Hello")



                Row(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth() ,
                    verticalAlignment = Alignment.CenterVertically){


                    OutlinedTextField(onValueChange = {} , value = "" , label = {Text("Ask Qustion")} ,
                        leadingIcon = {
                            IconButton(onClick = { pdflauncher.launch(arrayOf("application/pdf")) }) {
                                Icon(painter =  painterResource(R.drawable.baseline_add_24 ), contentDescription = "Search")
                            }
                        })

                    IconButton(onClick = {}) {
                        Icon(painter =  painterResource(R.drawable.baseline_send_24 ), contentDescription = "Search")
                    }
                }

        }

    }
}