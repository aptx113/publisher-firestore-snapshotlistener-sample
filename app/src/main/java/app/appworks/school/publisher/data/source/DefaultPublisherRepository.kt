package app.appworks.school.publisher.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.appworks.school.publisher.MyAnnotation
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.data.Result

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Concrete implementation to load Publisher sources.
 */
@MyAnnotation
class DefaultPublisherRepository(private val remoteDataSource: PublisherDataSource,
                                 private val localDataSource: PublisherDataSource
) : PublisherRepository {

    override suspend fun loginMockData(id: String): Result<Author> {
        return localDataSource.login(id)
    }

    override suspend fun getArticles(): Result<List<Article>> {
        return remoteDataSource.getArticles()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override suspend fun publish(article: Article): Result<Boolean> {
        return remoteDataSource.publish(article)
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        return remoteDataSource.delete(article)
    }
}
