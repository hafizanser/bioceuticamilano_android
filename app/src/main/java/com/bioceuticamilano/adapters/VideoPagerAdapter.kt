package com.bioceuticamilano.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bioceuticamilano.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import android.widget.ImageView

class VideoPagerAdapter(
    private val context: Context,
    private val videos: List<String>,
    private val viewPager: ViewPager2
) : RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder>() {

    private val players = mutableMapOf<Int, ExoPlayer>()

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        val ivMute: ImageView = itemView.findViewById(R.id.ivMute)
        var isMuted = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video_pager, parent, false)
        // IMPORTANT: do NOT set a custom width here. ViewPager2 requires pages to be match_parent.
        // Use ViewPager2 padding and a PageTransformer to create the peeking/scale effect.
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val url = videos[position]

        // initialize player lazily
        if (!players.containsKey(position)) {
            val player = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(url))
                repeatMode = Player.REPEAT_MODE_ONE
                prepare()
                volume = 0f
            }
            players[position] = player
        }

        holder.playerView.player = players[position]

        holder.isMuted = (players[position]?.volume ?: 0f) == 0f
        holder.ivMute.setImageResource(if (holder.isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up)

        holder.ivMute.setOnClickListener {
            val p = players[position] ?: return@setOnClickListener
            holder.isMuted = !holder.isMuted
            p.volume = if (holder.isMuted) 0f else 1f
            holder.ivMute.setImageResource(if (holder.isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up)
        }

        // control playback based on current item
        val current = viewPager.currentItem
        if (position == current) {
            players[position]?.playWhenReady = true
        } else {
            players[position]?.playWhenReady = false
        }
    }

    override fun getItemCount(): Int = videos.size

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        val pos = holder.bindingAdapterPosition
        players[pos]?.release()
        players.remove(pos)
        holder.playerView.player = null
    }

    fun releaseAll() {
        players.values.forEach { it.release() }
        players.clear()
    }
}
