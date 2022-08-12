package com.learning.pets.bl

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.learning.pets.model.Pet
import com.learning.pets.model.WorkingHours
import com.learning.pets.repository.PetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @author Anita
 * The class initializes repository for Pet database.(Business Logic)
 */
object PetRepository {

    private val TAG: String? = PetRepository::class.simpleName
    private var petDatabase: PetDatabase? = null
    private var petLiveData: LiveData<List<Pet?>?>? = null

    private fun initializeDB(context: Context): PetDatabase {
        return PetDatabase.getDatabaseClient(context)
    }

    /**
     * This fun is used to read json file and get data from it
     * @parameter : context : Object of context
     * @return : Object of WorkingHours
     */
    suspend fun readWorkingHours(context: Context): WorkingHours? {
        var workingHours: WorkingHours? = null
        try {
            val waitFor = CoroutineScope(IO).async {
                workingHours = ReadJsonFiles.readConfigFile(context)
                return@async workingHours
            }
            waitFor.await()
        } catch (exception: Exception) {
            Log.e(TAG, "Reading working hours:$exception")
        }
        Log.e("WorkingHours22", workingHours.toString())
        return workingHours
    }

    /**
     * This fun is used to insert data into database
     * @parameter  context : Object of context
     */
    fun insertData(
        context: Context
    ) {
        try {
            petDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                //read data from json files
                petDatabase!!.petDao().insertAllPets(ReadJsonFiles.readPetList(context))
            }
        } catch (exception: Exception) {
            Log.e(TAG, "inserting data into database:$exception")
        }
    }

    /**
     * This fun is used to get data from database
     * @parameter : context : Object of context
     * @return : List of Pets
     */
    fun getPetDetails(context: Context): LiveData<List<Pet?>?>? {
        try {
            petDatabase = initializeDB(context)
            petLiveData = petDatabase!!.petDao().getAllPets()

        } catch (exception: Exception) {
            Log.e(TAG, "Getting pets data into database:$exception")
        }
        return petLiveData

    }
}