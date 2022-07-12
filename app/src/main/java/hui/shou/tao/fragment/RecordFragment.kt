package hui.shou.tao.fragment

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import hui.shou.tao.activity.AddActivity
import hui.shou.tao.base.BaseFragment
import hui.shou.tao.databinding.FragmentRecordBinding
import hui.shou.tao.databinding.RecordItemBinding
import hui.shou.tao.utils.recordData
import me.lwb.bindingadapter.BindingAdapter


class RecordFragment : BaseFragment<FragmentRecordBinding>(FragmentRecordBinding::inflate) {
    override fun initialization() {
        val list = recordData()
        val adapter = BindingAdapter(RecordItemBinding::inflate, list) { position, item ->
            Glide.with(requireActivity()).load(item.img).into(binding.icon)
            binding.name.text = item.typeName
            binding.root.setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        AddActivity::class.java
                    ).apply {
                        putExtra("img", item.img)
                        putExtra("typeName", item.typeName)
                    })
            }
        }
        fragmentBinding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        fragmentBinding.recycler.adapter = adapter
    }
}