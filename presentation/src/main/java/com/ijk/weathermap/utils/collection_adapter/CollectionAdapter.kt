package com.ijk.weathermap.utils.collection_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class CollectionAdapter<T, V : View> constructor(
    private val delegate: CollectionDelegate<T>?
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    lateinit var viewSource: CollectionViewSource<T, V>
    lateinit var dataSource: List<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return if (viewSource.res != null)
            createHolderByResource(parent)
        else
            createHolderByView(parent)
    }

    private fun createHolderByResource(parent: ViewGroup): CollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CollectionViewHolder(
            inflater.inflate(viewSource.res ?: 0, parent, false)
        )
    }

    private fun createHolderByView(parent: ViewGroup): CollectionViewHolder {
        val constructor = viewSource.clazz?.constructors?.first()
        val view = constructor?.newInstance(parent.context) as V
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = dataSource[position]
        viewSource.bindView(holder.itemView, item, position)
        if (delegate == null) return
        holder.itemView.setOnClickListener {
            delegate.onItemClick(item, holder.itemView, position)
        }
    }

    override fun getItemCount() = dataSource.size

    class CollectionViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
