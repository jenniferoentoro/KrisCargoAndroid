package com.example.projectpaba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GalleryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GalleryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarNotification)
        val _title: ArrayList<String> = arrayListOf()
        val _foto: ArrayList<String> = arrayListOf()
        val _folder1: ArrayList<Int> = arrayListOf()
        val _folder2: ArrayList<Int> = arrayListOf()
        val _folder3: ArrayList<Int> = arrayListOf()
        val _index: ArrayList<Int> = arrayListOf()
        val _id: ArrayList<Int> = arrayListOf()

        val _kategori: ArrayList<String> = arrayListOf()
        val _isi: ArrayList<String> = arrayListOf()

        val arGal = arrayListOf<dataGallery>()

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val _rvGal : RecyclerView = view.findViewById(R.id.rvGallery)
        progressBar.visibility = View.VISIBLE
        db.collection("gallery")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("galleryData", "${document.id} => ${document.data.get("path")}")
                    _title.add("${document.data.get("title")}")
                    if ("${document.data.get("folder_id")}" == "0"){
                        _folder1.add(_title.size - 1)
                    }
                    else if ("${document.data.get("folder_id")}" == "1"){
                        _folder2.add(_title.size - 1)
                    }
                    else if ("${document.data.get("folder_id")}" == "2"){
                        _folder3.add(_title.size - 1)
                    }
                    _id.add(Integer.parseInt("${document.data.get("folder_id")}"))
                    _foto.add("${document.data.get("path")}").toString()
                    _kategori.add("${document.data.get("kategori")}")
                    _isi.add("${document.data.get("isi")}")
                }
                _index.add(_folder1[0])
                _index.add(_folder2[0])
                _index.add(_folder3[0])

//                Log.d("tambahData", _title.size.toString())
                for (position in _index){
                    val data = dataGallery(
                        _title[position],
                        _foto[position],
                        _isi[position],
                        _kategori[position],
                        _id[position]
                    )
                    Log.d("cobaaa", _foto[position])
                    arGal.add(data)
                }
                _rvGal.layoutManager = GridLayoutManager(context, 2)

                val adapterP = adapterImage(arGal)
                _rvGal.adapter = adapterP
                adapterP.setOnItemClickCallback(object : adapterImage.OnItemClickCallback{
                    override fun onItemClicked(data: dataGallery) {
                        val mBundle = Bundle()
                        mBundle.putInt("DATAFOLDER", data.folder_id)

                        val mfDua = FolderFragment()
                        mfDua.arguments = mBundle
                        val mFragmentManager = parentFragmentManager
                        mFragmentManager.beginTransaction().apply {
                            replace(R.id.frameLayout, mfDua, FolderFragment::class.java.simpleName)
                            addToBackStack(null)
                            commit()
                        }
                    }
                })
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                progressBar.visibility = View.GONE
                Log.w("TAGSUKSES2", "Error getting documents.", exception)
            }
    }

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
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GalleryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}