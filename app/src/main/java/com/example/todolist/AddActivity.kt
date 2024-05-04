package com.example.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout

class AddActivity : AppCompatActivity() {
  private lateinit var etTitle : EditText
  private lateinit var etDesc : EditText
  private lateinit var btnAdd : MaterialButton
  private lateinit var tabLayout : TabLayout

  private fun initComponents() {
    etTitle = findViewById(R.id.et_title)
    etDesc = findViewById(R.id.et_desc)
    btnAdd = findViewById(R.id.btn_add)
    tabLayout = findViewById(R.id.tab_layout)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add)
    initComponents()
    setupListener()
  }

  private fun setupListener() {
    btnAdd.setOnClickListener {
      if(TextUtils.isEmpty(etTitle.text.toString()) || TextUtils.isEmpty(etDesc.text.toString())) {
        Toast.makeText(this@AddActivity, "Input kosong, harap diisi!", Toast.LENGTH_SHORT).show()
      } else {
        val title = etTitle.text.toString()
        val desc = etDesc.text.toString()
        val intent = Intent(this@AddActivity, MainActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("desc", desc)
        startActivity(intent)
        finish()
      }
    }
    val tabs = tabLayout.getTabAt(1)
    tabs?.select()
    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
      override fun onTabSelected(p0: TabLayout.Tab?) {
        val position = p0!!.position
        if(position == 0) {
          replaceActivity(MainActivity())
          Toast.makeText(this@AddActivity, "Home", Toast.LENGTH_SHORT).show()
        } else if (position == 1) {
          replaceActivity(AddActivity())
          Toast.makeText(this@AddActivity, "Add", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onTabUnselected(p0: TabLayout.Tab?) {}

      override fun onTabReselected(p0: TabLayout.Tab?) {}

    })
  }

  private fun replaceActivity(activity: Activity) {
    val intent = Intent(this@AddActivity, activity::class.java)
    startActivity(intent)
  }

}