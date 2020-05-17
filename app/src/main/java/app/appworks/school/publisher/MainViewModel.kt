package app.appworks.school.publisher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.appworks.school.publisher.PublisherApplication
import app.appworks.school.publisher.R
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.data.Result
import app.appworks.school.publisher.data.source.PublisherRepository
import app.appworks.school.publisher.network.LoadApiStatus
import app.appworks.school.publisher.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel(private val repository: PublisherRepository) : ViewModel() {

    private val _author = MutableLiveData<Author>().apply {
        value = Author(
            "waynechen323",
            "AKA小安老師",
            "wayne@school.appworks.tw"
        )
    }

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun refresh() {
        _refresh.value = true
    }

    fun onRefreshed() {
        _refresh.value = null
    }
}
