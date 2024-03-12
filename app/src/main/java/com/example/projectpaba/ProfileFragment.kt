package com.example.projectpaba

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbarHome)

        val wa = toolbar.findViewById<ImageView>(R.id.wa)
        wa.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }

        val toolbar2 = view.findViewById<Toolbar>(R.id.toolbarHome2)

        val wa2 = toolbar2.findViewById<ImageView>(R.id.wa)
        wa2.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }

        val aboutUs = view.findViewById<ConstraintLayout>(R.id.btnProfileAboutUs)
        aboutUs.setOnClickListener{
            val aboutFragment = AboutFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,aboutFragment,AboutFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        val gallery = view.findViewById<ConstraintLayout>(R.id.btnProfileGallery)
        gallery.setOnClickListener{
            val galleryFrag = GalleryFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,galleryFrag,GalleryFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        val contactUs = view.findViewById<ConstraintLayout>(R.id.btnProfileContactUs)
        contactUs.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment


        //signup
        val usernameSign = view.findViewById<EditText>(R.id.usernameSign)
        val passSign = view.findViewById<EditText>(R.id.passwordSign)
        val conSign = view.findViewById<EditText>(R.id.conpasswordSign)
        val fullname = view.findViewById<EditText>(R.id.nameSign)
        val btnSign = view.findViewById<Button>(R.id.btn_loginSign)

        btnSign.setOnClickListener {
            if(passSign.text.length < 6){
                Toast.makeText(getActivity(),"Password harus setidaknya 6 character",Toast.LENGTH_SHORT).show()
            }else if (passSign.text.toString() != conSign.text.toString()){


                Toast.makeText(getActivity(),"Password dengan Confirmation Password tidak sama",Toast.LENGTH_SHORT).show()
            }else{

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.2.58:8000")
                .build()
            val service = retrofit.create(API::class.java)

            // Make the API request and handle the response
            val requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                "{\"email\":\"${usernameSign.text.toString()}\",\"password\":\"${passSign.text.toString()}\",\"password_confirmation\":\"${conSign.text.toString()}\",\"name\":\"${fullname.text.toString()}\"}"
            )

            val call = service.register(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        val jsonString = body.string()
                        val gson = Gson()
                        val jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
                        Log.d("testeric", response.toString())

                        if (jsonElement.isJsonObject) {
                            val jsonObject = jsonElement.asJsonObject

                            //parse the JSON object and handle the response

                            //close keyboard
                            val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view?.windowToken, 0)

                            //getName


                            //clear
                            fullname.text.clear()
                            passSign.text.clear()
                            conSign.text.clear()
                            usernameSign.text.clear()
                            Toast.makeText(
                                getActivity(),
                                "Account registered, silahkan login!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    } else {
                        //get response of code 400 (password salah)
                        Log.d("testeric", response.toString())
                        Toast.makeText(
                            getActivity(),
                            "Account sudah ada!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // handle error
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // not getting response back
                    Log.d("testeric", "gagal")
                    Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT)
                        .show()


                }
            })






        }

        }

        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val username = view.findViewById<EditText>(R.id.username)
        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileNameHome)
        val password = view.findViewById<EditText>(R.id.password)
        val clLoggedIn = view.findViewById<ConstraintLayout>(R.id.clLoggedIn)
        val clSignUp = view.findViewById<ConstraintLayout>(R.id.clSignUp)
        val sign = view.findViewById<TextView>(R.id.textView2)
        val back = view.findViewById<TextView>(R.id.textView2Sign)
        val clLoggedOut = view.findViewById<ConstraintLayout>(R.id.clLoggedOut)
        val cvLogOut = view.findViewById<CardView>(R.id.cvLogOut)
        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)

        val token = sp?.getString("token",null)


        sign.setOnClickListener {
            clLoggedIn.visibility = View.GONE
            clLoggedOut.visibility = View.GONE
            clSignUp.visibility = View.VISIBLE
        }

        back.setOnClickListener {
            clLoggedIn.visibility = View.GONE
            clLoggedOut.visibility = View.VISIBLE
            clSignUp.visibility = View.GONE
        }
        if (token == null) {

            //munculin login page
            clLoggedIn.visibility = View.GONE
            clLoggedOut.visibility = View.VISIBLE
            clSignUp.visibility = View.GONE
            Log.d("test2","gada")

        } else {
            //set name
            val name = sp?.getString("name",null)
            tvProfileName.setText(name.toString())

//            //cek kalo token masih valid
//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://192.168.2.231:8000")
//                .build()
//            val service = retrofit.create(API::class.java)
//
//            // Make the API request and handle the response
//            val call = service.getProfile("Bearer " + token)
//            call.enqueue(object : Callback<ResponseBody> {
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    if (response.isSuccessful) {
//                        val body = response.body()!!
//                        val jsonString = body.string()
//                        val gson = Gson()
//                        val jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
//                        Log.d("testeric", jsonString)
//
//                        if (jsonElement.isJsonObject) {
//                            val jsonObject = jsonElement.asJsonObject
//                            val value = jsonObject.get("name").asString
//                            //parse the JSON object and handle the response
//                            val editor = sp?.edit()
//                            val gson = Gson()
//                            val name = sp?.getString("name",null)
//                            if (name.isNullOrEmpty() && editor != null) {
//                                editor.putString("name",value)
//                                editor.apply()
//                            }
//                            tvProfileName.setText(value.toString())
//                        }
//
//                        //make visible
//
//                        clLoggedOut.visibility = View.GONE
//                        clLoggedIn.visibility = View.VISIBLE
//
//                    } else {
//                        //get response of code 400 (sudah ga valid)
//                        Log.d("testeric", response.toString())
//                        val editor = sp?.edit()
//                        editor?.remove("token")
//                        editor?.remove("name")
//                        editor?.apply()
//                        Toast.makeText(getActivity(), "Session Expired, Please Log In!", Toast.LENGTH_SHORT).show()
//                        clLoggedIn.visibility = View.GONE
//                        clLoggedOut.visibility = View.VISIBLE
//                        // handle error
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    // not getting response back
//                    Log.d("testeric", "gagal")
//                    Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT).show()
////                    clLoggedIn.visibility = View.GONE
////                    clLoggedOut.visibility = View.VISIBLE
//
//                }
//            })
////            Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show()
//
//
//            //munculin my account
//            Log.d("test2","Ada")
//            val username = sp.getString("username", "")
//            val password = sp.getString("password", "")
//            Log.d("test3",username.toString())
//            Log.d("test4",password.toString())
        }


        btnLogin.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.2.58:8000")
                .build()
            val service = retrofit.create(API::class.java)

            // Make the API request and handle the response
            val requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                "{\"email\":\"${username.text.toString()}\",\"password\":\"${password.text.toString()}\"}"
            )
            val call = service.login(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        val jsonString = body.string()
                        val gson = Gson()
                        val jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
                        Log.d("testeric", response.toString())

                        if (jsonElement.isJsonObject) {
                            val jsonObject = jsonElement.asJsonObject
                            val value = jsonObject.get("token").asString
                            //parse the JSON object and handle the response
                            val editor = sp?.edit()
                            val gson = Gson()
                            if (editor != null) {
                                editor.putString("token", value)
                                editor.apply()
                            }
                            //close keyboard
                            val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view?.windowToken, 0)

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
                                            val value_id = jsonObject.get("id").asString
                                            //parse the JSON object and handle the response
                                            val editor = sp?.edit()
                                            val gson = Gson()
                                            val name = sp?.getString("name",null)
                                            if (name.isNullOrEmpty() && editor != null) {
                                                editor.putString("name",value)
                                                editor.putString("user_id",value_id)
                                                editor.apply()
                                            }
                                            tvProfileName.setText(value.toString())
                                        }

                                        //make visible

                                        clLoggedOut.visibility = View.GONE
                                        clLoggedIn.visibility = View.VISIBLE

                                    } else {
                                        //get response of code 400 (sudah ga valid token)
                                        Log.d("testeric", response.toString())
                                        val editor = sp?.edit()
                                        editor?.remove("token")
                                        editor?.remove("name")
                                        editor?.apply()
//                                        Toast.makeText(getActivity(), "Session Expired, Please Log In!", Toast.LENGTH_SHORT).show()
                                        clLoggedIn.visibility = View.GONE
                                        clLoggedOut.visibility = View.VISIBLE
                                        // handle error
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    // not getting response back
                                    Log.d("testeric", "gagal")
                                    Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT).show()
                                    clLoggedIn.visibility = View.GONE
                                    clLoggedOut.visibility = View.VISIBLE

                                }
                            })

                            //clear
                            username.text.clear()
                            password.text.clear()

                            clLoggedOut.visibility = View.GONE
                            clLoggedIn.visibility = View.VISIBLE
                        }

                    } else {
                        //get response of code 400 (password salah)
                        Log.d("testeric", response.toString())
                        Toast.makeText(
                            getActivity(),
                            "Wrong Username or Password!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // handle error
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // not getting response back
                    Log.d("testeric", "gagal")
                    Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT)
                        .show()


                }
            })
        }

        cvLogOut.setOnClickListener{
            val builder = AlertDialog.Builder(getActivity())
            builder.setTitle("Confirm Log Out")
            builder.setMessage("Are you sure you want to log out?")

            builder.setPositiveButton("Log Out") { _, _ ->
                // log out the user
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.2.58:8000")
                    .build()
                val service = retrofit.create(API::class.java)

                // Make the API request and handle the response
                val call = service.logout("Bearer " + token)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val editor = sp?.edit()
                            editor?.remove("token")
                            editor?.remove("name")
                            editor?.apply()
                            //logged out
                            clLoggedIn.visibility = View.GONE
                            clLoggedOut.visibility = View.VISIBLE
                        } else {
                            //get response of code 400 (sudah ga valid)
                            Log.d("testeric", response.toString())
                            val editor = sp?.edit()
                            editor?.remove("token")
                            editor?.remove("name")
                            editor?.apply()
//                            Toast.makeText(getActivity(), "Session Expired, Please Log In!", Toast.LENGTH_SHORT).show()
                            clLoggedIn.visibility = View.GONE
                            clLoggedOut.visibility = View.VISIBLE
                            // handle error
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // not getting response back
                        Log.d("testeric", "gagal")
                        Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT).show()
//                    clLoggedIn.visibility = View.GONE
//                    clLoggedOut.visibility = View.VISIBLE

                    }
                })
//                val editor = sp?.edit()
//                editor?.remove("token")
//                editor?.remove("name")

//                val success = editor?.commit()
//                if (success == true) {
//                    // changes were persisted successfully
//                    clLoggedIn.visibility = View.GONE
//                    clLoggedOut.visibility = View.VISIBLE
//                } else {
//                    // an error occurred while saving the changes
//                    Toast.makeText(getActivity(), "Log Out Failed!", Toast.LENGTH_SHORT)
//                        .show()
//                }
            }

            builder.setNegativeButton("Cancel") { _, _ ->
                // do nothing
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}