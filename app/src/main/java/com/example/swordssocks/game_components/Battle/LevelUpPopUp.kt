package com.example.swordssocks.game_components.Battle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.swordssocks.R
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.CheckButton
import com.example.swordssocks.game_components.StatButtons
import com.example.swordssocks.ui.theme.SandPaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@Composable
fun LevelUpPopUp(userRepository:UserRepository,user: User, skillPoints:Int, done:()->Unit) {
    var points by remember{ mutableStateOf(skillPoints)}
    var statButtonNames by remember{ mutableStateOf(
        arrayOf(
            Pair("Health",user.health),
            Pair("Strength",user.strength),
            Pair("Charisma",user.charisma),
            Pair("Defence",user.defence),
            Pair("Magic",user.magic),
        )
    ) }
    val userStats by remember{ mutableStateOf(
        arrayOf(
            Pair("Health",user.health),
            Pair("Strength",user.strength),
            Pair("Charisma",user.charisma),
            Pair("Defence",user.defence),
            Pair("Magic",user.magic),
        )
    )}
    Popup(alignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .padding(8.dp)
                        .height(50.dp)
                        .width(175.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(SandPaper)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ){
                    Text(text = "LEVEL UP!", fontWeight = FontWeight.Bold)
                }
                Box(
                    Modifier
                        .padding(8.dp)
                        .height(300.dp)
                        .width(200.dp)
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

                        statButtonNames.forEachIndexed { index, skill ->
                            StatButtons(
                                text = skill.first,
                                points = skill.second,
                                minus = {
                                    if (statButtonNames[index].second > userStats[index].second && skillPoints != points){
                                        val updatedArray = statButtonNames.copyOf()
                                        updatedArray[index] = Pair(skill.first, skill.second-1)
                                        statButtonNames = updatedArray
                                        points++
                                    }
                                },
                                plus = {
                                    if (points > 0 && skill.second > 0){
                                        val updatedArray = statButtonNames.copyOf()
                                        updatedArray[index] = Pair(skill.first, skill.second+1)
                                        statButtonNames = updatedArray
                                        points--
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        Text(text = "$points", fontSize = 40.sp)
                    }
                }
                CheckButton(
                    75,
                    image = Pair(
                        R.drawable.button_check_back,
                        R.drawable.button_check_front
                    )){
                    userRepository.performDatabaseOperation(Dispatchers.IO){
                        userRepository.newStats(
                            user.id,
                            statButtonNames[0].second,
                            statButtonNames[1].second,
                            statButtonNames[2].second,
                            statButtonNames[3].second,
                            statButtonNames[4].second,
                        )
                        CoroutineScope(Dispatchers.Main).launch {
                            done.invoke()
                        }
                    }
                }
            }
        }
    }
}