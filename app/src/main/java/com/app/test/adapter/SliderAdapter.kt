package pokemon.co.id.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.test.R
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter(sliderItems: List<SliderItems>, viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private val sliderItems: List<SliderItems>
    private val viewPager2: ViewPager2
    private val runnable: Runnable? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView: RoundedImageView
        fun setImage(sliderItems: SliderItems) {

            Glide.with(viewPager2)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${sliderItems.image}.png")
                .centerCrop()
                .placeholder(R.drawable.ic_international_pok_mon_logo)
                .into(imageView)
        }

        init {
            imageView = itemView.findViewById(R.id.imageSlide)
        }
    }

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }
}