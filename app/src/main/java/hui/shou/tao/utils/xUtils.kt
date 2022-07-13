package hui.shou.tao.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.tencent.mmkv.MMKV
import hui.shou.tao.R
import hui.shou.tao.entity.HistoryEntity
import hui.shou.tao.entity.RecordEntity
import hui.shou.tao.fragment.HistoryFragment
import hui.shou.tao.fragment.RecentFragment
import hui.shou.tao.fragment.RecordFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val bottomTitle = arrayOf("Recent", "Record", "History")

//token
var launcherTime
    get() = MMKV.defaultMMKV().getString("Launcher_Time", "")
    set(value) {
        MMKV.defaultMMKV().putString("Launcher_Time", value)
    }

//token
var applink
    get() = MMKV.defaultMMKV().getString("Facebook_Applink", "")
    set(value) {
        MMKV.defaultMMKV().putString("Facebook_Applink", value)
    }

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

fun calculateCostByType(t: Type, allRecord: ArrayList<HistoryEntity>): Double {
    var result = 0.0
    when (t) {
        Type.TYPE1 -> {
            allRecord.forEach {
                if (it.typeName == "Clothing" || it.typeName == "Eating" || it.typeName == "Game" || it.typeName == "Product") {
                    val temp = it.cost.toDouble()
                    result += temp
                }
            }
        }
        Type.TYPE2 -> {
            allRecord.forEach {
                if (it.typeName == "Other" || it.typeName == "Travel" || it.typeName == "Gift" || it.typeName == "Wallet") {
                    val temp = it.cost.toDouble()
                    result += temp
                }
            }
        }
        Type.TYPE3 -> {
            allRecord.forEach {
                if (it.typeName == "InCharge" || it.typeName == "OutCharge" || it.typeName == "Store") {
                    val temp = it.cost.toDouble()
                    result += temp
                }
            }
        }
    }
    return result
}

fun AppCompatActivity.setFb(a: () -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        OkGo.post<String>("https://p8nigy5i0i.execute-api.us-west-2.amazonaws.com/default/804FBID")
            .params("appId", AesUtil.encrypt("804")).params("launchTime", AesUtil.encrypt(launcherTime))
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    val result = response?.body()
                    result?.let {
                        FacebookSdk.setApplicationId(it)
                        FacebookSdk.sdkInitialize(this@setFb)
                        if (applink.isNullOrEmpty()) {
                            AppLinkData.fetchDeferredAppLinkData(this@setFb) { a ->
                                applink = a?.targetUri?.toString() ?: "Channel is empty"
                                a()
                            }
                        } else {
                            a()
                        }
                    }
                }

            })
    }
}