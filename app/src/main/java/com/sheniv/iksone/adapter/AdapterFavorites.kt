package com.sheniv.iksone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.iksone.R
import com.sheniv.iksone.databinding.ItemNameBinding
import com.sheniv.iksone.helpers.beInvisible
import com.sheniv.iksone.helpers.beVisible
import com.sheniv.iksone.room.UserRoom

class AdapterFavorites(
    private val users: List<UserRoom>,
    private val deleteFromFavorite: DeleteFromFavorite,
    private val checkBox: Boolean
) : RecyclerView.Adapter<AdapterFavorites.UserViewHolder>() {

    val usersForDelete = arrayListOf<Int>()

    inner class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemNameBinding.bind(item)
        fun bind(userRoom: UserRoom) = with(binding) {
            playerName.text = userRoom.name
            if (!checkBox) checkboxSetting.beInvisible()
            else checkboxSetting.beVisible()
            checkboxSetting.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) userRoom.id?.let { usersForDelete.add(it) }
                else usersForDelete.remove(userRoom.id)
                deleteFromFavorite.delete(usersForDelete)
                Log.e("deleteFromFavorite", "${usersForDelete}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_name, parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}