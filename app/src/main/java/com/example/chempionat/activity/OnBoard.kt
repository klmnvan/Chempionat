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
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gestureDetector = GestureDetector(this@OnBoard, this@OnBoard)
        queue =
            LinkedList(listOf(
                OnBoardModel("Пропустить","Анализы", "Экспресс сбор и получение проб",
                    getDrawable(R.drawable.icon_analizi)!!, getDrawable(R.drawable.point_style_blue)!!,
                    getDrawable(R.drawable.point_style_stroke)!!, getDrawable(R.drawable.point_style_stroke)!!),
                OnBoardModel("Пропустить","Уведомления", "Вы быстро узнаете о результатах",
                    getDrawable(R.drawable.icon_yved)!!, getDrawable(R.drawable.point_style_stroke)!!,
                    getDrawable(R.drawable.point_style_blue)!!, getDrawable(R.drawable.point_style_stroke)!!),
                OnBoardModel("Завершить","Мониторинг", "Наши врачи всегда наблюдают за вашими показателями здоровья",
                    getDrawable(R.drawable.icon_monitor)!!, getDrawable(R.drawable.point_style_stroke)!!,
                    getDrawable(R.drawable.point_style_stroke)!!, getDrawable(R.drawable.point_style_blue)!!),
            ))
        enterOnBoard(queue.poll())
        init()

    }

    fun init(){
        with(binding!!){
            textTopscreen.setOnClickListener(){
                startActivity(Intent(this@OnBoard, InputRegist::class.java))
                finish()
            }
        }
    }

    /**
     * Отдельный метод, с помощью которого мы берем данные из заполенного листа (LinkedList)
     * и помещаем их соответствующий объект в layout
     */
    private fun enterOnBoard(onBoardModel: OnBoardModel){
        with(binding){
            textTopscreen.text = onBoardModel.button
            textName.text = onBoardModel.title
            textDes.text = onBoardModel.description
            picture.setImageDrawable(onBoardModel.image)
            pointFirst.setImageDrawable(onBoardModel.p1)
            pointSecond.setImageDrawable(onBoardModel.p2)
            pointThird.setImageDrawable(onBoardModel.p3)
            MainActivity.goActicity.bool = true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if(gestureDetector.onTouchEvent(event!!)){
            true
        }else{
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
        val diffY = e2.y - e1.y
        val diffX = e2.x - e1.x
        if(abs(diffX)>abs(diffY)){
            if(abs(diffX)>100 && abs(velocityX) > 100){
                if(diffX<0){
                    if(queue.size != 0){
                        enterOnBoard(queue.poll())
                    }
                }
            }
        }
        return true
    }
}