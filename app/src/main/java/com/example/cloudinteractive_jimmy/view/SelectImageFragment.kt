package com.example.cloudinteractive_jimmy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.cloudinteractive_jimmy.imageCache.DoubleCache
import com.example.cloudinteractive_jimmy.imageCache.ImageLoader
import com.example.cloudinteractive_jimmy.imageCache.MD5Encoder
import com.example.cloudinteractive_jimmy.databinding.SelectImageFragmentBinding
import com.example.cloudinteractive_jimmy.viewModel.SharedViewModel
import kotlinx.coroutines.launch


class SelectImageFragment : Fragment() {

    private var _binding: SelectImageFragmentBinding? = null
    private val binding get() = _binding!!


    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SelectImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //觀察sharedViewModel取得前頁的photo並顯示
        sharedViewModel.selectedItem.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { selectPhoto ->

                binding.detailIdTextView.text = selectPhoto.id

                binding.detailTitleTextView.text = selectPhoto.title

                binding.detailImageView.tag = MD5Encoder.encode(selectPhoto.url)
                viewLifecycleOwner.lifecycleScope.launch {
                    ImageLoader.displayImage(
                        selectPhoto.url,
                        binding.detailImageView,
                        DoubleCache()
                    )
                }
            })

        //點擊返回前頁
        binding.root.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}