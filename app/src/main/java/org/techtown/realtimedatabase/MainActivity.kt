package org.techtown.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG : String = "jeongmin"

    private val database = Firebase.database
    private val myRef = database.getReference("practice")

    private val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    private val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    private val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onLoadDatabase()

        rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter
    }
/* 일반 함수 */
    // realtime database 값 읽어오기
    private fun onLoadDatabase() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                val test = snapshot.child("dic")
                for(ds in test.children) {
                    Log.d(TAG, "저장된 값 : ${ds.toString()}")
                    val item = ListLayout(ds.key.toString(), ds.value.toString())
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged() // 리사이클러 뷰 갱신
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "읽어오지 못함")
            }
        })
    }
/* onClick 함수 */
    // 메모 단어와 뜻 추가하는 기능 구현
    fun onClickAddMemo(view: View) {
        Log.d(TAG, "Click Add Memo btn")

        onLoadDatabase()

        myRef.child("dic").child("${et1.text.toString()}").setValue(et2.text.toString())
    }
}