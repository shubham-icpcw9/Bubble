package com.example.bubble

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsAdapter { item ->
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(item.url))
        }
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url =
            "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=392f5d3a4fab43139273486eae83048c"
        val jsonObjectRequest = object : JsonObjectRequest(Method.GET,
            url,
            null, { response ->
                val jsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    newsArray.add(
                        News(
                            jsonObject.getString("title"),
                            jsonObject.getString("author"),
                            jsonObject.getString("url"),
                            jsonObject.getString("urlToImage")
                        )
                    )
                }
                mAdapter.updateNews(newsArray)
            }, {

            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}