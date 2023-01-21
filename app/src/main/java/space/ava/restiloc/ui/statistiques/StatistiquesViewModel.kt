package space.ava.restiloc.ui.statistiques



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatistiquesViewModel : ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This is statistiques Fragment"
    }
    val text: LiveData<String> = _text
}