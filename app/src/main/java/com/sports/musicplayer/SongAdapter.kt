import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sports.musicplayer.R
import com.sports.musicplayer.Song

class SongAdapter(private val context: Context, private val songs: List<Song>, private val onItemClick: (Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val song = songs[position]
        viewHolder.bind(song)
        itemView?.setOnClickListener { onItemClick(position) }

        return itemView!!
    }

    override fun getItem(position: Int): Any = songs[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = songs.size

    inner class ViewHolder(itemView: View) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(song: Song) {
            titleTextView.text = song.title
        }
    }
}
