package uz.gita.exam5bookapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.databinding.ItemBookBinding


class BookListAdapter : ListAdapter<BookData, BookListAdapter.BooksViewHolder>(BookDiff) {

    private var readListener: ((BookData) -> Unit)? = null

    object BookDiff : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }
    }

    inner class BooksViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                readListener?.invoke(currentList[absoluteAdapterPosition])
            }
        }
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            Glide.with(binding.root.context).load(item.coverUrl).placeholder(R.drawable.img).into(binding.imgCover)
            binding.tvBookTitle.text = item.title
            binding.tvAuthor.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder =
        BooksViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind()
    }

    fun putReadListener(block: (BookData) -> Unit) {
        this.readListener = block
    }

}