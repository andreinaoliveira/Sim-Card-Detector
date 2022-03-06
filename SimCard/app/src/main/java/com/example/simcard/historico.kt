package com.example.simcard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class historico : AppCompatActivity(), View.OnClickListener {
    private lateinit var historico: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_historico)

        historico = findViewById(R.id.historicotxt) as TextView

        try {
            historico.text = txtRead()
        } catch (e: Exception){
            historico.text = ("Sem dados históricos")
        }


        // Chamada do Menu
        var bt_home = findViewById<ImageView>(R.id.btHome2)
        var bt_dicas = findViewById<ImageView>(R.id.btTips2)

        bt_home.setOnClickListener(this)
        bt_dicas.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btHome2 -> {
                val intent = Intent(applicationContext, com.example.simcard.MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btTips2 -> {
                val intent = Intent(applicationContext, com.example.simcard.DicasSeguranca::class.java)
                startActivity(intent)
            }
        }
    }

    public fun checkDirectory(): File {
        val path = this.applicationContext.filesDir.absolutePath
        val logTxtDirectory = File(path,"LogTxt")
        if (!logTxtDirectory.isDirectory) logTxtDirectory.mkdirs()
        Log.i("Diretorio",path)
        return logTxtDirectory
    }

    public fun txtRead(): String {
        val fileTxt = File(checkDirectory(), "logSimCard.txt")
        val inputAsString = FileInputStream(fileTxt).bufferedReader().use { it.readText() }
        Log.i("Retorno",inputAsString)
        return inputAsString
    }
}