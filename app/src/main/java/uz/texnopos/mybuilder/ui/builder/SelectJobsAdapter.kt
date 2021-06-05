package uz.texnopos.mybuilder.ui.builder


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.JobsModel
import uz.texnopos.mybuilder.databinding.SelectableJobItemCheckboxBinding
import uz.texnopos.mybuilder.toast

class SelectJobsAdapter : RecyclerView.Adapter<SelectJobsAdapter.ItemViewHolder>() {
    private var count =0
    inner class ItemViewHolder(var binding: SelectableJobItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populatModel(job: JobsModel) {
            val item = binding.jobName
            item.text = job.jobName
            item.isChecked=job.checkable
            item.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    job.checkable = true
                    count++
                } else {
                    job.checkable = false
                    count--
                }
                if (count > 6) {
                    "Maximum 6".toast(item.context)
                    buttonView.isChecked = false
                    count--
                    job.checkable = false
                }
                onClick.invoke(job, isChecked)
            }

        }
    }

    var onClick: (model: JobsModel, isChecked: Boolean) -> Unit =
        { model: JobsModel, isChecked: Boolean ->

        }

    fun onItemClickListener(onClick: (model: JobsModel, isChecked: Boolean) -> Unit) {
        this.onClick = onClick
    }

    var models = mutableListOf<JobsModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        count = 0
        val binding = SelectableJobItemCheckboxBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populatModel(models[position])
    }

    override fun getItemCount() = models.size
}