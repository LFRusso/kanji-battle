package com.example.kanjibattle;

import java.io.Serializable;

import static java.lang.Math.floor;

public class Usuario implements Serializable {
    private int pontuacao;
    private int nivel;

    public Usuario() {
        this.pontuacao = 0;
        this.nivel = 0;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }
    public int getNivel() {
        return this.nivel;
    }

    /****
     * aumenta o nivel do jogador quando possivel
     * @return boolean se aumentou o nivel ou não
     */
    public boolean checkNivel() {
        if (this.pontuacao == 500) {
            this.nivel += 1;
            this.pontuacao -= 500;
            return true;
        }
        return false;
    }

    /****
     * Soma uma quantidade de pontos a pontuação
     * @param pontos pontos a serem somados
     * @return total
     */
    public int somaPontos(int pontos) {
        this.pontuacao += pontos;
        return this.pontuacao;
    }
}
