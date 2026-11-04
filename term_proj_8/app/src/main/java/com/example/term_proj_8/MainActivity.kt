package com.example.term_proj_8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO:
        // Add processing for all operations (+ - * /)
        // Iterate through boxes to read operations
        // Calculate values as it goes
        // Display Goal
        // Check Values as operations are places
        // Check end value equals goal value
        // Reset button to clear operations

        val textNum1 = findViewById<TextView>(R.id.num_1)
        val textNum2 = findViewById<TextView>(R.id.num_2)
        val textNum3 = findViewById<TextView>(R.id.num_3)
        val textNum4 = findViewById<TextView>(R.id.num_4)
        val textNum5 = findViewById<TextView>(R.id.goalVal)
        val textNumBoxes = ArrayList<TextView>()
        textNumBoxes.add(textNum1)
        textNumBoxes.add(textNum2)
        textNumBoxes.add(textNum3)
        textNumBoxes.add(textNum4)
        textNumBoxes.add(textNum5)

        val addButton: Button = findViewById(R.id.add_btn)
        val operator_1 = findViewById<TextView>(R.id.operator_1)
        val nums = intArrayOf(2, 4, 6, 8, 6)


        // 2. Attach the click listener lambda
        addButton.setOnClickListener { view ->
            // Your click action code goes here
            Toast.makeText(this, "Addition Sign Added", Toast.LENGTH_SHORT).show()
            operator_1.text = "+"
        }

        initializeGameState(nums, textNumBoxes)


    }
}

fun initializeGameState(nums:  IntArray, textNumBoxes: ArrayList<TextView>) {
    textNumBoxes.get(0).text=nums[0].toString();
    textNumBoxes.get(1).text=nums[1].toString();
    textNumBoxes.get(2).text=nums[2].toString();
    textNumBoxes.get(3).text=nums[3].toString();
    textNumBoxes.get(4).text=nums[4].toString();
}