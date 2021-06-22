package uz.texnopos.mybuilder.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilder.databinding.ItemAddressFilterBinding

class AddressFilterAdapter:RecyclerView.Adapter<AddressFilterAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(var bind: ItemAddressFilterBinding):RecyclerView.ViewHolder(bind.root){
    fun populateModel(address:String){
        bind.address.text=address
    }
    }
    var models= arrayListOf<String>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind=ItemAddressFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount()=models.size
}