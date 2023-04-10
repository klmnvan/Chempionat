package com.example.chempionat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.chempionat.R
import com.example.chempionat.databinding.ActivityOnBoardBinding
import com.example.chempionat.models.OnBoardModel
import java.lang.Math.abs
import java.util.LinkedList
import java.util.Queue

class OnBoard : AppCompatActivity(), GestureDetector.OnGestureListener {
    lateinit var binding: ActivityOnBoardBinding
    lateinit var queue: Queue<OnBoardModel>
    lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        gestureDetector = GestureDetector(this@OnBoard, this@OnBoard)
        queue = LinkedList(listOf(
            OnBoardModel("Анализы", "Экспресс сбор и получение проб", "Пропустить",
                getDrawable(R.drawable.icon_analizi)!!, getDrawable(R.drawable.point_style_blue)!!,
                getDrawable(R.drawable.point_style_stroke)!!,getDrawable(R.drawable.point_style_stroke)!!,),
            OnBoardModel("Уведомления", "Вы быстро узнаете о результатах", "Пропустить",
                getDrawable(R.drawable.icon_analizi)!!, getDrawable(R.drawable.point_style_stroke)!!,
                getDrawable(R.drawable.point_style_blue)!!,getDrawable(R.drawable.point_style_stroke)!!,),
            OnBoardModel("Мониторинг", "Наши врачи всегда наблюдают за вашими показателями здоровья", "Завершить",
                getDrawable(R.drawable.icon_analizi)!!, getDrawable(R.drawable.point_style_stroke)!!,
                getDrawable(R.drawable.point_style_stroke)!!,getDrawable(R.drawable.point_style_blue)!!,)
        ))
        enterOnBoard(queue.poll())
        init()
    }

    fun init(){
        binding.textTopscreen.setOnClickListener(){
            startActivity(Intent(this@OnBoard, InputRegist::class.java))
            finish()
        }
    }

    fun enterOnBoard(onBoardModel: OnBoardModel){
        with(binding){
            textName.text = onBoardModel.title
            textDes.text = onBoardModel.description
            textTopscreen.text = onBoardModel.button
            picture.setImageDrawable(onBoardModel.image)
            pointFirst.setImageDrawable(onBoardModel.p1)
            pointSecond.setImageDrawable(onBoardModel.p2)
            pointThird.setImageDrawable(onBoardModel.p3)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if(gestureDetector.onTouchEvent(event!!)){
            true
        }
        else {
            return super.onTouchEvent(event)
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        var diffX = e2.x - e1.x
        var diffY = e2.y - e1.y
        if(abs(diffX)>abs(diffY)){
            if(abs(diffX) > 100 && abs(velocityX) > 100){
                if(diffX < 0){
                    if(queue.size != 0){
                        enterOnBoard(queue.poll())
                    }
                }
            }
        }
        return true
    }
}