package app.appworks.school.publisher.home

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
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel(private val repository: PublisherRepository) : ViewModel() {

    private val _author = MutableLiveData<Author>()

    val author: LiveData<Author>
        get() = _author

    private var _articles = MutableLiveData<List<Article>>()

    val articles: LiveData<List<Article>>
        get() = _articles

    var liveArticles = MutableLiveData<List<Article>>()

    private val _navigateToPublish = MutableLiveData<Author>()

    val navigationToPublish: LiveData<Author>
        get() = _navigateToPublish

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

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

    /**
     * Call getArticlesResult() on init so we can display status immediately.
     */
    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

//        getArticlesResult()
//        getLiveArticlesResult()
    }

    fun getArticlesResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getArticles()

            _articles.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = PublisherApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun delete(article: Article) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.delete(article)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    refresh()
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = PublisherApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
            _refreshStatus.value = false
        }
    }

    private suspend fun login(id: String = ""): Author? = suspendCoroutine {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = repository.loginMockData(id)

            _author.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = PublisherApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false

            it.resume(_author.value)
        }
    }

    fun refresh() {
//        if (status.value != LoadApiStatus.LOADING) {
//            getArticlesResult()
//        }
    }

    fun navigationToPublish(author: Author?) {

        coroutineScope.launch {
            _navigateToPublish.value = author ?: login() //TODO put your id into login function
        }
    }

    fun onPublishNavigated() {
        _navigateToPublish.value = null
    }

    fun getLiveArticlesResult() {
        liveArticles = repository.getLiveArticles()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }
}
