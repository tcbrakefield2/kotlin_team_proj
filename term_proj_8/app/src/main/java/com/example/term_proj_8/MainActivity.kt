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

    private val opButtons = mutableListOf<Button>()

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
        // Add processing for all operations (+ - * /) ✓
        // Iterate through boxes to read operations ✓
        // Calculate values as it goes ✓
        // Display Goal ✓
        // Check end value equals goal value ✓
        // Reset button to clear operations -- Added Reset Button, need to make reset and give visual feedback

        val textNum1 = findViewById<TextView>(R.id.num_1)
        val textNum2 = findViewById<TextView>(R.id.num_2)
        val textNum3 = findViewById<TextView>(R.id.num_3)
        val textNum4 = findViewById<TextView>(R.id.num_4)
        val textNum5 = findViewById<TextView>(R.id.goalVal)

        var operations = ArrayList<String>(3)
        val maxPosition = 3
        var currValue = 0
        var position = 0

        opButtons.add(findViewById<Button>(R.id.add_btn))
        opButtons.add(findViewById<Button>(R.id.sub_btn))
        opButtons.add(findViewById<Button>(R.id.div_btn))
        opButtons.add(findViewById<Button>(R.id.mult_btn))

        val textNumBoxes = ArrayList<TextView>()
        textNumBoxes.add(textNum1)
        textNumBoxes.add(textNum2)
        textNumBoxes.add(textNum3)
        textNumBoxes.add(textNum4)
        textNumBoxes.add(textNum5)

        val addButton: Button = findViewById(R.id.add_btn)
        val subButton: Button = findViewById(R.id.sub_btn)
        val multButton: Button = findViewById(R.id.mult_btn)
        val divButton: Button = findViewById(R.id.div_btn)

        val operator1 = findViewById<TextView>(R.id.operator_1)
        val operator2 = findViewById<TextView>(R.id.operator_2)
        val operator3 = findViewById<TextView>(R.id.operator_3)

        val currValueText = findViewById<TextView>(R.id.currVal)

        val currOperators = ArrayList<TextView>()
        currOperators.add(operator1)
        currOperators.add(operator2)
        currOperators.add(operator3)

        val Instructions = findViewById<TextView>(R.id.Instructions)
        val nums = intArrayOf(2, 4, 6, 8, 6)
        val goalValue = nums[4]

        addButton.setOnClickListener {
            setOperation("+", position, currOperators)
            if (currValue == 0)
            {
                currValue = nums[position] + nums[position + 1]
            } else {
                currValue += nums[position + 1]
            }
            setCurrValue(currValue, currValueText)
            position++

            // !!! Evaluates if player wins, checks if current value == goal value
            // and if position is in final position
            if ((currValue == goalValue) && (position == maxPosition)) {
                Instructions.text = "You win!"
            }
        }

        subButton.setOnClickListener {
            setOperation("-", position, currOperators)
            if (currValue == 0)
            {
                currValue = nums[position] - nums[position + 1]
            } else {
                currValue -= nums[position + 1]
            }
            setCurrValue(currValue, currValueText)
            position++

            if ((currValue == goalValue) && (position == maxPosition)) {
                Instructions.text = "You win!"
            }
        }
        multButton.setOnClickListener {
            setOperation("*", position, currOperators)
            if (currValue == 0)
            {
                currValue = nums[position] * nums[position + 1]
            } else {
                currValue *= nums[position + 1]
            }
            setCurrValue(currValue, currValueText)
            position++

            if ((currValue == goalValue) && (position == maxPosition)) {
                Instructions.text = "You win!"
            }
        }
        divButton.setOnClickListener {
            setOperation("/", position, currOperators)
            if (currValue == 0)
            {
                currValue = nums[position] / nums[position + 1]
            } else {
                currValue /= nums[position + 1]
            }
            setCurrValue(currValue, currValueText)
            position++

            if ((currValue == goalValue) && (position == maxPosition)) {
                Instructions.text = "You win!"
            }
        }



        // 2. Attach the click listener lambda
//        /addButton.setOnClickListener { view ->
//            // Your click action code goes here
//            Toast.makeText(this, "Addition Sign Added", Toast.LENGTH_SHORT).show()
//            operator1.text = "+"
//        }

        initializeGameState(nums, textNumBoxes)


    }
}

fun initializeGameState(nums:  IntArray, textNumBoxes: ArrayList<TextView>) {
    textNumBoxes.get(0).text=nums[0].toString()
    textNumBoxes.get(1).text=nums[1].toString()
    textNumBoxes.get(2).text=nums[2].toString()
    textNumBoxes.get(3).text=nums[3].toString()
    textNumBoxes.get(4).text=nums[4].toString()
}

fun setOperation(operation: String, position: Int, operators: ArrayList<TextView>) {
    if (position == 0) {
        operators.get(0).text = operation
    }
    if (position == 1) {
        operators.get(1).text = operation
    }
    if (position == 2) {
        operators.get(2).text = operation
    }
}

fun setCurrValue(currValue: Int, currentText: TextView) {
    currentText.text = currValue.toString()
}