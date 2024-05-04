package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.ListResponse
import com.example.todolist.R

class TodoAdapter(
  val todos: ArrayList<ListResponse>,
  val onDeleteListener: (Int) -> Unit
): RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {
  class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    val tvDesc = view.findViewById<TextView>(R.id.tv_desc)
    val btnDelete = view.findViewById<TextView>(R.id.btn_delete)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
    return MyViewHolder(view)
  }

  override fun getItemCount(): Int = todos.size

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val results = todos[position]
    holder.tvTitle.text = results.title
    holder.tvDesc.text = results.description
    holder.btnDelete.setOnClickListener {
      onDeleteListener.invoke(holder.adapterPosition)
    }

  }

  public fun setData(result: List<ListResponse>) {
    todos.clear()
    todos.addAll(result)
    notifyDataSetChanged()
  }

}