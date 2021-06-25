package uz.texnopos.mybuilder.ui.builder.professions

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectSomeBinding


class SelectSomeFragment : BaseFragment(R.layout.fragment_profession_select_some) {
    lateinit var binding: FragmentProfessionSelectSomeBinding
    private val adapter = SelectSomeAdapter()
    private val dbHelper=FirebaseHelper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfessionSelectSomeBinding.bind(view)
        val navController=Navigation.findNavController(view)
        val rvJobList = binding.rvJobsList
        rvJobList.adapter = adapter
        binding.next.onClick {
            val bundle=Bundle()
            bundle.putStringArrayList("jobs",adapter.remoteModels)
            navController.navigate(R.id.action_professionFragment_to_professionSelectOneFragment,bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
    }
    private fun setData() {
       dbHelper.getJobList(
            {
                adapter.models = it
                adapter.remoteModels=getSelectableJobs()
            },
            {
            toast(it!!)
            }
        )
    }
}