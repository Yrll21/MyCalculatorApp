package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // variables
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    // main function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }
    // function for digits onClick
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    // function for the clear button
    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }
    // function for the decimal button
    fun onDecimal(view: View) {
        if(lastNumeric && !lastDot){
            tvInput?.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }
    // function for operators
    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }
    // function to show the answer of calculation
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                // negative prefix check
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                // operations
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    var result = (firstValue.toDouble() - secondValue.toDouble())
                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    var result = firstValue.toDouble() + secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    var result = firstValue.toDouble() * secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    var result = firstValue.toDouble() / secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)

        }
        return value
    }

    // checks whether an operator is already added or not.
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            return false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}