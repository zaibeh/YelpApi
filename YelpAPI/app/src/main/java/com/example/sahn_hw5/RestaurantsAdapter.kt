package com.example.sahn_hw5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide


class RestaurantsAdapter(private val restaurants: ArrayList<Restaurants>) : RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {
    private val TAG = "RestaurantsAdapter"
    private val mileFormat = DecimalFormat("#.##")

    private fun distanceInMiles(value: Float): String{
        val convertedValue = (value*0.0062137)
        val miles = (mileFormat.format(convertedValue)).toDouble()
        return "$miles mi"
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.restaurant_name)
        val distance = itemView.findViewById<TextView>(R.id.restaurant_distance)
        val address = itemView.findViewById<TextView>(R.id.restaurant_addy)
        val price = itemView.findViewById<TextView>(R.id.restaurant_price)
        val rate = itemView.findViewById<RatingBar>(R.id.restaurant_rate)
        val review = itemView.findViewById<TextView>(R.id.restaurant_review)
        val image = itemView.findViewById<ImageView>(R.id.restaurant_image)
        val foodCategory = itemView.findViewById<TextView>(R.id.restaurant_category)

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = restaurants[position]
        holder.name.text = currentItem.name
        holder.foodCategory.text = currentItem.categories[0].title
        holder.review.text = "${currentItem.review_count} reviews"
        holder.distance.text = distanceInMiles(currentItem.distance)
        holder.price.text = currentItem.price
        holder.rate.progress = (currentItem.rating*2).toInt()
        holder.address.text = currentItem.location.address1


        val context = holder.itemView.context

       Glide.with(context)
           .load(currentItem.image_url)
           .placeholder(R.drawable.semilla_im)
           .into(holder.image)
    }

    override fun getItemCount(): Int{
        return restaurants.size
    }




}
