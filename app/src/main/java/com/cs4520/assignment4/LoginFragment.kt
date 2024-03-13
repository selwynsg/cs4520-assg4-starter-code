package com.cs4520.assignment4
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cs4520.assignment4.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = view.findViewById<Button>(R.id.login_button)
        login.apply {
            setOnClickListener {
                val username = view.findViewById<EditText>(R.id.userInput)
                val password = view.findViewById<EditText>(R.id.password)
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

}







