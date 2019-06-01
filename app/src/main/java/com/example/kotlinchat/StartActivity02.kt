package com.example.kotlinchat


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_start02.*

class StartActivity02 : AppCompatActivity() {

    var user_chat: EditText ?=null
     var user_edit: EditText ?= null

      var chat_list: ListView?=null

     private val firebaseDatabase = FirebaseDatabase.getInstance()
    
    private val databaseReference = firebaseDatabase.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start02)



        user_next?.setOnClickListener(View.OnClickListener {
            if (user_edit?.text.toString() == "" || user_chat?.text.toString() == "")

return@OnClickListener
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("chatName", user_chat?.text.toString())
            intent.putExtra("userName", user_edit?.text.toString())
            startActivity(intent)

        })

        showChatList()

    }

     fun showChatList() {
        // 리스트 어댑터 생성 및 세팅
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
        chat_list?.adapter = adapter

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.key!!)
                adapter.add(dataSnapshot.key)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }
    
}
