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
    private val mutedState = mutableMapOf<Int, Boolean>()

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

        // initialize player lazily (or reuse existing)
        val player = players.getOrPut(position) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(url))
                repeatMode = Player.REPEAT_MODE_ONE
                prepare()
                volume = 0f
            }
        }

        // reduce flicker when switching players
        holder.playerView.setKeepContentOnPlayerReset( true)
        holder.playerView.player = player

        // restore mute state for this item
        val isMuted = mutedState[position] ?: true
        holder.isMuted = isMuted
        player.volume = if (isMuted) 0f else 1f
        holder.ivMute.setImageResource(if (isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up)

        holder.ivMute.setOnClickListener {
            val p = players[position] ?: return@setOnClickListener
            val newMuted = !(mutedState[position] ?: true)
            mutedState[position] = newMuted
            p.volume = if (newMuted) 0f else 1f
            holder.ivMute.setImageResource(if (newMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up)
        }

        // ensure correct play state set by external controller (playAt)
        // don't change play state here to avoid jank from unnecessary prepare/release
    }

    override fun getItemCount(): Int = videos.size

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        // detach player from view to avoid keeping view references
        holder.playerView.player = null
    }

    fun preloadAll() {
        // For small lists, prepare all players to avoid prepare cost during swipe
        for (i in videos.indices) {
            if (!players.containsKey(i)) {
                val player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri(videos[i]))
                    repeatMode = Player.REPEAT_MODE_ONE
                    prepare()
                    volume = 0f
                }
                players[i] = player
                mutedState[i] = true
            }
        }
    }

    fun playAt(position: Int) {
        // Play the requested position and pause others
        players.forEach { (idx, p) ->
            p.playWhenReady = (idx == position)
        }
    }

    fun releaseAll() {
        players.values.forEach { it.release() }
        players.clear()
        mutedState.clear()
    }
}
