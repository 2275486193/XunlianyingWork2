package com.example.w2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
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

        val spnCity = findViewById<Spinner>(R.id.spnCity)
        val tvCityName = findViewById<TextView>(R.id.tvCityName)
        val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvWind = findViewById<TextView>(R.id.tvWind)
        val rvForecast = findViewById<RecyclerView>(R.id.rvForecast)

        rvForecast.layoutManager = LinearLayoutManager(this)
        val cities = resources.getStringArray(R.array.city_options)
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCity.adapter = cityAdapter

        val realtimeMap = mapOf(
            "北京" to Triple("多云", "6℃", "东北风3级 湿度60%"),
            "上海" to Triple("晴", "12℃", "东南风2级 湿度55%"),
            "广州" to Triple("小雨", "18℃", "南风3级 湿度70%"),
            "深圳" to Triple("阴", "20℃", "东风2级 湿度65%")
        )

        fun forecastFor(city: String): List<ForecastItem> {
            return when (city) {
                "北京" -> listOf(
                    ForecastItem("周一", "多云", "-1℃ ~ 6℃", "北风3级"),
                    ForecastItem("周二", "晴", "-2℃ ~ 7℃", "微风"),
                    ForecastItem("周三", "小雪", "-4℃ ~ 3℃", "东北风3级"),
                    ForecastItem("周四", "多云", "-1℃ ~ 5℃", "北风2级")
                )
                "上海" -> listOf(
                    ForecastItem("周一", "晴", "10℃ ~ 15℃", "东南风2级"),
                    ForecastItem("周二", "多云", "9℃ ~ 14℃", "东风2级"),
                    ForecastItem("周三", "小雨", "8℃ ~ 12℃", "东北风3级"),
                    ForecastItem("周四", "阴", "7℃ ~ 11℃", "北风2级")
                )
                "广州" -> listOf(
                    ForecastItem("周一", "小雨", "17℃ ~ 22℃", "南风3级"),
                    ForecastItem("周二", "阴", "16℃ ~ 21℃", "东风2级"),
                    ForecastItem("周三", "多云", "18℃ ~ 24℃", "微风"),
                    ForecastItem("周四", "阵雨", "19℃ ~ 25℃", "南风2级")
                )
                "深圳" -> listOf(
                    ForecastItem("周一", "阴", "19℃ ~ 23℃", "东风2级"),
                    ForecastItem("周二", "多云", "18℃ ~ 24℃", "东北风2级"),
                    ForecastItem("周三", "小雨", "17℃ ~ 22℃", "北风2级"),
                    ForecastItem("周四", "多云", "18℃ ~ 23℃", "微风")
                )
                else -> emptyList()
            }
        }

        spnCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val city = cities[position]
                val (desc, temp, wind) = realtimeMap[city] ?: Triple("--", "--", "--")
                tvCityName.text = "$city 实时天气"
                tvWeatherDesc.text = desc
                tvTemp.text = temp
                tvWind.text = wind
                rvForecast.adapter = ForecastAdapter(forecastFor(city))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spnCity.setSelection(0)
    }
}
