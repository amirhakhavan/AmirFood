package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myapplication.Room.Food
import com.example.myapplication.Room.FoodDao
import com.example.myapplication.Room.MyDatabase
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.DialogAddNewItemBinding
import com.example.myapplication.databinding.DialogDeleteItemBinding
import com.example.myapplication.databinding.DialogUpdateItemBinding

const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"


class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FoodAdapter
    lateinit var FoodDao: FoodDao


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        FoodDao = MyDatabase.getDatabase(this).foodDao


        val sharedPreferences = getSharedPreferences("amirfood", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run", true)) {
            firstRun()
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }


        showAllData()


        binding.btnRemoveAll.setOnClickListener {
            deleteAllFoods()
        }
        binding.btnAdd.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)

            val alertDialog = dialog.create()
            alertDialog.show()

            alertDialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(this, R.drawable.circle_shape_search)
            )


            dialogBinding.btnSubmit.setOnClickListener {

                if (
                    dialogBinding.dialogTxtFoodname.length() > 0
                    && dialogBinding.dialogTxtPrice.length() > 0
                    && dialogBinding.dialogTxtPrice.length() > 0
                    && dialogBinding.dialogTxtDistance.length() > 0
                ) {


                    val txtSub = dialogBinding.dialogTxtFoodname.text.toString()
                    val txtCity = dialogBinding.dialogTxtCityname.text.toString()
                    val txtPrice = dialogBinding.dialogTxtPrice.text.toString()
                    val txtDistance = dialogBinding.dialogTxtDistance.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingStars: Float = (1..8).random().toFloat()

                    val randomForUrl = (0..11).random()
                    val urlPic = "$BASE_URL_IMAGE$randomForUrl.jpg"
                    val newfood = Food(
                        txtSubject = txtSub,
                        txtPrice = txtPrice,
                        txtDistance = txtDistance,
                        txtCity = txtCity,
                        urlImage = urlPic,
                        rating = txtRatingNumber,
                        numberOfRatings = ratingStars
                    )
                    adapter.addNewFood(newfood)
                    insertNewFood(newfood)
                    alertDialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)

                } else {
                    Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show()

                }
            }


        }
        binding.fieldtxtSearch.addTextChangedListener { editTextInput ->

                searchOnDatabase(editTextInput!!.toString())


        }
    }


    private fun searchOnDatabase(editTextInput: String) {

        if (editTextInput.isNotEmpty()) {


            val filteredList = FoodDao.searchFood(editTextInput)
            adapter.setData(ArrayList(filteredList))


        }
        else
        {
            val data = FoodDao.getAllFood()
            adapter.setData(ArrayList(data))
        }
    }


private fun showAllData() {
    val foodData = FoodDao.getAllFood()

    adapter = FoodAdapter(ArrayList(foodData), this)
    binding.recyclerMain.adapter = adapter
    binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    Log.v("testLog", foodData.toString())
}

private fun insertNewFood(newfood: Food) {
    FoodDao.insertFood(food = newfood)
}

private fun firstRun() {

    val foodList = arrayListOf(
        Food(
            txtSubject = "Hamburger",
            txtPrice = "15",
            txtDistance = "3",
            txtCity = "Isfahan, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
            rating = 20,
            numberOfRatings = 4.5f
        ),
        Food(
            txtSubject = "Grilled fish",
            txtPrice = "20",
            txtDistance = "2.1",
            txtCity = "Tehran, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
            rating = 10,
            numberOfRatings = 4f
        ),
        Food(
            txtSubject = "Lasania",
            txtPrice = "40",
            txtDistance = "1.4",
            txtCity = "Isfahan, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
            rating = 30,
            numberOfRatings = 2f
        ),
        Food(
            txtSubject = "pizza",
            txtPrice = "10",
            txtDistance = "2.5",
            txtCity = "Zahedan, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
            rating = 80,
            numberOfRatings = 1.5f
        ),
        Food(
            txtSubject = "Sushi",
            txtPrice = "20",
            txtDistance = "3.2",
            txtCity = "Mashhad, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
            rating = 200,
            numberOfRatings = 3f
        ),
        Food(
            txtSubject = "Roasted Fish",
            txtPrice = "40",
            txtDistance = "3.7",
            txtCity = "Jolfa, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
            rating = 50,
            numberOfRatings = 3.5f
        ),
        Food(
            txtSubject = "Fried chicken",
            txtPrice = "70",
            txtDistance = "3.5",
            txtCity = "NewYork, USA",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
            rating = 70,
            numberOfRatings = 2.5f
        ),
        Food(
            txtSubject = "Vegetable salad",
            txtPrice = "12",
            txtDistance = "3.6",
            txtCity = "Berlin, Germany",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
            rating = 40,
            numberOfRatings = 4.5f
        ),
        Food(
            txtSubject = "Grilled chicken",
            txtPrice = "10",
            txtDistance = "3.7",
            txtCity = "Beijing, China",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
            rating = 15,
            numberOfRatings = 5f
        ),
        Food(
            txtSubject = "Baryooni",
            txtPrice = "16",
            txtDistance = "10",
            txtCity = "Ilam, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
            rating = 28,
            numberOfRatings = 4.5f
        ),
        Food(
            txtSubject = "Ghorme Sabzi",
            txtPrice = "11.5",
            txtDistance = "7.5",
            txtCity = "Karaj, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
            rating = 27,
            numberOfRatings = 5f
        ),
        Food(
            txtSubject = "Rice",
            txtPrice = "12.5",
            txtDistance = "2.4",
            txtCity = "Shiraz, Iran",
            urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
            rating = 35,
            numberOfRatings = 2.5f
        ),
    )
    FoodDao.insertAllFood(foodList)


}

