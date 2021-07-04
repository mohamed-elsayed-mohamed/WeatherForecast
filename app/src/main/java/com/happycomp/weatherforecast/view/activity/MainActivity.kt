package com.happycomp.weatherforecast.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.adapters.PagerAdapter
import com.happycomp.weatherforecast.databinding.ActivityMainBinding
import com.happycomp.weatherforecast.view.fragment.*
import com.happycomp.weatherforecast.view.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpdates()
    }

    private fun setUpdates(){
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(FavoriteFragment())
        adapter.addFragment(AlertFragment())
        adapter.addFragment(SettingsFragment())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
        binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_alert_24)
        binding.tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_settings_24)
    }
}