package com.venturessoft.human.humanrhdapp.utilis.StickyHeader

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.ItemPruebasBinding
import com.venturessoft.human.humanrhdapp.databinding.ViewBookDetailsComponentBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.ListaMaestroReloj
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class BookDetailsComponentView : ConstraintLayout {

    private lateinit var binding: ViewBookDetailsComponentBinding
    private lateinit var bookItemBinding: ItemPruebasBinding
    private lateinit var adapter: BookAdapter

    var books: List<ListaMaestroReloj> = emptyList()
        set(value) {
            field = value
            onItemsUpdated()
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = ViewBookDetailsComponentBinding.inflate(LayoutInflater.from(context), this, true)
        adapter = BookAdapter(context)
        binding.bookDetailsList.adapter = adapter
    }

    private fun onItemsUpdated() {
        adapter.notifyDataSetChanged()
        binding.bookDetailsList.requestLayoutForChangedDataset()
    }

    inner class BookAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val book: ListaMaestroReloj = books[position]
            var view: View? = convertView

            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_pruebas, parent, false)
                bookItemBinding = ItemPruebasBinding.bind(view)
                view.tag = bookItemBinding
            } else {
                bookItemBinding = view.tag as ItemPruebasBinding
            }
            bookItemBinding.apply {
                date.text = Utilities.formatYearMonthDay(book.fechaAplicacion)
                badge.text = book.gafete.toString()
                calendar.text = book.calendario.toString()
                if (book.rolHorario.toString().isEmpty() || book.rolHorario.toString()=="0" ){
                    layoutRolTurn.text = context.getString(R.string.turno)
                    rolturn.text = book.turno.toString()
                }else{
                    layoutRolTurn.text = context.getString(R.string.rol)
                    rolturn.text = book.rolHorario.toString()
                }
                cardviewlista.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.DATA_KEY,book)
                    findNavController().navigate(R.id.to_generalInformationDetailsFragment,bundle)
                }
            }
            return bookItemBinding.root
        }
        override fun getItem(position: Int): Any {
            return books[position]
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
        override fun getCount(): Int {
            return books.size
        }
        override fun isEnabled(position: Int): Boolean {
            return false
        }
    }
}