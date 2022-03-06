package com.example.simcard

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.simcard.fragment.HistoricFragment
import com.example.simcard.fragment.HomeFragment
import com.example.simcard.fragment.TipsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val PHONE_STATE = 0
    private var viewModel: MainViewModel? = null
    private lateinit var serial: TextView
    private lateinit var numbertxt: TextView
    private lateinit var iccidtxt: TextView
    private lateinit var operadoratxt: TextView
    private lateinit var detalhestxt: TextView

    // Atributos para o Menu Bar
    private lateinit var navigationView: BottomNavigationView
    private val homeFragment = HomeFragment()
    private val historicFragment = HistoricFragment()
    private  val tipsFragment = TipsFragment()

    public val TAG: String = "Time06"
    private val CHANNEL_ID = "Time06Notify"
    private val notificationId = 101

    var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        initPermissions()

        //Declaração das strings na tela
        serial = findViewById(R.id.SerialSIM) as TextView
        numbertxt = findViewById(R.id.number) as TextView
        iccidtxt = findViewById(R.id.iccid) as TextView
        operadoratxt = findViewById(R.id.operator) as TextView
        detalhestxt = findViewById(R.id.SerialSIM) as TextView

        //Input dos valores das strings na tela
        numbertxt.text = getNumber()
        iccidtxt.text = getIccId()
        operadoratxt.text = getOperator()
        detalhestxt.text = getDetails()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        observeData()
        val intent = Intent(this, SimCardService::class.java)
        startService(intent)

        // Chamada do Menu
        var bt_historico = findViewById<ImageView>(R.id.btHistory1)
        var bt_dicas = findViewById<ImageView>(R.id.btTips1)

        bt_historico.setOnClickListener(this)
        bt_dicas.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btHistory1 -> {
                val intent = Intent(applicationContext, com.example.simcard.historico::class.java)
                startActivity(intent)
            }
            R.id.btTips1 -> {
                val intent = Intent(applicationContext, com.example.simcard.DicasSeguranca::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getDetails(): String {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val d1 = telephonyManager.simCarrierId
        val d3 = telephonyManager.simCountryIso
        val d4 = telephonyManager.simState
        return "ID Operadora: $d1 \nPaís: $d3 \nStatus: $d4"
    }

    @SuppressLint("MissingPermission")
    fun getNumber(): String {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val number = telephonyManager.line1Number
        Log.i(TAG, "Numero: $number")
        return "$number"
    }

    @SuppressLint("MissingPermission")
    fun getIccId(): String {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val iccid = telephonyManager.simSerialNumber
        Log.i(TAG, "ICCID: $iccid")

        return "$iccid"
    }

    @SuppressLint("MissingPermission")
    fun getOperator(): CharSequence? {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val operator = telephonyManager.simCarrierIdName
        Log.i(TAG, "Operadora: $operator")

        return "$operator"
    }

    // Atualizar dados da activity conforme troca de chip
    private fun observeData() {
        viewModel!!.observeLiveSimState().observe(this,
            { value ->
                if (value) {
                    numbertxt.text = getNumber()
                    iccidtxt.text = getIccId()
                    operadoratxt.text = getOperator()
                    detalhestxt.text = getDetails()
                } else {
                    numbertxt.text = getNumber()
                    iccidtxt.text = getIccId()
                    operadoratxt.text = getOperator()
                    detalhestxt.text = getDetails()
                }
            })
    }


            override fun onPause() {
                super.onPause()
                viewModel!!.unregisterSimState()
            }

            override fun onResume() {
                super.onResume()
                requestPermissions(viewModel!!.checkPermission())
                viewModel!!.checkSimState()
                viewModel!!.registerSimState()
            }

            private fun requestPermissions(permissionGranted: Boolean) {
                if (!permissionGranted) {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.READ_PHONE_STATE
                        ),
                        PHONE_STATE
                    )
                }
            }

            override fun onDestroy() {
                super.onDestroy()
            }


    // Função para pedir permissão ao usuário
    private fun initPermissions() {
        if (!getPermission()) setPermission()
        else check = true
    }

    private fun getPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun setPermission() {
        val permissionsList = listOf<String>(
            Manifest.permission.READ_PHONE_STATE
        )
        ActivityCompat.requestPermissions(this, permissionsList.toTypedArray(), 1)
    }

    private fun errorPermission() {
        Toast.makeText(this, "Não tem permissão para ler arquivos.", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission: ", "A permissão foi negada pelo usuário\"")
                    errorPermission()
                } else {
                    Log.i("Permission: ", "A permissão foi concedida pelo usuário")
                    check = true
                }
            }
        }
    }
}