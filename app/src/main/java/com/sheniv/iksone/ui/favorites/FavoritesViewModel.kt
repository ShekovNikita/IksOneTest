package com.sheniv.iksone.ui.favorites

import androidx.lifecycle.ViewModel
import com.sheniv.iksone.helpers.db

class FavoritesViewModel : ViewModel() {

    fun getFavorites() = db.getFavorites()

    fun deleteFromFavorites(userIds: List<Int>) = db.deleteFromFavorites(userIds)
}