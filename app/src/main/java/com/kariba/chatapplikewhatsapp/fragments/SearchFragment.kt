package com.kariba.chatapplikewhatsapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kariba.chatapplikewhatsapp.R
import com.kariba.chatapplikewhatsapp.adapterclasses.UserAdapter
import com.kariba.chatapplikewhatsapp.modelclasses.Users
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var userAdapter : UserAdapter? = null
    private var mUsers : List<Users>? = null
    private var recyclerView : RecyclerView? = null

    var editTextSearch : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val view : View =  inflater.inflate(R.layout.fragment_search, container, false)


        editTextSearch = view.findViewById(R.id.editText_search)

        recyclerView = view.findViewById(R.id.recyclerView_searchList)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        mUsers = ArrayList()
        retrieveAllUsrs()

        editTextSearch?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUsers(cs.toString().toLowerCase())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return view
    }

    private fun retrieveAllUsrs() {
        var firebaseUserId = FirebaseAuth.getInstance().currentUser?.uid

        var refUsers = FirebaseDatabase.getInstance().reference.child("Users")

        refUsers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshots: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                if(editTextSearch?.text.toString() == ""){
                    for(snapshot in snapshots.children){
                    val user : Users? = snapshot.getValue(Users::class.java)

                      if(!(user?.getUid()).equals(firebaseUserId)){
                        if (user != null) {
                            (mUsers as ArrayList<Users>).add(user)
                        }
                      }
                   }
                }
                userAdapter = context?.let { UserAdapter(it, mUsers as ArrayList<Users>, false) }
                recyclerView?.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun searchForUsers(str : String){
        var firebaseUserId = FirebaseAuth.getInstance().currentUser?.uid

        var queryUsers = FirebaseDatabase.getInstance().reference
            .child("Users").orderByChild("search")
            .startAt(str).endAt(str + "\uf8ff")

        queryUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshots: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()

                for(snapshot in snapshots.children){

                    val user : Users? = snapshot.getValue(Users::class.java)

                    if(!(user?.getUid()).equals(firebaseUserId)){
                        if (user != null) {
                            (mUsers as ArrayList<Users>).add(user)
                        }
                    }
                }

                userAdapter = context?.let { UserAdapter(it, mUsers as ArrayList<Users>, false) }
                recyclerView?.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}