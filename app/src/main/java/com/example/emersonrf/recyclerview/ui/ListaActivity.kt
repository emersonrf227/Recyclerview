package com.example.emersonrf.recyclerview.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.emersonrf.recyclerview.R
import com.example.emersonrf.recyclerview.api.getPokemonAPI
import com.example.emersonrf.recyclerview.model.Pokemon
import com.example.emersonrf.recyclerview.model.PokemonResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {


    private var disposable:  Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        carregarDados()
    }

    private fun exibeNaLista(pokemons: List<Pokemon>){

        rvPokemons.adapter = ListaPokemonAdapter(this, pokemons, {
            Toast.makeText(this, it.nome, Toast.LENGTH_LONG).show()
        })

        rvPokemons.layoutManager = LinearLayoutManager(this)
    }


        private fun exibeError(erro: String){
            Toast.makeText(this, erro, Toast.LENGTH_LONG)

        }



    private fun carregarDados() {

        getPokemonAPI()

                .buscar(400)
                .subscribeOn(Schedulers.io()) // metodo observador de requisições de entrada e saída
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<PokemonResponse>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {

                        disposable = d

                    }

                    override fun onNext(t: PokemonResponse) {

                        exibeNaLista(t.content)

                    }

                    override fun onError(e: Throwable) {

                        exibeError(e.message!!)
                    }
                }) //implementação do observador

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
