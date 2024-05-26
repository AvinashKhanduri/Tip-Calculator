package com.example.jettipapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InputField(userInput:MutableState<String>,ifInput:Boolean) {
    val keybordController = LocalSoftwareKeyboardController.current
    OutlinedTextField(value = userInput.value,
        onValueChange = { userInput.value = it },
        modifier = Modifier.padding(20.dp),
        label = { Text(text = "Enter Bill", fontWeight = FontWeight.Bold) },
        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        enabled = true,
        singleLine = true,
        keyboardActions = KeyboardActions {
            if(!ifInput){
                return@KeyboardActions
            }
            else{
                keybordController?.hide()
            }
        }
    )

}


@Composable
fun SplitContent(split:MutableState<Int>){
    val anotherModifier = Modifier.size(40.dp)
   // var addPerson = split.value
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),
        // horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Split",modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(100.dp))
        Surface(modifier = Modifier.size(60.dp),
            shape = CircleShape,
            shadowElevation = 30.dp,
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(enabled = true) {
                        split.value += 1
                        Log.d("round", "totoal person are ${split.value}")
                    }
                    .then(anotherModifier),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
        Text(text = "${split.value}",modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Surface(modifier = Modifier.size(60.dp),
            shape = CircleShape,
            shadowElevation = 30.dp,
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(enabled = true) {
                        if (split.value <= 1) {
                            split.value = split.value
                        } else {
                            split.value -= 1
                        }
                        Log.d("round", "total person are ${split.value} ")
                    }
                    .then(anotherModifier),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription ="Remove" )
            }
        }
    }
}



@Composable
fun TopHeader(totalAmount:Double,modifier:Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(130.dp)
            .width(400.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color(0xFFD6C2E7)),
        elevation = CardDefaults.cardElevation(50.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "₹${String.format("%.2f",totalAmount)}",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun TipRowSlider(sliderPosition:MutableState<Float>,tipPercentage:Int,billAmount:Int){
    val tipAmount = billAmount*tipPercentage/100

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Tip",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 11.dp),
        fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(200.dp))
        Text(text = "₹$tipAmount",modifier = Modifier.align(Alignment.CenterVertically),
             fontWeight = FontWeight.Bold)
    }
    Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$tipPercentage%",
                fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Slider(value =sliderPosition.value ,
                    onValueChange ={ it->
                        sliderPosition.value = it
                        Log.d("slider", "TipRowSlider: ${sliderPosition.value}")
                    } ,
                    modifier = Modifier.padding(10.dp),
                    //valueRange = 0f..100f,
                    //steps = 10,
                    enabled = true,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        inactiveTickColor = MaterialTheme.colorScheme.onSurface,
                        inactiveTrackColor = MaterialTheme.colorScheme.outline
                    ))

            }

    }
}