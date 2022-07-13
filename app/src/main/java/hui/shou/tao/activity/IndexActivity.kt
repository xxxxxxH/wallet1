package hui.shou.tao.activity

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import hui.shou.tao.base.BaseActivity
import hui.shou.tao.databinding.ActivityIndexBinding
import hui.shou.tao.utils.launcherTime
import hui.shou.tao.utils.setFb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndexActivity : BaseActivity<ActivityIndexBinding>() {

    override fun getViewBinding() = ActivityIndexBinding.inflate(layoutInflater)

    override fun initialization() {
        if (launcherTime.isNullOrEmpty()) {
            launcherTime = System.currentTimeMillis().toString()
        }
        setFb {
            lifecycleScope.launch(Dispatchers.Main) {
                startActivity(Intent(this@IndexActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}