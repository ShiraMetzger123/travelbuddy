package com.project.travelbuddy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.project.travelbuddy.R
import com.project.travelbuddy.databinding.FragmentCountryDetailBinding
import com.project.travelbuddy.db.TravelBuddyDatabase
import com.project.travelbuddy.repo.CountryRepository
import com.project.travelbuddy.viewmodel.CountryViewModel
import com.project.travelbuddy.viewmodel.CountryViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ExploreFragment : Fragment(R.layout.fragment_country_detail) {

    private lateinit var binding: FragmentCountryDetailBinding
    private lateinit var viewModel: CountryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCountryDetailBinding.bind(view)
        val countryDao = TravelBuddyDatabase.getDatabase(requireContext()).countryDao()
        val repository = CountryRepository(countryDao)
        val viewModelFactory = CountryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory ).get(CountryViewModel::class.java)

//        val countryName = arguments?.getString("countryName") ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchCountryData("Germany")

            viewModel.country.observe(viewLifecycleOwner) { country ->
                binding.tvCountryName.text = country.name
                binding.tvCountryCapital.text = "Capital: ${country.capital}"
                binding.tvCountryRegion.text = "Region: ${country.region}"
                binding.tvCountryPopulation.text = "Population: ${country.population}"

                Picasso.get().load(country.flag).into(binding.ivCountryFlag)
            }
        }
    }
}
