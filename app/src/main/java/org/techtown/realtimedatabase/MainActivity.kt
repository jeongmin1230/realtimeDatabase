package org.techtown.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG : String = "jeongmin"

    private val database = Firebase.database
    private val myRef = database.getReference("practice")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
/* onClick 함수 */
    // 메모 단어와 뜻 추가하는 기능 구현
    fun onClickAddMemo(view: View) {
        Log.d(TAG, "Click Add Memo btn")

        myRef.child("dic").child("${et1.text.toString()}").setValue(et2.text.toString())
    }
}