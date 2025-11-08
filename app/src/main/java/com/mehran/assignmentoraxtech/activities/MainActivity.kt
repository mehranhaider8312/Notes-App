package com.mehran.assignmentoraxtech.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.mehran.assignmentoraxtech.R
import com.mehran.assignmentoraxtech.adapters.HomeViewPagerAdapter
import com.mehran.assignmentoraxtech.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }
    fun init(){

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.homeViewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        binding.homeViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {  }

            override fun onPageSelected(position: Int) {
                if (position==0)
                    binding.homeBottomNavigation.selectedItemId = R.id.addNewNote
                else
                    binding.homeBottomNavigation.selectedItemId = R.id.viewNotes
            }

            override fun onPageScrollStateChanged(state: Int) { }
        })

        binding.homeBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addNewNote -> {
                    binding.homeViewPager.currentItem = 0
                    true
                }
                R.id.viewNotes -> {
                    binding.homeViewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }
}