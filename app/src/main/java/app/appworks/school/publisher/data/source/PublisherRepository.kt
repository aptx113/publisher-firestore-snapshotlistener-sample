package app.appworks.school.publisher.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.data.Result

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Interface to the Publisher layers.
 */
interface PublisherRepository {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publish(article: Article): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>
}
