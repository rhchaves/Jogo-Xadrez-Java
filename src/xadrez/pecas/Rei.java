package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {
    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "R";
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

        //Verificar acima
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar abaixo
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar esquerda
        p.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar direita
        p.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Esquerda Acima (Noroeste)
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Direita Acima (Nordeste)
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Esquerda Abaixo (Suldoeste)
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Direita Abaixo (Suldeste)
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1) ;
        if (getTabuleiro().posicaoExitente(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
