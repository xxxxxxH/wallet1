package hui.shou.tao.fragment

import hui.shou.tao.base.BaseFragment
import hui.shou.tao.databinding.FragmentRecentBinding
import hui.shou.tao.event.xEvent
import hui.shou.tao.utils.Constant
import hui.shou.tao.utils.Type
import hui.shou.tao.utils.calculateCostByType
import hui.shou.tao.utils.getAllRecords
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RecentFragment : BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate) {

    override fun initialization() {
        EventBus.getDefault().register(this)
        val data = getAllRecords()
        val type1Cost = calculateCostByType(Type.TYPE1, data)
        val type2Cost = calculateCostByType(Type.TYPE2, data)
        val type3Cost = calculateCostByType(Type.TYPE3, data)
        setTotalCost(type1 = type1Cost.toString(), type2 = type2Cost.toString(), type3 = type3Cost.toString())
    }

    private fun setTotalCost(type1: String, type2: String, type3: String) {
        fragmentBinding.liveTotal.text = type1
        fragmentBinding.shopTotal.text = type2
        fragmentBinding.cashTotal.text = type3
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: xEvent) {
        val msg = e.getMessage()
        when (msg[0]) {
            Constant.MSG_ADD_RECORD -> {
                val data = getAllRecords()
                val type1Cost = calculateCostByType(Type.TYPE1, data)
                val type2Cost = calculateCostByType(Type.TYPE2, data)
                val type3Cost = calculateCostByType(Type.TYPE3, data)
                setTotalCost(type1 = type1Cost.toString(), type2 = type2Cost.toString(), type3 = type3Cost.toString())
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}