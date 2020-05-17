package app.appworks.school.publisher.ext

import androidx.fragment.app.Fragment
import app.appworks.school.publisher.PublisherApplication
import app.appworks.school.publisher.data.Author
import app.appworks.school.publisher.factory.AuthorViewModelFactory
import app.appworks.school.publisher.factory.ViewModelFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PublisherApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(author: Author?): AuthorViewModelFactory {
    val repository = (requireContext().applicationContext as PublisherApplication).repository
    return AuthorViewModelFactory(repository, author)
}