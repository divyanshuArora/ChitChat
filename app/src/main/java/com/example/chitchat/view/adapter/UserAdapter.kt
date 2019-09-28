package com.example.chitchat.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.databinding.UserItemBinding
import com.example.chitchat.services.model.UserModel


class UserAdapter(var context: Context, var userList: List<UserModel>): RecyclerView.Adapter<UserAdapter.ItemViewHolder>()
{

    var list:ArrayList<UserModel> = userList as ArrayList<UserModel>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
    {
        val userLayoutBinding:UserItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.users_layout_item,parent,false)
        return ItemViewHolder(userLayoutBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        holder.bind(list[position],position)

    }



    class ItemViewHolder(var userItemBinding: UserItemBinding):RecyclerView.ViewHolder(userItemBinding.root)
    {
        fun bind(user:UserModel,position: Int)
        {
            userItemBinding.userModelBind = user
            userItemBinding.executePendingBindings()
        }

    }

}