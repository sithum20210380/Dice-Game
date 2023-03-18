package com.example.cw_w1867464

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var aboutButton : Button
    private lateinit var newGameButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aboutButton = findViewById(R.id.about_button)
        newGameButton = findViewById(R.id.new_game)

        aboutButton.setOnClickListener {
            val aboutMessage = "Student ID and Name: 20210380 - Sithum Raveesha\n\n" +
                    "I confirm that I understand what plagiarism is and have read and understood the " +
                    "section on Assessment Offences in the Essential Information for Students. " +
                    "The work that I have submitted is entirely my own. Any work from other authors is " +
                    "duly referenced and acknowledged."
            val builder = AlertDialog.Builder(this)
            builder.setTitle("About")
                .setMessage(aboutMessage)
                .setPositiveButton("OK",null)
            val dialog = builder.create()
            dialog.show()
        }

        newGameButton.setOnClickListener {
            val intent = Intent(this,ThirdActivity::class.java)
            startActivity(intent)
        }
}
}

