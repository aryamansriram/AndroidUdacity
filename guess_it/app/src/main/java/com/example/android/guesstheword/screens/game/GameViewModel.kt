package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>get() = _word
    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>get() = _score

    private var _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>get() = _eventGameFinished

    private var _timerText = MutableLiveData<Long>()
    val timerText: LiveData<Long>get() = _timerText


    val cur_time_String = Transformations.map(timerText,{time->
        DateUtils.formatElapsedTime(time)
    })
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer


    init {
        resetList()
        nextWord()
        _score.value = 0
        Log.i("GameViewModel","GameViewModel Created")

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                _timerText.value = millisUntilFinished/1000


            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _eventGameFinished.value = true
            }
        }

        timer.start()



    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel destroyed")
    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //gameFinished()
            //_eventGameFinished.value = true
            resetList()
        }
        _word.value = wordList.removeAt(0)


    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete(){
        _eventGameFinished.value = false
    }



}