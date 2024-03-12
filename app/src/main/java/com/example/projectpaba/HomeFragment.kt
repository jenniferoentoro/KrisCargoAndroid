package com.example.projectpaba

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar

import android.widget.Button
import android.widget.LinearLayout

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import de.hdodenhof.circleimageview.CircleImageView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toolbar : Toolbar
    lateinit var db : FirebaseFirestore

    private lateinit var adapter : AdapterNews
    private var arNews = arrayListOf<DataNews>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileNameHome)
        val flProfileNameHome = view.findViewById<FrameLayout>(R.id.flProfileNameHome)
        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)

        val token = sp?.getString("token",null)

        Log.d("test2",sp?.getString("user_id",null).toString())

        if (token == null) {

            //munculin login page
            flProfileNameHome.visibility = View.GONE
            Log.d("test2","gada")

        } else {
            //set name
            val name = sp?.getString("name", null)
            tvProfileName.setText(name.toString())
            flProfileNameHome.visibility = View.VISIBLE


            //cek kalo token masih valid
//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://192.168.2.231:8000")
//                .build()
//            val service = retrofit.create(API::class.java)
//
//            // Make the API request and handle the response
//            val call = service.getProfile("Bearer " + token)
//            call.enqueue(object : Callback<ResponseBody> {
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
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
//                            val name = sp?.getString("name", null)
//                            if (name.isNullOrEmpty() && editor != null) {
//                                editor.putString("name", value)
//                                editor.apply()
//                            }
//                            tvProfileName.setText(value.toString())
//                        }
//
//                        //make visible
//                        //refresh token nya
//
//
//
//                    } else {
//                        //get response of code 400 (sudah ga valid)
//                        Log.d("testeric", response.toString())
//                        val editor = sp?.edit()
//                        editor?.remove("token")
//                        editor?.remove("name")
//                        editor?.apply()
//                        Toast.makeText(
//                            getActivity(),
//                            "Session Expired, Please Log In!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        flProfileNameHome.visibility = View.VISIBLE
//
//                        // handle error
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    // not getting response back
//                    Log.d("testeric", "gagal")
//                    Toast.makeText(getActivity(), "Unable to get response!", Toast.LENGTH_SHORT)
//                        .show()
////                    clLoggedIn.visibility = View.GONE
////                    clLoggedOut.visibility = View.VISIBLE
//
//                }
//            })

        }

        // Bind the toolbar defined in the layout

        val recyclerView: RecyclerView = view.findViewById(R.id.rvNews)
        toolbar = view.findViewById(R.id.toolbarHome)

        val wa = toolbar.findViewById<ImageView>(R.id.wa)
        wa.setOnClickListener {

            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)


        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter = AdapterNews(arNews)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterNews.OnItemClickCallBack {
            override fun onItemClicked(data: DataNews) {
                val mBundle = Bundle()
                mBundle.putString("DATAFOTO", data.foto)
                mBundle.putString("DATATITLE", data.title)
                mBundle.putString("DATADESCRIPTION", data.description)
                mBundle.putString("DATADATE", data.date)

                val fragDetNews = NewsDetailFragment()
                fragDetNews.arguments = mBundle

                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, fragDetNews, NewsDetailFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        })



        if (arNews.isEmpty()) {
            db = FirebaseFirestore.getInstance()
            val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)

            // Show the progress bar when the data fetching starts
            progressBar.visibility = View.VISIBLE
            db.collection("news")
                .get()
                .addOnSuccessListener { result ->
//                    Toast.makeText(getActivity(), "load", Toast.LENGTH_SHORT).show()

                    var counter = 0
                    for (document in result) {
                        Log.d("lihatData", "${document.id} => ${document.data.get("title")}")
                        val data = DataNews(counter++,
                            document.data.get("title").toString(),
                            document.data.get("url_gambar").toString(),
                            document.data.get("description").toString(),
                            document.data.get("tanggal").toString()
                        )

                        arNews.add(data)

                    }
//                    var sortedArNews = arNews.sortedByDescending { it.date }
//                    arNews = sortedArNews.toMutableList() as ArrayList<DataNews>
                    adapter.notifyDataSetChanged()


                    progressBar.visibility = View.GONE

                    Log.d("tambahData", arNews.size.toString())
                }
                .addOnFailureListener { exception ->
                    progressBar.visibility = View.GONE

                    Log.w("TAGERROR", "Error getting documents.", exception)
                }
                .addOnCompleteListener {

                }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)
        val token = sp?.getString("token", null)


//        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
//        toolbarTitle.text = "Home"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val btn_FLC = view.findViewById<LinearLayout>(R.id.btnFCL)
        btn_FLC.setOnClickListener {

            val flcFrag = FLCFragment()
            val mFragmentManager = parentFragmentManager
            if (token.isNullOrEmpty()) {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,ProfileFragment(),ProfileFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,flcFrag,FLCFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

            
        }

        val btnOrder = view.findViewById<LinearLayout>(R.id.btnOrder)
        btnOrder.setOnClickListener {
            val flcFrag = LCLFragment()
            val mFragmentManager = parentFragmentManager
            if (token.isNullOrEmpty()) {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,ProfileFragment(),ProfileFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,OrderFragment(),OrderFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

        val btn_LCL = view.findViewById<LinearLayout>(R.id.btnLCL)
        btn_LCL.setOnClickListener {
            val flcFrag = LCLFragment()
            val mFragmentManager = parentFragmentManager
            if (token.isNullOrEmpty()) {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,ProfileFragment(),ProfileFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,flcFrag,LCLFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        val btn_PL = view.findViewById<LinearLayout>(R.id.btnPriceList)
        btn_PL.setOnClickListener {
            val PLFrag = pricelist()
            val mFragmentManager = parentFragmentManager
            if (token.isNullOrEmpty()) {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,ProfileFragment(),ProfileFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,PLFrag,pricelist::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

        }

        val btn_bill = view.findViewById<LinearLayout>(R.id.btnBills)
        btn_bill.setOnClickListener {
            val BLFrag = billingFragment()
            val mFragmentManager = parentFragmentManager
            if (token.isNullOrEmpty()) {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,ProfileFragment(),ProfileFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout,BLFrag,billingFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

        }

        val btn_gallery = view.findViewById<LinearLayout>(R.id.btnGallery)
        btn_gallery.setOnClickListener {
            val galleryFrag = GalleryFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,galleryFrag,GalleryFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

        }

        val btn_about = view.findViewById<LinearLayout>(R.id.btnAbout)
        btn_about.setOnClickListener {
            val aboutFragment = AboutFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,aboutFragment,AboutFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

        }



        val ivToAllNews = view.findViewById<ImageView>(R.id.ivToAllNews)
        ivToAllNews.setOnClickListener{
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, AllNewsFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}