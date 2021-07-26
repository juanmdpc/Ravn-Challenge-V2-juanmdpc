package com.example.apolloandroid.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apolloandroid.databinding.FragmentDetailBinding
import com.example.apolloandroid.view.adapter.PersonDetailAdapter
import com.example.apolloandroid.view.state.ViewState
import com.example.apolloandroid.viewmodel.PeopleViewModel
import com.example.starwars.DetailPersonQuery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModels<PeopleViewModel>()

    private val vehicles = mutableListOf<DetailPersonQuery.Vehicle>()
    private val vehicleAdapter = PersonDetailAdapter(vehicles)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up PeopleRecyclerView
        binding.vehicleRv.layoutManager = LinearLayoutManager(view.context)
        binding.vehicleRv.adapter = vehicleAdapter

        // Call the query of our viewModel
        viewModel.queryPersonDetail(args.id)

        // Call method
        observerLiveData()
    }

    /**
     * This method will handle the logic when:
     * show a loading progress bar,
     * assign the list to VehicleRecyclerView,
     * show error text on the display.
     */
    private fun observerLiveData() {
        viewModel.detail.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.generalInformationLayout.visibility = View.GONE
                    binding.vehicleRv.visibility = View.GONE
                    binding.personDetailFetchProgress.visibility = View.VISIBLE
                    binding.personDetailLoadingText.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.person?.vehicleConnection?.vehicles?.size == 0) {
                        vehicles.addAll(emptyList())
                        vehicleAdapter.notifyDataSetChanged()
                        binding.personDetailFetchProgress.visibility = View.GONE
                        binding.personDetailLoadingText.visibility = View.GONE
                        binding.vehicleRv.visibility = View.GONE
                        binding.generalInformationLayout.visibility = View.VISIBLE
                        binding.personDetailEmptyText.visibility = View.VISIBLE
                        binding.personDetailLoadingText.visibility = View.VISIBLE

                    } else {
                        binding.generalInformationLayout.visibility = View.VISIBLE
                        binding.vehicleRv.visibility = View.VISIBLE
                        binding.personDetailEmptyText.visibility = View.GONE
                    }

                    binding.eyeColorValue.text = response.value?.data?.person?.eyeColor
                    binding.hairColorValue.text = response.value?.data?.person?.hairColor
                    binding.skinColorValue.text = response.value?.data?.person?.skinColor
                    binding.birthYearValue.text = response.value?.data?.person?.birthYear

                    val data = response.value?.data?.person?.vehicleConnection?.vehicles?.filterNotNull()
                    vehicles.addAll(data!!)
                    vehicleAdapter.notifyDataSetChanged()
                    binding.personDetailFetchProgress.visibility = View.GONE
                    binding.personDetailLoadingText.visibility = View.GONE
                }
                is ViewState.Error -> {
                    binding.eyeColorValue.text = response.value?.data?.person?.eyeColor
                    binding.hairColorValue.text = response.value?.data?.person?.hairColor
                    binding.skinColorValue.text = response.value?.data?.person?.skinColor
                    binding.birthYearValue.text = response.value?.data?.person?.birthYear

                    vehicles.addAll(emptyList())
                    vehicleAdapter.notifyDataSetChanged()
                    binding.personDetailFetchProgress.visibility = View.GONE
                    binding.personDetailLoadingText.visibility = View.GONE
                    binding.vehicleRv.visibility = View.GONE
                    binding.personDetailEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }
}
