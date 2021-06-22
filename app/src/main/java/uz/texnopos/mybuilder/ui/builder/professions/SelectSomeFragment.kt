package uz.texnopos.mybuilder.ui.builder.editFragments

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.BaseFragment
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectSomeBinding
import uz.texnopos.mybuilder.onClick
import uz.texnopos.mybuilder.toast
import uz.texnopos.mybuilder.ui.builder.SelectSomeAdapter


class SelectSomeFragment : BaseFragment(R.layout.fragment_profession_select_some) {
    lateinit var binding: FragmentProfessionSelectSomeBinding
    private val adapter = SelectSomeAdapter()
    private val dbHelper=FirebaseHelper()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfessionSelectSomeBinding.bind(view)
        val navController=Navigation.findNavController(view)
        val jobList = binding.jobsList
        jobList.adapter = adapter
        setData()
        binding.next.onClick {
            val bundle=Bundle()
            bundle.putStringArrayList("jobs",adapter.remoteModels)
            navController.navigate(R.id.action_professionFragment_to_professionSelectOneFragment,bundle)

        }
    }

    private fun setData() {
       dbHelper.getJobList(
            {
                adapter.models = it
            },
            {
            toast(it!!)
            }
        )
    }
}