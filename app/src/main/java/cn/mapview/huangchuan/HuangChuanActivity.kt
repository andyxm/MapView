package cn.mapview.huangchuan

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.mapview.BaseMapView
import cn.mapview.databinding.ActivityHuangchuanBinding

class HuangChuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHuangchuanBinding
    var scale: Float = 0f
    var mapWidth: Float = 1450f
    var mapHeight: Float = 1200f
    var screenWidth: Int = 0
    private val hasProjectColor = -0xbf4910
    private val selectdColor = -0x14a3b9
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHuangchuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        screenWidth = getScreenWidth(this)
        scale = screenWidth / mapWidth
        binding.hcScrollView.postDelayed({ scaleAndScroll() }, 800)
        binding.hcMapView.apply {
            setSelectedColor(selectdColor)
            setOnProvinceDoubleClickListener { scaleAndScroll() }
            setOnProvinceSelectedListener(BaseMapView.OnProvinceSelectedListener { selected, repeatClick ->
                if (repeatClick) {
                    scaleAndScroll()
                    return@OnProvinceSelectedListener
                }
                val name = HuangChuanManager.name[selected]
                binding.name.text = "名称: $name"
            })
        }
    }

    private fun scaleAndScroll() {
        binding.hcLayout.scaleX = scale
        binding.hcLayout.scaleY = scale
        binding.hcScrollView.smoothScrollTo(
            (binding.hcLayout.width - screenWidth) / 2, (binding.hcLayout.height - (scale * mapWidth)).toInt() / 2 - 200
        )
    }

    private fun getScreenWidth(context: Context): Int {
        val manager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val defaultDisplay = manager.defaultDisplay
        val outMetrics = DisplayMetrics()
        defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }
}