package com.example.instagramapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.instagramapp.navigations.AlarmFragment
import com.example.instagramapp.navigations.DetailViewFragment
import com.example.instagramapp.navigations.GridFragment
import com.example.instagramapp.navigations.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home -> {
                var detailViewFragment = DetailViewFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, detailViewFragment).commit()
                return true
            }
            R.id.action_search -> {
                var gridFragment = GridFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, gridFragment).commit()
                return true
            }
            R.id.action_Add_photo -> {

                return true
            }
            R.id.action_favorite_Alarm -> {
                var alarmFragment = AlarmFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, alarmFragment).commit()
                return true
            }
            R.id.action_account -> {
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, userFragment).commit()
                return true
            }
        }
        return false
    }









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }


}