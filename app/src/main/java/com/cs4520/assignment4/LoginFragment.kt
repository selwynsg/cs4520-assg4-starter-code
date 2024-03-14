package com.cs4520.assignment4
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.loginButton.setOnClickListener {
                val username = binding.userInput
                val password = binding.password
                Log.d("LoginFragment", "Username entered: $username and Pass $password")
                if (username.text.toString() == "admin" && password.text.toString() == "admin") {
                    findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                } else {
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                }
                username.text.clear()
                password.text.clear()
            }
        }
    }









