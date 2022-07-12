package hui.shou.tao.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryEntity(
    var img: Int = 0,
    var typeName: String = "",
    var time: String = "",
    var cost: String = ""
) :
    Parcelable
