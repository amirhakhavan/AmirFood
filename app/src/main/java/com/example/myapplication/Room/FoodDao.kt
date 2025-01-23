package com.example.myapplication.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {

    // Insert a list of foods
    @Insert
    fun insertAllFood(data: List<Food>)

    // Insert a single food
    @Insert
    fun insertFood(food: Food)

    // Delete a single food
    @Delete
    fun removeFood(food: Food)

    // Update a single food
    @Update
    fun updateFood(food: Food)

    // Get all foods as a List
    @Query("SELECT * FROM table_food")
    fun getAllFood(): List<Food>

    // Get all foods as LiveData (observable)
    @Query("SELECT * FROM table_food")
    fun getAllFoodLiveData(): LiveData<List<Food>>

    // Delete all foods and return the number of rows deleted
    @Query("DELETE FROM table_food")
    fun deleteAllData()

    // Search for foods by subject
    @Query("SELECT * FROM table_food WHERE txtSubject LIKE '%' || :search || '%'")
    fun searchFood(search: String): List<Food>
}