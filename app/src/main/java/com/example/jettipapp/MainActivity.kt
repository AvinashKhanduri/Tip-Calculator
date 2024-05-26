package com.example.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.components.SplitContent
import com.example.jettipapp.components.TipRowSlider
import com.example.jettipapp.components.TopHeader
import com.example.jettipapp.ui.theme.JetTipAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipAppTheme {
                // A surface container using the 'background' color from the theme
                MyApp {
                        val handleInputState = remember{mutableStateOf("") }
                        val forSplit = remember{ mutableStateOf(1) }

                        MainContent(userInput = handleInputState, splitBill = forSplit)
               }

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.outline
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            content()
        }
    }
}




@Composable
fun MainContent(userInput:MutableState<String>,
                splitBill:MutableState<Int>,
                modifier:Modifier = Modifier) {
    val validState = remember(userInput.value) {
        userInput.value.trim().isNotEmpty()
    }
    val sliderPositionsState = remember{ mutableStateOf(0f) }
    val tipPercentage  = (sliderPositionsState.value*100).toInt()

    val billAmount = userInput.value.toDoubleOrNull()
    val billWithTip = billAmount?.plus(tipPercentage)
    var totalBillPerPerson = billWithTip?.div(splitBill.value)
    if(totalBillPerPerson==null){
        totalBillPerPerson=0.0
    }
    TopHeader(totalAmount = totalBillPerPerson.toDouble(),
            modifier = Modifier.padding(20.dp))

    Card(
        modifier = modifier
            .height(400.dp)
            .width(330.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(ifInput = validState,userInput = userInput)
            if (validState){
                SplitContent(splitBill)
                TipRowSlider(sliderPosition = sliderPositionsState,tipPercentage = tipPercentage,billAmount = userInput.value.toIntOrNull()?:0)
             }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {

    }
}