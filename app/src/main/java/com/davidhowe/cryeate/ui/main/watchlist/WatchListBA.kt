package com.davidhowe.cryeate.ui.main.watchlist
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.utils.CustomFormatter
import timber.log.Timber

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_loading)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("priceChangeArrow")
fun bindArrowFromPrice(view: ImageView, priceChange: Double) {
    if(priceChange>=0)
        view.setImageResource(R.drawable.ic_arrow_up_green)
    else
        view.setImageResource(R.drawable.ic_arrow_down_red)
}

@BindingAdapter("priceChangeText")
fun bindTextFromPrice(view: TextView, priceChange: Double) {
    Timber.d("priceChange=$priceChange")
    view.text = "${CustomFormatter.getPriceDecimalFormatter(priceChange).format(priceChange)}%"
}