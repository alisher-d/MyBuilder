package uz.texnopos.mybuilder.ui.profileFeedbacks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentBuilderFeedbacksBinding

class FragementBuilderFeedback:Fragment(R.layout.fragment_builder_feedbacks) {
    lateinit var binding: FragmentBuilderFeedbacksBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBuilderFeedbacksBinding.bind(view)
    }
}