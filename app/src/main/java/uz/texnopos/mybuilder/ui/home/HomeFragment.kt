package uz.texnopos.mybuilder.ui.home

import android.os.Bundle
import android.view.View
import uz.texnopos.mybuilder.BaseFragment
import uz.texnopos.mybuilder.MainActivity
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentHomeBinding
import uz.texnopos.mybuilder.models.JobModel
import uz.texnopos.mybuilder.toast


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    lateinit var bind: FragmentHomeBinding
    val adapter = JobListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        bind = FragmentHomeBinding.bind(view)
        bind.rvBuilders.adapter = adapter
        setData()
    }
    private fun setData(){
        FirebaseHelper().getJobList(
            {
                adapter.models=it
                hideProgress()
            },
            {
                hideProgress()
                toast(it!!)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).navView.visibility=View.VISIBLE
    }
}