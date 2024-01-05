package com.example.e_commerceapp.shopping.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.shopping.presentation.adapter.HomeViewPagerAdapter
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.AccessoryFragment
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.ChairFragment
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.CupboardFragment
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.FurnitureFragment
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.MainCategory
import com.example.e_commerceapp.shopping.presentation.fragment.categories_fragments.TableFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategory(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()

        )

        val viewpager2Adapter  = HomeViewPagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewPager2.adapter = viewpager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2){tab, position ->
            when(position){
                0 -> tab.text = "Main"
                1 ->tab.text = "Chair"
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessories"
                5 -> tab.text = "Furniture"
            }

        }.attach()
    }
}
