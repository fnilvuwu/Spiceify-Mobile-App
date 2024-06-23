package com.example.herbs

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.herbs.databinding.ActivitySearchSpiceBinding

class SearchSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchSpiceBinding
    private lateinit var searchSpiceAdapter: SearchSpiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        val spices = DataSource.spices
        searchSpiceAdapter.spices = spices

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchSpiceAdapter.filter.filter(newText)

                if (newText!!.isBlank()) {
                    binding.searchView.clearFocus()
                }
                return false
            }
        })
    }

    private fun setupRecyclerView() = binding.rvSearchSpice.apply {
        searchSpiceAdapter = SearchSpiceAdapter()
        searchSpiceAdapter.setClickListener(object : SearchSpiceAdapter.ClickListener {
            override fun onItemClicked(spice: Spice) {
                val intent = Intent(this@SearchSpiceActivity, DetailSpiceActivity::class.java)
                intent.putExtra("name", spice.name)
                intent.putExtra("type", spice.type)
                startActivity(intent)
            }
        })
        adapter = searchSpiceAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}