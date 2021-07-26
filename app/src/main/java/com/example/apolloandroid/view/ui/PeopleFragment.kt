package com.example.apolloandroid.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apolloandroid.databinding.FragmentPeopleBinding
import com.example.apolloandroid.view.adapter.PeopleAdapter
import com.example.apolloandroid.view.state.ViewState
import com.example.apolloandroid.viewmodel.PeopleViewModel
import com.example.starwars.PeopleListQuery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private lateinit var binding: FragmentPeopleBinding
    private val viewModel by viewModels<PeopleViewModel>()

    private val people = mutableListOf<PeopleListQuery.Edge>()
    private val peopleAdapter = PeopleAdapter(people)

    var after: String? = null

    companion object {
        const val first = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Return the root field of binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up PeopleRecyclerView
        binding.peopleRv.layoutManager = LinearLayoutManager(view.context)
        binding.peopleRv.addItemDecoration(DividerItemDecoration(
            binding.root.context
            , DividerItemDecoration.VERTICAL))
        binding.peopleRv.adapter = peopleAdapter

        // Call the query method of our viewModel
        viewModel.queryPeopleList()

        // Call method
        observeLiveData()

        // When an item was clicked, we navigate to DetailFragment and pass two args (id, title)
        peopleAdapter.onItemClicked = { people ->
            people.let {
                if (!people.node?.id.isNullOrBlank()) {
                    findNavController().navigate(
                        PeopleFragmentDirections.actionPeopleFragmentToDetailFragment(
                            id = people.node?.id!!,
                            title = people.node.name!!
                        )
                    )
                }
            }
        }
    }

    /**
     * This method will handle the logic when:
     * show a loading progress bar,
     * assign the list to PeopleRecyclerView,
     * show error text on the display.
     */
    private fun observeLiveData() {
        viewModel.peopleList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.peopleRv.visibility = View.GONE
                    binding.peopleFetchProgress.visibility = View.VISIBLE
                    binding.peopleLoadingText.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.allPeople?.edges?.size == 0) {
                        people.addAll(emptyList())
                        peopleAdapter.notifyDataSetChanged()
                        binding.peopleFetchProgress.visibility = View.GONE
                        binding.peopleLoadingText.visibility = View.GONE
                        binding.peopleRv.visibility = View.GONE
                        binding.peopleEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.peopleRv.visibility = View.VISIBLE
                        binding.peopleEmptyText.visibility = View.GONE
                    }
                    val data = response.value?.data?.allPeople?.edges?.filterNotNull()
                    people.clear()
                    people.addAll(data!!)
                    peopleAdapter.notifyDataSetChanged()
                    binding.peopleFetchProgress.visibility = View.GONE
                    binding.peopleLoadingText.visibility = View.GONE
                }
                is ViewState.Error -> {
                    people.addAll(emptyList())
                    peopleAdapter.notifyDataSetChanged()
                    binding.peopleFetchProgress.visibility = View.GONE
                    binding.peopleLoadingText.visibility = View.GONE
                    binding.peopleRv.visibility = View.GONE
                    binding.peopleEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }
}