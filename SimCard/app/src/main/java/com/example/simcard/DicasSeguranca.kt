package com.example.simcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class DicasSeguranca : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_dicas_seguranca)

        // Chamada do Menu
        var bt_home = findViewById<ImageView>(R.id.btHome3)
        var bt_historico = findViewById<ImageView>(R.id.btHistory3)

        bt_home.setOnClickListener(this)
        bt_historico.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btHome3 -> {
                val intent = Intent(applicationContext, com.example.simcard.MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btHistory3 -> {
                val intent = Intent(applicationContext, com.example.simcard.historico::class.java)
                startActivity(intent)
            }
        }
    }
}