package uz.texnopos.mybuilder.ui.builder


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.SelectableJobItemRadiobuttonBinding

class SingleSelectJobsAdapter : RecyclerView.Adapter<SingleSelectJobsAdapter.ItemViewHolder>() {
    private var lastChecked: TextView? = null

    inner class ItemViewHolder(var binding: SelectableJobItemRadiobuttonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populatModel(job: String, position: Int) {
            val item = binding.jobName
            item.text = job
            if (position == 0) lastChecked = item
            item.setOnClickListener {
                lastChecked!!.setBackgroundResource(0)
                item.setBackgroundResource(R.color.title)
                lastChecked = it as TextView
            }
        }
    }

    var onClick: (model: JobsModel, isChecked: Boolean) -> Unit =
        { model: JobsModel, isChecked: Boolean ->

        }

    fun onItemClickListener(onClick: (model: JobsModel, isChecked: Boolean) -> Unit) {
        this.onClick = onClick
    }

    var models = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectableJobItemRadiobuttonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populatModel(models[position], position)
    }

    override fun getItemCount() = models.size
}