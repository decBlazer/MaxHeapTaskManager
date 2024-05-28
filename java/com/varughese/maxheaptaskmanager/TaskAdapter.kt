package com.varughese.maxheaptaskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maxheaptaskmanager.R
import com.varughese.maxheaptaskmanager.Task
import com.varughese.maxheaptaskmanager.TaskQueue

class TaskAdapter(private val taskList: Array<Task?>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        task?.let {
            holder.titleTextView.text = it.title
            holder.descriptionTextView.text = it.description
            holder.timeTextView.text = it.time.toString()
        }
    }

    override fun getItemCount() = taskList.size
}