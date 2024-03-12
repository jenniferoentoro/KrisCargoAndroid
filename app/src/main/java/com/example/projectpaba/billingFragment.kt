package com.example.projectpaba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [billingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class billingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        retainInstance = true

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_billing, container, false)

    }

//
//    private val pagerAdapter by lazy { MyFragmentPagerAdapter(childFragmentManager) }
//    class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//        override fun getItem(position: Int): Fragment {
//            return when (position) {
//                0 -> billingUnpaidFragment()
//                else -> billingHistoryFragment()
//            }
//        }
//
//        override fun getCount(): Int = 2
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return when (position) {
//                0 -> "Unpaid"
//                else -> "History"
//            }
//        }
//
//    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        var tablayout = view.findViewById<TabLayout>(R.id.tablayout)

        val adapter = billingadapter(childFragmentManager)
        viewPager.adapter = adapter
        tablayout.setupWithViewPager(viewPager)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment billingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            billingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}