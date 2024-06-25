package com.example.umc_6th.ui.main.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.umc_6th.ui.main.home.HomeFragment
import com.example.umc_6th.R
import com.example.umc_6th.ui.adapter.AlbumPagerAdapter
import com.example.umc_6th.data.entities.Album
import com.example.umc_6th.data.entities.Like
import com.example.umc_6th.data.local.SongDatabase
import com.example.umc_6th.databinding.FragmentAlbumBinding
import com.example.umc_6th.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment: Fragment(R.layout.fragment_album) {
    // 여기에 Fragment의 구현 내용을 작성합니다.

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private var gson: Gson = Gson()
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //앨범 데이터
        val albumToJson = arguments?.getString("album")
        val album = gson.fromJson(albumToJson, Album::class.java)
        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListener(album)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        // 뒤로가기 이미지 뷰에 클릭 리스너 설정
        binding.albumBackIv.setOnClickListener {
            // 이전 프래그먼트로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }

 */

        val adapter = AlbumPagerAdapter(this)
        binding.albumViewPager.adapter = adapter

        TabLayoutMediator(binding.tlAlbum, binding.albumViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "수록곡"
                1 -> "상세정보"
                2 -> "영상"
                else -> null
            }
        }.attach()

        arguments?.getString("album")?.let { json ->
            val gson = Gson()
            val album = gson.fromJson(json, Album::class.java)
            Log.d("AlbumFragment", "Album parsed: ${album.title}, ${album.artist}, ${album.coverImg}")
            setInit(album)
        }
    }

    private fun setInit(album : Album) {
        Log.d("AlbumFragment", "Album parsed: ${album.title}, ${album.artist}, ${album.coverImg}")
        binding.imgAlbumAlbumCov.setImageResource(album.coverImg!!)
        binding.txAlbumAlbumTitle.text = album.title
        binding.txAlbumAlbumArtist.text = album.artist
        if(isLiked) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else {
            binding.albumLikeIv.setImageResource((R.drawable.ic_my_like_off))
        }
    }

    private fun getJwt() : Int {
        val spf = requireActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("jwt", 0)
    }

    private fun likeAlbum(userId : Int, albumId : Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }
    private fun isLikedAlbum(albumId : Int) : Boolean {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
        return likeId != null
    }

    private fun disLikeAlbum(albumId : Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun setOnClickListener(album : Album) {
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(album.id)
            }else {
                binding.albumLikeIv.setImageResource((R.drawable.ic_my_like_on))
                likeAlbum(userId, album.id)
            }
            isLiked = !isLiked
        }
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commitAllowingStateLoss()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수를 방지하기 위해 binding 객체를 null로 설정
        _binding = null
    }
}