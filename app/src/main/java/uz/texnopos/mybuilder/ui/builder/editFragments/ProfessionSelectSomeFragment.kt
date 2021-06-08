package uz.texnopos.mybuilder.ui.builder.editFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.ui.builder.JobsModel
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentProfessionSelectSomeBinding
import uz.texnopos.mybuilder.ui.builder.SelectJobsAdapter


class ProfessionSelectSomeFragment : Fragment(R.layout.fragment_profession_select_some) {
    lateinit var binding: FragmentProfessionSelectSomeBinding
    private val adapter = SelectJobsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfessionSelectSomeBinding.bind(view)
        val navController=Navigation.findNavController(view)
        val jobList = binding.jobsList
        jobList.adapter = adapter
        setData()
        binding.next.setOnClickListener {
            val bundle=Bundle()
            bundle.putStringArrayList("jobs",adapter.remoteModels)
            navController.navigate(R.id.action_professionFragment_to_professionSelectOneFragment,bundle)

        }
    }

    private fun setData() {
        val localJobs = mutableListOf(
            "Carpenter", "Electrician", "Plumber", "Painter",
            "Drywall Installer", "Glazier", "Tile Contractor", "Brickmason"
        )
        val remoteJobs= arrayListOf("Carpenter", "Electrician", "Painter",
            "Drywall Installer", "Glazier", "Brickmason")
        val models = mutableListOf<String>()
        repeat(8) { i ->
            val model = localJobs[i]
            models.add(model)
        }
        adapter.models = models
        adapter.remoteModels=remoteJobs
    }
}