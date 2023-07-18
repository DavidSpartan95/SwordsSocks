package com.example.swordssocks.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swordssocks.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel(){
    private val _hairColor = MutableStateFlow<Color>(Color(255,0,0))
    val hairColor: StateFlow<Color> get() = _hairColor

    private val colors = arrayOf(
        Color(255,0,0),
        Color(0,255,0),
        Color(0,0,255)
    )
    private var selectNum = 0
    fun cycleHairColor(direction:String?){
        if (direction == "backward") {
            selectNum = (selectNum - 1 + colors.size) % colors.size
        } else {
            selectNum = (selectNum + 1) % colors.size
        }
        _hairColor.value = colors[selectNum]

    }
}