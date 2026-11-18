package com.example.term_proj_8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.math.RoundingMode
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val opButtons = mutableListOf<Button>()
    private val textNumBoxes = mutableListOf<TextView>()
    private val currOperators = mutableListOf<TextView>()

    private var gameState:String = "None"

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
        var currValue: Double = 0.0
        var position = 0
        var level = 0

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
        val levels = arrayOf(
            intArrayOf(2, 4, 6, 8, 6),
            intArrayOf(3, 8, 2, 5, 17),
            intArrayOf(6, 3, 8, 2, 12),
            intArrayOf(7, 4, 8, 2, 22),
            intArrayOf(3, 3, 7, 2, 14),
            intArrayOf(8, 2, 3, 5, 8),
            intArrayOf(2, 8, 2, 5, 13),
            intArrayOf(3, 2, 5, 9, 9),
            intArrayOf(8, 7, 9, 9, 15),
            intArrayOf(9, 9, 3, 3, 18),
            intArrayOf(7, 2, 9, 7, 7),
            intArrayOf(4, 8, 6, 8, 10),
            intArrayOf(3, 4, 4, 5, 8),
            intArrayOf(8, 7, 7, 4, 12),
            intArrayOf(11, 2, 12, 4, 70)
        )
        var goalValue = 0.0

        val konfettiView = findViewById<KonfettiView>(R.id.konfettiView)



        addButton.setOnClickListener {
            setOperation("+", position, currOperators)
            currValue = calcCurrValueAdd(position, currValue, levels[level])
            setCurrValue(currValue, currValueText)
            position++

            // !!! Evaluates if player wins, checks if current value == goal value
            // and if position is in final position
            if(position == maxPosition) {
                gameState =  checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView, resetButton)
            }
        }

        subButton.setOnClickListener {
            setOperation("-", position, currOperators)
            currValue = calcCurrValueSub(position, currValue, levels[level])
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                gameState =  checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView, resetButton)
            }
        }
        multButton.setOnClickListener {
            setOperation("*", position, currOperators)
            currValue = calcCurrValueMult(position, currValue, levels[level])
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                gameState =  checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView, resetButton)
            }
        }
        divButton.setOnClickListener {
            setOperation("/", position, currOperators)
            currValue = calcCurrValueDiv(position, currValue, levels[level])
            setCurrValue(currValue, currValueText)
            position++

            if(position == maxPosition) {
                gameState = checkGameState(currValue, goalValue, Instructions, opButtons, konfettiView, resetButton)
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
            if (gameState.equals("WIN") && (level < (levels.size - 1))) {
                resetButton.text = "Reset"
                level++
                goalValue = initializeGameState(levels[level], textNumBoxes)
                gameState = "Fail"
            } else if (gameState.equals("WIN") && (level < levels.size)) {
                resetButton.text = "Reset"
                level = 0
                goalValue = initializeGameState(levels[level], textNumBoxes)
                gameState = "Fail"
            }
        }
        goalValue = initializeGameState(levels[level], textNumBoxes)
    }


}

fun initializeGameState(nums:  IntArray, textNumBoxes: List<TextView>): Double {
    textNumBoxes[0].text=nums[0].toString()
    textNumBoxes[1].text=nums[1].toString()
    textNumBoxes[2].text=nums[2].toString()
    textNumBoxes[3].text=nums[3].toString()
    textNumBoxes[4].text=nums[4].toString()
    return nums[4].toDouble()
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
        currentText.text = currValue.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString()
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

    if (position == 0)
    {
        currValue = (nums[position] + nums[position + 1]).toDouble()
    } else {
        currValue += nums[position + 1]
    }
    return currValue
}

fun calcCurrValueSub(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (position == 0)
    {
        currValue = (nums[position] - nums[position + 1]).toDouble()
    } else {
        currValue -= nums[position + 1]
    }
    return currValue
}

fun calcCurrValueMult(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (position == 0)
    {
        currValue = (nums[position] * nums[position + 1]).toDouble()
    } else {
        currValue *= nums[position + 1]
    }
    return currValue
}

fun calcCurrValueDiv(position: Int, current: Double, nums: IntArray): Double {
    var currValue = current

    if (position == 0)
    {
        currValue = ((nums[position]).toDouble() / nums[position + 1]).toDouble()
    } else {
        currValue /= nums[position + 1]
    }
    return currValue
}

fun checkGameState(currValue: Double, goalValue: Double, Instructions: TextView, buttons: List<Button>, konfettiView: KonfettiView, resetButton: TextView): String {
    var GameState = "Fail"
    if (currValue == goalValue) {
        Instructions.text = "You win!"
        resetButton.text = "Next->"
        konfettiView.start(Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        ))
        GameState = "WIN"
    } else {
        Instructions.text = "Try again!"
    }
    disableButtons(buttons)
    return GameState
}