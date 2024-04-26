# Introduce
* MyApplication3 : Layout
* MyApplication4 : Chronometer, OnKeyDown


# Index
* [Chronometer](#Chronometer)
* [Dialog](#Dialog)
* [ActionBar](#ActionBar)
* [Fragment](#Fragment)
* [RecyclerView](#RecyclerView)
* [TabLayout](#TabLayout)
* [NavigationView](#NavigationView)
* [Exam](#시험 대비)

# Chronometer

## onKeyDown
```kotlin
    var prevTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (SystemClock.elapsedRealtime() - prevTime > 3000) {
                    Toast.makeText(this, "종료하려면 한번 더 누르세요", Toast.LENGTH_LONG).show()
                    prevTime = SystemClock.elapsedRealtime() // 현재 시간을 넣음 
                    return true
                }
            }

             KeyEvent.KEYCODE_VOLUME_DOWN -> {
                 AlertDialog.Builder(this).run {
                     setTitle("볼륨업 버튼 눌림")
                     setPositiveButton("확인", null)
                     show()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
```
* keyCode : 눌린 키


# Dialog
## DatePickerDialog
```kotlin
        binding.btnDate.setOnClickListener {
            DatePickerDialog(
                this, object: DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        // Log.d("mobileapp", "${year}년 ${month+1}월 ${dayOfMonth}일 선택됨")
                        Toast.makeText(applicationContext, "${year}년 ${month+1}월 ${dayOfMonth}일 선택됨", Toast.LENGTH_LONG).show()
                        binding.btnDate.text = "${year}년 ${month+1}월 ${dayOfMonth}일"
                        binding.btnDate.setTextColor(Color.parseColor("#ff0000"))
                        binding.btnDate.textSize = 30f
                    }

                }, 2024, 3, 3
            ).show()
        }
```
* `DatePickerDialog()` 괄호 안에 보일 내용을 기술
* `this` : 자신을 호출하는 부모 지정
* `object: DatePickerDialog.OnClickListener` : 리스너 지정
* 2024, 3(month - 1), 4 : 초기 날짜 지정
*  `show()` 메서드를 호출하여 화면에 보여야 함


## DatePickerDialog
```kotlin
        binding.btnTime.setOnClickListener {
            TimePickerDialog(
                this, object: TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(
                        view: TimePicker?,
                        hourOfDay: Int,
                        minute: Int
                    ) {
                        Toast.makeText(applicationContext, "${hourOfDay}시 ${minute}분", Toast.LENGTH_LONG).show()
                        binding.btnTime.text = "${hourOfDay}시 ${minute}분"
                        binding.btnTime.setTextColor(Color.parseColor("#ff0000"))
                        binding.btnTime.textSize = 30f
                    }

                }, 3, 14, true
            ).show()
        }
```

## AlertDialog
```kotlin
        binding.alert.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창")
                setMessage("알림창입니다")
                setIcon(android.R.drawable.ic_dialog_alert)
                setPositiveButton("확인", null)
                setNegativeButton("취소", null)
                setNeutralButton("상세정보", null)
                show()
            }
        }
```
* `Builder`로 알림창을 만듦
* `setTitle(문자열)`
* `setMessage(문자열)`
* `setIcon(아이콘 경로)`
  * android.R -> android가 제공하는 resource를 사용하겠다
* `setPositiveButton(문자열, 이벤트 리스너)`
* `setNegativeButton문자열, 이벤트 리스너)`
* `setNeutralButton문자열, 이벤트 리스너)`
* `show()` : 화면에 보임 

### 이벤트 리스너 작성법
```kotlin
        val eventListener = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.d("mobileapp", "BUTTON_POSITIVE")
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Log.d("mobileapp", "BUTTON_NEGATIVE")
                    }
                }
            }
        }
```
* `DialogInterface`의 `OnClickListener` 구현


### items 리스트 중에서 선택할 수 있는 알림창 

🎀 `setItems()`
```kotlin
val itmes = arrayOf<String>("컴네", "컴구", "모앱", "데베")

setItems(itmes, object: DialogInterface.OnClickListener {
    override fun onClick(dialog: DialogInterface?, which: Int) {
        Toast.makeText(applicationContext, "${itmes[which]} 선택됨", Toast.LENGTH_LONG).show()
        binding.alertItem.text = "${itmes[which]} 선택됨"
    }
})
```
* `itmes` : 배열을 넣음
* `object: DialogInterface.OnClickListener` : 이벤트 리스너 구현
  * which 값은 선택한 아이템의 인덱스 번호 

🎀 `setSingleChoiceItems()`
```kotlin
setSingleChoiceItems(itmes, 2, object: DialogInterface.OnClickListener {
   override fun onClick(dialog: DialogInterface?, which: Int) {
       Toast.makeText(applicationContext, "${itmes[which]} 선택됨", Toast.LENGTH_LONG).show()
       selected = which
   }
})
```

🎀 `setMultiChoiceItems()`
```kotlin
setMultiChoiceItems(items, booleanArrayOf(true, false, false, true), object: DialogInterface.OnMultiChoiceClickListener {
       override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
       Toast.makeText(applicationContext, "${items[which]} ${if (isChecked) "선택됨" else "취소됨"}", Toast.LENGTH_LONG).show()
           // "${if (조건) "출력할 문자1" else "출력할 문자2"}"
           // 템플릿에서 if문 사용할 수 있음 
   }
})
```
* items : 배열
* booleanArrayOf() : 초기값 지정
* object: DialogInterface.OnMultiChoiceClickListener : 이벤트 리스너 구현
  * 선택되었는지, 해제되었는지 정보를 알 수 있는 isChecked 매개변수 추가됨 

🎀 `setView(커스텀 뷰)`
```kotlin
        // xml 파일을 부로 가져옴 (dialog_custom.xml)
        val customDialog = DialogCustomBinding.inflate(layoutInflater)

        binding.alertCustom.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 커스텀")
                setIcon(android.R.drawable.ic_dialog_alert)
                // dialog에 띄움
                setView(customDialog.root)
                setPositiveButton("확인", eventListener2)
                setNegativeButton("취소", eventListener)
                setNeutralButton("상세정보", eventListener)
                show()
            }
        }
```

* 커스텀 다이얼로그에서 이벤트 리스너 작성하는 법
```kotlin
        val eventListener3 = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    // 확인 버튼을 눌렀을 경우
                    DialogInterface.BUTTON_POSITIVE -> {
                        // 커스텀 다이얼로그 항목 중 어느 것이 선택되었는지 확인
                        if(customDialog.rbn1.isChecked) // 각각의 요소에 접근해서 선택 확인 
                            Toast.makeText(applicationContext, "${customDialog.rbn1.text.toString()}", Toast.LENGTH_LONG).show()

                        else if(customDialog.rbn2.isChecked)
                            Toast.makeText(applicationContext, "${customDialog.rbn2.text.toString()}", Toast.LENGTH_LONG).show()

                        else if(customDialog.rbn3.isChecked)
                            Toast.makeText(applicationContext, "${customDialog.rbn3.text.toString()}", Toast.LENGTH_LONG).show()

                        else if(customDialog.rbn4.isChecked)
                            Toast.makeText(applicationContext, "${customDialog.rbn4.text.toString()}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
```

# ActionBar
## 액션 바 출력 설정
```kotlin
// res/values/themes에서 NoActionBar 제거 
<style name="Base.Theme.Practice" parent="Theme.Material3.DayNight.NoActionBar">
```
![img.png](img/img.png)
* menu 라는 이름으로 menu 리소스 디렉터리 생성


## 메뉴 구성
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <!--showAsAction = true : 항상 화면에 보임 -->
    <!-- SearchView를 지정해줌으로서 화면에 띄울 수 있음 -->
    <item
        android:id="@+id/menu_search"
        android:title="search"
        app:actionViewClass="androidx.appcompat.widget.SearchView"
        app:showAsAction="always"
        />

    <!-- 메뉴 안쪽에 보임 -->
    <item
        android:id="@+id/item1"
        android:title="아이템1"/>
    <item
        android:id="@+id/item2"
        android:title="아이템2"/>
    <item
        android:id="@+id/item3"
        android:title="아이템3"/>
    <item
        android:id="@+id/item4"
        android:title="아이템4"/>

</menu>
```
![img_1.png](img/img_1.png)
* `onCreateOptionMenu`를 통해 메뉴 띄움
* `onOptionsItemSelected`를 통해 메뉴 선택 시 기능 수행 

### onCreateOptionsMenu
```kotlin
    // 아이템을 클릭하면 onOptionsItemSelected 함수가 자동으로 호출된다
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu) // res/menu/menu_navigation.xml 파일을 menu로 등록하겠다
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView // menu_navigation.xml 파일의 menu_search를 가져옴

        // 검색 관련 이벤트 리스너
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            // 검색 버튼 누를 경우 이벤트 리스너
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, "${query} 검색합니다.", Toast.LENGTH_LONG).show()
                return true
            }

            // 검색 중 이벤트 리스너
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
```

### onOptionsItemSelected
```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { // 선택된 아이템 비교
            R.id.item1 -> {
                binding.btnDate.setTextColor(Color.parseColor("#00FF00"))
            }
            R.id.item2 -> {

            }
            R.id.item3 -> {

            }
            R.id.item4 -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
```

#### 📌 뷰 전역변수로 선언해서 다른 함수에서 불러오는 방법
```kotlin
// 클래스 바로 아래에
lateinit var binding: ActivityMainBinding

// onCreate 함수 아래에
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
```

# Drawer
* Drawer 태그로 전체를 감싸줌 -> id 설정까지 진행해주기 
* 폰의 화면 크기에 맞춰 보여주기 위해 fitsSystemWindows true로 설정하기 
```xml
<androidx.drawerlayout.widget.DrawerLayout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity"

    android:id="@+id/drawer"
    android:fitsSystemWindows="true"
    >
    
</androidx.drawerlayout.widget.DrawerLayout>
```
```xml
<!-- drawer 가 출력될 자리 -->
<TextView
        android:layout_width="300dp"
        android:layout_height="match_parent"
        android:text="I am drawer"
        android:background="#ff0000"
        android:textColor="#ffffff"
        android:textSize="20dp"
        android:textStyle="bold"
        android:gravity="center"

        android:fitsSystemWindows="true"
        android:layout_gravity="start"
/>
```
* `android:fitsSystemWindows="true"`로 폰의 화면 크기에 맞춰서 보이도록 설정
* `android:layout_gravity="start"` 로 왼쪽에서 오른쪽으로 끌어올 수 있도록 함 

```kotlin
lateinit var toggle: ActionBarDrawerToggle // 초기 선언

// onStart 함수 내부에 아래 코드 작성 
// drawer와 toggle 연결 - 클릭했을 때 동작하는 부분은 빠져 있음
toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
toggle.syncState()

override fun onOptionsItemSelected(item: MenuItem): Boolean {
  if(toggle.onOptionsItemSelected(item)) { // 토굴이 클릭될때마다 이 함수가 실행됨
    return true // 토굴의 본래 기능을 실행해라
  }
  // 아래 코드 생략
  return super.onOptionsItemSelected(item)
}
```
* toggle = ActionBarDrawerToggle(`this`, `binding.drawer`, R.string.drawaer_opened, R.string.drawer_closed)
  

# Fragment
* build.graddle에서 `implementation("androidx.viewpager2:viewpager2:1.0.0")` 추가 
![img_2.png](img/img_2.png)
* Fragment와 xml 파일이 생성됨

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- 꼭 2로 임포트해야 함 -->
<androidx.viewpager2.widget.ViewPager2 xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/viewpaper"
    tools:context=".MainActivity">

    <!-- 내용 채우기 -->
</androidx.viewpager2.widget.ViewPager2>
```
* 생성된 xml 파일에서는 binding으로 접근할 수 있도록 루트 태그에 `id값을 부여`해야 함
* implement 한 `ViewPaper2`로 루트 바꿔주기 

```kotlin
    // Fragment.kt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        /**
         * view에서의 동작을 넣어줌 
         */
        return inflater.inflate(R.layout.fragment_one, container, false)
    }
```
* Fragment 파일의 onCreateView에 동작 정의
```kotlin
// MainActivity.kt

class MainActivity : AppCompatActivity() {
  lateinit var binding: ActivityMainBinding

  // FragmentStateAdapter를 상속받는 MyFragmentPagerAdapter 클래스를 생성함
  class MyFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    // 어떤 fragement를 다룰 것인지 변수를 선언해둠
    val fragments: List<Fragment>

    init {
      fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment()) // 3개의 Fragment를 리스트로 담고 있음
    }
    override fun getItemCount(): Int {
      return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
      return fragments[position]
    }

  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // setContentView(R.layout.activity_main)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.viewpaper.adapter = MyFragmentAdapter(this)
  }
}
```

## RecyclerView

```xml
<androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#00ff00"
        android:id="@+id/recyclerView"
        tools:context=".TwoFragment">

    </androidx.recyclerview.widget.RecyclerView>
```
* xml에 RecyclerView 추가
  * id로 RecyclerView 지정해주기


* 코틀린 파일에서 데이터 항목 리스트 생성 
  * ex ) `mutableListOf<String>` : 데이터가 변경되므로 mutable 
```kotlin
// adapter & view holder
// binding.recyclerView.adapter = MyAdapter(datas)
val adapter = MyAdapter(datas)
binding.recyclerView.adapter = adapter 
```
* xml의 recyclerView에 어뎁터를 연결
* MyAdapter라는 클래스를 새롭게 생성하고 인자로 어떤 내용이 반복되어 보일 것인지 지정해줌


```kotlin
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // adapter를 생성할 때 Recyclerview에 있는 ViewAdapter로 만들겠다 지정해줌
    // 이 항목은 어떤 레이아웃을 이용할 것인가에 관한 내용 
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder { 
        // 별도의 클래스 이용
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) // 형식 정해져 있음
    }

    override fun getItemCount(): Int { // 뷰 홀더에서 담고 있는 아이템의 개수가 몇개인가? - 잔딜빋은 데이터 리스트의 길이 리턴 
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { // holder: MyViewHolder 의미, position: int -> 항목들이 쭉 나열된다
        // 각각의 항목들에 대해서 어떻게 데이터를 집어넣을 것인가?
        // 전달받은  datas와 ItemRecyclerviewBinding 화면을 연결해주는 작업
        val binding = (holder as MyViewHolder).binding // MyViewHolder로 다운캐스팅 필요 -> binding에는 item_recyclerview가 들어가게 된다 -> 내부 id 변수처럼 사용 가능 
        binding.itemData.text = datas[position]
    }

}
```
* Holder 로 다운캐스팅해주어야 함 !!!!
 * RecyclerView가 제공하는 Adapter 클래스를 상속받는 것임 -> () 표시 해주기
 * adapter를 생성할 때 Recyclerview에 있는 ViewAdapter로 만들겠다 지정해줌


### onCreateViewHolder
* 뷰 홀더와 연결해주는 역할 -> 새로운 클래스를 생성해서 binding 전달해주어야 함 
* item_recyclerview에 있는 레이아웃을 가지고 와서 Fragment나 Layout에서 binding 변수처럼 사용했던 것을 사용할 수 있다
```kotlin
class MyViewHolder(val bindding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(bindding.root)
```
* 주의 : 꼭 var을 써주어야 한다! 

### onBindViewHolder
* 각각의 항목들에 대해서 어떻게 데이터를 집어넣을 것인가?
* 전달받은  datas와 ItemRecyclerviewBinding 화면을 연결해주는 작업
  * MyViewHolder로 다운캐스팅 필요 -> binding에는 item_recyclerview가 들어가게 된다
  * 지정한 id 값으로 뷰들을 사용할 수 있음 



### layoutManager
* 어뎁터 설정까지 완료하면 화면에 출력되기 위한 layoutManager을 설정해주어야 함
* binding.recyclerView.layoutManager = {생성한 레이아웃}

#### 🎀 LinearLayoutManager
```kotlin
val linearLayout = LinearLayoutManager(activity)
linearLayout.orientation = LinearLayoutManager.HORIZONTAL
binding.recyclerView.layoutManager = linearLayout
```
#### 🎀 GridLayoutManager
```kotlin
var gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
binding.recyclerView.layoutManager = gridLayout
```
* spanCount, orientation, reversLayou 매개변수 지정해주기 


## 수정될 수 있는 부분
* ItemRecyclerviewBinding : xml 파일 이름
* MyAdapter(val datas: MutableList<String>) : 화면에 표현하고자 하는 데이터
* onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) : 데이터 넣기



### Decorator 지정
```kotlin
binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))
```
* MyItemDecoration 클래스 생성해주기


```kotlin
class MyItemDecoration(val context: Context): RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 그림 -> 항목
        super.onDraw(c, parent, state)
        // ImageView를 사용하지 않고 화면에 그림
        // 코틀린에서 사진을 가져와 화면에 뿌리는 방법
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 0f, 0f, null) // 화면 어디에 배치할 것인가?
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 항목 -> 그림 (항목에 가려짐)
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
```

## FloatingButton 설정하기
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".TwoFragment">

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recyclerView"
        android:background="#00ff00" >
    </androidx.recyclerview.widget.RecyclerView>
  
    <com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
        android:id="@+id/main_fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="30dp"
        app:icon="@android:drawable/ic_input_add"
        android:text="Add Item"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"/>

</RelativeLayout>
```
* 전체 RelativeLayout으로 설정해주기 
* `ExtendedFloatingActionButton` 사용 
* id 부여하는 거 잊지 말기 

### 버튼 누르면 recyclerView 항목 추가
```kotlin
binding.mainFab.setOnClickListener{
            datas.add("Add Item") // 데이터를 변경하기 위해서는 RecyclerView가 참조하고 있는 데이터를 변경해야 함
            adapter.notifyDataSetChanged()
}
```

## TabLayout

* activity_main.xml 파일에 TabLayout 추가
* LinearLayout으로 감싸주기
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".MainActivity">

  <androidx.viewpager2.widget.ViewPager2
          xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:id="@+id/viewpager"
          android:layout_weight="1"
          tools:context=".MainActivity">
  </androidx.viewpager2.widget.ViewPager2>

  <com.google.android.material.tabs.TabLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:id="@+id/tabs"
  />

</LinearLayout> 
```
* ActivityMain.kt에 ViewPager와 TabLayout 연결
* layout_weight : TabLayout 먼저 배치하고 남은 공간을 사용하겠다는 뜻 
```kotlin
TabLayoutMediator(binding.tabs, binding.viewpager) { // tabs 와 viewpager을 연결하겠다
            tab, position ->
                tab.text = "TAB ${position + 1}" // 탭에 쓰여지는 글자를 정할 수 있음
        }.attach()
```

## NavigationView
* 드로어 화면 구성 
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#FF0000">
  <TextView
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="Navigation Title"
          android:textColor="@color/white"
          android:textStyle="bold"
          android:textSize="20dp"
          android:layout_alignParentBottom="true"
          android:layout_alignParentLeft="true"
          android:layout_marginBottom="30dp"
          android:layout_marginLeft="30dp"/>

</RelativeLayout> 
```
* 네비게이션을 위한 헤더 파일 생성해두기 
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:background="#FF0000">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Navigation Title"
        android:textColor="@color/white"
        android:textStyle="bold"
        android:textSize="20dp"
        android:layout_alignParentBottom="true"
        android:layout_alignParentLeft="true"
        android:layout_marginBottom="30dp"
        android:layout_marginLeft="30dp"/>

</RelativeLayout>
```
* activity_main.xml 파일에 드로어 헤더 지정해주기 
  * id 부여하기
  * headerLayout과 기존 만들었던 헤더 xml 파일 연결하기
  * menu 연결하기 

```xml
    <!-- 드로어 화면 -->
    <com.google.android.material.navigation.NavigationView
        android:layout_width="300dp"
        android:layout_height="match_parent"
        android:gravity="center"

        android:fitsSystemWindows="true"
        android:layout_gravity="start"
        android:id="@+id/mainDrawerView"
        app:headerLayout="@layout/navigation_header"
        app:menu="@menu/menu_navigation"
        />
```

* MainActivity의 onCreate 내부에 아래 코드 추가 
```kotlin
// 네비게이션 연결
binding.mainDrawerView.setNavigationItemSelectedListener(this)
```
* setNavigationItemSelectedListener를 쓰기 위해서는 상속받는 클래스가 하나 더 추가되어야 함
  * NavigationView.OnNavigationItemSelectedListener 상속시켜줌 

  * 상속받으면 에러 -> 오버라이드 진행하기 
```kotlin
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 전달받은 아이템의 아이디에 따라 처리하면 된다
        when(item.itemId) {
            R.id.item1 -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 1")
                // 함수 밖이므로 binding에 접근할 수 없음 -> 전역 변수로 선언
                true
            }
            R.id.item2 -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 2")
                true
            }
            R.id.item3 -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 3")
                true
            }
            R.id.item4 -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 4")
                true
            }
        }
        return false // 디폴트 false
    }
```
* 상속받은 함수로 이벤트 클릭 처리를 할 수 있게 됨 -> `onOptionsItemSelected`의 함수와 구현 똑같음 


## View
* AddActivity 추가
```kotlin
binding.btnMain.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java) // AddActivity의 intent를 부름
            startActivity(intent)
        }
```
* ActivitySecondBinding이 아니라 Activity의 이름을 그대로 적어줌 
* main에서 add를 부름
```kotlin
binding.btnAdd.setOnClickListener {
  finish() // 종료
  true
}
```
* app에서 main으로 돌아감 

## Activity 간 데이터 전달
* MainActivity에서 putExtra를 통해 addActivity에 데이터 전달 
```kotlin
binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java) // AddActivity의 intent를 부름

            /**
             * addActivity를 start 하기 전에 데이터 전달
             */
            // 오늘 날짜를 가지고 옴
            val dataFormat = SimpleDateFormat("yyyy-mm-dd") // 년 월 일
            intent.putExtra("today", dataFormat.format(System.currentTimeMillis())) // putExtra를 통해서 date라는 이름으로 string 형태로 넣어줌


            startActivity(intent)
        }
```
* Addactivity는 onCreate 내부에 get{형식}Extra로 데이터를 가져옴 
```kotlin
var date = intent.getStringExtra("today") // string 형태로 된 today를 가져오겠다
binding.date.text = date
```

### AddActivity에서 MainActivity에 값을 전달하려면?
* startActivity는 돌아왔을 때 리턴 값이 없다는 뜻 
* `startActivityForResult(Intent intent, int requestCode` :  전달 받을 값이 있는 인텐트를 호출하는거야! -> 처음 부를 때 표시
* `ActivityRequestLauncher` : 별도의 클래스를 만들어 사용하기도 함 

### ActivityRequestLauncher
* 어떤 값을 리턴시키는지 지정
* 값이 왔을 때 어떻게 처리해줄 것인지 처리해주는 부분 (콜백 - 되돌아왔을 때 처리)
```kotlin
// registerForActivityResult : 결과를 전달받는 호출 
val requestLaunchar: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // 결과를 돌려받았을 때 처리해주는 부분
            // 전달된 값을 it이라는 변수로 사용
            it.data!!.getStringExtra("result")?.let {  // data에 값이 있다면 -> null이 아니면
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }
```
* Activity를 시작하는 코드로 requestLaunchar의 launch 이용 
```kotlin
// startActivity(intent)
requestLaunchar.launch(intent) // launch로 실행함
```
* AddActivity의 경우
```kotlin
binding.btnSave.setOnClickListener {
            // 돌아가기 전에 값을 넣음
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            // intent 만든 것을 전달
            setResult(Activity.RESULT_OK, intent) // ok 상태로 전달 -> 자신을 불렀던 곳으로 되돌아간다

            finish() // 종료
            true
        }
```
* MainActivity 추가 코드 
```kotlin
datas = mutableListOf<String>()
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // 기존에 만들어둔 adapter와 연결함
        
        val layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL) // 항목마다 선을 그어줌 
        )
```

## Activity간 BACK 버튼
* android:parentActivityName=".MainActivity"를 추가하여 MainActivity가 부모임을 알림 
```xml
<activity
            android:name=".AddActivity"
            android:parentActivityName=".MainActivity"
            android:exported="false" />
```
* AddActivity 추가
```kotlin
supportActionBar?.setDisplayHomeAsUpEnabled(true) // addActivity의 home 화면에 표시하겠다는 뜻 ㅐ
```
* `isLocalVoiceInteractionSupported` Override 진행 
```kotlin
    override fun isLocalVoiceInteractionSupported(): Boolean {
        return super.isLocalVoiceInteractionSupported()
    }
```

## Bundle
* 액티비티를 종료할 때 저장했다가 복원해야 할 데이터가 있을 때 `Bundle`이라는 객체에 담아주면 됨 
* MainActivity 쪽에
```kotlin
override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
```
```kotlin
// Bundle에 값이 있으면 그 값을 가져오겠다
        // savedInstanceState : onCreate의 매개변수로 받은 bundle 값을 쓰겠다는 뜻
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList() // bundle의 값을 넣음
        } ?: let {
            mutableListOf<String>() // 값이 없을 경우는 mutableList로 넣겠다
        }
```

## ContentsProvider
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <!-- "android.widget.SearchView" -->

    <item
        android:id="@+id/item_info"
        android:icon="@android:drawable/ic_menu_info_details"
        android:title="웹사이트 방문하기"/>
    <item
        android:id="@+id/item_map"
        android:icon="@android:drawable/ic_dialog_map"
        android:title="지도보기"/>
    <item
        android:id="@+id/item_gallery"
        android:icon="@android:drawable/ic_menu_gallery"
        android:title="갤러리보기"/>

    <item
        android:id="@+id/item_call"
        android:icon="@android:drawable/ic_menu_call"
        android:title="전화걸기"/>
    <item
        android:id="@+id/item_mail"
        android:icon="@android:drawable/ic_dialog_email"
        android:title="메일 보내기"/>
</menu>
```
* `onNavigationItemSelected` 이용 
```kotlin
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_info -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 1")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }
            R.id.item_map -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 2")
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.566292, 126.9779751"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/덕성여대/선바위역/"));
                startActivity(intent)
                true
            }
            R.id.item_gallery -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 3")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
                startActivity(intent)
                true
            }
            R.id.item_call -> {
                Log.d("mobileapp", "Navigation Menu : 메뉴 4")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:991"))
                startActivity(intent)
                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SEND, Uri.parse("mail:kimes0403@gmail.com"))
                startActivity(intent)
                true
            }
        }
        return false // 디폴트 false
    }
```
o

# 시험 대비

### 메뉴 선택 시 입력 처리
```kotlin
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // item.itemId와 내부에 저장되어 있는 id값과 비교한 후 내용 수행 
        // 로그인 메뉴를 누르면 Toast가 뜨게 하는 경우 
        if(item.itemId == R.id.login) {
            Toast.makeText(this@MainActivity, "개발 중 입니다", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }
```

### 텍스트 검색 내용 다이얼로그 출력
```kotlin
// searchView의 setOnQueryTextListener에 작성 
searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
            AlertDialog.Builder(this@MainActivity).run() { // this@MainActivity 로 설정해주기
            setTitle("검색어 입력 화면")
            setMessage("검색어" + query + "를 입력하였습니다.") // 색 바꾸기 해야 함 
            setPositiveButton("확인", null)
            show()
        }
        return true
    }
     // 생략
    })
    return super.onCreateOptionsMenu(menu)
}
```


