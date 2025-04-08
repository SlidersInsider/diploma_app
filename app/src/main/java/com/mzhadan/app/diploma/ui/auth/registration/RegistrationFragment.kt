package com.mzhadan.app.diploma.ui.auth.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mzhadan.app.diploma.databinding.FragmentRegistrationBinding
import com.mzhadan.app.diploma.ui.auth.AuthViewModel
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    registerButton.isEnabled = usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        with(binding) {
            usernameEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)

            registerButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                authViewModel.register(username, password, 3,
                    onSuccess = {
                        ScreenNavigator.reopenLoginScreen()
                        Toast.makeText(requireContext(), "Вход успешен!", Toast.LENGTH_SHORT).show()
                    },
                    onError = { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
}