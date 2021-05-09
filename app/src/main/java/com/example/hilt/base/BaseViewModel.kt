package com.example.hilt.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hilt.internal.util.Event
import com.example.hilt.internal.util.Failure

open class BaseViewModel : ViewModel() {

    private val _failure = MutableLiveData<Event<Failure>>()
    val failure: LiveData<Event<Failure>> get() = _failure


    protected open fun handleFailure(failure: Failure) {
        _failure.value = Event(failure)
    }
}