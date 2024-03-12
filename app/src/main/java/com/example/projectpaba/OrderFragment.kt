package com.example.projectpaba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_order, container, false)
    }
    lateinit var db : FirebaseFirestore
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //firebase
        db = FirebaseFirestore.getInstance()

        //edit text data

        val schedule = view.findViewById<Spinner>(R.id.schedule)
        val tipe = view.findViewById<Spinner>(R.id.tipe)
        val deskripsi = view.findViewById<EditText>(R.id.tgl_brg_received2)
        var arData = arrayListOf<dataScheduleFull>()
        db.collection("schedules").get()
            .addOnSuccessListener { result ->
            for (document in result){
                Log.d("fireee","xixi")
                val eta = "${document.data.get("etd")} - ${document.data.get("eta")}"
                arData.add(dataScheduleFull(document.data.get("title").toString(),document.data.get("kotaAsal").toString(),document.data.get("kotaTujuan").toString(),document.data.get("closeDate").toString(),document.data.get("duration").toString(),eta,document.data.get("id").toString().toInt()))
            }
                Log.d("fireee",arData.toString())

                var items2 = arrayListOf<String>()
                for (i in arData){
                    items2.add("${i.kotaAsal} - ${i.kotaTujuan} (${i.eta})")
                }

                val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items2)
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                schedule.adapter = adapter2

            }


        val items = listOf("FCL", "LCL")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipe.adapter = adapter




        val dimen_lebar = view.findViewById<EditText>(R.id.dimen_lebar)
        val dimen_panjang = view.findViewById<EditText>(R.id.dimen_panjang)
        val dimen_tinggi = view.findViewById<EditText>(R.id.dimen_tinggi)
        val qty = view.findViewById<EditText>(R.id.qty)
        val btn_order = view.findViewById<Button>(R.id.button2)

        val sp = activity?.getSharedPreferences("dataSP", AppCompatActivity.MODE_PRIVATE)
        var user_id = sp?.getString("user_id", null)
        val name = sp?.getString("name", null)
        var order_id = ""
        var invoice_id = "INV/"
        var container_id = "CON"
        var resi_id = "L"
        var brg_id = "BRG/"

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val jatuh_tempo = dateFormat.format(date)

        btn_order.setOnClickListener{
            order_id = generateUniqueNumber()
            invoice_id += generateUniqueNumber()
            container_id += generateUniqueNumber()
            resi_id += generateUniqueNumber()
            brg_id += generateUniqueNumber()

            val tipe_id = tipe.selectedItemPosition
            val schedule_id = arData[schedule.selectedItemPosition].id




            val s = SimpleDateFormat("dd-MM-yyyy")
            val format: String = s.format(Date())
            val dataOrde = dataOrder(order_id,"",format,user_id.toString().toInt(),name.toString())
            db.collection("order")
                .add(dataOrde)
                .addOnSuccessListener { documentReference ->
                    var dataInvoic = dataInvoice("0",invoice_id,"",format,jatuh_tempo,"0")
                    db.collection("invoice")
                        .add(dataInvoic)
                        .addOnSuccessListener { documentReference ->
                            var dataContain = DataContainer(invoice_id,container_id,order_id, "",resi_id, schedule_id, "0","","","", tipe_id.toString(),user_id.toString())

                            db.collection("container_load")
                                .add(dataContain)
                                .addOnSuccessListener { documentReference ->
                                    var dataDetBrg = DataBrg(deskripsi.text.toString(),dimen_lebar.text.toString().toDouble(),dimen_panjang.text.toString().toInt(),dimen_tinggi.text.toString().toInt(),brg_id,order_id,qty.text.toString().toInt())
                                    db.collection("detail_barang")
                                        .add(dataDetBrg)
                                        .addOnSuccessListener { documentReference ->
                                            var datBrgCon = DataBrgContainer(brg_id,qty.text.toString().toInt(),resi_id)
                                            db.collection("detail_barang_container_load")
                                                .add(datBrgCon)
                                                .addOnSuccessListener { documentReference ->
                                                    Toast.makeText(view.context,"Order berhasil!",Toast.LENGTH_SHORT).show()
                                                    deskripsi.text.clear()
                                                    dimen_lebar.text.clear()
                                                    dimen_panjang.text.clear()
                                                    dimen_tinggi.text.clear()
                                                    qty.text.clear()
                                                }
                                                .addOnFailureListener { e ->

                                                }

                                        }
                                        .addOnFailureListener { e ->

                                        }


                                }
                                .addOnFailureListener { e ->

                                }



                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(view.context,"Gagal!",Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(view.context,"Gagal!",Toast.LENGTH_SHORT).show()
                }
        }


    }

    fun generateUniqueNumber(): String {
        var uuid = UUID.randomUUID().toString()
        val shortenedUuid = uuid.substring(0, 8)
        return shortenedUuid
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}