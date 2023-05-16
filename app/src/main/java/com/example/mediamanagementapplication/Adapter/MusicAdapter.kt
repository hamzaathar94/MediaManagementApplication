package com.example.playit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediamanagementapplication.databinding.MusicItemBinding
import com.example.playit.Interface.onAudioClick
import com.example.playit.Model.Music
import com.example.playit.Model.formatDuration

class MusicAdapter(val context: Context,var data:List<Music>,private val onAudioClick: onAudioClick)
    :RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyViewHolder {
        val binding=MusicItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicAdapter.MyViewHolder, position: Int) {
        val musicList=data[position]
        holder.binding.txtsongname.text=musicList.title
        holder.binding.txtalbum.text=musicList.album
        holder.binding.txtduration.text= formatDuration(musicList.duration)



        holder.itemView.setOnClickListener {
            onAudioClick.onAudioItemClick(position,musicList)
        }
        holder.itemView.setOnLongClickListener {
            onAudioClick.onAudioItemLongClick(position,musicList)
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class MyViewHolder(var binding: MusicItemBinding):RecyclerView.ViewHolder(binding.root){

    }
}