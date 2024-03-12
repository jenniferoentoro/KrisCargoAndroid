package com.example.projectpaba

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projectpaba.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val sp = getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        val token = sp?.getString("token",null)



        if (token != null) {
            //refresh login token
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.2.58:8000")
                .build()
            val service = retrofit.create(API::class.java)
            val call = service.refresh("Bearer " + token)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        val jsonString = body.string()
                        val gson = Gson()
                        val jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
                        Log.d("testeric", response.toString())

                        if (jsonElement.isJsonObject) {
                            val jsonObject = jsonElement.asJsonObject
                            val value = jsonObject.get("token").asString
                            //get name
                            //getName

                            val retrofit = Retrofit.Builder()
                                .baseUrl("http://192.168.2.58:8000")
                                .build()
                            val service = retrofit.create(API::class.java)

                            // Make the API request and handle the response

                            val call = service.getProfile("Bearer " + value)

                            call.enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if (response.isSuccessful) {
                                        val body = response.body()!!
                                        val jsonString = body.string()
                                        val gson = Gson()
                                        val jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
                                        Log.d("testeric", jsonString)

                                        if (jsonElement.isJsonObject) {
                                            val jsonObject = jsonElement.asJsonObject
                                            val value = jsonObject.get("name").asString
                                            //parse the JSON object and handle the response
                                            val editor = sp?.edit()
                                            val gson = Gson()
                                            val name = sp?.getString("name",null)
                                            if (name.isNullOrEmpty() && editor != null) {
                                                editor.putString("name",value)
                                                editor.apply()
                                            }
                                        }

                                    } else {
                                        //get response of code 400 (sudah ga valid token)
                                        Log.d("testeric", response.toString())
                                        val editor = sp?.edit()
                                        editor?.remove("token")
                                        editor?.remove("name")
//                                        editor?.apply()
//                                        Toast.makeText(getActivity(), "Session Expired, Please Log In!", Toast.LENGTH_SHORT).show()

                                        // handle error
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    // not getting response back
                                    Log.d("testeric", "gagal")
                                    Toast.makeText(this@MainActivity, "Unable to get response!", Toast.LENGTH_SHORT).show()

                                }
                            })
                            //parse the JSON object and handle the response
                            val editor = sp?.edit()
                            val gson = Gson()
                            if (editor != null) {
                                editor.putString("token", value)
                                editor.apply()
                            }
                        }
                    } else {
                        //get response of code 400 (sudah ga valid)
                        Log.d("testeric", response.toString())
                        val editor = sp?.edit()
                        editor?.remove("token")
                        editor?.remove("name")
                        val flProfileNameHome = findViewById<FrameLayout>(R.id.flProfileNameHome)
                        flProfileNameHome.visibility = View.GONE
                        editor?.apply()
                        Toast.makeText(this@MainActivity, "Session Expired, Please Log In!", Toast.LENGTH_SHORT).show()
                        // handle error
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // not getting response back
                    Log.d("testeric", "gagal")
                    Toast.makeText(this@MainActivity, "Unable to get response, check your internet!", Toast.LENGTH_SHORT).show()
//                    clLoggedIn.visibility = View.GONE
//                    clLoggedOut.visibility = View.VISIBLE

                }
            })
        }



        replaceFragment(HomeFragment())
        binding.fbTracking.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")))


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.setOnItemSelectedListener {
            binding.fbTracking.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")))
            binding.tvTracking.setTextColor(Color.parseColor("#808080"));

            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.notification -> {
                    if (token.isNullOrEmpty()) {
                        replaceFragment(ProfileFragment())
                    } else {
                        replaceFragment(NotificationFragment())
                    }
                }
                R.id.schedule -> replaceFragment(ScheduleFragment())

                else -> {

                }
            }
            true
        }

        binding.fbTracking.setOnClickListener{
            changeTrackingColor()
        }

        binding.tvTracking.setOnClickListener {
            changeTrackingColor()
        }

    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun changeTrackingColor() {
        val sp = getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)

        binding.fbTracking.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")))

        binding.bottomNavigationView.menu.getItem(2).isChecked = true

        binding.tvTracking.setTextColor(Color.parseColor("#ff0000"))

        val token = sp?.getString("token", null)
        if (token.isNullOrEmpty()) {
            replaceFragment(ProfileFragment())
        } else {
            replaceFragment(TrackingFragment())

        }



    }

}