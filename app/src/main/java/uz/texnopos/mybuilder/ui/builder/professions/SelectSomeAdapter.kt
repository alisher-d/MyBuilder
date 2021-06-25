package uz.texnopos.mybuilder.ui.builder.professions


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.databinding.SelectableJobItemCheckboxBinding
import uz.texnopos.mybuilder.models.JobModel
import uz.texnopos.mybuilder.toast

class SelectSomeAdapter : RecyclerView.Adapter<SelectSomeAdapter.ItemViewHolder>() {
    private var count =0
    inner class ItemViewHolder(var binding: SelectableJobItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(job: JobModel, position: Int) {
            val item = binding.jobName
            item.text = job.name
            if (remoteModels.contains(job.name)) item.isChecked=true
            item.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    count++
                    remoteModels.add(models[position].name)
                } else {
                    count--
                    remoteModels.remove(models[position].name)
                }
                if (count > 6) {
                    item.context.toast("Maximum 6")
                    buttonView.isChecked = false
                    count--
                    remoteModels.remove(models[position].name)
                }
                onClick.invoke(job.name)
            }

        }
    }

    var onClick: (model: String,) -> Unit =
        { model: String ->

        }

    fun onItemClickListener(onClick: (model: String) -> Unit) {
        this.onClick = onClick
    }

    var models = mutableListOf<JobModel>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    var remoteModels= arrayListOf<String>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        count = remoteModels.size
        val binding = SelectableJobItemCheckboxBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position],position)
    }

    override fun getItemCount() = models.size
}