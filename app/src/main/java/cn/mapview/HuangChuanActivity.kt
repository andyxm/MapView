package cn.mapview

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.mapview.databinding.ActivityHuangChuanBinding

class HuangChuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHuangChuanBinding
    var scale: Float = 0f
    var mapWidth: Float = 1450f
    var mapHeight: Float = 1200f
    var screenWidth: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHuangChuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        screenWidth = getScreenWidth(this)
        scale = screenWidth / mapWidth
        binding.hcScrollView.postDelayed({ scaleAndScroll() }, 800)
    }

    private fun scaleAndScroll() {
        binding.hcLayout.scaleX = scale
        binding.hcLayout.scaleY = scale
        binding.hcScrollView.smoothScrollTo(
            (binding.hcLayout.width - screenWidth) / 2,
            (binding.hcLayout.height - (scale * mapWidth)).toInt() / 2 - 200
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