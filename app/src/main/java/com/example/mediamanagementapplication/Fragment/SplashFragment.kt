package com.example.playit.Fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mediamanagementapplication.R
import com.example.mediamanagementapplication.databinding.FragmentSplashBinding



class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentSplashBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        requestPermissions()

//        Handler(Looper.myLooper()!!).postDelayed({
//            findNavController().navigate(R.id.homeFragment)
//        }, 3000)
        return binding?.root
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
    //check permission
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PERMISSION_REQUEST_CODE == 100) {
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
               // findNavController().navigate(R.id.homeFragment)
//            Handler(Looper.myLooper()!!).postDelayed({
//                findNavController().navigate(R.id.homeFragment)
//            }, 3000)
            }
        }
    }


}