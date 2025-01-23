package com.example.myapplication


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.Room.Food


class FoodAdapter(private val data: ArrayList<Food>, private val foodEvent: FoodEvent) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtSub = itemView.findViewById<TextView>(R.id.txt_food_name)
        val txtCity = itemView.findViewById<TextView>(R.id.txt_subject_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.txt_price_food)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingMain = itemView.findViewById<RatingBar>(R.id.item_rating_main)


        fun bindData(position: Int) {
            txtCity.text = data[position].txtCity
            txtSub.text = data[position].txtSubject
            txtPrice.text = data[position].txtPrice + "$"
            txtDistance.text = data[position].txtDistance + " Miles from you"
            ratingMain.rating = data[position].numberOfRatings

            Glide
                .with(context)
                .load(data[position].urlImage)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .into(imgMain)

            itemView.setOnClickListener {
                foodEvent.onFoodClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.bindData(position)


    }

    fun addNewFood(newFood: Food) {

        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(newFood: Food, position: Int) {
        data[position] = newFood
        notifyItemChanged(position)
    }

    fun setData(newList: ArrayList<Food>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvent {

        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, position: Int)


    }


}