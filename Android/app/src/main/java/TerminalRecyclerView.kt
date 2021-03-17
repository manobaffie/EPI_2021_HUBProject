package com.example.terminal
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class TerminalRecyclerView(private val list: JSONArray) :
    RecyclerView.Adapter<TerminalRecyclerView.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewShell: TextView = view.findViewById(R.id.textViewShell)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_terminal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list.getJSONObject(position)
        Handler(Looper.getMainLooper()).post {
            viewHolder.textViewShell.text = item.getString("line")
        }
    }
    override fun getItemCount() = list.length()
}
