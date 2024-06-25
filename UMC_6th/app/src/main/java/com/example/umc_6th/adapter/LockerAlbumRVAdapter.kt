package com.example.umc_6th.adapter

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.model.Song
import com.example.umc_6th.databinding.ItemLockerAlbumBinding

class LockerAlbumRVAdapter () : RecyclerView.Adapter<LockerAlbumRVAdapter.ViewHolder>(){

    private val switchStatus = SparseBooleanArray()
    private val songs = ArrayList<Song>()
    private var isGrayOverlayEnabled = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])

        holder.binding.itemLockerAlbumMoreIv.setOnClickListener {
            itemClickListener.onRemoveAlbum(songs[position].id)
            removeSong(position) // 화면에서 아이템 제거
        }

        // 항목 배경색 변경
//        if (isGrayOverlayEnabled) {
//            holder.binding.root.setBackgroundColor(holder.binding.root.context.getColor(R.color.gray_transparent))
//        } else {
//            holder.binding.root.setBackgroundColor(holder.binding.root.context.getColor(R.color.transparent))
//        }


        val switch = holder.binding.switchRV
        switch.isChecked = switchStatus[position]
        switch.setOnClickListener {
            if(switch.isChecked){
                switchStatus.put(position,true)
            }
            else{
                switchStatus.put(position,false)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int  = songs.size

    inner class ViewHolder(val binding:ItemLockerAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemLockerAlbumTitleTv.text = song.title
            binding.itemLockerAlbumSingerTv.text = song.singer
            binding.itemLockerAlbumCoverImgIv.setImageResource(song.coverImg!!)
        }
    }

    interface OnItemClickListener {
        fun onRemoveAlbum(SongId: Int) {
        }
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }
}