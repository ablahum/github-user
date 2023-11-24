package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowsBinding
import com.example.githubuser.ui.adapter.ItemAdapter
import com.example.githubuser.ui.viewmodel.DetailViewModel

class FollowsFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private val adapter: ItemAdapter = ItemAdapter()

    private var position: Int? = null
    private var username: String? = null

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            detailViewModel.getUserFollowers(username.toString())
            detailViewModel.listFollowers.observe(viewLifecycleOwner) {
                showRecycleView(it)
            }
        } else {
            detailViewModel.getUserFollowing(username.toString())
            detailViewModel.listFollowing.observe(viewLifecycleOwner) {
                showRecycleView(it)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showRecycleView(item: List<ItemsItem>) {
        binding.rvItems.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvItems.setHasFixedSize(true)
        adapter.submitList(item)
        binding.rvItems.adapter = adapter

        adapter.setOnItemClickCallback(object : ItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                selectedUser(data)
            }
        })
    }

    private fun selectedUser(user: ItemsItem) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user.login)

        startActivity(intent)
    }
}