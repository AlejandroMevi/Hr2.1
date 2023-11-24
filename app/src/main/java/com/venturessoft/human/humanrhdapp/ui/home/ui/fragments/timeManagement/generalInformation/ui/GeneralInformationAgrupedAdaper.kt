package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.databinding.ViewListItemBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.ListaMaestroReloj

class GeneralInformationAgrupedAdaper : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookHeaders: List<String> = listOf()

    var bookData: Map<String, List<ListaMaestroReloj>> = emptyMap()
        set(value) {
            field = value
            bookHeaders = bookData.keys.toList()
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ViewListItemBinding = ViewListItemBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(viewBinding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 0 && position < bookHeaders.size) {
            (holder as BookViewHolder).bind(bookHeaders[position])
        }
    }
    override fun getItemCount() = bookHeaders.size

    fun getHeaderForCurrentPosition(position: Int) = if (position in bookHeaders.indices) {
        bookHeaders[position]
    } else {
        ""
    }
    inner class BookViewHolder(
        private val viewBinding: ViewListItemBinding
    ) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(header: String) {
            viewBinding.tvHeader.text = header
            bookData[header]?.let { books ->
                viewBinding.bookDetailsView.books = books
            }
        }
    }
}