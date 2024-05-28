package cn.mapview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.mapview.databinding.ActivityHuangChuanBinding

class HuangChuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHuangChuanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHuangChuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}