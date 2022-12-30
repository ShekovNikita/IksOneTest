package com.sheniv.iksone.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sheniv.iksone.databinding.FragmentMainBinding
import com.sheniv.iksone.helpers.*
import com.sheniv.iksone.room.UserRoom


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        enterName()
        showAge()

        return binding.root
    }

    private fun addToFavorite(userRoom: UserRoom) {
        userRoom.name.let { mainViewModel.addToFavorite(it) }
    }

    private fun share(userRoom: UserRoom) {
        val user = "Меня зовут ${userRoom.name + " и мне " + userRoom.age + " лет"}"
        val site = "https://api.agify.io/"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "$user\nСсылка на API:\n$site")
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Поделиться"))
    }

    private fun showAge() {
        mainViewModel.age.observe(viewLifecycleOwner){
            if (it != null){
                with(binding){
                    buttonLayout.beVisible()
                    textForEmptySearch.beGone()
                    age.beVisible()
                    age.text = it.age.toString()
                }

                val userRoom = Convertor().fromPersonToUser(it)

                var have = false
                for (i in mainViewModel.getAllUsers()){
                    if (userRoom.name == i.name) have = true
                }
                if (!have) mainViewModel.insertUser(userRoom)

                with(binding){
                    btnShare.setOnClickListener { share(userRoom) }
                    btnAddToFavorite.setOnClickListener { addToFavorite(userRoom) }
                }
            }
        }
    }

    private fun enterName() {
        val pattern = "[a-zA-Z]+".toRegex()
        binding.etName.setOnEditorActionListener { _, actionId, event ->
            val name = binding.etName.text.toString().trim()
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER))
                || (actionId == EditorInfo.IME_ACTION_DONE)) {
                if (!name.matches(pattern) || name.isEmpty()) {
                    showToast("Введите имя только латинскими буквами")
                } else {
                    mainViewModel.getAge(name)
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}