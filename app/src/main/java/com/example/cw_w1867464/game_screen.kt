package com.example.cw_w1867464

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SecondActivity : AppCompatActivity() {

    private lateinit var playerScoreView: TextView
    private lateinit var computerScoreView: TextView
    private lateinit var winnerView : TextView
    private lateinit var rollButton: Button

    private var playerScore = 0
    private var computerScore = 0
    private val targetScore = 101

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        playerScoreView = findViewById(R.id.player_score)
        computerScoreView = findViewById(R.id.computer_score)
        winnerView =findViewById(R.id.winner_view)
        rollButton = findViewById(R.id.roll_button)


        rollButton.setOnClickListener {
            // Player's turn
            val playerThrow = rollDice()
            playerScore += playerThrow
            playerScoreView.text = "Player Score: $playerScore"
            if (playerScore >= targetScore) {
                endGame("Player")
            } else {
                // Computer's turn
                val computerThrow = rollDice()
                computerScore += computerThrow
                computerScoreView.text = "Computer Score: $computerScore"
                if (computerScore >= targetScore) {
                    endGame("Computer")
                }
            }
        }
    }

    private fun rollDice(): Int {
        var sum = 0
        for (i in 1..5) {
            val face = Random.nextInt(1, 7)
            sum += face
        }
        return sum
    }

    private fun endGame(winner: String) {
        rollButton.isEnabled = false
        val message = "$winner wins! \nFinal scores: Player $playerScore, computer $computerScore"
        winnerView.text = message
//        playerScoreView.text = message
//        computerScoreView.text = message
    }
}
