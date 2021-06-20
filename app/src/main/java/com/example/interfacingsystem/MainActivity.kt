package com.example.interfacingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var database : FirebaseDatabase
    private lateinit var referance : DatabaseReference
    private lateinit var referanceTemp : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        referance = database.getReference("")
        referanceTemp = database.getReference("Temperature")

        switchLamp1?.setOnCheckedChangeListener({ _ , isChecked ->
            changeLamp1(if(isChecked) 1 else 0)
        })

        switchLamp2?.setOnCheckedChangeListener({ _ , isChecked ->
            changeLamp2(if(isChecked) 1 else 0)
        })

        switchDoor?.setOnCheckedChangeListener({ _ , isChecked ->
            changeDoor(if(isChecked) 1 else 0)
        })

        getData();
    }

    private fun changeDoor(temp : Number){
        referance.child("Door").setValue(temp)
    }

    private fun changeLamp1(temp: Number){
        referance.child("Lamp1").setValue(temp)
    }

    private fun changeLamp2(temp: Number){
        referance.child("Lamp2").setValue(temp)
    }

    private fun getData(){
        referanceTemp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.toString())
            }

            val dec = DecimalFormat("##")

            override fun onDataChange(snapshot: DataSnapshot) {
                text_temperature.setText(dec.format(snapshot.value).toString()+"°")
            }
        })
    }
}