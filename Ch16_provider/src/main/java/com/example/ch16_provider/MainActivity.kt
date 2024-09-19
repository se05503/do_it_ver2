package com.example.ch16_provider

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ch16_provider.databinding.ActivityMainBinding
import java.io.File

/*
갤러리 앱, 카메라 앱의 콘텐츠 프로바이더를 이용해야 함
콘텐츠 프로바이더는 안드로이드 컴포넌트이지만, 인텐트가 필요하지 않다.
콘텐츠 프로바이더에 정의되어있는 query(), insert(), update(), delete() 함수만 호출해 주면 된다.

 */
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        val requestGalleyLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                // inSampleSize 의 적절하 비율 계산 및 지정
                val calRatio = calculateInSampleSize(
                    it!!.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize), // 출력할 이미지의 화면 크기
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                // it.data.data = 사용자가 갤러리 앱에서 선택한 사진 데이터
                // InputStream = 갤러리 앱(외부 앱)의 콘텐츠 프로바이더가 제공함
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream?.close()
                inputStream = null
                bitmap.let {
                    binding.ivProfile.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val requestCameraThumbnailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            // "data" 키 값 : 안드로이드에서 카메라 앱이 썸네일 이미지를 반환할 때 사용하는 표준 키
            val bitmap = it.data?.extras?.get("data") as Bitmap
            binding.ivProfile.setImageBitmap(bitmap)
        }



        binding.btnGallery.setOnClickListener {
            /*
            갤러리 앱과 연동하기
            다음 속성을 지닌 인텐트를 시스템에 전달하면 갤러리 앱의 목록 화면이 실행된다.
            */
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleyLauncher.launch(intent)
        }
        binding.btnCamera.setOnClickListener {
            /*
            카메라 앱과 연동
            사진을 촬영한 후 확인을 누르면 사진이 뷰에 반영됨
             */
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestCameraThumbnailLauncher.launch(intent)
        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        /*
        fileUri: 사진의 Uri 값, reqWidth & reqHeight : 화면에 이미지가 출력되는 크기(이 크기로 이미지가 출력됐음 좋겠다!)
         */
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // true 로 설정하면 decodeStream 을 쓰더라도 Bitmap 객체가 만들어지지 않음. 대신 이미지의 각종 정보가 options 객체에 설정됨.
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val (height:Int, width:Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if(height>reqHeight || width>reqWidth) {
            val halfHeight = height/2
            val halfWidth = width/2
            while (halfHeight/inSampleSize >= reqHeight && halfWidth/inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}

