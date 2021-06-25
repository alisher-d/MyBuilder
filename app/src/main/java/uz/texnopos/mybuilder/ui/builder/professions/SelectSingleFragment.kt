package uz.texnopos.mybuilder.ui.builder.professions

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectOneBinding
import uz.texnopos.mybuilder.models.BuilderModel

class SelectSingleFragment : BaseFragment(R.layout.fragment_profession_select_one) {
    lateinit var binding: FragmentProfessionSelectOneBinding
    private val adapter = SelectSingleAdapter()
    private var selected = getProfession().isNotEmpty()
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfessionSelectOneBinding.bind(view)
        navController = Navigation.findNavController(view)
        val btnSave = binding.btnSave
        val bundle = this.arguments
        val list = bundle?.getStringArrayList("jobs") as ArrayList
        binding.rv.adapter = adapter
        adapter.models = list

        adapter.onItemClickListener {
            selected = true
            list.forEachIndexed { i, s ->
                if (s == it) list[i] = list[0].also { list[0] = s }
            }
        }
        btnSave.onClick {
            if (selected) {
                showProgress()
                FirebaseHelper().updateBuilderJobs(list,
                    {
                        hideProgress()
                        toast(it!!)
                        navController.navigate(R.id.action_professionSelectOneFragment_to_homeMainFragment, bundle)
                    },
                    {
                        toast(it!!)
                    })
            } else toast("Please, select one")
        }
    }

}