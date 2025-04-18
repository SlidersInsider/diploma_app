package com.mzhadan.app.diploma.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mzhadan.app.diploma.MainViewModel
import com.mzhadan.app.diploma.databinding.FragmentLoginBinding
import com.mzhadan.app.diploma.ui.auth.AuthViewModel
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.updateBottomNavigationBarVisibility(View.GONE)
        setupUI()
    }

    private fun setupUI() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    loginButton.isEnabled = loginEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        with(binding) {
            loginEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)

            binding.loginButton.setOnClickListener {
                val username = loginEditText.text.toString()
                val password = passwordEditText.text.toString()

                authViewModel.login(username, password,
                    onSuccess = {
                        ScreenNavigator.openHomeScreen()
                        mainViewModel.updateBottomNavigationBarVisibility(View.VISIBLE)
                        Toast.makeText(requireContext(), "Вход успешен!", Toast.LENGTH_SHORT).show()
                    },
                    onError = { message ->
//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    })
            }

            binding.registerText.setOnClickListener {
                ScreenNavigator.openRegistrationScreen()
            }
        }
    }
}