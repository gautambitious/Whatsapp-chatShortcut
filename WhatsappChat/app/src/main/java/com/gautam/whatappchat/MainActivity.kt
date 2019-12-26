package com.gautam.whatappchat

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import co.metalab.asyncawait.Async
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceCode.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://github.com/gautambitious/Whatsapp-chatShortcut")
            startActivity(intent)
        }


        var number: String = "0"
        if(intent.action== Intent.ACTION_PROCESS_TEXT){
            number=intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            checkNumber(number)
        }
        else{
            openButton.setOnClickListener {
                number=phoneNumber.editText?.text.toString()
                if (number.isNullOrBlank())
                    Toast.makeText(this,"Number can't be null",Toast.LENGTH_LONG).show()
                else
                checkNumber(number)
            }
        }

    }

    private fun checkNumber(number: String) {
        var num=number
        if(num[0] == '+'){
            num=num.substring(1)
        }
        when {
            num.isDigitsOnly() -> startWhatsapp(num)
            else -> Toast.makeText(this, "Invalid Number!", Toast.LENGTH_LONG).show()
        }
    }

    private fun startWhatsapp(number: String) {

        val intent= Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        val data= when {
            number[0]== '+' -> {
                number.substring(1)
            }
            number.length==10 -> {
                "91$number"
            }
            else -> {
                number
            }
        }

        intent.data= Uri.parse("https://wa.me/$data")
        if(packageManager.resolveActivity(intent,0)!=null){
            startActivity(intent)
//            Thread.sleep(2000)
//            finishAndRemoveTask()
        }
        else {
            Toast.makeText(this, "Couldn't find Whatsapp", Toast.LENGTH_LONG).show()
        }
    }
}
