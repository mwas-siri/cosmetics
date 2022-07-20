package com.example.shop

import adapters.RecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import models.Product
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var productList:ArrayList<Product>
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var progress: ProgressBar
    lateinit var recyclerView: RecyclerView











    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    //we find the recycleview and progerssbarfrm mainactivity xml
        recyclerView=findViewById(R.id.recycler)
        progress=findViewById(R.id.progress)
        progress.visibility = View.VISIBLE



        val client=AsyncHttpClient(true,80,443)
        //pas lst of products to the adapter
        recyclerAdapter= RecyclerAdapter(applicationContext)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(applicationContext)

        client.get(this,"https://modcom.pythonanywhere.com/api/alll",null,
            "application/json",
            object:JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray?
                ) {
                    //convat json array of product frm api to a list of given models
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(
                        response.toString(),
                        Array<Product>::class.java
                    ).toList()
                    recyclerAdapter.setProductListItems(list)
                    progress.visibility = View.GONE
                }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse:JSONArray

                    ) {
                        Toast.makeText(
                            applicationContext,
                            "no products on sale",
                            Toast.LENGTH_LONG
                        ).show()
                        progress.visibility = View.GONE
                }

            }



        )
recyclerView.adapter=recyclerAdapter

















    }
}