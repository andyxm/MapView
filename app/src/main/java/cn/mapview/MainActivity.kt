package cn.mapview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.mapview.ChinaMapView.OnProvinceSelectedListener
import cn.mapview.beijing.BeijingActivity
import cn.mapview.china.ChinaActivity
import cn.mapview.databinding.ActivityMainBinding
import cn.mapview.huangchuan.HuangChuanActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val hasProjectColor = -0xbf4910
    private val selectdColor = -0x14a3b9
    var scale: Float = 0f
    var mapWidth: Float = 1450f
    var mapHeight: Float = 1200f
    var screenWidth: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapView.apply {
            setPaintColor(ChinaMapView.Area.XinJiang, hasProjectColor, true)
            setPaintColor(ChinaMapView.Area.GanSu, hasProjectColor, true)
            setPaintColor(ChinaMapView.Area.SiChuan, hasProjectColor, true)
            setPaintColor(ChinaMapView.Area.GuiZhou, hasProjectColor, true)
            setPaintColor(ChinaMapView.Area.GuangDong, hasProjectColor, true)
            setSelectdColor(selectdColor)
            setOnProvinceDoubleClickListener { scaleAndScroll() }
            setOnProvinceSelectedListener(OnProvinceSelectedListener { pArea, repeatClick ->
                if (repeatClick) {
                    scaleAndScroll()
                    return@OnProvinceSelectedListener
                }
                binding.tvData.text = "名称: ${pArea.name} 值: ${pArea.value}"
            })
        }
        screenWidth = getScreenWidth(this)
        scale = screenWidth / mapWidth


        binding.scrollView.postDelayed({ scaleAndScroll() }, 800)

        binding.china.setOnClickListener {
            startActivity(Intent(this@MainActivity, ChinaActivity::class.java))
        }
        binding.bejing.setOnClickListener {
            startActivity(Intent(this@MainActivity, BeijingActivity::class.java))
        }
        binding.huangchuan.setOnClickListener {
            startActivity(Intent(this@MainActivity, HuangChuanActivity::class.java))
        }

        XmlToPathConverter.printSvg(this.applicationContext)
    }

    private fun scaleAndScroll() {
        binding.rlMap.scaleX = scale
        binding.rlMap.scaleY = scale
        binding.scrollView.smoothScrollTo(
            (binding.rlMap.width - screenWidth) / 2,
            (binding.rlMap.height - (scale * mapWidth)).toInt() / 2 - 200
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