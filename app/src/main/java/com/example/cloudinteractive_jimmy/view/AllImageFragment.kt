package com.example.cloudinteractive_jimmy.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloudinteractive_jimmy.sealed.PhotoStatus
import com.example.cloudinteractive_jimmy.R
import com.example.cloudinteractive_jimmy.recyclerView.PhotoListAdapter
import com.example.cloudinteractive_jimmy.databinding.AllImageFragmentBinding
import com.example.cloudinteractive_jimmy.model.DatabasePhoto
import com.example.cloudinteractive_jimmy.viewModel.AllImageViewModel
import com.example.cloudinteractive_jimmy.viewModel.SharedViewModel


class AllImageFragment : Fragment() {

    private val allImageViewModel by viewModels<AllImageViewModel>()

    private var _binding: AllImageFragmentBinding? = null

    private val binding get() = _binding!!

    private var photoListAdapter: PhotoListAdapter? = null

    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //確認要從網路載Json資料還是從資料庫拿
        allImageViewModel.checkDatabasePhotoOrRefresh()

        photoListAdapter =
            PhotoListAdapter(
                onItemClick = { photo ->
                    sharedViewModel.openItem(photo)


                },
                allImageViewModel = allImageViewModel
            )

        binding.recyclerview.layoutManager =
            GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)

        binding.recyclerview.adapter = photoListAdapter

        //網路連線失敗後重撈按鈕
        binding.retryImageView.setOnClickListener {
            binding.loadingTextView.visibility = View.VISIBLE
            binding.retryConstraintLayout.visibility = View.GONE
            binding.recyclerview.visibility = View.GONE
            allImageViewModel.checkDatabasePhotoOrRefresh()
        }


        //觀察資料更新recyclerview
        allImageViewModel.dataBasePhoto.observe(viewLifecycleOwner, Observer { dataBasePhotoList ->

            photoListAdapter!!.submitList(dataBasePhotoList)
        })

        //觀察status更新UI
        allImageViewModel.photoStatusLiveData.observe(viewLifecycleOwner, Observer { photoStatus ->
            updateStatus(photoStatus)
        })

        sharedViewModel.checkNavigate.observe(viewLifecycleOwner, Observer {
            Navigation.findNavController(view)
                .navigate(R.id.action_allImageFragment_to_selectImageFragment)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





    private fun updateStatus(photoStatus: PhotoStatus) {
        binding.loadingTextView.visibility = View.GONE

        when (photoStatus) {

            PhotoStatus.GetDataSuccess -> {
                binding.recyclerview.visibility = View.VISIBLE
            }

            PhotoStatus.LinkFailure -> {
                binding.retryConstraintLayout.visibility = View.VISIBLE

            }
        }

    }
}