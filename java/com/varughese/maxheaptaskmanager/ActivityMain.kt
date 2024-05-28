package com.varughese.maxheaptaskmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.maxheaptaskmanager.databinding.ActivityMainBinding
import com.varughese.maxheaptaskmanager.adapter.TaskAdapter
import com.varughese.maxheaptaskmanager.TaskQueue


class ActivityMain : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tasksList = TaskQueue(10, CompareCriteria.TIME)
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(tasksList.getHeapData())

        binding.createTaskButton.setOnClickListener {
            addTask()
        }

        binding.viewHeapLayout.setOnClickListener {
            val intent = Intent(this@ActivityMain, LayoutActivity::class.java)
            intent.putExtra("tasksList", tasksList.getHeapData())
            startActivity(intent)
        }
    }

    private fun addTask() {
        if (binding.titleEditText.text.isBlank() || binding.descriptionEditText.text.isBlank() || binding.descriptionEditText.text.isBlank()) {
            return
        }
        title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val time = binding.timeEditText.text.toString().toInt()

        val task = Task(title as String, description, time, PriorityLevel.OPTIONAL, false)
        tasksList.enqueue(task)
        taskAdapter.notifyDataSetChanged()


        binding.titleEditText.setText("")
        binding.descriptionEditText.setText("")
        binding.timeEditText.setText("")
    }
}