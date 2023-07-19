package com.example.swordssocks.gameComponents

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.ui.theme.SandPaper
import com.example.swordssocks.viewModel.CharacterViewModel
import com.example.swordssocks.R
import com.example.swordssocks.characters.randomNameGenerator

var charViewModel = CharacterViewModel()

@Composable
fun CreationScreen() {
    var screenSelected by remember { mutableStateOf(1) }

    when (screenSelected){
        1 ->{PickName({screenSelected++},{})}
        2 ->{Customization({},{screenSelected--})}
        3 ->{Customization({},{screenSelected--})}
    }

}

@Composable
fun PickName(fnForward: () -> Unit,fnBack:() -> Unit) {

    var name by remember {mutableStateOf(randomNameGenerator())}

    Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        
        Text(text = "What is your name?", fontSize = 24.sp)
        TextField(
            value = name,
            onValueChange = {name = it},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center,fontSize = 24.sp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        //Random Generator Button
        Button(
            onClick = { name = randomNameGenerator() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(153,64,40))
        ) {
            Text(text = "RANDOM")
        }
        Row(
            Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CheckButton(image = Pair(
                R.drawable.button_check_back,
                R.drawable.button_check_front
            )){fnForward.invoke()}
            CheckButton(image = Pair(
                R.drawable.button_cancel_back,
                R.drawable.button_cancel_front
            )){}
        }
    }
}
@Composable
fun Customization(fnForward: () -> Unit,fnBack:() -> Unit) {
    val hairColor by charViewModel.hairColor.collectAsState()
    val hairStyle by charViewModel.hairStyle.collectAsState()
    val eyes by charViewModel.eye.collectAsState()
    val mouth by charViewModel.mouth.collectAsState()
    val skin by charViewModel.skin.collectAsState()

    Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column() {
                AppearanceSelectorBox {

                    SelectorButtons(
                        "Hair Color",
                        {charViewModel.cycleHairColor("backward")},
                        {charViewModel.cycleHairColor("forwards")}
                    )
                    SelectorButtons(
                        "Hair Style",
                        {charViewModel.cycleHair("backward")},
                        {charViewModel.cycleHair("forwards")}
                    )
                    SelectorButtons(
                        "Eye",
                        {charViewModel.cycleEyes("backward")},
                        {charViewModel.cycleEyes("forwards")}
                    )
                    SelectorButtons(
                        "Mouth",
                        {charViewModel.cycleMouth("backward")},
                        {charViewModel.cycleMouth("forwards")}
                    )
                    SelectorButtons(
                        "Skin",
                        {charViewModel.cycleSkin("backward")},
                        {charViewModel.cycleSkin("forwards")}
                    )
                }
                Row(
                    Modifier.width(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CheckButton(image = Pair(
                        R.drawable.button_check_back,
                        R.drawable.button_check_front
                    )){}
                    CheckButton(image = Pair(
                        R.drawable.button_cancel_back,
                        R.drawable.button_cancel_front
                    )){fnBack.invoke()}

                }
            }
            CharacterBox(
                hairColor = ColorFilter.tint(hairColor),
                hairStyle = hairStyle,
                eye = eyes,
                mouth = mouth,
                skin = skin
            )
        }
    }
}

@Composable
fun AppearanceSelectorBox(content: @Composable () -> Unit) {
    Box(
        Modifier
            .heightIn(100.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(SandPaper)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center,

    ) {
        Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
            content()
        }
    }
}

@Composable
fun SelectorButtons(text:String, ArrowBack:()-> Unit,ArrowForward:()-> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { ArrowBack.invoke() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(text = text)
        IconButton(onClick = { ArrowForward.invoke() }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Back")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckButton(image:Pair<Int,Int>, fn:() -> Unit) {
    var opacity by remember { mutableStateOf(1f) }
    var size by remember { mutableStateOf(75) }
    var isBeingPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(75.dp)
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isBeingPressed = true
                        size = 65
                        opacity = 0.5f
                    }
                    MotionEvent.ACTION_UP -> {
                        isBeingPressed = false
                        size = 75
                        opacity = 1f
                        fn.invoke()
                    }
                }
                true
            }
    ) {
        Image(
            painter = painterResource(image.first),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
                .alpha(opacity)
                .align(Center)
        )
        Image(
            painter = painterResource(image.second),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
                .alpha(opacity)
                .align(Center)
        )
    }
}


