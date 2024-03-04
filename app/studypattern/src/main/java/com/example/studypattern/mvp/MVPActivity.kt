package com.example.studypattern.mvp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.studypattern.R
import com.example.studypattern.databinding.ActivityMvpactivityBinding
import com.example.studypattern.mvc.ImageCountModel
import com.example.studypattern.mvp.repository.ImageRepositoryImpl

/*
Model
- 데이터 관리 및 처리
- 비즈니스 로직 수행
- Controller와 종속적이지 않아 재사용 가능!

View
- 유저에게 보일 화면 표현
- Presenter에 의존적
- Activity나 Fragment 등이 포함됨
- Presenter를 통해 데이터를 전달받고 처리하기 떄문에 의존적

Presenter
- Model과 View 연결
- View에 Interface로 연결

특징
1. View와 Model 간의 의존성이 없음
2. View와 Presenter가 1:1 관계 => View가 많아지면 Presenter도 많아짐
3. 기능이 추가 될수록 Presenter가 비대해짐
 */
class MVPActivity : AppCompatActivity(), MvpContractor.View {
    private lateinit var binding: ActivityMvpactivityBinding
    private lateinit var presenter: MvpContractor.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMvpactivityBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }

        presenter = MvpPresenter(ImageCountModel(), ImageRepositoryImpl())
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    fun loadImage() {
        presenter.loadRandomImage()
    }

    override fun showImage(url: String, color: String) {
        binding.ivImage.run {
            setBackgroundColor(Color.parseColor(color))
            load(url) {
                crossfade(300) // 부드럽게 이미지를 가져옴
            }
        }
    }

    override fun showImageCountText(count: Int) {
        binding.tvImageCount.text = "불러온 이미지  : $count"
    }
}