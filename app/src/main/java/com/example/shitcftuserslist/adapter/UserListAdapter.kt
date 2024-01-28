package com.example.shitcftuserslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.shitcftuserslist.room.User
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shitcftuserslist.databinding.UsersListUserBinding
import com.squareup.picasso.Picasso

class UserListAdapter(private val onItemClicked: (User) -> Unit):
    ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            UsersListUserBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class UserViewHolder(private var binding: UsersListUserBinding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(user: User) = with(binding) {
                    Picasso.get().load(user.image).into(userImage)
                    userName.text = user.name
                    userAddress.text = user.street
                    userPhone.text = user.cell
                }
    }

    companion object {
        private var DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}