package com.aplikasi.bitasteapp.presentation.detailmenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aplikasi.bitasteapp.R
import com.aplikasi.bitasteapp.databinding.FragmentDetailMenuBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentDetailMenu : Fragment() {

    lateinit var binding: FragmentDetailMenuBinding
    //lateinit var detailViewModel: DetailMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewModel = ViewModelProvider(this).get(DetailMenuViewModel::class.java)
        binding.cvPlusButton.setOnClickListener {
            detailViewModel.incrementCount()
        }
        binding.cvMinusButton.setOnClickListener {
            detailViewModel.decrementCount()
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE
        val bundle = arguments
        val menu = bundle!!.getString("namaMenu")
        val price = bundle!!.getString("hargaMenu")
        val rating = bundle!!.getDouble("rating")
        val image = bundle!!.getInt("gambar")
        val location = bundle!!.getString("loc")
        val description = bundle!!.getString("description")
        binding.tvPriceDetail.text = price
        binding.tvFoodNameDetail.text = menu
        binding.tvRatingDetail.text = rating.toString()
        binding.dataLocation.text = location.toString()
        binding.tvDescription.text = description
        Glide.with(requireContext()).load(image).into(binding.ivRectangleImg)
        Glide.with(requireContext()).load(image).into(binding.banner)
        binding.ivBack.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_fragmentDetailMenu2_to_fragmentHome)
        }
        binding.cvLocation.setOnClickListener {
            val data = binding.dataLocation.text.toString()
            val uri = Uri.parse("https://www.google.com/maps/search/$data")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(mapIntent)
        }
//        countingFood()
        detailViewModel.counter.observe(viewLifecycleOwner){
            if(price != null){
                val numericPart = price.replace("[^0-9]".toRegex(), "")
                binding.btnAddCart.text = "Add to Cart - Rp. ${it*numericPart.toInt()}"
                binding.tvNumber.text = it.toString()
            }
        }
    }

    private fun countingFood() {
        var foodTotal = 0
        binding.cvPlusButton.setOnClickListener {
            foodTotal += 1
            binding.tvNumber.text = foodTotal.toString()
        }
        binding.cvMinusButton.setOnClickListener {
            if (foodTotal > 0) {
                foodTotal -= 1
                binding.tvNumber.text = foodTotal.toString()
            }
        }
    }
}
