package com.learning.pets.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learning.pets.model.Pet

/**
 * @author Anita
 * This dao class is used handle query related database for Pets.
 * It provides the methods that the rest of the app uses to interact with data in the the Pet table.
 */
@Dao
interface IPetListDao {
    //Insert all values
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPets(pet: ArrayList<Pet>)

    //Get all values
    @Query("SELECT * FROM Pet ORDER BY pet_name ASC")
    fun getAllPets(): LiveData<List<Pet?>?>?
}