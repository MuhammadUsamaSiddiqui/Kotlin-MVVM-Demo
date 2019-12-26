package com.example.mvvmdemo.ui.home.quotes

import com.example.mvvmdemo.R
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem (private val quote : Quote) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}