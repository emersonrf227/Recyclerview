package com.example.emersonrf.recyclerview.ui

import android.content.Context
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import com.example.emersonrf.recyclerview.R
import com.example.emersonrf.recyclerview.api.getPicassoauth
import com.example.emersonrf.recyclerview.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_row.view.*

class ListaPokemonAdapter(

        private val context: Context,
        private val pokemons: List<Pokemon>,
        private val listener: (Pokemon) -> Unit
) :
        RecyclerView.Adapter<ListaPokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val view = LayoutInflater.from(context)
                .inflate(R.layout.pokemon_row, parent, false)
        return PokemonViewHolder(view)

    }

    override fun getItemCount(): Int {


        return pokemons.size


    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.bindView(pokemons[position], listener)


    }


    // ele pede como parametro o view Holder


    class PokemonViewHolder(itemView: View
                            ):
            RecyclerView.ViewHolder(itemView) {


    fun bindView(pokemon: Pokemon,
                 listener: (Pokemon) -> Unit
                 ) = with(itemView) {
        tvPokemon.text = pokemon.nome
        tvIdPokemon.text = pokemon.numero
       getPicassoauth(itemView.context) // autentica no piccasso a imagem com api
               .load("https://pokedexdx.herokuapp.com${pokemon.img}")
               .into(ivPokemon) // atribui a imagem que vem do picasso na view

        setOnClickListener{listener(pokemon)}


    }
    }

}