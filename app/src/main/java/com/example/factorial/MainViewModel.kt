package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
    get() = _state

    fun calculate(value: String?) {
        _state.value = State.Progress
       if (value.isNullOrEmpty()){
           _state.value = State.Error
           return
       }
        viewModelScope.launch {
            val number = value.toLong()
            //calculate
            delay(1000)
            _state.value = State.Result(number.toString())
        }
    }
}