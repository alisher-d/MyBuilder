package uz.texnopos.mybuilder.ui.builder


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.databinding.SelectableJobItemCheckboxBinding
import uz.texnopos.mybuilder.toast

class SelectJobsAdapter : RecyclerView.Adapter<SelectJobsAdapter.ItemViewHolder>() {
    private var count =0
    inner class ItemViewHolder(var binding: SelectableJobItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populatModel(job: String,position: Int) {
            val item = binding.jobName
            item.text = job
//            item.isChecked=job.checkable
            if (remoteModels.contains(job)) item.isChecked=true
            item.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    count++
                    remoteModels.add(models[position])
                } else {
                    count--
                    remoteModels.remove(models[position])
                }
                if (count > 6) {
                    item.context.toast("Maximum 6")
                    buttonView.isChecked = false
                    count--
                    remoteModels.remove(models[position])
                }
                onClick.invoke(job)
            }

        }
    }

    var onClick: (model: String,) -> Unit =
        { model: String ->

        }

    fun onItemClickListener(onClick: (model: String) -> Unit) {
        this.onClick = onClick
    }

    var models = mutableListOf<String>()
    var remoteModels= arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        count = remoteModels.size
        val binding = SelectableJobItemCheckboxBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populatModel(models[position],position)
    }

    override fun getItemCount() = models.size
}