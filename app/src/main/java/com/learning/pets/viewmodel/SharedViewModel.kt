package com.learning.pets.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.learning.pets.bl.CommonUtil
import com.learning.pets.bl.PetRepository
import com.learning.pets.model.Pet
import kotlinx.coroutines.launch

/**
 * @author Anita
 * A shared view model which used for activity as well as for fragment
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private var petLiveData: LiveData<List<Pet?>?>? = null

    /**
     * This fun used to get list pets
     * @param context: required context
     * @return list: list of pets
     */
    fun getPetList(context: Context): LiveData<List<Pet?>?>? {
        petLiveData = PetRepository.getPetDetails(context)
        return petLiveData
    }

    /**
     * This fun used insert pet data
     * @param context: Required context
     */
    fun insertPet(context: Context) {
        PetRepository.insertData(context)
    }

    /**
     * This fun is used to read working hours details
     * @param context: Required context
     * @param editor : Editor to store value
     */
    fun readWorkingHours(context: Context, editor: SharedPreferences.Editor) {
        viewModelScope.launch {
            val workingHours = PetRepository.readWorkingHours(context)
            editor.putLong(CommonUtil.START_TIME, workingHours!!.startTime)
            editor.putLong(CommonUtil.END_TIME, workingHours.endTime)
            editor.commit()
        }
    }

    /**
     * This fun is used load images of url
     */
    companion object {
        @JvmStatic
        @BindingAdapter("pet_image_url")
        fun loadImage(view: ImageView, profileImage: String) {
            Glide.with(view.context)
                .load(profileImage)
                .into(view)
        }
    }
}