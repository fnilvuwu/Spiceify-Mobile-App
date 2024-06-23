package com.example.herbs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.herbs.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var spiceAdapter: SpiceAdapter
    private lateinit var searchSpiceAdapter: SearchSpiceAdapter
    private lateinit var mylist: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchRecyclerView()

        val spices = DataSource.spices
        spiceAdapter.spices = spices
        searchSpiceAdapter.spices = spices
        mylist = ArrayList(spices.map { it.name })

        binding.detectButton.setOnClickListener {
            val intent = Intent(activity, ClassifyActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnExplore.setOnClickListener {
            val intent = Intent(activity, SearchSpiceActivity::class.java)
            startActivity(intent)
        }

        binding.btnRecipe.setOnClickListener {
            val intent = Intent(activity, SearchRecipeActivity::class.java)
            startActivity(intent)
        }

        binding.btnFindRecipe.setOnClickListener {
            val intent = Intent(activity, SearchRecipeActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Filter search results as the user types
                search(newText)
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            // Handle search view close
            if (binding.rvSearchSpice.visibility == View.VISIBLE) {
                binding.mainConstraintLayout.visibility = View.VISIBLE
                binding.rvSearchSpice.visibility = View.GONE
            }
            false
        }

        binding.btnAddRecipe.setOnClickListener {
            val intent = Intent(activity, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun search(query: String) {
        if (query.isNotBlank()) {
            binding.mainConstraintLayout.visibility = View.GONE
            binding.rvSearchSpice.visibility = View.VISIBLE
            searchSpiceAdapter.filter.filter(query)
        } else {
            binding.mainConstraintLayout.visibility = View.VISIBLE
            binding.rvSearchSpice.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() = binding.rvSpice.apply {
        spiceAdapter = SpiceAdapter()
        spiceAdapter.setClickListener(object : SpiceAdapter.ClickListener {
            override fun onItemClicked(spice: Spice) {
                val intent = Intent(activity, DetailSpiceActivity::class.java).apply {
                    putExtra("name", spice.name)
                    putExtra("type", spice.type)
                }
                startActivity(intent)
            }
        })
        adapter = spiceAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupSearchRecyclerView() = binding.rvSearchSpice.apply {
        searchSpiceAdapter = SearchSpiceAdapter()
        searchSpiceAdapter.setClickListener(object : SearchSpiceAdapter.ClickListener {
            override fun onItemClicked(spice: Spice) {
                val intent = Intent(activity, DetailSpiceActivity::class.java).apply {
                    putExtra("name", spice.name)
                    putExtra("type", spice.type)
                }
                startActivity(intent)
            }
        })
        adapter = searchSpiceAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
