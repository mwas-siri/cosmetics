package com.example.shop

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class single: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        val prefs:SharedPreferences=this.getSharedPreferences("shop",Context.MODE_PRIVATE)


        val prodname= findViewById<TextView>(R.id.p_name)
        val prodesc = findViewById<TextView>(R.id.p_desc)
        val prodcost = findViewById<TextView>(R.id.p_cost)
        val img = findViewById<ImageView>(R.id.img_url)



        val flashname = prefs.getString("product_name","")
        val flashdesc = prefs.getString("product_desc","")
        val flashcost = prefs.getString("product_cost","")
        val flashimg = prefs.getString("img_url","")


        prodname.text =flashname
        prodesc.text = flashdesc
        prodcost.text =flashcost

        Glide.with(applicationContext).load(flashimg)
            .apply(RequestOptions().centerCrop())
            .into(img)


        val progressBar=findViewById(R.id.progressbar) as ProgressBar
        progressBar.visibility=View.GONE
        val phone=findViewById<EditText>(R.id.phone)
        val pay=findViewById<Button>(R.id.pay)

        pay.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            val client=AsyncHttpClient(true,88,443)
            val json=JSONObject()
            json.put("amount","1")
            json.put("phone",phone.text.toString())
            val body= StringEntity(json.toString())
            client.post(this,"https://modcom.phythonanywhere.com/mpesa_payment",body,"application/json",object:JsonHttpResponseHandler(){

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext,"Paid Successfully",Toast.LENGTH_LONG).show()
                    progressBar.visibility=View.GONE
                }





            })

        }



    }
}