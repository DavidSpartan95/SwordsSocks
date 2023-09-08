package com.davidspartangame.swordssocks.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class StatNumberViewModel: ViewModel() {
    //Index 0 = HP, 1 = Strength, 2 = Charisma, 3 = Defence, 4 = Magic
    private val _statList = MutableStateFlow(arrayListOf(1,1,1,1,1))
    val statList: StateFlow<ArrayList<Int>> get() = _statList

    private val _pointsToDistribute = MutableStateFlow(28)
    val pointsToDistribute: StateFlow<Int> get() = _pointsToDistribute

    private val statIndexMap = mapOf("Health" to 0, "Strength" to 1, "Charisma" to 2, "Defence" to 3, "Magic" to 4)

    fun changeStatValue(info: Pair<String, Int>) {
        if (_pointsToDistribute.value != 0 || info.second <= 0) {
            val index = statIndexMap[info.first]
            if (index != null) {
                val newValue = _statList.value[index] + info.second
                if (newValue >= 1) {
                    if (info.second == -1) _pointsToDistribute.value++ else _pointsToDistribute.value--
                    _statList.value = _statList.value.toMutableList().apply { set(index, newValue) } as ArrayList<Int>
                }
            }
        }
    }
    fun randomizeStats(){
        _pointsToDistribute.value = 28
        var stats = arrayListOf<Int>(1,1,1,1,1)
        while (_pointsToDistribute.value > 0){
            val rng = Random.nextInt(5)
            _pointsToDistribute.value--
            stats[rng] = stats[rng]+1
        }
        _statList.value = stats
    }
    fun reset(){
        _pointsToDistribute.value = 28
        _statList.value = arrayListOf(1,1,1,1,1)
    }

}