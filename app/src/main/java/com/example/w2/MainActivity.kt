package com.example.w2

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.TextView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
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

        val cityCodeMap = mapOf(
            "北京" to "110000",
            "上海" to "310000",
            "广州" to "440100",
            "深圳" to "440300"
        )

        fun queryWeather(city: String) {
            val adcode = cityCodeMap[city] ?: return
            Thread {
                try {
                    fun getJson(urlStr: String): JSONObject? {
                        return try {
                            val url = URL(urlStr)
                            val conn = url.openConnection() as HttpURLConnection
                            conn.connectTimeout = 8000
                            conn.readTimeout = 8000
                            conn.requestMethod = "GET"
                            BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8)).use { r ->
                                val sb = StringBuilder()
                                var line: String?
                                while (r.readLine().also { line = it } != null) sb.append(line)
                                JSONObject(sb.toString())
                            }
                        } catch (_: Exception) { null }
                    }

                    val liveRoot = getJson("https://restapi.amap.com/v3/weather/weatherInfo?city=$adcode&key=${BuildConfig.AMAP_KEY}&extensions=base")
                    val lives = liveRoot?.optJSONArray("lives")
                    val live = lives?.optJSONObject(0)
                    val desc = live?.optString("weather").orEmpty()
                    val temp = live?.optString("temperature")?.let { "$it℃" }.orEmpty()
                    val winddir = live?.optString("winddirection").orEmpty()
                    val windpower = live?.optString("windpower").orEmpty()
                    val humidity = live?.optString("humidity").orEmpty()
                    val wind = if (winddir.isNotEmpty() && windpower.isNotEmpty() && humidity.isNotEmpty()) "${winddir}风${windpower}级 湿度${humidity}%" else ""

                    val forecastRoot = getJson("https://restapi.amap.com/v3/weather/weatherInfo?city=$adcode&key=${BuildConfig.AMAP_KEY}&extensions=all")
                    val forecasts = forecastRoot?.optJSONArray("forecasts")
                    val first = forecasts?.optJSONObject(0)
                    val casts = first?.optJSONArray("casts")
                    val list = mutableListOf<ForecastItem>()
                    if (casts != null) {
                        val count = kotlin.math.min(4, casts.length())
                        for (i in 0 until count) {
                            val c = casts.getJSONObject(i)
                            val date = c.optString("date")
                            val dayweather = c.optString("dayweather")
                            val daytemp = c.optString("daytemp")
                            val nighttemp = c.optString("nighttemp")
                            val daywind = c.optString("daywind")
                            val tempRange = if (daytemp.isNotEmpty() && nighttemp.isNotEmpty()) "${nighttemp}℃ ~ ${daytemp}℃" else ""
                            val windText = if (daywind.isNotEmpty()) "${daywind}风" else ""
                            list.add(ForecastItem(date, dayweather, tempRange, windText))
                        }
                    }
                    runOnUiThread {
                        tvCityName.text = "$city 实时天气"
                        if (desc.isNotEmpty()) {
                            tvWeatherDesc.text = desc
                            tvWeatherDesc.visibility = View.VISIBLE
                        } else tvWeatherDesc.visibility = View.GONE

                        if (temp.isNotEmpty()) {
                            tvTemp.text = temp
                            tvTemp.visibility = View.VISIBLE
                        } else tvTemp.visibility = View.GONE

                        if (wind.isNotEmpty()) {
                            tvWind.text = wind
                            tvWind.visibility = View.VISIBLE
                        } else tvWind.visibility = View.GONE

                        rvForecast.adapter = ForecastAdapter(list)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        tvCityName.text = "$city 实时天气"
                        tvWeatherDesc.visibility = View.GONE
                        tvTemp.visibility = View.GONE
                        tvWind.visibility = View.GONE
                        rvForecast.adapter = ForecastAdapter(emptyList())
                    }
                }
            }.start()
        }

        spnCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val city = cities[position]
                queryWeather(city)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spnCity.setSelection(0)
    }
}
