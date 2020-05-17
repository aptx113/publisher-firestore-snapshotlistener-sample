package app.appworks.school.publisher.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.publisher.data.Article
import app.appworks.school.publisher.databinding.ItemArticleBinding

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [Article], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class HomeAdapter(private val onClickListener: OnClickListener ) :
        ListAdapter<Article, RecyclerView.ViewHolder>(DiffCallback) {
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Article]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Article]
     */
    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }

    class ArticleViewHolder(private var binding: ItemArticleBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onClickListener: OnClickListener) {

            binding.article = article
            binding.root.setOnClickListener { onClickListener.onClick(article) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> ArticleViewHolder(ItemArticleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ArticleViewHolder -> {
                holder.bind((getItem(position) as Article), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }
}
