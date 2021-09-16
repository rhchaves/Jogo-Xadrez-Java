package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Cavalo extends XadrezPeca {

    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "C";
    }

    private boolean podeMover(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        //iniciando posição padrão
        Posicao p = new Posicao(0,0);


        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() - 2 , posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 2) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() + 2, posicao.getColuna() + 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() + 2, posicao.getColuna() - 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 2) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
