package com.davidspartangame.swordssocks.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.davidspartangame.swordssocks.characters.randomNameGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NameViewModel: ViewModel(){
    private val _name = MutableStateFlow<String>(randomNameGenerator())
    val name: StateFlow<String> get() = _name

    fun setName(newName:String){
        _name.value = newName
    }

}