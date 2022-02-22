package com.example.agecalculator

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import kotlinx.android.synthetic.main.activity_actual_age.*

class ActualAge : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_age)

        val age = intent?.getIntExtra("AGE", 0) ?: 0
        val birtdhate = intent?.getIntExtra("BIRTHDATE", 0) ?: 0

        val boldSpan = StyleSpan(Typeface.BOLD)

        val spannableHB = SpannableString("Hi, you are " + age + "\n\n HAPPY BIRTHDAY!!")
        spannableHB.setSpan(boldSpan, 16, spannableHB.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val spannableHW = SpannableString("Hi, you are born today\n\nSay HELLO WORLD")
        spannableHW.setSpan(boldSpan, 28, spannableHW.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        if(birtdhate == -1)
            actualAge.text = spannableHW
        else{
            if(birtdhate == 0)
                actualAge.text = "Hi, you are " + age
            else
                actualAge.text = spannableHB
        }
    }
}