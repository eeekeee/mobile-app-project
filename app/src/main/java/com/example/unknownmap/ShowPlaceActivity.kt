package com.example.unknownmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.unknownmap.databinding.ActivityShowPlaceBinding
import net.daum.mf.map.api.MapPOIItem

class ShowPlaceActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)//이거 있어야 이미치 처리할 수 있음
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShowPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent에서 데이터를 추출
        val name = intent.getStringExtra("show_name") ?: ""
        val latitude = String.format("%.2f", intent.getDoubleExtra("show_latitude", 0.0))
        val longitude = String.format("%.2f", intent.getDoubleExtra("show_longitude", 0.0))
        val category = intent.getIntExtra("show_category", 0)
        val byteArray = intent.getByteArrayExtra("show_image")
        val star = intent.getIntExtra("show_star", 0)
        Log.d("star", star.toString())
        val imageBitmap = if (byteArray != null) {
            // 바이트 배열을 Bitmap으로 변환
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else {
            null
        }

        //MainActivity의 static 변수에 저장된 유저 정보를 출력해본다
        Log.d("user", "in ShowPlaceActivity, ${MainActivity.staticUserId}")
        Log.d("user", "in ShowPlaceActivity, ${MainActivity.staticUserEmail}")
        Log.d("user", "in ShowPlaceActivity, ${MainActivity.staticUserNickname}")
        Log.d("user", "in ShowPlaceActivity, ${MainActivity.staticUserToken}")

        //닫기 버튼
        binding.closeButton.setOnClickListener {
            finish()
        }
        binding.heartButton.setOnClickListener {
            // 현재 이미지 리소스 가져오기
            val currentImageResource = binding.heartButton.drawable

            // 현재 이미지와 비교하여 변경
            if (currentImageResource.constantState == resources.getDrawable(R.drawable.blank_heart).constantState) {
                // 현재 이미지가 blank_heart이면 filled_heart로 변경
                binding.heartButton.setImageResource(R.drawable.red_heart)
            } else {
                // 현재 이미지가 red_heart이면 blank_heart로 변경
                binding.heartButton.setImageResource(R.drawable.blank_heart)
            }
        }
        
        if(star == 0){
            binding.starScore1.setImageResource(R.drawable.star_dark)
            binding.starScore2.setImageResource(R.drawable.star_dark)
            binding.starScore3.setImageResource(R.drawable.star_dark)
            binding.starScore4.setImageResource(R.drawable.star_dark)
            binding.starScore5.setImageResource(R.drawable.star_dark)
        }else if(star == 1){
            binding.starScore1.setImageResource(R.drawable.star_light)
            binding.starScore2.setImageResource(R.drawable.star_dark)
            binding.starScore3.setImageResource(R.drawable.star_dark)
            binding.starScore4.setImageResource(R.drawable.star_dark)
            binding.starScore5.setImageResource(R.drawable.star_dark)
        }else if(star == 2) {
            binding.starScore1.setImageResource(R.drawable.star_light)
            binding.starScore2.setImageResource(R.drawable.star_light)
            binding.starScore3.setImageResource(R.drawable.star_dark)
            binding.starScore4.setImageResource(R.drawable.star_dark)
            binding.starScore5.setImageResource(R.drawable.star_dark)
        }else if(star == 3) {
            binding.starScore1.setImageResource(R.drawable.star_light)
            binding.starScore2.setImageResource(R.drawable.star_light)
            binding.starScore3.setImageResource(R.drawable.star_light)
            binding.starScore4.setImageResource(R.drawable.star_dark)
            binding.starScore5.setImageResource(R.drawable.star_dark)
        }else if(star == 4) {
            binding.starScore1.setImageResource(R.drawable.star_light)
            binding.starScore2.setImageResource(R.drawable.star_light)
            binding.starScore3.setImageResource(R.drawable.star_light)
            binding.starScore4.setImageResource(R.drawable.star_light)
            binding.starScore5.setImageResource(R.drawable.star_dark)
        }else if(star == 5){
            binding.starScore1.setImageResource(R.drawable.star_light)
            binding.starScore2.setImageResource(R.drawable.star_light)
            binding.starScore3.setImageResource(R.drawable.star_light)
            binding.starScore4.setImageResource(R.drawable.star_light)
            binding.starScore5.setImageResource(R.drawable.star_light)
        }

        Log.d("show_image from intent", imageBitmap?.toString() ?: "Bitmap is null")

        // XML에서 뷰 찾기
        val nameTextView: TextView = findViewById(R.id.show_name_textView)
        val locationTextView: TextView = findViewById(R.id.show_location_textView)
        val categoryTextView: TextView = findViewById(R.id.show_category_textView)
        val imageView: ImageView = findViewById(R.id.show_imageView)

        // 값 설정
        nameTextView.text = name
        locationTextView.text = "위도: $latitude\n경도: $longitude"
        categoryTextView.text = getCategoryString(category)
        imageView.setImageBitmap(imageBitmap)
    }

    private fun getCategoryString(category: Int): String {
        return when (category) {
            0 -> "쓰레기통"
            1 -> "자판기"
            2 -> "붕어빵"
            else -> "기타"
        }
    }
}