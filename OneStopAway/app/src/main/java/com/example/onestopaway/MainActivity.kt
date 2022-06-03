package com.example.onestopaway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.onestopaway.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), StopListener {

    private var _binding : ActivityMainBinding? = null
    private val viewModel : TransitItemsViewModel by viewModels { TransitItemsViewmodelFactory((application as OneBusAway).repository)}
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create ViewModel
        //add the initial route list fragment

        binding.menuBar.setOnItemSelectedListener {
            onOptionsItemSelected(it)
        }
        if(savedInstanceState == null) {
            viewModel.populateDatabase()
            val route = RouteListFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_page_container, route)
                commit()
            }

        } else {
            //get the current menu item and recreate that fragment on reinstation
           val itemid = savedInstanceState.getInt("MENU_ITEM")
            when(itemid) {
                R.id.routes -> {
                        // Populate nearest routes
                        val newFrag = RouteListFragment()
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, newFrag)
                            commit()
                        }
                    }
                R.id.favorites -> {
                        val newFrag = FavoritesFragment()
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, newFrag)
                            commit()
                        }
                }
                R.id.stops -> {
                        val newFrag = StopsListFragment.newInstance(this)
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.main_page_container, newFrag)
                            commit()
                        }

                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("MENU_ITEM", binding.menuBar.selectedItemId)
        super.onSaveInstanceState(outState)
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
                        // Populate nearest routes
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
                    // Populate ViewModel with favorites

                    val newFrag = FavoritesFragment.newInstance(this)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, newFrag)
                        commit()
                    }
                }
                Log.d("BUTTONLOG", "clicked favorites tab")
                true
            }
            R.id.stops -> {
                val data : List<Stop> = viewModel.stops
                val frag = supportFragmentManager.findFragmentById(R.id.main_page_container)

                if(frag is StopsListFragment) {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_page_container, frag)
                        commit()
                    }
                } else {
                    // Populate nearest stops
                    val newFrag = StopsListFragment.newInstance(this)
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

    override fun onStopClicked(stop: Stop) {
        Intent(this, StopDetailActivity::class.java).apply {
            this.putExtra("stop_lat", stop.latitude)
            this.putExtra("stop_long", stop.longitude)
            this.putExtra("stop_name", stop.name)
            this.putExtra("stop_id", stop.id)
            startActivity(this)
        }
    }

}