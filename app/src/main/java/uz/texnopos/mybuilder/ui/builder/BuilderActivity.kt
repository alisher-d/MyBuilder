package uz.texnopos.mybuilder.ui.builder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.texnopos.mybuilder.AppBaseActivity
import uz.texnopos.mybuilder.databinding.ActivityCreateRezyumeBinding

class BuilderActivity : AppBaseActivity() {
    lateinit var binding: ActivityCreateRezyumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRezyumeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}