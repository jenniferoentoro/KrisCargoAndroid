package com.example.projectpaba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.content.Context
import android.widget.TextView

class ExpandableListViewAdapter internal constructor(private val context: Context, private val statusList:List<String>,private val statusisi: HashMap<String,List<String>>):
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return statusList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.statusisi[this.statusList[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return statusList[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.statusisi[this.statusList[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
       return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        var statusTitle = getGroup(p0) as String
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.statuslist,null)
        }

        val isiStatus = convertView!!.findViewById<TextView>(R.id.status)
        isiStatus.setText(statusTitle)
        return convertView


    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        var statusisi = getChild(p0,p1) as String
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.statusisilist,null)
        }

        val isiStatus = convertView!!.findViewById<TextView>(R.id.statusisi)
        isiStatus.setText(statusisi)
        return convertView


    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}