private fun deleteAllFoods() {
    FoodDao.deleteAllData()
    showAllData()
}

private fun updateFood(newfood: Food) {
    FoodDao.updateFood(newfood)
}

override fun onFoodClicked(food: Food, position: Int) {

    val dialog = AlertDialog.Builder(this)
    val dialogUpdatebinding = DialogUpdateItemBinding.inflate(layoutInflater)
    dialog.setView(dialogUpdatebinding.root)
    dialog.setCancelable(true)
    val alertDialog2 = dialog.create()
    alertDialog2.show()

    dialogUpdatebinding.dialogTxtCityname.setText(food.txtCity)
    dialogUpdatebinding.dialogTxtPrice.setText(food.txtPrice)
    dialogUpdatebinding.dialogTxtFoodname.setText(food.txtSubject)
    dialogUpdatebinding.dialogTxtDistance.setText(food.txtDistance)

    alertDialog2.window?.setBackgroundDrawable(
        ContextCompat.getDrawable(this, R.drawable.circle_shape_search)
    )

    dialogUpdatebinding.btnCancel.setOnClickListener {
        alertDialog2.dismiss()
    }

    dialogUpdatebinding.btnSubmit1.setOnClickListener {


        val txtSub = dialogUpdatebinding.dialogTxtFoodname.text.toString()
        val txtCity = dialogUpdatebinding.dialogTxtCityname.text.toString()
        val txtPrice = dialogUpdatebinding.dialogTxtPrice.text.toString()
        val txtDistance = dialogUpdatebinding.dialogTxtDistance.text.toString()


        if (
            dialogUpdatebinding.dialogTxtCityname.length() > 0 &&
            dialogUpdatebinding.dialogTxtPrice.length() > 0 &&
            dialogUpdatebinding.dialogTxtFoodname.length() > 0 &&
            dialogUpdatebinding.dialogTxtDistance.length() > 0


        ) {

            val newfood = Food(
                id = food.id,
                txtSubject = txtSub,
                txtPrice = txtPrice,
                txtDistance = txtDistance,
                txtCity = txtCity,
                urlImage = food.urlImage,
                numberOfRatings = food.rating.toFloat(),
                rating = food.numberOfRatings.toInt()
            )


            adapter.updateFood(newfood, position)
            updateFood(newfood)
            alertDialog2.dismiss()
        } else {
            Toast.makeText(this, "Enter All Values", Toast.LENGTH_SHORT).show()
        }
    }

}

override fun onFoodLongClicked(food: Food, position: Int) {


    val dialog = AlertDialog.Builder(this)
    val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
    dialog.setView(dialogDeleteBinding.root)
    dialog.setCancelable(true)

    val alertDialog1 = dialog.create()
    alertDialog1.show()

    alertDialog1.window?.setBackgroundDrawable(
        ContextCompat.getDrawable(this, R.drawable.circle_shape_search)
    )

    dialogDeleteBinding.btnNo.setOnClickListener {
        alertDialog1.dismiss()
    }

    dialogDeleteBinding.btnYes.setOnClickListener {
        adapter.removeFood(food, position)
        FoodDao.removeFood(food)
        alertDialog1.dismiss()
        binding.recyclerMain.scrollToPosition(0)
    }

}


}




