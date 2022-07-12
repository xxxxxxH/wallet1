package hui.shou.tao.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordEntity(var img: Int = 0, var typeName: String = "") : Parcelable
