package uz.texnopos.mybuilder.ui.builder.editFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectOneBinding
import uz.texnopos.mybuilder.toast
import uz.texnopos.mybuilder.models.BuilderModel
import uz.texnopos.mybuilder.ui.builder.SelectSingleAdapter

class SelectSingleFragment : Fragment(R.layout.fragment_profession_select_one) {
lateinit var binding: FragmentProfessionSelectOneBinding
private val adapter=SelectSingleAdapter()
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProfessionSelectOneBinding.bind(view)
        navController=Navigation.findNavController(view)
        val btnSave=binding.btnSave
        val bundle=this.arguments
        val list=bundle?.getStringArrayList("jobs") as ArrayList
        binding.rv.adapter=adapter
        adapter.models=list

        val builderModel= BuilderModel()
        builderModel.selectableModel=list
        adapter.onItemClickListener {
            builderModel.profession=it
        }
        btnSave.setOnClickListener {
        FirebaseHelper().updateBuilderJobs(
            {
                navController.navigate(R.id.action_professionSelectOneFragment_to_homeMainFragment)
                toast(it!!)
            },
            {
                toast(it!!)
            },
           builderModel
        )
        }
    }

}