package hui.shou.tao.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    lateinit var activityBinding: T
    private val binding get() = activityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = getViewBinding()
        setContentView(binding.root)
        initialization()
    }

    protected abstract fun getViewBinding(): T

    abstract fun initialization()

}