package com.example.arditsarja.sliide

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.arditsarja.sliide.adapter.ListViewModelAdapter
import com.example.arditsarja.sliide.user.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.example.arditsarja.sliide.api.User as Api


class MainActivity : AppCompatActivity() {

    private val user by lazy { Api.create() }
    private var lastPage = 1
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listView = findViewById<ListView>(R.id.sample_listVw)

        getList()

        val addUser = findViewById<FloatingActionButton>(R.id.addUser)
        addUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        })


    }


    private fun loading(b: Boolean) {


        if (b)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun showDialog(title: String, id: Int) {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Toast.makeText(this, "Deleting " + id, Toast.LENGTH_LONG).show()
                    delete(id)
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

    }

    private fun delete(id: Int) {
        val kk = user.delete(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Toast.makeText(this, "user created with id " + result, Toast.LENGTH_LONG).show()
                            getList()
                        },
                        { error ->
                            Toast.makeText(this, "Error " + error.toString(), Toast.LENGTH_LONG).show()

                        })
    }

    private fun getList() {
        loading(true)
        val kk = user.search(lastPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            lastPage = result.meta.pagination.pages!!
                            val kkk = user.search(lastPage)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            { result ->
                                                var users = ArrayList<User>()

                                                for (user in result.data) {
                                                    users.add(0, user)

                                                }

                                                var listViewAdapter = ListViewModelAdapter(this, users)
                                                listView.adapter = listViewAdapter
                                                loading(false)
                                                listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                                                }
                                                listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, position, id ->

                                                    val t = view?.findViewById<TextView>(R.id.userId)
                                                    if (t != null) {

                                                        Log.v("Tag", "t" + t.text)
                                                        showDialog("", t.text.toString().toInt())
                                                    }

                                                    true
                                                }
                                            },
                                            { error ->
                                                loading(false)
                                            })

                        },
                        { error ->
                            loading(false)
                        })

    }
}
