package com.example.projectpaba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_news_detail, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            val foto = arguments?.getString("DATAFOTO")
            val title = arguments?.getString("DATATITLE")
            val description = arguments?.getString("DATADESCRIPTION")
            val date = arguments?.getString("DATADATE")



            val ivFoto = view?.findViewById<ImageView>(R.id.ivNewsDetFoto)
            val tvTitle = view?.findViewById<TextView>(R.id.tvNewsDetTitle)
            val tvDate = view?.findViewById<TextView>(R.id.tvNewsDetDate)
            val tvDescription = view?.findViewById<TextView>(R.id.tvNewsDetDescription)

            Picasso.get()
                .load(foto)
                .into(ivFoto)

            tvTitle?.setText(title.toString())
            tvDate?.setText(date.toString())
//            tvDate?.setText("")
            tvDescription?.setText(description.toString())

//            Toast.makeText(getActivity(), foto, Toast.LENGTH_SHORT).show()

        }
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}