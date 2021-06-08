package uz.texnopos.mybuilder.ui.builder.editFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectOneBinding
import uz.texnopos.mybuilder.ui.builder.SingleSelectJobsAdapter

class ProfessionSelectOneFragment : Fragment(R.layout.fragment_profession_select_one) {
lateinit var binding: FragmentProfessionSelectOneBinding
private val adapter=SingleSelectJobsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProfessionSelectOneBinding.bind(view)
        val bundle=this.arguments
        val list=bundle?.getStringArrayList("jobs") as ArrayList
        binding.rv.adapter=adapter
        adapter.models=list
    }

}