package com.example.sahn_hw5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.ContentValues.TAG
import retrofit2.Response
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val YELP_URL = "https://api.yelp.com/v3/"
    private val API_KEY =
        "7v50NMnCnW-aZHDfUR_WNH_eD-8m-iKgtQhUQBRQwVD4ZzCPIyE-VDhGDX1JvvgJxx99lseuDN1YSvdUbyt8pEX_WMn9QzmlgP7C-lDNk4i03V_65C8AgWFmR2FnYnYx"
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showSearchDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oops! Missing search term")
        builder.setMessage("Please enter a valid search term.")
        builder.setIcon(android.R.drawable.ic_delete)
        builder.setNegativeButton("Got it!") { dialog, which ->
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun showLocationDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oops! Missing location term")
        builder.setMessage("Please enter a valid location.")
        builder.setIcon(android.R.drawable.ic_delete)
        builder.setNegativeButton("Got it!") { dialog, which ->
        }
        val dialog = builder.create()
        dialog.show()
    }

        fun onSearchClick(view: View) {
            food_search.hideKeyboard()
            location_search.hideKeyboard()
            val searchTerm = food_search.text.toString()
            val searchLocation = location_search.text.toString()

            val restaurants = ArrayList<Restaurants>()
            val adapter = RestaurantsAdapter(restaurants)
            val recyclerView = findViewById<RecyclerView>(R.id.yelp_recyclerview)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)


            val retrofit =
                Retrofit.Builder().baseUrl(YELP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val yelpInterface = retrofit.create(YelpInterface::class.java)


            yelpInterface.getRestaurantInfo("Bearer $API_KEY", searchTerm, searchLocation)
                .enqueue(object : Callback<YelpData> {

                    override fun onFailure(call: Call<YelpData>, t: Throwable) {
                        Log.i(TAG, "onFailure:  $t")

                    }
                    override fun onResponse(call: Call<YelpData>, response: Response<YelpData>) {
                        Log.i(TAG, "onResponse: $response")
                        val body = response.body()?:return
                        restaurants.addAll(body.businesses)
                        adapter.notifyDataSetChanged()
                    }

                })

            when {
                searchTerm.isEmpty() -> {
                    showSearchDialogue()
                    return
                }
                searchLocation.isEmpty() -> {
                    showLocationDialogue()
                    return
                }
            }

        }
    }








