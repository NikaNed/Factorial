package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("My coroutine scope"))

    fun calculate(value: String?) {
        _state.value = State.Progress
        if (value.isNullOrEmpty()) {
            _state.value = State.Error
            return
        }
        scope.launch (Dispatchers.Main) {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default){
                factorial(number)
            }
            _state.value = State.Factorial(result)
        }
    }

    private fun factorial(number: Long): String {
            var result = BigInteger.ONE
            for (i in 1..number) {
                result = result.multiply(BigInteger.valueOf(i))
            }
            return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
