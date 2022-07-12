package hui.shou.tao.fragment

import hui.shou.tao.base.BaseFragment
import hui.shou.tao.databinding.FragmentRecentBinding
import hui.shou.tao.event.xEvent
import hui.shou.tao.utils.Constant
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RecentFragment : BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate) {

    override fun initialization() {
        EventBus.getDefault().register(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: xEvent){
        val msg = e.getMessage()
        when(msg[0]){
            Constant.MSG_ADD_RECORD -> {

            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}