package com.example.kotlinchat



import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {

    private  var CHAT_NAME: String ?=null
    private  var USER_NAME: String ?=null
private var chat_send:Button?=null
    private  var chat_view: ListView ?=null
    private  var chat_edit: EditText?=null


    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // 위젯 ID 참조
        chat_view = findViewById<View>(R.id.chat_view) as ListView
        chat_edit = findViewById<View>(R.id.chat_edit) as EditText
        chat_send = findViewById<View>(R.id.chat_sent) as Button

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        val intent = intent
        CHAT_NAME = intent.getStringExtra("chatName")
        USER_NAME = intent.getStringExtra("userName")

        // 채팅 방 입장
        openChat(CHAT_NAME)

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send?.setOnClickListener(View.OnClickListener {
            if (chat_edit!!.text.toString() == "")
                return@OnClickListener

            val chat = ChatDTO(USER_NAME!!, chat_edit!!.text.toString()) //ChatDTO를 이용하여 데이터를 묶는다.
            databaseReference.child("chat").child(CHAT_NAME!!).push().setValue(chat) // 데이터 푸쉬
            chat_edit!!.setText("") //입력창 초기화
        })
    }

    private fun addMessage(dataSnapshot: DataSnapshot, adapter: ArrayAdapter<String>) {
        val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)
        adapter.add(chatDTO!!.getUserName() + " : " + chatDTO.getMessage())
    }

    private fun removeMessage(dataSnapshot: DataSnapshot, adapter: ArrayAdapter<String>) {
        val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)
        adapter.remove(chatDTO!!.getUserName() + " : " + chatDTO.getMessage())
    }

    private fun openChat(chatName: String?) {
        // 리스트 어댑터 생성 및 세팅
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
        chat_view?.adapter = adapter

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName!!).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                addMessage(dataSnapshot, adapter)
                Log.e("LOG", "s:" + prevChildKey!!)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, prevChildKey: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                removeMessage(dataSnapshot, adapter)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, prevChileKey: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}

