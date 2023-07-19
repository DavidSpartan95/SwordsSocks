package com.example.swordssocks.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.swordssocks.R

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

    //Hair colors in stored in an array
    private val colors = arrayOf(
        Color(83,41,14),
        Color(179,35,23),
        Color(179,139,103),
        Color(0, 126, 180),
        Color(56,81,61),
        Color(31, 22, 12)
    )
    //Hair styles stored in an array
    private val hairStyles = arrayOf(
        R.drawable.character_hair_1,
        R.drawable.character_hair_2,
        R.drawable.character_hair_3,
    )
    private val eyes = arrayOf(
        R.drawable.character_eyes_1,
        R.drawable.character_eyes_2,
        R.drawable.character_eyes_3,
    )
    private val mouths = arrayOf(
        R.drawable.character_mouth_1,
        R.drawable.character_mouth_2,
        R.drawable.character_mouth_3,
    )
    private val skins = arrayOf(
        R.drawable.character_base_tan,
        R.drawable.character_base_lightskin,
        R.drawable.character_base_black,
    )
    private var selectNumHair = 0
    private var selectNumHairColor = 0
    private var selectNumEye = 0
    private var selectNumMouth = 0
    private var selectNumSkin = 0
    fun cycleHair(direction:String?){
        if (direction == "backward") {
            selectNumHair = (selectNumHair - 1 + hairStyles.size) % hairStyles.size
        } else {
            selectNumHair = (selectNumHair + 1) % hairStyles.size
        }
        _hairStyle.value = hairStyles[selectNumHair]
    }
    fun cycleHairColor(direction:String?){
        if (direction == "backward") {
            selectNumHairColor = (selectNumHairColor - 1 + colors.size) % colors.size
        } else {
            selectNumHairColor = (selectNumHairColor + 1) % colors.size
        }
        _hairColor.value = colors[selectNumHairColor]
    }
    fun cycleEyes(direction:String?){
        if (direction == "backward") {
            selectNumEye = (selectNumEye - 1 + eyes.size) % eyes.size
        } else {
            selectNumEye = (selectNumEye + 1) % eyes.size
        }
        _eye.value = eyes[selectNumEye]
    }
    fun cycleMouth(direction:String?){
        if (direction == "backward") {
            selectNumMouth = (selectNumMouth - 1 + mouths.size) % mouths.size
        } else {
            selectNumMouth = (selectNumMouth + 1) % mouths.size
        }
        _mouth.value = mouths[selectNumMouth]
    }
    fun cycleSkin(direction:String?){
        if (direction == "backward") {
            selectNumSkin = (selectNumSkin - 1 + skins.size) % skins.size
        } else {
            selectNumSkin = (selectNumSkin + 1) % skins.size
        }
        _skin.value = skins[selectNumSkin]
    }
}
