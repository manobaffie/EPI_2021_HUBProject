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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var networkclient: NetworkClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        networkclient = ViewModelProvider(requireActivity()).get(NetworkClient::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edittextcmd : EditText = view.findViewById(R.id.editTextCommande)
        val button : Button = view.findViewById(R.id.buttonExec)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        button.setOnClickListener {
            networkclient.postToApi("shell", edittextcmd.text.toString(), object : GetLastCallback {
                override fun lastCallback(response: JSONObject?) {
                    Log.e("->", response.toString())

                    try {
                        val productAdapter = TerminalRecyclerView(response?.getJSONArray("result")!!)
                        recyclerView.layoutManager = GridLayoutManager(context, 1)
                        recyclerView.adapter = productAdapter
                    } catch (e : Throwable) {
                        Log.e("->", response.toString())
                    }
                }
            })
        }
    }
}