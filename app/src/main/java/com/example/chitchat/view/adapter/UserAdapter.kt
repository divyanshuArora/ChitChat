package com.example.chitchat.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.databinding.ActivityDashboardBinding
import com.example.chitchat.databinding.userItemBinding
import com.example.chitchat.services.model.UserModel

class UserAdapter(var context: Context,var userList: List<UserModel>): RecyclerView.Adapter<UserAdapter.ItemViewHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val userLayoutBinding =  userItemBinding.inflate(layoutInflater,parent,false)

        return ItemViewHolder(userLayoutBinding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        holder.bind(userList[position],position)

    }



    class ItemViewHolder(var userItemBinding: userItemBinding):RecyclerView.ViewHolder(userItemBinding.root)
    {
        fun bind(user:UserModel,position: Int)
        {
            userItemBinding.userModelBind = user
            userItemBinding.executePendingBindings()
        }

    }

}