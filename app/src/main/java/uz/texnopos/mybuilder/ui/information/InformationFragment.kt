package uz.texnopos.mybuilder.ui.information

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentInformationBinding

class InformationFragment : Fragment(R.layout.fragment_information) {
    lateinit var binding:FragmentInformationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
binding= FragmentInformationBinding.bind(view)

    }
}