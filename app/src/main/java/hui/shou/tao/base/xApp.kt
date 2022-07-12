package hui.shou.tao.base

import android.app.Application
import com.tencent.mmkv.MMKV

class xApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}