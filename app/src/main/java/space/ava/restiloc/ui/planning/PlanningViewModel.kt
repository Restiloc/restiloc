package space.ava.restiloc.ui.planning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlanningViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is planning Fragment"
    }
    val text: LiveData<String> = _text
}