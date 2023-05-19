package com.example.cw_w1867464

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class ThirdActivity : AppCompatActivity() {

    private lateinit var throwButton: Button
    private lateinit var scoreButton: Button
    private lateinit var resetButton: Button
    private lateinit var humanDiceViews: List<ImageView>
    private lateinit var computerDiceViews: List<ImageView>
    private lateinit var targetScoreInput: TextInputEditText

    private lateinit var playerScoreView: TextView
    private lateinit var computerScoreView: TextView
    private lateinit var scoreCounter: TextView
    private lateinit var humanPlayer : TextView
    private lateinit var computerPlayer : TextView
    private lateinit var instructionButton: Button

    private var playerScore = 0
    private var computerScore = 0
    private val DEFAULT_TARGET_SCORE = 101
    private var targetScore = DEFAULT_TARGET_SCORE

    private var numRerolls = 0
    private var canReroll = true

    private var selectedDice: Int? = null

    // initialize wins count for human and computer to remain even it navigates to another page
    companion object{
        private var humanWins = 0
        private var computerWins = 0
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        throwButton = findViewById(R.id.throw_button)
        scoreButton = findViewById(R.id.score_button)
        resetButton = findViewById(R.id.reset_button)
        instructionButton =findViewById(R.id.instruction_button)

        playerScoreView = findViewById(R.id.player_score)
        computerScoreView = findViewById(R.id.computer_score)
        targetScoreInput = findViewById(R.id.target_score_input)
        scoreCounter = findViewById(R.id.wins_view)
        humanPlayer = findViewById(R.id.human_player)
        computerPlayer = findViewById(R.id.computer_player)

        humanDiceViews = listOf(
            findViewById(R.id.human_dice1),
            findViewById(R.id.human_dice2),
            findViewById(R.id.human_dice3),
            findViewById(R.id.human_dice4),
            findViewById(R.id.human_dice5)
        )

        computerDiceViews = listOf(
            findViewById(R.id.computer_dice1),
            findViewById(R.id.computer_dice2),
            findViewById(R.id.computer_dice3),
            findViewById(R.id.computer_dice4),
            findViewById(R.id.computer_dice5)
        )
        // display the initial score of human and computer players
        scoreCounter.text = "H:$humanWins/C:$computerWins"

        //method to calculate score from dice images
        fun calculateScoreFromImages(diceValues: List<Int> , selectedDice: Int?): Int {
            var score = 0
            var hasStraight = true
            var counts = IntArray(6)

            for (i in diceValues.indices) {
                if (i != selectedDice){
                val diceValue = diceValues[i]
                counts[diceValue - 1]++
                score += diceValue
            }}

            for (i in counts.indices) {
                if (counts[i] != 1) {
                    hasStraight = false
                    break
                }
            }
            if (hasStraight) {
                score = 200
            }
            return score
        }

        var throwCounter = 0
        var targetScore = DEFAULT_TARGET_SCORE
        val winColor = Color.GREEN
        val loseColor = Color.RED

        // click listener for the throw button
        throwButton.setOnClickListener {
            val customScore = targetScoreInput.text.toString().toIntOrNull()
            if (customScore != null) {
                targetScore = customScore
            }
            //Roll human dice
            val humanThrows = rollDice1()
            // Display human dice images
            displayDice(humanDiceViews, humanThrows)

            val computerThrows = rollDice1()
            // Display human dice images
            displayDice(computerDiceViews, computerThrows)
            // Calculate human score based on the dice image
            var humanScoreFromImages = calculateScoreFromImages(humanThrows,selectedDice)
            var computerScoreFromImages = calculateScoreFromImages(computerThrows,null)
            playerScore += humanScoreFromImages
            computerScore += computerScoreFromImages
            humanPlayer.text = "Player: $playerScore"
            computerPlayer.text = "Computer: $computerScore"

            throwCounter++
            throwButton.text = "Reroll ($throwCounter/3)"
            if (throwCounter >= 3) {
                playerScoreView.text = "Player: $playerScore"
                computerScoreView.text = "Computer: $computerScore"
                if (playerScore >= targetScore && playerScore >= computerScore) {
                    endGame("Player", winColor)
                    throwButton.isEnabled = false
                } else if (computerScore >= targetScore && playerScore <= computerScore) {
                    endGame("Computer", loseColor)
                    // Disable throw button to prevent additional rolls during computer's turn
                    throwButton.isEnabled = false
                }
                throwCounter = 0
            }

        }

        // click listener for the reset button
        resetButton.setOnClickListener {
            resetGame()
        }

        // click listener for the instruction button
        instructionButton.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.rules,null)

            val myDialognew = Dialog(this)
            myDialognew.setContentView(dialogBinding)

            myDialognew.setCancelable(true)
            myDialognew.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialognew.show()

            val closebtn = dialogBinding.findViewById<TextView>(R.id.close)
            closebtn.setOnClickListener{
                myDialognew.dismiss()
            }
        }

        // click listener for the score button
        scoreButton.setOnClickListener {
            // Calculate computer's score
            val computerThrows = rollDice1()
            var computerScoreFromImages = calculateScoreFromImages(computerThrows,null)
            computerScore += computerScoreFromImages
            computerScoreView.text = "Computer: $computerScore"

            val humanThrows = rollDice1()
            var humanScoreFromImages = calculateScoreFromImages(humanThrows,selectedDice)
            playerScore += humanScoreFromImages
            playerScoreView.text = "Player: $playerScore"

            if (playerScore >= targetScore && playerScore >= computerScore) {
                endGame("Player", winColor)
            } else if (computerScore >= targetScore && playerScore <= computerScore) {
                endGame("Computer", loseColor)
                // Disable throw button to prevent additional rolls during computer's turn
                scoreButton.isEnabled = false
            }
            // Reset the throw counter
            throwCounter = 0
            throwButton.text = "Reroll ($throwCounter/3)"
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save any necessary data here
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle orientation change here if needed
    }

    // function that olls five dice an returns the sum of their face values
    private fun rollDice(): Int {
        var sum = 0
        for (i in 1..5) {
            val face = Random.nextInt(1, 7)
            sum += face
        }
        return sum
    }

    // function to reset the values
    private fun resetGame() {
        playerScore = 0
        computerScore = 0
        playerScoreView.text = "Player: 0"
        computerScoreView.text = "Computer: 0"
        //winnerView.text = ""
        throwButton.text = "Reroll (1/3)"
        throwButton.isEnabled = true
        scoreButton.isEnabled =true
        displayDice(humanDiceViews, listOf(1, 1, 1, 1, 1))
        displayDice(computerDiceViews, listOf(1, 1, 1, 1, 1))
    }

    //function to displays an alert dialog to inform the user whether they won or lost the game.
    fun endGame(winner: String, color: Int) {
        val message = if (winner == "Player") "You win!" else "You lose"
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(message)
            .setMessage("Click Reroll to play again.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
        }
        // calculate total wins of computer and user.
        if (winner == "Player") {
            humanWins++
        } else {
            computerWins++
        }
        scoreCounter.text = "H:$humanWins/C:$computerWins"

        alertDialog.show()
    }

    //  function to generate a list of 5 random integers between 1 and 6 (inclusive) representing
    //  the faces of 5 dice being rolled.
    private fun rollDice1(): List<Int> {
        return List(5) { Random.nextInt(1, 7) }
    }


    private fun displayDice(diceViews: List<ImageView>, throws: List<Int>) {
        for (i in 0 until 5) {
            if (selectedDice != null && selectedDice == i) {
                // If the user selected a dice, skip rolling it
                continue
            }

            val drawableResource = when (throws[i]) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }
            diceViews[i].setImageResource(drawableResource)
            diceViews[i].setOnClickListener {
                if (selectedDice == i) {
                    // Deselect the dice if it's already selected
                    selectedDice = null
                    diceViews[i].alpha = 1.0f
                } else {
                    // Select the dice if it's not already selected
                    selectedDice = i
                    diceViews[i].alpha = 0.5f
                }
            }
        }
    }
}
