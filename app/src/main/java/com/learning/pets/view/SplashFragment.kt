package com.learning.pets.view

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.learning.pets.PetApplication
import com.learning.pets.R
import com.learning.pets.bl.CommonUtil
import com.learning.pets.databinding.FragmentSplashBinding
import com.learning.pets.viewmodel.SharedViewModel
import com.learning.pets.viewmodel.ViewModelFactory

class SplashFragment : Fragment() {

    private var fragmentSplashBinding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = fragmentSplashBinding!!

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        checkFirstLunch()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSplashBinding = null
    }

    /**
     * This method is used to setup data
     */
    private fun setupData() {
        sharedViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(PetApplication.instance!!)
            )[SharedViewModel::class.java]

        editor = PetApplication.getSharedPreference()!!.edit()
    }

    /**
     * This fun is used to check whether application is lunching for the first time or second time.
     * OnFirstLunch: Read json file data and add into database
     * OnSecondLunch: Read data directly from database or shared preferences
     */
    private fun checkFirstLunch() {
        val activity = activity as MainActivity
        val navController =
            Navigation.findNavController(activity, R.id.nav_host_fragment_content_main)

        //Check for first time app lunched
        if (PetApplication.getSharedPreference()!!.getBoolean(CommonUtil.FIRST_RUN, true)) {
            editor = PetApplication.getSharedPreference()!!.edit()

            //inserting data
            sharedViewModel.insertPet(PetApplication.applicationContext())
            sharedViewModel.readWorkingHours(PetApplication.applicationContext(), editor)

            editor.putBoolean(CommonUtil.FIRST_RUN, false)
            editor.commit()

            //show first page
            Handler(Looper.getMainLooper()).postDelayed({
                navController.setGraph(R.navigation.nav_graph)
            }, CommonUtil.SPLASH_SCREEN_TIME_OUT)

        } else {
            navController.setGraph(R.navigation.nav_graph_second)
        }
    }
}