package uz.gita.exam5bookapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.common.CategoryData
import uz.gita.exam5bookapp.databinding.ItemCategoryBinding

class BookCategoryAdapter : ListAdapter<CategoryData, BookCategoryAdapter.BooksViewHolder>(BookDiff) {

    private var childItemListener: ((BookData) -> Unit)? = null
    fun setChildItemListener(block : (BookData) -> Unit){
        childItemListener = block
    }

    object BookDiff : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.genre == newItem.genre
        }
        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }
    }

    inner class BooksViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val category = getItem(absoluteAdapterPosition)
            val adapter = BookListAdapter()
            adapter.putReadListener {
                childItemListener!!.invoke(it)
            }
            binding.tvGenre.text = category.genre
            adapter.submitList(category.bookList)
            binding.childRecyclerView.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder =
        BooksViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind()
    }

}