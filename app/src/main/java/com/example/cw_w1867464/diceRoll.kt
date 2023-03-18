package com.example.cw_w1867464

import kotlin.random.Random

fun main() {
    val targetScore = 101 // Change this value to set a different target score
    var humanScore = 0
    var computerScore = 0
    val dice = Array(5) { Random.nextInt(1, 7) }

    while (humanScore < targetScore && computerScore < targetScore) {
        // Human player's turn
        println("Your turn. Press enter to roll the dice.")
        readLine()
        dice.forEachIndexed { index, _ ->
            dice[index] = Random.nextInt(1, 7)
        }
        val humanTurnScore = dice.sum()
        humanScore += humanTurnScore
        println("You rolled: ${dice.joinToString(", ")}")
        println("Your turn score: $humanTurnScore")
        println("Your total score: $humanScore")

        if (humanScore >= targetScore) break // Human wins if they reach or exceed the target score

        // Computer player's turn
        println("Computer's turn. Press enter to roll the dice.")
        readLine()
        dice.forEachIndexed { index, _ ->
            dice[index] = Random.nextInt(1, 7)
        }
        val computerTurnScore = dice.sum()
        computerScore += computerTurnScore
        println("Computer rolled: ${dice.joinToString(", ")}")
        println("Computer turn score: $computerTurnScore")
        println("Computer total score: $computerScore")
    }

    if (humanScore >= targetScore) {
        println("Congratulations! You won with a score of $humanScore.")
    } else {
        println("Sorry, the computer won with a score of $computerScore.")
    }
}