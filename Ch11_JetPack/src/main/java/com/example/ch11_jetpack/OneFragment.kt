package com.example.ch11_jetpack

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch11_jetpack.databinding.FragmentOneBinding
import com.example.ch11_jetpack.databinding.ItemMainBinding

class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 항목의 뷰를 가지는 뷰 홀더 객체 준비, 반환된 뷰 홀더 객체는 onBindViewHolder 함수의 매개변수로 전달됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 뷰 홀더의 데이터 출력
        val binding = (holder as MyViewHolder).binding
        binding.tvItem.text = datas[position]
        binding.itemRoot.setOnClickListener {
            Log.d("item number : ", "$position")
        }
    }

    // 항목 개수를 판단, 이 함수가 반환한 숫자만큼 onBindViewHolder 함수가 호출됨
    override fun getItemCount(): Int = datas.size

}

class RecyclerViewDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    // 항목이 배치되기 전에 호출
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // recyclerview 의 background image 지정
//        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 0f, 0f, null)
    }

    // 항목이 모두 배치된 후 호출
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 뷰 크기 계산
        val width = parent.width
        val height = parent.height

        // 이미지 크기 계산
        val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 이미지가 그려질 위치 계산
        val left = width / 2 - drWidth?.div(2) as Int
        val top = height / 2 - drHeight?.div(2) as Int

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    // 개별 항목 꾸밀 때 호출 → 각 항목의 여백 설정 및 바탕색 설정
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1
        if (index % 3 == 0) {
            // index 가 3, 6, 9 ... 인 경우 → 3번째, 6번째, 9번째 항목 ( 1을 더했음 )
            outRect.set(10, 10, 10, 150)
        } else {
            outRect.set(10, 10, 10, 0)
        }
        view.setBackgroundColor(Color.CYAN)
        ViewCompat.setElevation(view, 20.0f)
    }
}

class OneFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        // 여기 사이에 작성하면 된다 (실습 책 내용 기준)
        val datas = mutableListOf<String>()
        for (i in 1..10) {
            datas.add("Item $i")
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(activity) // 매개변수 = context
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(RecyclerViewDecoration(activity as Context)) // 매개변수 = context
        return binding.root
    }
}