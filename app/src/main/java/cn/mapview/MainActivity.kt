package cn.mapview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.mapview.ChinaMapView.OnProvinceSelectedListener
import cn.mapview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val hasProjectColor = -0xbf4910
    private val selectdColor = -0x14a3b9
    var scale: Float = 0f
    var mapWidth: Float = 1450f
    var mapHeight: Float = 1200f
    var screenWidth: Int = 0
    var rlMap: RelativeLayout? = null
    var tvData: TextView? = null
    var scaleScrollView: HVScaleScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val chinaMapView = findViewById(R.id.mapView) as ChinaMapView
        rlMap = findViewById(R.id.rl_map) as RelativeLayout
        tvData = findViewById(R.id.tv_data) as TextView


        scaleScrollView = findViewById(R.id.scrollView) as HVScaleScrollView
        chinaMapView.setPaintColor(ChinaMapView.Area.XinJiang, hasProjectColor, true)
        chinaMapView.setPaintColor(ChinaMapView.Area.GanSu, hasProjectColor, true)
        chinaMapView.setPaintColor(ChinaMapView.Area.SiChuan, hasProjectColor, true)
        chinaMapView.setPaintColor(ChinaMapView.Area.GuiZhou, hasProjectColor, true)
        chinaMapView.setPaintColor(ChinaMapView.Area.GuangDong, hasProjectColor, true)
        chinaMapView.setSelectdColor(selectdColor)
        chinaMapView.setOnProvinceDoubleClickListener { scaleAndScroll() }

        chinaMapView.setOnProvinceSelectedListener(OnProvinceSelectedListener { pArea, repeatClick -> //                Log.e(TAG, "onProvinceSelected: " + pArea.name + "  " + pArea.value);
            if (repeatClick) {
                scaleAndScroll()
                return@OnProvinceSelectedListener
            }
            tvData!!.text = "名称：" + pArea.name + "   值：" + pArea.value
        })
        screenWidth = getScreenWidth(this)
        scale = screenWidth / mapWidth


        scaleScrollView!!.postDelayed({ scaleAndScroll() }, 800)

        binding.huangchuan.setOnClickListener {
            startActivity(Intent(this@MainActivity,HuangChuanActivity::class.java))
        }
    }

    private fun scaleAndScroll() {
        rlMap!!.scaleX = scale
        rlMap!!.scaleY = scale
        scaleScrollView!!.smoothScrollTo(
            (rlMap!!.width - screenWidth) / 2,
            (rlMap!!.height - (scale * mapWidth)).toInt() / 2 - 200
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