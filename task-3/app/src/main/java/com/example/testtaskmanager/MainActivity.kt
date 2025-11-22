package com.example.testtaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testtaskmanager.ui.theme.TestTaskManagerTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isCompleted: Boolean = false
)
class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(title: String) {
        if (title.isNotBlank()) {
            _tasks.add(Task(title = title))
        }
    }

    fun toggleTaskStatus(taskId: String) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun deleteTask(taskId: String) {
        _tasks.removeAll { it.id == taskId }
    }
}

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
    var newTaskText by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.addTask(newTaskText)
                    newTaskText = ""
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { viewModel.toggleTaskStatus(task.id) },
                    onDeleteClick = { viewModel.deleteTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange
            )
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) Color.Gray else Color.Unspecified
            )
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar tarea",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTaskManagerTheme {
        TaskScreen(modifier = Modifier.padding(16.dp))
    }
}