package `in`.kgdroid.blackboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var tvValue:TextView?=null
    var lastNum= false
    var lastDot= false
    var firstZero=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        tvValue= findViewById(R.id.tvInput)
        tvValue?.movementMethod= ScrollingMovementMethod()
    }

    fun clkDigit(view: View){
        //Add to calculate string
        tvValue?.append((view as Button).text)
        lastNum=true

    }

    fun clkAC(view: View){
        tvValue?.text=""
        lastNum=false
        lastDot=false
        firstZero=false
    }


    fun back(view: View){
        var backspace= tvValue?.text
        if(backspace?.length!! <1) {
            tvValue?.text=""
            lastDot=false
            lastNum=false
            firstZero=false
        }else{
            backspace = backspace?.substring(0, backspace.length - 1)
            tvValue?.text = backspace
            if(backspace?.length!! <1){
                lastDot=false
                lastNum=false
                firstZero=false
            }
        }
    }

    fun clkDot(view: View){
        if(lastNum && !lastDot){
            tvValue?.append((view as Button).text)
            lastDot=true
        }
        else if(!firstZero && !lastDot){
            tvValue?.append("0.")
            lastDot=true
            firstZero=true
            lastNum=true
        }

    }

    fun clkZero(view: View){
        if(lastNum){
            tvValue?.append((view as Button).text)
            firstZero=true
        }

    }

    fun clkEquals(view: View){
        if(lastNum){
            var prefix=""
            var tvInput= tvValue?.text.toString()
            try{
                if(tvInput.startsWith("-")){
                    prefix="-"
                    tvInput=tvInput.substring(1)
                }
                if(tvInput.contains("-")){
                    val splitValue= tvInput.split("-")
                    var val1= splitValue[0]
                    var val2= splitValue[1]

                    if(prefix.isNotEmpty()){
                        val1=prefix+val1
                    }
                    tvValue?.text= removeZero((val1.toDouble()-val2.toDouble()).toString())
                }
                if(tvInput.contains("+")){
                    val splitValue= tvInput.split("+")
                    var val1= splitValue[0]
                    var val2= splitValue[1]

                    if(prefix.isNotEmpty()){
                        val1=prefix+val1
                    }
                    tvValue?.text= removeZero((val1.toDouble()+val2.toDouble()).toString())
                }
                if(tvInput.contains("/")){
                    val splitValue= tvInput.split("/")
                    var val1= splitValue[0]
                    var val2= splitValue[1]

                    if(prefix.isNotEmpty()){
                        val1=prefix+val1
                    }
                    tvValue?.text= removeZero((val1.toDouble()/val2.toDouble()).toString())
                }
                if(tvInput.contains("*")){
                    val splitValue= tvInput.split("*")
                    var val1= splitValue[0]
                    var val2= splitValue[1]

                    if(prefix.isNotEmpty()){
                        val1=prefix+val1
                    }
                    tvValue?.text= removeZero((val1.toDouble()*val2.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }

    fun clkOperator(view: View){
        tvValue?.text.let {
            if(lastNum && !isOperatorAdded(it.toString())){
                tvValue?.append((view as Button).text)
                lastNum=false
                lastDot=false
                firstZero=false
            }
        }
    }

    fun isOperatorAdded(value: String):Boolean{
        return if(value.startsWith("-")) {
            return false
        }
        else{
            value.contains("/")
                    ||value.contains("+")
                    ||value.contains("*")
                    ||value.contains("-")
        }
    }

    fun removeZero(value: String):String{
        var result= value
        if(value.contains(".0")){
            result= value.substring(0,value.length-2)
        }
        return result
    }
}