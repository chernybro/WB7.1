package com.chernybro.wb51.presentation.hero_list_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chernybro.wb51.R
import com.chernybro.wb51.databinding.ItemHeroBinding
import com.chernybro.wb51.domain.models.HeroAttribute
import com.chernybro.wb51.domain.models.HeroItem

interface HeroClickHandler {
    fun onItemClick(item: HeroItem)
}

class HeroesListAdapter : RecyclerView.Adapter<HeroesListAdapter.HeroListViewHolder>() {
    private val data: MutableList<HeroItem> = ArrayList()
    private var heroClickHandler: HeroClickHandler? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroListViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroListViewHolder(binding, heroClickHandler = heroClickHandler)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HeroListViewHolder, position: Int) {
        holder.bind(item = data[position])
    }

    fun setData(items: List<HeroItem>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun attachClickHandler(heroClickHandler: HeroClickHandler) {
        this.heroClickHandler = heroClickHandler
    }

    class HeroListViewHolder(
        private val itemHeroBinding: ItemHeroBinding,
        private val heroClickHandler: HeroClickHandler?,
    ) : RecyclerView.ViewHolder(itemHeroBinding.root) {

        fun bind(item: HeroItem) {
            itemHeroBinding.apply {
                tvHeroName.text = item.name
                ivPrimaryAttr.load(
                    when (item.primaryAttr) {
                        HeroAttribute.Intelligence -> R.drawable.ic_intelligence
                        HeroAttribute.Agility -> R.drawable.ic_agility_2
                        HeroAttribute.Strength -> R.drawable.ic_strength
                        else -> {}
                    }
                )
                ivIcon.load(item.avatar)
                this.root.setOnClickListener{ heroClickHandler?.onItemClick(item) }
            }
        }
    }
}