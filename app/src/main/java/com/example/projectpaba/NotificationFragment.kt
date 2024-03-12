package com.example.projectpaba

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectpaba.notification.DataNotification
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : AdapterNotification
    private var arNotif = arrayListOf<DataNotification>()
    lateinit var db : FirebaseFirestore


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
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvNotification)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = AdapterNotification(arNotif)
        recyclerView.adapter = adapter



        if (arNotif.isEmpty()) {
            db = FirebaseFirestore.getInstance()
            val progressBar: ProgressBar = view.findViewById(R.id.progressBarNotification)

            // Show the progress bar when the data fetching starts
            progressBar.visibility = View.VISIBLE
            db.collection("notifications")
                .get()
                .addOnSuccessListener { result ->
//                    Toast.makeText(getActivity(), "load", Toast.LENGTH_SHORT).show()

                    var counter = 0
                    for (document in result) {
                        Log.d("lihatData", "${document.id} => ${document.data.get("title")}")
                        val data = DataNotification(counter++,
                            document.data.get("title").toString(),
                            document.data.get("message").toString(),
                            document.data.get("timestamp").toString(),
                        )
                        arNotif.add(data)
                    }
//                    var sortedArNews = arNotif.sortedByDescending { it.date }
//                    arNotif = sortedArNews.toMutableList() as ArrayList<DataNotification>
                    adapter.notifyDataSetChanged()


                    progressBar.visibility = View.GONE

                    Log.d("tambahData", arNotif.toString())
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
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarHome2)

        val wa = toolbar.findViewById<ImageView>(R.id.wa)
        wa.setOnClickListener {
            val url = "http://wa.me/6281231592369"
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}