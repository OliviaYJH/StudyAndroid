package com.example.studypattern.mvc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.studypattern.databinding.ActivityMvcactivityBinding
import com.example.studypattern.mvc.provider.ImageProvider

/*
MVC 패턴
1. Model
- 데이터 관리
- 비즈니스 로직 수행

2. View
- 유저에게 보일 화면 표현
- 어떠한 데이터나 로직이 있으면 안됨

3. Controller
- Model과 View 연결
- 유저의 입력을 받고 처리

특징
1. Model이 종속적이지 않아 재사용 가능
2. Controller에 많은 코드가 생김
3. View와 Model의 결합도 상승
4. 테스트 코드 작성 어려움

=> MVC 패턴은 Activity 안에 View와 Controller 영역이 전부 들어가 있음
 */

class MVCActivity : AppCompatActivity(), ImageProvider.Callback {
    private lateinit var binding: ActivityMvcactivityBinding

    // model과 provider를 직접 참조
    // 하지만 model과 provider 안에서는 activity의 상황을 알 수 없음 => 다른쪽에서 재사용 가능
    private val model = ImageCountModel()
    private val imageProvider = ImageProvider(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMvcactivityBinding.inflate(layoutInflater).also {
            setContentView(binding.root)
            it.view = this
        }

    }

    fun loadImage() {
        imageProvider.getRandomImage()
    }

    override fun loadImage(url: String, color: String) {
        model.increase()
        with(binding) {
            ivImage.run {
                setBackgroundColor(Color.parseColor(color))
                load(url) // coil 사용해 이미지 띄우기
            }
            tvImageCount.text = "불러온 이미지 수: ${model.count}"
        }
    }

}