package app.appworks.school.publisher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.data.source.PublisherRepository
import app.appworks.school.publisher.publish.PublishViewModel

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels which need [Author].
 */
@Suppress("UNCHECKED_CAST")
class AuthorViewModelFactory(
    private val repository: PublisherRepository,
    private val author: Author?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PublishViewModel::class.java)) {
            return PublishViewModel(repository, author) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}