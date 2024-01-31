package com.example.myframe

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myframe.databinding.ActivityMainBinding
import java.util.Collections.addAll

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: ImageAdapter

    // 이미지를 한번에 여러개를 가져올 예정
    private val imageLoadLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            updateImages(uriList)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // toolbar
        binding.toolbar.apply {
            title = "사진 가져오기"
            setSupportActionBar(this)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLoadImage()
        initRecyclerView()
        navigateToFrameActivity()
    }

    private fun navigateToFrameActivity() {
        binding.btnNavigateFrameActivity.setOnClickListener {
            val images = imageAdapter.currentList.filterIsInstance<ImageItems.FrameImage>().map { it.uri.toString() }.toTypedArray()

            val intent = Intent(this, FrameActivity::class.java)
                .putExtra("images", images)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        imageAdapter = ImageAdapter(object : ImageAdapter.ItemClickListener{
            override fun onLoadMoreClick() {
                checkPermissions()
            }

        })

        binding.rvImage.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun clickLoadImage() {
        binding.btnLoadImg.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        // 권한이 있는지 여부 확인
        when {
            // 권한을 이미 허용한 경우
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }

            // 권한을 허용한 적이 없는 경우
            shouldShowRequestPermissionRationale(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showPermissionInfoDialog()
            }

            else -> {
                requestReadExternalStorage()
            }

        }
    }

    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위해서, 외부 저장소 읽기 권한이 필요합니다.")
            setNegativeButton("취소", null)
            setPositiveButton("동의") { _, _ ->
                requestReadExternalStorage()
            }
        }.show()
    }

    private fun requestReadExternalStorage() {
        // permission 요청
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE
        ) // 요청코드
    }

    private fun loadImage() {
        //Toast.makeText(this, "loadImage", Toast.LENGTH_SHORT).show()

        // image 타입으로 된 모든 파일을 열어서 가져올 것임
        imageLoadLauncher.launch("image/*")
    }

    private fun updateImages(uriList: List<Uri>) {
        //Log.i("updateImages", "$uriList")
        val images = uriList.map { ImageItems.FrameImage(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                val resultCode = grantResults.firstOrNull() ?: PackageManager.PERMISSION_DENIED
                if (resultCode == PackageManager.PERMISSION_GRANTED) {
                    // 허용된 상태
                    loadImage()
                }
            }
        }
    }


    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }
}