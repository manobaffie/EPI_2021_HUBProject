package com.example.terminal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.json.JSONObject

class MenuFragment : Fragment() {

    private lateinit var networkclient: NetworkClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        networkclient = ViewModelProvider(requireActivity()).get(NetworkClient::class.java)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edittextip : EditText = view.findViewById(R.id.editTextIP)
        val edittextport : EditText = view.findViewById(R.id.editTextPort)
        val button : Button = view.findViewById(R.id.buttonLink)

        button.setOnClickListener {

            networkclient.setURL(edittextip.text.toString(), edittextport.text.toString())

            networkclient.requestToApi("log", object : GetLastCallback {
                override fun lastCallback(response: JSONObject?) {
                    Log.e("->", response.toString())
                    if (response?.getString("connect") == "accepted") {
                        view.findNavController().navigate(R.id.action_menuFragment_to_homeFragment)
                    }
                }
            })
        }
    }
}