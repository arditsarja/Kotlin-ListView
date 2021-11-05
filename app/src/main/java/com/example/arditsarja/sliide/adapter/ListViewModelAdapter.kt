package com.example.arditsarja.sliide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.arditsarja.sliide.R
import com.example.arditsarja.sliide.user.User

class ListViewModelAdapter(val context: Context, val listModelArrayList: ArrayList<User>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.list_view_item, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.name.text = listModelArrayList[position].name
        vh.email.text = listModelArrayList[position].email
        vh.gender.text = listModelArrayList[position].gender
        vh.status.text = listModelArrayList[position].status
        vh.userId.text = listModelArrayList[position].id.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return listModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listModelArrayList.size
    }
}

private class ViewHolder(view: View?) {
    val name: TextView = view?.findViewById<TextView>(R.id.name) as TextView
    val email: TextView = view?.findViewById<TextView>(R.id.email) as TextView
    val gender: TextView = view?.findViewById<TextView>(R.id.gender) as TextView
    val status: TextView = view?.findViewById<TextView>(R.id.status) as TextView
    val userId: TextView = view?.findViewById<TextView>(R.id.userId) as TextView
}