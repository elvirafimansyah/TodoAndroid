package com.example.todolist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.model.ListResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
  private lateinit var rvTodo : RecyclerView
  private lateinit var tabLayout : TabLayout
  private lateinit var todoAdapter : TodoAdapter
  private lateinit var sharedPreferences : SharedPreferences

  private lateinit var title : String
  private lateinit var desc : String

  private var todoItem : ArrayList<ListResponse> = arrayListOf()

  private fun initComponents() {
    rvTodo = findViewById(R.id.rv_todo)
    tabLayout = findViewById(R.id.tab_layout)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    title = intent.getStringExtra("title") ?: ""
    desc = intent.getStringExtra("desc") ?: ""

    sharedPreferences = getSharedPreferences("TodoItems", Context.MODE_PRIVATE)

    initComponents()
    setupList()
    setupListener()
    loadTodoItems()
    inputTodoItem()
    saveTodoItem()
  }

  private fun setupList() {
    todoAdapter = TodoAdapter(arrayListOf()) {position ->
      deleteTodo(position)
      todoAdapter.setData(todoItem)
    }
    rvTodo.adapter = todoAdapter
  }

  private fun deleteTodo(position: Int) {
    todoItem.removeAt(position)
    todoAdapter.notifyItemRemoved(position)
    saveTodoItem()
  }

  private fun saveTodoItem() {
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(todoItem)
    editor.putString("todoItems", json)
    editor.apply()
  }

  private fun loadTodoItems() {
    val gson = Gson()
    val json = sharedPreferences.getString("todoItems", null)
    val type = object : TypeToken<ArrayList<ListResponse>>() {}.type
    todoItem = gson.fromJson(json, type) ?: ArrayList()
    todoAdapter.setData(todoItem)
  }

  private fun inputTodoItem() {
    if(title.isNotEmpty()) {
      val todoResponse = ListResponse(
        title = title,
        description =  desc
      )

      todoItem.add(todoResponse)
      saveTodoItem()
      todoAdapter.setData(todoItem)
    }
  }

  private fun setupListener() {
    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
      override fun onTabSelected(p0: TabLayout.Tab?) {
        val position = p0!!.position
        if(position == 0) {
          replaceActivity(MainActivity())
          Toast.makeText(this@MainActivity, "Home", Toast.LENGTH_SHORT).show()
        } else if(position == 1){
          replaceActivity(AddActivity())
          Toast.makeText(this@MainActivity, "Add", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onTabUnselected(p0: TabLayout.Tab?) {}

      override fun onTabReselected(p0: TabLayout.Tab?) {}

    })
  }

  private fun replaceActivity(activity: Activity) {
    val intent = Intent(this@MainActivity, activity::class.java)
    startActivity(intent)
  }
}