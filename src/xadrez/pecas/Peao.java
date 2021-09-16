package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {


    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "P";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        //iniciando posição padrão
        Posicao p = new Posicao(0,0);
        //regra peão branco
        if (getCor() == Cor.BRANCA) {

            p.definirValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //testa se é o primeiro movimento e se poderá andar duas casas
            p.definirValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p) && getTabuleiro().posicaoExitente(p2) && !getTabuleiro().existePeca(p2) && getContaMovimento() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //movimento diagonal a esquerda
            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //movimento diagonal a direita
            p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        else {
            //regra peão preto
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //testa se é o primeiro movimento e se poderá andar duas casas
            p.definirValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p) && getTabuleiro().posicaoExitente(p2) && !getTabuleiro().existePeca(p2) && getContaMovimento() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //movimento diagonal a esquerda
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            //movimento diagonal a direita
            p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        return mat;
    }
}
