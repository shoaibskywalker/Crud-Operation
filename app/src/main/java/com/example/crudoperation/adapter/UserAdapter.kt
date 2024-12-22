package com.example.crudoperation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudoperation.databinding.ItemLayoutBinding
import com.example.crudoperation.model.User

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textname.text = user.name
            binding.textrole.text = user.role
            binding.textnumber.text = user.mobileNumber
            binding.textamount.text = "₹ ${user.subscriptionFee}"
            binding.textloanamoumt.text = "₹ ${user.loanAmount}"
            binding.textdepositamount.text = "₹ ${user.depositAmount}"
            // Bind other views similarly...
        }
    }
}
