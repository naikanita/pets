package com.learning.pets.bl

import android.content.Context
import android.util.Log
import com.learning.pets.PetApplication
import com.learning.pets.bl.CommonUtil.INDEX0
import com.learning.pets.bl.CommonUtil.INDEX3
import com.learning.pets.bl.CommonUtil.INDEX4
import com.learning.pets.model.Pet
import com.learning.pets.model.WorkingHours
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: Anita
 * This utility  class is used to read json files
 */
object ReadJsonFiles {

    private val TAG: String? = ReadJsonFiles::class.simpleName

    /**
     * This fun is used to read config.json file from assets folder
     * @param: context : Application context
     */
    fun readConfigFile(context: Context): WorkingHours {
        // get JSONObject from JSON file
        val obj = JSONObject(loadJsonData(context, "config.json"))
        val petSetting = obj.getJSONObject("settings")

        lateinit var workHours: String

        for (petIndex in 0 until petSetting.length()) {
            workHours = petSetting.optString("workHours")
        }
        val (digits, alphabets) = workHours.partition { it.isDigit() }
        val filteredString = alphabets.filter { it.isLetter() }

        val startTime = digits[INDEX0].toString()
        val endTime = (digits[INDEX3].toString().plus(digits[INDEX4]))
        return WorkingHours(
            filteredString.first(),
            filteredString.last(),
            startTime.toLong(),
            endTime.toLong()
        )
    }

    /**
     * This fun is used to read list of pets from pet_list.json file from assets folder
     * @param: context : Application context
     */
    fun readPetList(context: Context): ArrayList<Pet> {
        // get JSONObject from JSON file
        val obj = JSONObject(loadJsonData(context, "pets_list.json"))
        // fetch JSONArray named pets
        val petArray = obj.getJSONArray("pets")
        val petList: ArrayList<Pet> = ArrayList()

        for (petIndex in 0 until petArray.length()) {
            val subDetails = petArray.getJSONObject(petIndex)
            val pet = Pet()
            pet.imageUrl = subDetails.optString("image_url")
            pet.title = subDetails.optString("title")
            pet.contentUrl = subDetails.optString("content_url")
            pet.dataAdded = subDetails.optString("date_added")
            //add data to list
            petList.add(petIndex, pet)
        }
        return petList
    }

    /**
     * This fun is used to check working hours condition to block user content
     * @return isDataShouldBeVisible: true/false for content blocking
     */
    fun chekWorkingHoursFeasibility(): Boolean {
        var isDataShouldBeVisible = false
        val date = Date()
        val day = firstLetterOfDayOfTheWeek(date)

        val startTime = PetApplication.getSharedPreference()!!.getLong(CommonUtil.START_TIME, 0)
        val endTime = PetApplication.getSharedPreference()!!.getLong(CommonUtil.END_TIME, 0)

        if (day == "M" || day == "T" || day == "W" || day == "F") {
            val currentHoursFormat = SimpleDateFormat("HH", Locale.getDefault())
            val currentHours: String = currentHoursFormat.format(Date())
            isDataShouldBeVisible = currentHours.toInt() in startTime..endTime
            Log.d(TAG, "chekWorkingHoursFeasibility:$isDataShouldBeVisible + $currentHours")
        }
        return isDataShouldBeVisible
    }

    /**
     * This method is used to get first week letter of calender
     * @param: date : Object of date
     * @return: string: Day of week
     */
    private fun firstLetterOfDayOfTheWeek(date: Date?): String {
        val locale = Locale.getDefault()
        val weekdayNameFormat: DateFormat = SimpleDateFormat("EEE", locale)
        val weekday: String = weekdayNameFormat.format(date!!)
        return weekday[0].toString() + ""
    }

    /**
     * This fun is used to read json files
     * @param: context : Current context
     * @return: string: Json string
     */
    private fun loadJsonData(context: Context, fileName: String): String {
        val json: String?
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}

