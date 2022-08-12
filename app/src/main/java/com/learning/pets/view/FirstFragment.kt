package com.learning.pets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.learning.pets.PetApplication
import com.learning.pets.R
import com.learning.pets.bl.CommonUtil.URL
import com.learning.pets.bl.ReadJsonFiles
import com.learning.pets.databinding.EmptyLayoutBinding
import com.learning.pets.databinding.FragmentFirstBinding
import com.learning.pets.databinding.LoadingLayoutBinding
import com.learning.pets.viewmodel.SharedViewModel

/**
 * @author Anita
 * This is first fragment in which Pet List will be shown
 */
class FirstFragment : Fragment(), PetAdapter.ItemClickListener {

    private var fragmentFirstBinding: FragmentFirstBinding? = null
    private val binding get() = fragmentFirstBinding!!
    private lateinit var viewModel: SharedViewModel
    private lateinit var emptyBinding: EmptyLayoutBinding
    private lateinit var loadingBinding: LoadingLayoutBinding
    private var emptyText: String = ""
        set(value) {
            field = value
            emptyBinding.emptyMessage.text = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFirstBinding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emptyBinding = binding.customEmptyView
        loadingBinding = binding.customOverlayView

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        viewModel.getPetList(PetApplication.applicationContext())!!
            .observe(viewLifecycleOwner) { petList ->
                val adapter = PetAdapter(this, petList)
                fragmentFirstBinding?.recyclerViewPets?.adapter = adapter
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFirstBinding = null
    }

    override fun onItemClick(url: String, view: View) {
        val bundle = Bundle()
        bundle.putString(URL, url)
        Navigation.findNavController(view)
            .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        if (ReadJsonFiles.chekWorkingHoursFeasibility()) {
            showLoadingView()
        } else {
            showEmptyView()
        }
    }

    /**
     * This method is used to show empty view after working hours
     */
    private fun showEmptyView() {
        emptyText = getString(R.string.non_working)
        loadingBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.VISIBLE
        fragmentFirstBinding?.recyclerViewPets?.visibility = View.GONE
    }

    /**
     * This method is used to show list of pets
     */
    private fun showLoadingView() {
        emptyBinding.root.visibility = View.GONE
        loadingBinding.root.visibility = View.VISIBLE
        fragmentFirstBinding?.recyclerViewPets?.visibility = View.VISIBLE
    }
}
