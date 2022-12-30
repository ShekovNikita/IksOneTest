package com.sheniv.iksone.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sheniv.iksone.adapter.AdapterFavorites
import com.sheniv.iksone.adapter.DeleteFromFavorite
import com.sheniv.iksone.databinding.FragmentFavoritesBinding
import com.sheniv.iksone.helpers.beGone
import com.sheniv.iksone.helpers.beVisible


class FavoritesFragment : Fragment(), DeleteFromFavorite {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        initRecycler()
        touchRecycler()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun touchRecycler() {
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                binding.btnDeleteFromFavorite.beVisible()
                binding.recyclerPlayers.adapter =
                    AdapterFavorites(
                        favoritesViewModel.getFavorites(),
                        this@FavoritesFragment,
                        true
                    )
            }
        }
        binding.recyclerPlayers.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                timer.start()
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                timer.cancel()
            }
            return@setOnTouchListener true
        }
    }

    private fun initRecycler() {
        binding.recyclerPlayers.adapter =
            AdapterFavorites(
                favoritesViewModel.getFavorites(),
                this,
                false
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun delete(users: List<Int>) {
        binding.btnDeleteFromFavorite.setOnClickListener {

            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Удалить имя")
                .setMessage("Вы уверены что хотите удалить выбранное имя из избранных?")
                .setPositiveButton("Да") { dialog, _ ->
                    favoritesViewModel.deleteFromFavorites(users)
                    initRecycler()
                    binding.btnDeleteFromFavorite.beGone()
                    dialog.dismiss()
                }
                .setNegativeButton("Нет", null)
                .show()
        }
    }
}