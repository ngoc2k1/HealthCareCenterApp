package com.example.myapplication.user

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.toast
import java.net.URI
import java.util.ArrayList
import java.util.HashMap

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webSocketClient: ChatWebSocketClient
    private var listMess: ArrayList<HashMap<String, Any>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ChatActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val hashCodeChat = bundle.getString(Constant.CHAT)
            binding.apply {
                val roomId = "/room/123"
//            val roomId = "/room/$hashCodeChat"
                // load server url from strings.xml
                val serverUri = URI(getString(R.string.server_url) + roomId)
                webSocketClient = ChatWebSocketClient(serverUri) { message ->
                    // display incoming message in ListView
                    runOnUiThread {
                        if (hashCodeChat != null) {
                            run {
                                val _item = HashMap<String, Any>()
                                val first = message.split("*")[0]
                                val second = message.split("*")[1]
                                val third = message.split("*")[2]
                                var check = false
                                when (second) {
                                    hashCodeChat.split("*").get(1) -> {
                                        _item["message"] = "Tôi: $third"
                                        check = true
                                    }

                                    "nvtv" -> {
                                        if (hashCodeChat.split("*")
                                                .get(0) == first.split(": ")[1]
                                        ) {
                                            check = true
                                            _item["message"] = "Nhân viên tư vấn: $third"
                                        }
                                    }
                                    else -> {
                                    }
                                }
                                if (check) listMess.add(_item)
                            }
                        }
                        listview1.adapter = Listview1Adapter(listMess)
                    }
                }

                // connect to websocket server
                webSocketClient.connect()
                btnSend.setOnClickListener {
                    if (editMsg.text.toString().isNotBlank()) {
                        try {
                            // send message to websocket server
                            webSocketClient.sendMessage(hashCodeChat + "*" + editMsg.text.toString())
                            editMsg.setText("")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            toast(e.toString())
                        }
                    } else toast("Vui lòng nhập nội dung!")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // close websocket connection
        webSocketClient.close()
    }


    class Listview1Adapter(private val _data: ArrayList<HashMap<String, Any>>) : BaseAdapter() {
        override fun getCount(): Int {
            return _data.size
        }

        override fun getItem(_index: Int): HashMap<String, Any> {
            return _data[_index]
        }

        override fun getItemId(_index: Int): Long {
            return _index.toLong()
        }

        override fun getView(_position: Int, _v: View?, _container: ViewGroup?): View? {
            val _inflater = LayoutInflater.from(_container?.context)
            var _view = _v
            if (_view == null) {
                _view = _inflater.inflate(R.layout.item_chat, _container, false)
            }

            val text2 = _view?.findViewById<TextView>(R.id.text2)

            if (text2 != null) {
                text2.text = _data[_position]["message"].toString()
            }

            return _view
        }

    }

}