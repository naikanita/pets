package com.learning.pets.view

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learning.pets.R
import com.learning.pets.bl.CommonUtil.URL
import com.learning.pets.bl.ReadJsonFiles
import com.learning.pets.databinding.EmptyLayoutBinding
import com.learning.pets.databinding.FragmentSecondBinding

/**
 * @author Anita
 * This class is used to show pet details
 */
class SecondFragment : Fragment() {

    private var fragmentSecondBinding: FragmentSecondBinding? = null
    private lateinit var emptyBinding: EmptyLayoutBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = fragmentSecondBinding!!
    private var emptyText: String = ""
        set(value) {
            field = value
            emptyBinding.emptyMessage.text = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSecondBinding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    override fun onResume() {
        super.onResume()
        if (ReadJsonFiles.chekWorkingHoursFeasibility()) {
            showLoadingView()
        } else {
            showEmptyView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSecondBinding = null
    }

    /**
     * This method is used to set data to fragment
     */
    private fun setupData() {
        val urlLink = requireArguments().getString(URL)

        emptyBinding = binding.customEmptyView

        binding.textviewSecond.text = getString(R.string.press_button)
        binding.buttonSecond.text = urlLink
        binding.buttonSecond.paintFlags =
            binding.buttonSecond.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.buttonSecond.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlLink))
            startActivity(browserIntent)
        }
    }

    /**
     * This method is used to show empty view after working hours
     */
    private fun showEmptyView() {
        emptyText = getString(R.string.non_working)
        emptyBinding.root.visibility = View.VISIBLE
    }

    /**
     * This method is used to show list of pets
     */
    private fun showLoadingView() {
        emptyBinding.root.visibility = View.GONE
    }
}