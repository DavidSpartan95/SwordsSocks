package com.example.swordssocks.game_components

import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.ui.theme.SandPaper
import com.example.swordssocks.viewModel.CharacterViewModel
import com.example.swordssocks.R
import com.example.swordssocks.characters.containsOnlyAlphabets
import com.example.swordssocks.characters.randomNameGenerator
import com.example.swordssocks.database.*
import com.example.swordssocks.gladiator_items.Potion
import com.example.swordssocks.gladiator_items.smallPotion
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.viewModel.NameViewModel
import com.example.swordssocks.viewModel.StatNumberViewModel
import com.google.gson.Gson

var charViewModel = CharacterViewModel()
var statViewModel = StatNumberViewModel()
var nameViewModel = NameViewModel()
@Composable
fun CreationScreen(
    navController: NavHostController,
    userRepository: UserRepository
) {
    var screenSelected by remember { mutableStateOf(1) }
    val stats by statViewModel.statList.collectAsState()
    LaunchedEffect(false){
        nameViewModel.setName(randomNameGenerator())
        statViewModel.reset()
        charViewModel.reset()
    }

    when (screenSelected){
        1 ->{
            PickName(
            {screenSelected++},
                {
                    navController.navigate(route = "home_screen"){
                        popUpTo(Screen.Creation.route){
                            inclusive = true
                        }}
                }
            )
        }
        2 ->{Customization({screenSelected++},{screenSelected--})}
        3 ->{StatDistribution({
            val newUser = User(
                name = nameViewModel.name.value,
                stats[0],
                stats[1],
                stats[2],
                stats[3],
                stats[4],
                100,
                0,
                1,
                DrawInstruction(
                    hair = charViewModel.hairStyle.value,
                    hairColor = charViewModel.hairColor.value,
                    eyes = charViewModel.eye.value,
                    mouth = charViewModel.mouth.value,
                    skin = charViewModel.skin.value
                ),
                Inventory(
                    potions = arrayListOf(smallPotion),
                    meleeWeapons = arrayListOf(),
                    magicWeapons = arrayListOf(),
                    armors = arrayListOf()
                )
            )
            addUser(
                userRepository,
                user = newUser

            )
            val userJson = Gson().toJson(newUser)
            navController.navigate(route = "town_screen/$userJson"){
                popUpTo(Screen.Creation.route){
                    inclusive = true
                }
            }
        },{screenSelected--})}
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PickName(fnForward: () -> Unit,fnBack:() -> Unit) {

    val name by nameViewModel.name.collectAsState()

    Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        
        Text(text = "What is your name?", fontSize = 24.sp)

        Log.d("TAG","ENTER PRESS")
        TextField(
            value = name,
            onValueChange = {
                if(it.length <= 12&&containsOnlyAlphabets(it)){
                    nameViewModel.setName(it)
                }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center,fontSize = 24.sp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { fnForward.invoke() }
            ),

        )
        //Random Generator Button
        Button(
            onClick = { nameViewModel.setName(randomNameGenerator()) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(153,64,40))
        ) {
            Text(text = "RANDOM", color = Color.White)
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
            )){fnBack.invoke()}
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

    Box(Modifier.fillMaxSize(),contentAlignment = Center) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column() {
                AppearanceSelectorBox {

                    val buttonNames = arrayOf("Hair Color","Hair Style","Eye","Mouth","Skin")
                    for (x in buttonNames){
                        SelectorButtons(
                            text = x,
                            ArrowBack = {charViewModel.cycleParts(x,"backward")},
                            ArrowForward = {charViewModel.cycleParts(x,"forward")}
                        )
                    }
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
                    )){fnBack.invoke()}
                }
            }
            CharacterBox(
                hairColor = ColorFilter.tint(hairColor),
                hairStyle = hairStyle,
                eye = eyes,
                mouth = mouth,
                skin = skin,
                size = 300,
                opacity = 1.0F,
                colorTint = null
            )
        }
    }
}
@Composable
fun StatDistribution(fnForward: () -> Unit,fnBack:() -> Unit) {
    val skills by statViewModel.statList.collectAsState()
    val skillPoints by statViewModel.pointsToDistribute.collectAsState()

    Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Box(
                Modifier
                    .heightIn(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,
            ){
                Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Random", fontWeight = FontWeight.Bold)
                    Image(
                        painter = painterResource(id = R.drawable.six_sided_dice),
                        contentDescription = null,
                        Modifier
                            .size(50.dp)
                            .clickable { statViewModel.randomizeStats() }
                    )
                }
            }
            Box(
                Modifier
                    .padding(8.dp)
                    .heightIn(100.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Center,
                ){
                Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    val statButtonNames = arrayOf("Health","Strength","Charisma","Defence","Magic")

                    statButtonNames.forEachIndexed { index, name ->
                        StatButtons(
                            text = name,
                            points = skills[index],
                            minus = {statViewModel.changeStatValue(Pair(name,-1))},
                            plus = {statViewModel.changeStatValue(Pair(name,1))}
                        )
                    }
                }
            }
            Box(
                Modifier
                    .heightIn(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,
            ){
                Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Skill Points",fontWeight = FontWeight.Bold)
                    Text(text = "$skillPoints", fontSize = 40.sp)
                }
            }
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
            )){fnBack.invoke()}

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
        contentAlignment = Center,

    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
        Text(text = text, fontWeight = FontWeight.Bold)
        IconButton(onClick = { ArrowForward.invoke() }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Back")
        }
    }
}

@Composable
fun StatButtons(text:String,points:Int ,minus:()-> Unit,plus:()-> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Text(text = text,fontWeight = FontWeight.Bold,modifier = Modifier.padding(start = 8.dp))
        }
        Row(horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically,) {
            IconButton(onClick = { minus.invoke() }) {
                Image(painter = painterResource(id = R.drawable.remove_circle), contentDescription = null)
            }
            Text(text = "$points", color = Color.White, fontWeight = FontWeight.Bold)
            IconButton(onClick = { plus.invoke() }) {
                Icon(Icons.Default.AddCircle, contentDescription = "Back")            }
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


