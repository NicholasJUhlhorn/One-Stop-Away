package com.example.onestopaway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.onestopaway.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val route = RouteListFragment()
        val dbman = DatabaseManager(this)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_page_container, route)
            commit()
        }
        binding.menuBar.setOnItemSelectedListener {
            onOptionsItemSelected(it)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.routes -> {
                    val routeFrag = supportFragmentManager.findFragmentById(R.id.main_page_container)
                    if(routeFrag is RouteListFragment) {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, routeFrag)
                            commit()
                        }
                    } else {
                        val newFrag = RouteListFragment()
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, newFrag)
                            commit()
                        }
                    }
                Log.d("BUTTONLOG", "clicked route tab")
                true
            }
            R.id.favorites -> {
                supportFragmentManager.beginTransaction().apply {
                    val favoritesFragment = FavoritesFragment()
                    replace(R.id.main_page_container, favoritesFragment)
                    commit()
                }
                Log.d("BUTTONLOG", "clicked favorites tab")
                true
            }
            R.id.stops -> {
                supportFragmentManager.beginTransaction().apply {
                    val routeFrag = RouteListFragment()
                    replace(R.id.main_page_container, routeFrag)
                    commit()
                }
                Log.d("BUTTONLOG", "clicked stop tab")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}