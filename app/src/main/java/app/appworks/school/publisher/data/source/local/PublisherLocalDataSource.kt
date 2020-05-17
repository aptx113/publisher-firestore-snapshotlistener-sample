package app.appworks.school.publisher.data.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.data.Result
import app.appworks.school.publisher.data.source.PublisherDataSource

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Concrete implementation of a Publisher source as a db.
 */
class PublisherLocalDataSource(val context: Context) : PublisherDataSource {

    override suspend fun login(id: String): Result<Author> {
        return when (id) {
            "waynechen323" -> Result.Success((Author(
                id,
                "AKA小安老師",
                "wayne@school.appworks.tw"
            )))
            "dlwlrma" -> Result.Success((Author(
                id,
                "IU",
                "dlwlrma@school.appworks.tw"
            )))
            //TODO add your profile here
            else -> Result.Fail("You have to add $id info in local data source")
        }
    }

    override suspend fun getArticles(): Result<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun publish(article: Article): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
