package uz.texnopos.mybuilder.ui.builder

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import uz.texnopos.mybuilder.JobsModel
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.ActivityCreateRezyumeBinding
import uz.texnopos.mybuilder.databinding.DialogJobsBinding
import uz.texnopos.mybuilder.toast

class BuilderActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateRezyumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRezyumeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}