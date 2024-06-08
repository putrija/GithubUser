package com.dicoding.githubusersecond.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusersecond.databinding.FragmentListFollowBinding


class ListFollowFragment : Fragment() {

    private var _binding: FragmentListFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter
    private var username: String = ""
    private var position: Int = 0
    private lateinit var viewModel: ListFollowViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
            Log.d("ListFollowFragment", "Username: $username, Position: $position")
        }

        viewModel = ViewModelProvider(this)[ListFollowViewModel::class.java]

        userAdapter = UserAdapter {}
        binding.rvListFollow.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.listFollow.observe(viewLifecycleOwner) { listFollow ->
            userAdapter.setData(listFollow)
        }

        if (position == 1) {
            viewModel.getFollowers(username)
        } else {
            viewModel.getFollowing(username)
        }
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}