package com.example.term_proj_8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val opButtons = mutableListOf<Button>()
    private val textNumBoxes = mutableListOf<TextView>()
    private val currOperators = mutableListOf<TextView>()

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
        // Reset button to clear operations ✓

        val textNum1 = findViewById<TextView>(R.id.num_1)
        val textNum2 = findViewById<TextView>(R.id.num_2)
        val textNum3 = findViewById<TextView>(R.id.num_3)
        val textNum4 = findViewById<TextView>(R.id.num_4)
        val textNum5 = findViewById<TextView>(R.id.goalVal)

        val maxPosition = 3
        var currValue:Double = 0.0
        var position = 0

        opButtons.add(findViewById<Button>(R.id.add_btn))
        opButtons.add(findViewById<Button>(R.id.sub_btn))
        opButtons.add(findViewById<Button>(R.id.div_btn))
        opButtons.add(findViewById<Button>(R.id.mult_btn))
        //opButtons.add(findViewById<Button>(R.id.reset_btn))

        textNumBoxes.add(textNum1)
        textNumBoxes.add(textNum2)
        textNumBoxes.add(textNum3)
        textNumBoxes.add(textNum4)
        textNumBoxes.add(textNum5)

        val addButton: Button = findViewById(R.id.add_btn)
        val subButton: Button = findViewById(R.id.sub_btn)
        val multButton: Button = findViewById(R.id.mult_btn)
        val divButton: Button = findViewById(R.id.div_btn)
        val resetButton: Button = findViewById(R.id.reset_btn)

        val operator1 = findViewById<TextView>(R.id.operator_1)
        val operator2 = findViewById<TextView>(R.id.operator_2)
        val operator3 = findViewById<TextView>(R.id.operator_3)

        val currValueText = findViewById<TextView>(R.id.currVal)

        currOperators.add(operator1)
        currOperators.add(operator2)
        currOperators.add(operator3)

        val Instructions = findViewById<TextView>(R.id.Instructions)
        val nums = intArrayOf(2, 4, 6, 8, 6)
        val goalValue = nums[4].toDouble()

        val konfettiView = findViewById<KonfettiView>(R.id.konfettiView)



        addButton.setOnClickListener {
            setOperation("+", position, currOperators)
            currValue = calcCurrValueAdd(position, currValue, nums)
            setCurrValue(currValue, currValueText)
            position++

            // !!! Evaluates if player wins, checks if current value == goal value
            // and if position is in final position
            if(position == maxPosition) {
                checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView)
            }
        }

        subButton.setOnClickListener {
            setOperation("-", position, currOperators)
            currValue = calcCurrValueSub(position, currValue, nums)
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView)
            }
        }
        multButton.setOnClickListener {
            setOperation("*", position, currOperators)
            currValue = calcCurrValueMult(position, currValue, nums)
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView)
            }
        }
        divButton.setOnClickListener {
            setOperation("/", position, currOperators)
            currValue = calcCurrValueDiv(position, currValue, nums)
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView)
            }
        }

        resetButton.setOnClickListener {
            operator1.text = "___"
            operator2.text = "___"
            operator3.text = "___"

            position = 0
            currValue = 0.0
            setCurrValue(currValue, currValueText)

            if (Instructions.text != "Math evaluates clockwise (non-PEMDAS)") {
                Instructions.text = "Math evaluates clockwise (non-PEMDAS)"
            }

            enableButtons(opButtons)

        }
        initializeGameState(nums, textNumBoxes)
    }


}

fun initializeGameState(nums:  IntArray, textNumBoxes: List<TextView>) {
    textNumBoxes[0].text=nums[0].toString()
    textNumBoxes[1].text=nums[1].toString()
    textNumBoxes[2].text=nums[2].toString()
    textNumBoxes[3].text=nums[3].toString()
    textNumBoxes[4].text=nums[4].toString()
}

fun setOperation(operation: String, position: Int, operators: List<TextView>) {
    if (position == 0) {
        operators[0].text = operation
    }
    if (position == 1) {
        operators[1].text = operation
    }
    if (position == 2) {
        operators[2].text = operation
    }
}

fun setCurrValue(currValue: Double, currentText: TextView) {
    if (currValue % 1.0 == 0.0) {
        val intValue = currValue.toInt()
        currentText.text = intValue.toString()
    } else {
        currentText.text = currValue.toString()
    }
}

fun disableButtons(buttons: List<Button>) {
    for (button in buttons) {
        button.isEnabled = false
    }
}

fun enableButtons(buttons: List<Button>) {
    for (button in buttons) {
        button.isEnabled = true
    }
}

fun calcCurrValueAdd(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (currValue == 0.0)
    {
        currValue = (nums[position] + nums[position + 1]).toDouble()
    } else {
        currValue += nums[position + 1]
    }
    return currValue
}

fun calcCurrValueSub(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (currValue == 0.0)
    {
        currValue = (nums[position] - nums[position + 1]).toDouble()
    } else {
        currValue -= nums[position + 1]
    }
    return currValue
}

fun calcCurrValueMult(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (currValue == 0.0)
    {
        currValue = (nums[position] * nums[position + 1]).toDouble()
    } else {
        currValue *= nums[position + 1]
    }
    return currValue
}

fun calcCurrValueDiv(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (currValue == 0.0)
    {
        currValue = (nums[position] / nums[position + 1]).toDouble()
    } else {
        currValue /= nums[position + 1]
    }
    return currValue
}

fun checkGameState(currValue: Double, goalValue: Double, Instructions: TextView, buttons: List<Button>, konfettiView: KonfettiView) {
    if (currValue == goalValue) {
        Instructions.text = "You win!"
        konfettiView.start(Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        ))
    } else {
        Instructions.text = "Try again!"
    }
    disableButtons(buttons)
}