package com.example.androidexamdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexamdemo.UserDataModel
import com.example.androidexamdemo.databinding.UserItemBinding

class UserAdapter():RecyclerView.Adapter<UserAdapter.UserViewModel>(),Filterable{
var list= arrayListOf<UserDataModel>()
    var copyArrayList= ArrayList<UserDataModel>()
    var seach_query:String?=null

    init {
        copyArrayList=list
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val view=UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewModel(view)
    }

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun addUser(list:List<UserDataModel>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

   inner class UserViewModel(val userItemBinding: UserItemBinding):RecyclerView.ViewHolder(userItemBinding.root) {
        fun bind(i:UserDataModel){

            userItemBinding.countryNameTxt.text=i.countryName.toString()
            userItemBinding.countryCodeTxt.text=i.countryCode.toString()
            userItemBinding.wonderNameTxt.text=i.wonderName.toString()
            userItemBinding.wonderImg.setImageDrawable(i.image)

        }

    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterArrayList= arrayListOf<UserDataModel>()
            seach_query=constraint.toString().toUpperCase().trim()
                if (seach_query.isNullOrEmpty()||seach_query.isNullOrBlank()){
                    filterArrayList.addAll(copyArrayList)
                }
                else{
                    for (item in list){
                        if (item.countryName.toUpperCase().contains(seach_query!!)){
                            filterArrayList.add(item)
                        }
                    }

                }
                val filterResults = FilterResults()
                filterResults.values = filterArrayList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                list=results!!.values as ArrayList<UserDataModel>
                notifyDataSetChanged()
            }

        }
    }

}