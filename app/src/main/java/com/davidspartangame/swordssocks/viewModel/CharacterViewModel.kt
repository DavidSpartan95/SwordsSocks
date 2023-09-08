package com.davidspartangame.swordssocks.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.davidspartangame.swordssocks.R
import com.davidspartangame.swordssocks.characters.*
import com.davidspartangame.swordssocks.game_components.charViewModel

class CharacterViewModel : ViewModel(){
    private val _hairColor = MutableStateFlow<Color>(Color(83,41,14))
    val hairColor: StateFlow<Color> get() = _hairColor

    private val _hairStyle = MutableStateFlow<Int>(R.drawable.character_hair_1)
    val hairStyle: StateFlow<Int> get() = _hairStyle

    private val _eye = MutableStateFlow<Int>(R.drawable.character_eyes_1)
    val eye: StateFlow<Int> get() = _eye

    private val _mouth = MutableStateFlow<Int>(R.drawable.character_mouth_1)
    val mouth: StateFlow<Int> get() = _mouth

    private val _skin = MutableStateFlow<Int>(R.drawable.character_base_tan)
    val skin: StateFlow<Int> get() = _skin


    private var selectNumHair = 0
    private var selectNumHairColor = 0
    private var selectNumEye = 0
    private var selectNumMouth = 0
    private var selectNumSkin = 0

    fun cycleParts(part:String,direction:String){
        when(part){
            "Hair Color" -> charViewModel.cycleHairColor(direction)
            "Hair Style" -> charViewModel.cycleHair(direction)
            "Eye" -> charViewModel.cycleEyes(direction)
            "Mouth" -> charViewModel.cycleMouth(direction)
            "Skin" -> charViewModel.cycleSkin(direction)
        }
    }
    fun cycleHair(direction:String?){
        if (direction == "backward") {
            selectNumHair = (selectNumHair - 1 + com.davidspartangame.swordssocks.characters.hairStyles.size) % com.davidspartangame.swordssocks.characters.hairStyles.size
        } else {
            selectNumHair = (selectNumHair + 1) % com.davidspartangame.swordssocks.characters.hairStyles.size
        }
        _hairStyle.value = com.davidspartangame.swordssocks.characters.hairStyles[selectNumHair]
    }
    fun cycleHairColor(direction:String?){
        if (direction == "backward") {
            selectNumHairColor = (selectNumHairColor - 1 + com.davidspartangame.swordssocks.characters.colors.size) % com.davidspartangame.swordssocks.characters.colors.size
        } else {
            selectNumHairColor = (selectNumHairColor + 1) % com.davidspartangame.swordssocks.characters.colors.size
        }
        _hairColor.value = com.davidspartangame.swordssocks.characters.colors[selectNumHairColor]
    }
    fun cycleEyes(direction:String?){
        if (direction == "backward") {
            selectNumEye = (selectNumEye - 1 + com.davidspartangame.swordssocks.characters.eyes.size) % com.davidspartangame.swordssocks.characters.eyes.size
        } else {
            selectNumEye = (selectNumEye + 1) % com.davidspartangame.swordssocks.characters.eyes.size
        }
        _eye.value = com.davidspartangame.swordssocks.characters.eyes[selectNumEye]
    }
    fun cycleMouth(direction:String?){
        if (direction == "backward") {
            selectNumMouth = (selectNumMouth - 1 + com.davidspartangame.swordssocks.characters.mouths.size) % com.davidspartangame.swordssocks.characters.mouths.size
        } else {
            selectNumMouth = (selectNumMouth + 1) % com.davidspartangame.swordssocks.characters.mouths.size
        }
        _mouth.value = com.davidspartangame.swordssocks.characters.mouths[selectNumMouth]
    }
    fun cycleSkin(direction:String?){
        if (direction == "backward") {
            selectNumSkin = (selectNumSkin - 1 + com.davidspartangame.swordssocks.characters.skins.size) % com.davidspartangame.swordssocks.characters.skins.size
        } else {
            selectNumSkin = (selectNumSkin + 1) % com.davidspartangame.swordssocks.characters.skins.size
        }
        _skin.value = com.davidspartangame.swordssocks.characters.skins[selectNumSkin]
    }

    fun reset(){
        _hairStyle.value = com.davidspartangame.swordssocks.characters.hairStyles[0]
        _hairColor.value = com.davidspartangame.swordssocks.characters.colors[0]
        _eye.value = com.davidspartangame.swordssocks.characters.eyes[0]
        _skin.value = com.davidspartangame.swordssocks.characters.skins[0]
        _mouth.value = com.davidspartangame.swordssocks.characters.mouths[0]
    }
}
