package hui.shou.tao.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import hui.shou.tao.base.BaseFragment
import hui.shou.tao.databinding.FragmentHistoryBinding
import hui.shou.tao.databinding.HistoryItemBinding
import hui.shou.tao.event.xEvent
import hui.shou.tao.utils.Constant
import hui.shou.tao.utils.getAllRecords
import me.lwb.bindingadapter.BindingAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {


    override fun initialization() {
        EventBus.getDefault().register(this)
        getAllRecords().apply {
            if (size > 0) {
                val adapter = BindingAdapter(HistoryItemBinding::inflate, this) { _, item ->
                    Glide.with(requireActivity()).load(item.img).into(binding.icon)
                    binding.time.text = item.time
                    binding.name.text = item.typeName
                    binding.cost.text = item.cost
                }
                fragmentBinding.recycler.layoutManager = LinearLayoutManager(requireActivity())
                fragmentBinding.recycler.adapter = adapter
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: xEvent) {
        val msg = e.getMessage()
        when (msg[0]) {
            Constant.MSG_ADD_RECORD -> {
                getAllRecords().apply {
                    if (size > 0) {
                        val adapter = BindingAdapter(HistoryItemBinding::inflate, this) { _, item ->
                            Glide.with(requireActivity()).load(item.img).into(binding.icon)
                            binding.time.text = item.time
                            binding.name.text = item.typeName
                            binding.cost.text = item.cost
                        }
                        fragmentBinding.recycler.layoutManager = LinearLayoutManager(requireActivity())
                        fragmentBinding.recycler.adapter = adapter
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}