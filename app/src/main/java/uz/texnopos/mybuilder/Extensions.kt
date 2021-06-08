package uz.texnopos.mybuilder

import android.content.Context
import android.widget.Toast
import uz.texnopos.mybuilder.ui.builder.*

fun String?.toast(context: Context?){
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}
//package uz.texnopos.mybuilder.ui.builder
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.isVisible
//import uz.texnopos.mybuilder.ui.builder.JobsModel
//import uz.texnopos.mybuilder.R
//import uz.texnopos.mybuilder.data.FirebaseHelper
//import uz.texnopos.mybuilder.databinding.ActivityCreateRezyumeBinding
//import uz.texnopos.mybuilder.databinding.DialogJobsBinding
//import uz.texnopos.mybuilder.toast
//
//class BuilderActivity : AppCompatActivity(), BuilderView {
//    lateinit var binding: ActivityCreateRezyumeBinding
//    private val jobsAdapter = SelectJobsAdapter()
//    private val singleJobAdapter = SingleSelectJobsAdapter()
//    private val firebaseHelper = FirebaseHelper()
//    private val presenter = BuilderPresenter(this)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCreateRezyumeBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        binding.selectSomeJobs.setOnClickListener {
////            jobsAdapter.models = setData()
//            val builder = AlertDialog.Builder(this, R.style.MultiChoiceAlertDialog)
//            val binding1 = DialogJobsBinding.inflate(LayoutInflater.from(this), null, false)
//            binding1.jobsList.adapter = jobsAdapter
//            builder.setView(binding1.root)
//            builder.create()
//            presenter.getBuilderData()
//            builder.setPositiveButton("OK") { d, i: Int ->
//                binding.chosenRv.adapter = singleJobAdapter
//                singleJobAdapter.models = jobsAdapter.models.filter {
//                    it.checkable
//                }
//                firebaseHelper.setBuilderData(
//                    BuilderModel(selectableModel = jobsAdapter.models),
//                    {
//                    it.toast(this)
//                    },
//                    {
//                        it.toast(this)
//                    }
//                )
//                d.dismiss()
//
//            }
//            builder.show()
//        }
//    }
//
//
//    private fun setData(): MutableList<JobsModel> {
//        val jobs = mutableListOf(
//            "Carpenter", "Electrician", "Plumber", "Painter",
//            "Drywall Installer", "Glazier", "Tile Contractor", "Brickmason",
//        )
//        val models = mutableListOf<JobsModel>()
//        repeat(8) { i ->
//            val model = JobsModel(jobs[i], false)
//            models.add(model)
//        }
//        return models
//    }
//
//    override fun showError(msg: String?) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun setData(builder: BuilderModel, msg: String) {
//        jobsAdapter.models=builder.selectableModel
//        msg.toast(this)
//    }
//
//
//    override fun loading(isLoading: Boolean) {
////        binding.loading.isVisible = isLoading
//    }
//}