package hui.shou.tao.activity

import hui.shou.tao.base.BaseActivity
import hui.shou.tao.databinding.ActivityMainBinding
import hui.shou.tao.utils.bottomTitle
import hui.shou.tao.utils.fragmentCollections

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initialization() {
        activityBinding.tabLayout.setViewPager(
            activityBinding.viewpager,
            bottomTitle,
            this,
            fragmentCollections()
        )
    }
}