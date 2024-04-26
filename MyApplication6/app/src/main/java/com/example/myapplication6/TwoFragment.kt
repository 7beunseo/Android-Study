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
import androidx.recyclerview.widget.DividerItemDecoration
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
 * 수정될 수 있는 부분
 * ItemRecyclerviewBinding : xml 파일 이름
 * MyAdapter(val datas: MutableList<String>) : 화면에 표현하고자 하는 데이터
 * onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) : 데이터 넣기
 */


// 화면을 가지고 오는 레이아웃을 가지고 오는 클래스임 -> 레이아웃을 가지고 올때는 binding을 쓴다!!
// 뷰에서 adapter를 직접 다루고 있지 않음 -> 별도로 클래스를 만들어 주어야 함
/**
 * Viewholder 클래스 생성 (2)
 * item_recycler view에 있는 레이아웃을 가져와 binding 변수처럼 사용할 수 있게 해줌
 * item_recycler 말고 다른 리사이클러 뷰를 지정할 수 있음 !
 */
class MyViewHolder(val bindding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(bindding.root) { } // item_recyclerview를 가져옴, binding.root를 리턴함

// RecyclerView가 제공하는 Adapter 클래스를 상속받는 것임 -> () 표시 해주기
// 전달받는 데이터가 String 하나만은 아닐 것임 => 각각의 항목들의 list를 표현해주는 것
/**
 * adapter 클래스 생성 (1)
 */
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // adapter를 생성할 때 Recyclerview에 있는 ViewAdapter로 만들겠다 지정해줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder { // 이 항목은 어떤 레이아웃을 이용할 것인가?
        // 별도의 클래스 이용
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) // 형식 정해져 있음
    }

    override fun getItemCount(): Int { // 뷰 홀더에서 담고 있는 아이템의 개수가 몇개인가? - 잔딜빋은 데이터 리스트의 길이
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { // holder: MyViewHolder 의미, position: int -> 항목들이 쭉 나열된다
        val binding = (holder as MyViewHolder).bindding // MyViewHolder로 다운캐스팅 필요 -> binding에는 item_recyclerview가 들어가게 된다
        /**
         * 데이터를 가지고 view에 어떻게 넣을 것인지를 정의함
         */
        binding.itemData.text = datas[position]
    }

}

class MyItemDecoration(val context: Context): RecyclerView.ItemDecoration() {
    /**
     * 그림을 그린 후 항목을 씀 -> 그림이 항목에 가려짐
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // ImageView를 사용하지 않고 화면에 그림
        // 코틀린에서 사진을 가져와 화면에 뿌리는 방법
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 0f, 0f, null) // 화면 어디에 배치할 것인가?
    }

    /**
     * 항목을 쓴 후 그림을 그림 -> 항목이 그림에 가려짐
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 500f, 500f, null)
    }

    /**
     * 각각의 아이템 화면을 꾸밈
     */
    override fun getItemOffsets(
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

        /**
         * recycler view에 들어갈 데이터 구성 (1)
         * -> 변경될 수 있으므로 mutableListOf<String>()으로 지정해줌
         */
        val datas = mutableListOf<String>()
        for(i in 1 .. 10) { // 1 부터 10까지
            datas.add("item $i")
        }

        /**
         * adapter & view holder
         */
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // 기존에 만든 datas로 recycler view를 만들겠다

        /**
         * layoutManager
         */

        /**
         * LinearLayout 설정하는 법
         */
        /*
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = linearLayout
        */

        /**
         * GridLayout 설정하는 법
         * activity, 정렬, 정렬위치, false
         */
        /*
        - 항목을 가로로 배치
        val layoutManager = GridLayoutManager(activity, 3(행의 수), GridLaoutManager.HORIZENTAL, false)
        -  그리드에서 항목을 오른쪽부터 배치
        val layoutManager = GridLayoutManager(activity, 3, ridLaoutManager.HORIZENTAL, true) -> true로 설정해주면 됨
         */
        // vertical로 배치해야 왼쪽 -> 오른쪽으로 이동하게 됨!
        var gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridLayout

        // 선택적
        binding.recyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        // binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        binding.mainFab.setOnClickListener{
            datas.add("Add Item") // 데이터를 변경하기 위해서는 RecyclerView가 참조하고 있는 데이터를 볁경해야 함
            /**
             * 데이터 변경 시 작성
             */
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