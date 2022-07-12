package hui.shou.tao.activity

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bumptech.glide.Glide
import com.tencent.mmkv.MMKV
import hui.shou.tao.base.BaseActivity
import hui.shou.tao.databinding.ActivityAddBinding
import hui.shou.tao.entity.HistoryEntity
import hui.shou.tao.event.xEvent
import hui.shou.tao.utils.Constant
import hui.shou.tao.utils.saveKeys
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val img by lazy {
        intent.getIntExtra("img", -1)
    }

    private val typeName by lazy {
        intent.getStringExtra("typeName")
    }

    override fun getViewBinding() = ActivityAddBinding.inflate(layoutInflater)

    override fun initialization() {
        Glide.with(this).load(img).into(activityBinding.icon)
        activityBinding.costType.text = typeName
        activityBinding.chooseType.setOnClickListener { finish() }
        activityBinding.chooseDate.setOnClickListener {
            TimePickerBuilder(
                this
            ) { date, _ ->
                activityBinding.timeDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
            }.setType(booleanArrayOf(true, true, true, true, true, true))
                .isDialog(true)
                .build().show()
        }
        activityBinding.costInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm: InputMethodManager =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            }
            false
        }
        activityBinding.saveRecord.setOnClickListener {
            if (TextUtils.isEmpty(activityBinding.costInput.text.toString())){
                finish()
            }
            val history = HistoryEntity(
                img = img,
                typeName = typeName!!,
                time = activityBinding.timeDate.text.toString(),
                cost = activityBinding.costInput.text.toString()
            )
            val currentRecordKey = System.currentTimeMillis().toString()
            saveKeys(currentRecordKey)
            MMKV.defaultMMKV().encode(currentRecordKey, history)
            EventBus.getDefault().post(xEvent(Constant.MSG_ADD_RECORD))
            finish()
        }
    }
}