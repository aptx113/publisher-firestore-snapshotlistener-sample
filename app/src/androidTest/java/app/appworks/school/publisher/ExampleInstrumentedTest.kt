package app.appworks.school.publisher

import androidx.lifecycle.LiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.data.Result
import app.appworks.school.publisher.data.source.DefaultPublisherRepository
import app.appworks.school.publisher.home.HomeViewModel
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("app.appworks.school.publisher", appContext.packageName)
    }

    @Mock
    lateinit var repository: DefaultPublisherRepository

    lateinit var viewModel: HomeViewModel

    private lateinit var articles: LiveData<List<Article>>

    @Before
    fun setup() {

        runBlocking {
            `when`(repository.getArticles()).thenReturn(Result.Success(listOf()))
        }
        viewModel = HomeViewModel(repository)
        articles = viewModel.articles
    }

    @Test
    fun getArticles_isEmpty() {
        viewModel.getArticlesResult()
        articles.value?.let {
            assertNotNull(it)
        }
    }


    @Test
    fun getArticles_isFail() {

        runBlocking {
            `when`(repository.getArticles()).thenReturn(Result.Fail("hihi"))
        }

        viewModel.getArticlesResult()

        assertNull(articles.value)
    }
}
