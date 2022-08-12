package com.learning.pets.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.learning.pets.PetApplication
import com.learning.pets.bl.CommonUtil
import com.learning.pets.bl.CommonUtil.SPLASH_SCREEN_TIME_OUT
import com.learning.pets.databinding.ActivityLunchBinding
import com.learning.pets.viewmodel.SharedViewModel


/**
 * @author Anita
 * This is the first activity for this application which is full screen
 */
class LunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLunchBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: ConstraintLayout
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var context: Context
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        binding = ActivityLunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        context = this@LunchActivity
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        editor = PetApplication.getSharedPreference()!!.edit()

        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.fullscreenContent
        fullscreenContentControls = binding.fullscreenControl
    }

    override fun onResume() {
        super.onResume()
        //Check for first time app lunched
        if (PetApplication.getSharedPreference()!!.getBoolean(CommonUtil.FIRST_RUN, true)) {
            editor = PetApplication.getSharedPreference()!!.edit()

            sharedViewModel.insertPet(context)
            sharedViewModel.readWorkingHours(context, editor)

            editor.putBoolean(CommonUtil.FIRST_RUN, false)
            editor.commit()
            Handler(Looper.getMainLooper()).postDelayed({
                lunchPetsList()
            }, SPLASH_SCREEN_TIME_OUT)
        } else {
            lunchPetsList()
        }
    }

    /**
     * This fun is used to lunch next screen
     */
    private fun lunchPetsList() {
        val i = Intent(this@LunchActivity, MainActivity::class.java)
        startActivity(i)
        finish()

    }
}