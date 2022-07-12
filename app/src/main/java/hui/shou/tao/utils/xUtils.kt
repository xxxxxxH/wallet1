package hui.shou.tao.utils

import androidx.fragment.app.Fragment
import com.tencent.mmkv.MMKV
import hui.shou.tao.R
import hui.shou.tao.entity.HistoryEntity
import hui.shou.tao.entity.RecordEntity
import hui.shou.tao.fragment.HistoryFragment
import hui.shou.tao.fragment.RecentFragment
import hui.shou.tao.fragment.RecordFragment

val bottomTitle = arrayOf("Recent", "Record", "History")

fun fragmentCollections(): ArrayList<Fragment> {
    val list = ArrayList<Fragment>()
    list.add(RecentFragment())
    list.add(RecordFragment())
    list.add(HistoryFragment())
    return list
}

fun recordData(): ArrayList<RecordEntity> {
    val list = ArrayList<RecordEntity>()
    list.add(RecordEntity(img = R.mipmap.ic_clothing, typeName = "Clothing"))
    list.add(RecordEntity(img = R.mipmap.ic_eating, typeName = "Eating"))
    list.add(RecordEntity(img = R.mipmap.ic_games, typeName = "Game"))
    list.add(RecordEntity(img = R.mipmap.ic_products, typeName = "Product"))
    list.add(RecordEntity(img = R.mipmap.ic_travel, typeName = "Travel"))
    list.add(RecordEntity(img = R.mipmap.ic_other, typeName = "Other"))
    list.add(RecordEntity(img = R.mipmap.ic_gift, typeName = "Gift"))
    list.add(RecordEntity(img = R.mipmap.ic_in_charges, typeName = "InCharges"))
    list.add(RecordEntity(img = R.mipmap.ic_out_charges, typeName = "OutCharges"))
    list.add(RecordEntity(img = R.mipmap.ic_wallet, typeName = "Wallet"))
    list.add(RecordEntity(img = R.mipmap.ic_pig, typeName = "Store"))
    return list
}

fun saveKeys(newKey: String) {
    var keys = MMKV.defaultMMKV().decodeStringSet(Constant.KEY_RECORDS)
    if (keys == null) {
        keys = HashSet<String>()
    }
    keys.add(newKey)
    MMKV.defaultMMKV().encode(Constant.KEY_RECORDS, keys)
}

fun getKeys(): ArrayList<String> {
    val data = ArrayList<String>()
    val keys = MMKV.defaultMMKV().decodeStringSet(Constant.KEY_RECORDS)
    if (keys != null) {
        data.addAll(keys)
    }
    return data
}

fun getAllRecords(): ArrayList<HistoryEntity> {
    val data = ArrayList<HistoryEntity>()
    MMKV.defaultMMKV().decodeStringSet(Constant.KEY_RECORDS)?.forEach {
        MMKV.defaultMMKV().decodeParcelable(it, HistoryEntity::class.java)?.let { item ->
            data.add(item)
        }
    }
    return data
}