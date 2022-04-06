package com.app.test.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.app.test.databinding.FragmentPokemonDetailBinding
import com.app.test.model.Evolution
import com.app.test.model.detailRecylerlist
import com.app.test.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import pokemon.co.id.utils.SliderAdapter
import pokemon.co.id.utils.SliderItems

class PokemonDetailFragment : Fragment() {

    private lateinit var sliderItems: ArrayList<SliderItems>
    private lateinit var sliderName: ArrayList<String>
    private var id_pokemon: Int = 0
    private var nama_pokemon: String = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)

        loadbundle()
        loadonclick()

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel?.getRecyclerListDataObserver()
            ?.observe(viewLifecycleOwner, Observer<detailRecylerlist> {
                if (it != null) {
                    //update the adapter
                    Log.e("isi detail", it.toString())

                    id_pokemon = it.id

                    val number = if (it.forms.get(0).url.endsWith("/")) {
                        it.forms.get(0).url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        it.forms.get(0).url.takeLastWhile { it.isDigit() }
                    }
                    val url2 =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${number}.png"

                    Glide.with(this)
                        .load(url2)
                        .centerCrop()
                        .into(binding.ivGambar)

                    binding.pbViewHp.setProgress(it.stats.get(0).base_stat)
                    binding.pbViewHp.setProgressText("HP            :" + it.stats.get(0).base_stat.toString())
                    binding.pbViewAttack.setProgress(it.stats.get(1).base_stat)
                    binding.pbViewAttack.setProgressText("ATTACK        :" + it.stats.get(1).base_stat.toString())
                    binding.pbViewDefense.setProgress(it.stats.get(2).base_stat)
                    binding.pbViewDefense.setProgressText("DEFENSE       :" + it.stats.get(2).base_stat.toString())
                    binding.pbViewSpesialAttack.setProgress(it.stats.get(3).base_stat)
                    binding.pbViewSpesialAttack.setProgressText(
                        "SPESIAL ATTACK       :" + it.stats.get(
                            3
                        ).base_stat.toString()
                    )
                    binding.pbViewSpesialDefense.setProgress(it.stats.get(3).base_stat)
                    binding.pbViewSpesialDefense.setProgressText(
                        "SPESIAL DEFENSE       :" + it.stats.get(
                            3
                        ).base_stat.toString()
                    )
                    binding.pbViewSpeed.setProgress(it.stats.get(4).base_stat)
                    binding.pbViewSpeed.setProgressText("SPEED       :" + it.stats.get(4).base_stat.toString())
                } else {

                }
            })
        viewModel?.panggildetailapi(nama_pokemon)

        return binding.root
    }

    private fun loadonclick() {
        binding.tvEvolution.setOnClickListener {
            binding.llStatistics.visibility = View.GONE
            binding.llEvolution.visibility = View.VISIBLE

            viewModel?.getRecycler2ListDataObserver()?.observe(viewLifecycleOwner, Observer<Evolution> {
                if (it != null) {
                    Log.e("isi evolution", it.toString())

                    sliderItems = ArrayList<SliderItems>()
                    sliderName = ArrayList<String>()
                    sliderItems.clear()
                    sliderName.clear()

                    try {
                        val ans: Boolean = it.chain.species.url.isEmpty()
                        if (ans == true) {

                        }
                        else {
                            val number = if (it.chain.species.url.endsWith("/")) {
                                it.chain.species.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                it.chain.species.url.takeLastWhile { it.isDigit() }
                            }

                            sliderItems.add(SliderItems(number))
                        }
                    }
                    finally {

                    }

                    try {
                        val ans: Boolean = it.chain.evolves_to.get(0).species.url.isEmpty()
                        if (ans == true) {

                        }
                        else {
                            val number2 = if (it.chain.evolves_to.get(0).species.url.endsWith("/")) {
                                it.chain.evolves_to.get(0).species.url.dropLast(1)
                                    .takeLastWhile { it.isDigit() }
                            } else {
                                it.chain.evolves_to.get(0).species.url.takeLastWhile { it.isDigit() }
                            }
                            sliderItems.add(SliderItems(number2))
                        }
                    }
                    finally {

                    }


                    try {
                        val ans: Boolean = it.chain.evolves_to.get(0).evolves_to.get(0).species.url.isEmpty()
                        if (ans == true) {

                        }
                        else {
                            val number3 = if(it.chain.evolves_to.get(0).evolves_to.get(0).species.url.endsWith("/")) {
                                it.chain.evolves_to.get(0).evolves_to.get(0).species.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                it.chain.evolves_to.get(0).evolves_to.get(0).species.url.takeLastWhile { it.isDigit() }
                            }
                            sliderItems.add(SliderItems(number3))
                        }
                    }
                    finally{

                    }



                    sliderName.add(it.chain.species.name)
                    sliderName.add(it.chain.evolves_to.get(0).species.name)
                    sliderName.add(it.chain.evolves_to.get(0).evolves_to.get(0).species.name)

                    binding.viewPagerImageSlider.adapter =
                        SliderAdapter(sliderItems, binding.viewPagerImageSlider)
                    binding.viewPagerImageSlider.clipToPadding = false
                    binding.viewPagerImageSlider.clipChildren = false
                    binding.viewPagerImageSlider.offscreenPageLimit = 3
                    binding.viewPagerImageSlider.getChildAt(0).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER
                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer(MarginPageTransformer(40))
                    compositePageTransformer.addTransformer { page, position ->
                        val r = 1 - Math.abs(position)
                        page.scaleY = 0.85f + r * 0.15f
                        val pos = position.toInt()
                        try {
                            binding.tvDesciption == sliderName
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer)
                }
            })
            viewModel?.panggilevolution(id_pokemon)
        }

        binding.tvStatistik.setOnClickListener {
            binding.llStatistics.visibility = View.VISIBLE
            binding.llEvolution.visibility = View.GONE
        }
    }

    private fun loadbundle() {
        val bundle = this.arguments
        if (bundle != null) {
            //id_pokemon = bundle?.getInt("id_pokemon").toInt()
            nama_pokemon = bundle?.getString("nama_pokemon").toString()
        }
    }
}