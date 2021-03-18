package com.example.cloudinteractive_jimmy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.cloudinteractive_jimmy.R
import com.example.cloudinteractive_jimmy.databinding.CheckRequestFragmentBinding

class CheckRequestFragment : Fragment() {



    private var _binding: CheckRequestFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckRequestFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextPageButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_checkRequestFragment_to_allImageFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}