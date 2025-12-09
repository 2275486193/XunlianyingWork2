package com.example.w2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtCity = findViewById<TextInputEditText>(R.id.edtCityCode)
        val btnQuery = findViewById<MaterialButton>(R.id.btnQuery)
        val tvCityName = findViewById<TextView>(R.id.tvCityName)
        val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvWind = findViewById<TextView>(R.id.tvWind)
        val rvForecast = findViewById<RecyclerView>(R.id.rvForecast)

        rvForecast.layoutManager = LinearLayoutManager(this)
        val items = mutableListOf(
            ForecastItem("周一", "晴转多云", "-2℃ ~ 6℃", "北风3级"),
            ForecastItem("周二", "小雨", "0℃ ~ 5℃", "东风2级"),
            ForecastItem("周三", "多云", "1℃ ~ 7℃", "西风3级"),
            ForecastItem("周四", "晴", "-1℃ ~ 8℃", "微风")
        )
        rvForecast.adapter = ForecastAdapter(items)

        btnQuery.setOnClickListener {
            val code = edtCity.text?.toString().orEmpty()
            tvCityName.text = getString(R.string.label_city_with_code, code.ifEmpty { "--" })
            tvWeatherDesc.text = "多云"
            tvTemp.text = "6℃"
            tvWind.text = "东北风3级 湿度60%"
        }
    }
}
