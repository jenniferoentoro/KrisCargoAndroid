package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllNewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var db : FirebaseFirestore

    private lateinit var adapter : AdapterAllNews
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
        val view = inflater.inflate(R.layout.fragment_all_news, container, false)



        val recyclerView: RecyclerView = view.findViewById(R.id.rvNotification)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = AdapterAllNews(arNews)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterAllNews.OnItemClickCallBack {
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
            val progressBar: ProgressBar = view.findViewById(R.id.progressBarNotification)

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllNewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}