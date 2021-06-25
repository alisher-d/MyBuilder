package uz.texnopos.mybuilder.ui.builder.professions


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.SelectableJobItemRadiobuttonBinding
import uz.texnopos.mybuilder.getProfession
import uz.texnopos.mybuilder.getSelectableJobs
import uz.texnopos.mybuilder.onClick

class SelectSingleAdapter : RecyclerView.Adapter<SelectSingleAdapter.ItemViewHolder>() {
    private var lastChecked: TextView? = null

    inner class ItemViewHolder(var binding: SelectableJobItemRadiobuttonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populatModel(job: String, position: Int) {
            val item = binding.jobName
            item.text = job
            if (position==0) lastChecked=item
            if (job== getProfession()){
                item.setBackgroundResource(R.color.title)
                lastChecked = item
            }


            item.onClick {
                onClick.invoke(job)
                lastChecked!!.setBackgroundResource(0)
                item.setBackgroundResource(R.color.title)
                lastChecked = this
            }
        }
    }

    var onClick: (job:String) -> Unit ={

    }
       fun onItemClickListener(onClick: (job:String) -> Unit){
           this.onClick=onClick
       }

    var models = mutableListOf<String>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
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