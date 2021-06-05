package uz.texnopos.mybuilder.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding:FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)
    }
}