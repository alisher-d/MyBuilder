package uz.texnopos.mybuilder.ui.builder


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.JobsModel
import uz.texnopos.mybuilder.databinding.SelectableJobItemRadiobuttonBinding

class SingleSelectJobsAdapter : RecyclerView.Adapter<SingleSelectJobsAdapter.ItemViewHolder>() {
private var lastChecked:RadioButton?=null
    private var lastCheckedPosition=0
    inner class ItemViewHolder(var binding: SelectableJobItemRadiobuttonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populatModel(job: JobsModel, position: Int) {
        val item=binding.jobName
            item.text=job.jobName
            item.isChecked=!job.checkable
            item.tag=position
            if (position==0&&models[0].checkable&&item.isChecked){
                lastChecked=item
                lastCheckedPosition=0
            }
            item.setOnClickListener{
            val rb=it as RadioButton
                val clickedPosition=rb.tag
                if (rb.isChecked){
                    if (lastChecked!=null){
                        lastChecked!!.isChecked=false
                        models[lastCheckedPosition].checkable=false
                    }
                    lastChecked=rb
                    lastCheckedPosition=clickedPosition as Int
                }
                else lastChecked=null
                models[clickedPosition as Int].checkable=rb.isChecked
            }
        }
    }

    var onClick: (model: JobsModel, isChecked: Boolean) -> Unit =
        { model: JobsModel, isChecked: Boolean ->

        }

    fun onItemClickListener(onClick: (model: JobsModel, isChecked: Boolean) -> Unit) {
        this.onClick = onClick
    }

    var models = listOf<JobsModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectableJobItemRadiobuttonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populatModel(models[position],position)
    }

    override fun getItemCount() = models.size
}