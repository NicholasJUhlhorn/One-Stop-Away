package com.example.onestopaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.onestopaway.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val db : DatabaseManager = DatabaseManager.getDatabase(this)
    private val dataRepo : DataRepository = DataRepository(db)
    private val viewModel : TransitItemsViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //add the initial route list fragment
        val route = RouteListFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_page_container, route)
            commit()
        }
        binding.menuBar.setOnItemSelectedListener {
            onOptionsItemSelected(it)
        }

        if (savedInstanceState == null) {
            db.clearDBAndRecreate()

            GlobalScope.launch {
                dataRepo.populateDatabase()
            }
        }


    }

    // clicklistener for the bottom menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.routes -> {
                    val frag = supportFragmentManager.findFragmentById(R.id.main_page_container)
                    if(frag is RouteListFragment) {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, frag)
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
                val frag = supportFragmentManager.findFragmentById(R.id.main_page_container)
                if(frag is FavoritesFragment) {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, frag)
                        commit()
                    }
                } else {
                    val newFrag = FavoritesFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, newFrag)
                        commit()
                    }
                }
                Log.d("BUTTONLOG", "clicked favorites tab")
                true
            }
            R.id.stops -> {
                val frag = supportFragmentManager.findFragmentById(R.id.main_page_container)
                if(frag is StopsListFragment) {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, frag)
                        commit()
                    }
                } else {
                    val newFrag = StopsListFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, newFrag)
                        commit()
                    }
                }
                Log.d("BUTTONLOG", "clicked stop tab")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}