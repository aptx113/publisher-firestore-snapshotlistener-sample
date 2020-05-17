package app.appworks.school.publisher.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen on 2020-01-15.
 */
@Parcelize
data class Article(
    var id: String = "",
    var tag: String = "",
    var createdTime: Long = -1,
    var title: String = "",
    var content: String = "",
    val author: Author? = null
) : Parcelable