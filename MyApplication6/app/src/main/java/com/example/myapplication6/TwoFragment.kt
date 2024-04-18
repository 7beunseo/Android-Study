package com.example.myapplication6

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication6.databinding.FragmentTwoBinding
import com.example.myapplication6.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

/**
 * 수정될 수 있는 부분
 * ItemRecyclerviewBinding : xml 파일 이름
 * MyAdapter(val datas: MutableList<String>) : 화면에 표현하고자 하는 데이터
 * onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) : 데이터 넣기
 */
// 화면을 가지고 오는 레이아웃을 가지고 오는 클래스임 -> 레이아웃을 가지고 올때는 binding을 쓴다!!
// MyViewHolder 클래스를 이용하면 item_recycler view에 있는 레이아웃을 가지고 와서 Fragment나 Layout에서 binding 변수처럼 사용했던 것을 사용할 수 있다
// 뷰에서 adapter를 직접 다루고 있지 않음 -> 별도로 클래스를 만들어 주어야 함
class MyViewHolder(val bindding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(bindding.root) { } // item_recyclerview를 가져옴, binding.root를 리턴함

// RecyclerView가 제공하는 Adapter 클래스를 상속받는 것임 -> () 표시 해주기
// 전달받는 데이터가 String 하나만은 아닐 것임 => 각각의 항목들의 list를 표현해주는 것
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // adapter를 생성할 때 Recyclerview에 있는 ViewAdapter로 만들겠다 지정해줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder { // 이 항목은 어떤 레이아웃을 이용할 것인가?
        // 별도의 클래스 이용
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) // 형식 정해져 있음
    }

    override fun getItemCount(): Int { // 뷰 홀더에서 담고 있는 아이템의 개수가 몇개인가? - 잔딜빋은 데이터 리스트의 길이
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { // holder: MyViewHolder 의미, position: int -> 항목들이 쭉 나열된다
        // 각각의 항목들에 대해서 어떻게 데이터를 집어넣을 것인가?
        // 전달받은  datas와 ItemRecyclerviewBinding 화면을 연결해주는 작업이다
        val binding = (holder as MyViewHolder).bindding // MyViewHolder로 다운캐스팅 필요 -> binding에는 item_recyclerview가 들어가게 된다
        binding.itemData.text = datas[position]
    }

}

class MyItemDecoration(val context: Context): RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 그림 -> 항목
        super.onDraw(c, parent, state)
        // ImageView를 사용하지 않고 화면에 그림
        // 코틀린에서 사진을 가져와 화면에 뿌리는 방법
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 0f, 0f, null) // 화면 어디에 배치할 것인가?
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 항목 -> 그림
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 500f, 500f, null)
    }

    override fun getItemOffsets( // 전체 화면이 아닌 각각의 아이템 화면을 꾸며줌
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State // 임포트 rect, view, parent 매개변수로 선택해주어야 함
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        view.setBackgroundColor(Color.parseColor("#ffdddd"))
    }

}
class TwoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        // recycler view에 들어갈 String 리스트 또는 배열 만들어주기
        // 항목들이 추가될 수도 있고 줄어들 수도 있다 -> mutable
        val datas = mutableListOf<String>()
        for(i in 1 .. 10) { // 1 부터 10까지
            datas.add("item $i")
        }

        // adapter & view holder
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // 기존에 만든 datas로 recycler view를 만들겠다

        // layoutManager
        val linearLayout = LinearLayoutManager(activity)
        /**
         * linearLayout의 값을 설정하여 넣을 수 있음
         */
        /*
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = linearLayout
        */

        var gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridLayout

        // 선택적
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        binding.mainFab.setOnClickListener{
            datas.add("Add Item") // 데이터를 변경하기 위해서는 RecyclerView가 참조하고 있는 데이터를 볁경해야 함
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}