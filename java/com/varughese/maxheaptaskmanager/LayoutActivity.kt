package com.varughese.maxheaptaskmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maxheaptaskmanager.databinding.LayoutActivityBinding
import com.varughese.maxheaptaskmanager.adapter.TaskAdapter

class LayoutActivity : ComponentActivity() {
    private lateinit var binding: LayoutActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tasksList = intent.getSerializableExtra("tasksList") as Array<Task?>
        val taskAdapter = TaskAdapter(tasksList)
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = taskAdapter

        binding.createTaskButton.setOnClickListener {
            // Navigate back to ActivityMain
            val intent = Intent(this@LayoutActivity, ActivityMain::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}