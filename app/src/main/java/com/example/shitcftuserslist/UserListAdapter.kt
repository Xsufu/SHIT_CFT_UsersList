package com.example.shitcftuserslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.shitcftuserslist.room.User
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shitcftuserslist.databinding.FragmentUsersListBinding
import com.example.shitcftuserslist.databinding.UsersListUserBinding

class UserListAdapter(private val onItemClicked: (User) -> Unit):
    ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserViewHolder {
        return UserViewHolder(
            UsersListUserBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class UserViewHolder(private var binding: UsersListUserBinding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(user: User) = with(binding) {
                    userImage.setImageURI(user.image)
                    userName.text = user.name
                    userPhoneAddress.text = user.street
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