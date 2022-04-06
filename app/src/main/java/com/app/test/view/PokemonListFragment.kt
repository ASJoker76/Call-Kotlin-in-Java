package com.app.test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.test.R
import com.app.test.adapter.PokemonAdapter
import com.app.test.databinding.FragmentPokemonListBinding
import com.app.test.model.RecyclerList
import com.app.test.model.Results
import com.app.test.utils.GridSpacingItemDecoration
import com.app.test.viewmodel.HomeViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class PokemonListFragment : Fragment(), PokemonAdapter.OnKlickListener {

    private var viewModel: HomeViewModel? = null
    private lateinit var binding: FragmentPokemonListBinding
    private var pokemonArrayList: List<Results>? = null
    private val pokemonArray: ArrayList<Results> = java.util.ArrayList<Results>()
    private var pokemonAdapter: PokemonAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        loadtable()
        //callApiList();
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel?.getRecyclerListDataObserver()?.observe(viewLifecycleOwner, Observer<RecyclerList>{
            if(it != null) {
                //update the adapter
                pokemonAdapter?.setDataList(it.results)
                pokemonArray.addAll(it.results)
            } else {

            }
        })
        viewModel?.panggilapi()

        return binding.root
    }

    private fun loadtable() {
        pokemonArrayList = ArrayList<Results>()
        pokemonAdapter = PokemonAdapter(this, pokemonArrayList, this)
        binding.rvList.setAdapter(AlphaInAnimationAdapter(pokemonAdapter!!))
        binding.rvList.setLayoutManager(
            GridLayoutManager(
                getActivity(),
                3,
                GridLayoutManager.VERTICAL,
                false
            )
        )
        binding.rvList.addItemDecoration(GridSpacingItemDecoration(2, 2, true, 2))
        val alphaInAnimationAdapter = AlphaInAnimationAdapter(pokemonAdapter!!)
        alphaInAnimationAdapter.setDuration(1000)
        alphaInAnimationAdapter.setInterpolator(OvershootInterpolator())
        alphaInAnimationAdapter.setFirstOnly(false)
    }

    override fun onKlickClick(position: Int) {
        val id2 = pokemonArray[position].url

        //Toast.makeText(activity, id2, Toast.LENGTH_SHORT).show()

        val number = if(id2?.endsWith("/") == true) {
            id2.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            id2?.takeLastWhile { it.isDigit() }
        }
//
//        Toast.makeText(activity, number, Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        if (number != null) {
            bundle.putInt("id_pokemon", number.toInt())
        }
        bundle.putString("nama_pokemon", pokemonArray[position].name)
        val fragementIntent = PokemonDetailFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        transaction?.replace(R.id.fl_view, fragementIntent)
        fragementIntent.setArguments(bundle)
        transaction!!.addToBackStack(null)
        transaction?.commit()
    }
}