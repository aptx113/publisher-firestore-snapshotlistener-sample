package app.appworks.school.publisher.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen on 2020-01-15.
 */
@Parcelize
data class Author(
    val id: String = "",
    val name: String = "",
    val email: String = ""
) : Parcelable