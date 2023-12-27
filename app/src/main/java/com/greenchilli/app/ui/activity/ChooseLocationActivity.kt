package com.greenchilli.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.greenchilli.app.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {
    val binding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val location = arrayOf("Delhi","Agra","Lucknow","kanpur","Etawah")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,location)
        val spinner = binding.spinner
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}