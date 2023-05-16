package com.example.playit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediamanagementapplication.databinding.ActivityMainBinding
import com.example.playit.Adapter.ViewPagerAdapter
import com.example.playit.Fragment.FavoriteFragment
import com.example.playit.Fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val adapter= ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(),"Home")
        adapter.addFragment(FavoriteFragment(),"Book Mark")
        binding!!.viewpager.adapter=adapter
        binding!!.tablayout.setupWithViewPager(binding!!.viewpager)
    }
}