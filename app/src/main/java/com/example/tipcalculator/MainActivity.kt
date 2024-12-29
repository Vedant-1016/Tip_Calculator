package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {

                Tiplayout(

                )
            }
        }
    }
}


@Composable
fun Tiplayout(modifier: Modifier = Modifier) {
    var amountimput by remember { mutableStateOf("") }
    val amount = amountimput.toDoubleOrNull() ?: 0.0
    var tipinput by remember { mutableStateOf("") }
    val tipamount = tipinput.toDoubleOrNull() ?: 0.0
    var round by remember { mutableStateOf(false) }
    val tip = Calculatetip(amount, tipamount, round)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.Start)
        )
        Editnumber(
            label = R.string.bill_Amount,

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountimput,
            onValueChange = { amountimput = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Editnumber(
            label = R.string.tip_percent,
           
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipinput,
            onValueChange = { tipinput = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        toggleswitch(
            roundUp = round,
            onRoundUpchanged = { round = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.Tip_Amount, tip),
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun Editnumber(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,

        label = {
            Text(
                stringResource(label)
            )
        },

        onValueChange = onValueChange,
        modifier = modifier
            .background(color = Color.Cyan),
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun toggleswitch(
    roundUp: Boolean,
    onRoundUpchanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.Round_up)
        )
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpchanged
        )
    }
}

private fun Calculatetip(amount: Double, percent: Double, roundUp: Boolean): String {
    var finaltipvalue = (percent / 100) * amount
    if (roundUp) {
        finaltipvalue = kotlin.math.ceil(finaltipvalue)
    }
    return NumberFormat.getCurrencyInstance().format(finaltipvalue)
}

@Preview(showBackground = true)
@Composable
fun TipPreview() {
    TipCalculatorTheme {
        Tiplayout()
    }
}