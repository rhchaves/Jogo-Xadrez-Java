package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Rainha extends XadrezPeca {
    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "Q";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        //iniciando posição padrão
        Posicao p = new Posicao(0,0);

        //Verificar acima
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar abaixo
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar esquerda
        p.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar direita
        p.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Esquerda Acima (Noroeste)
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.definirValores(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Direita Acima (Nordeste)
        p.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.definirValores(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Direita Abaixo (Suldeste)
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.definirValores(p.getLinha() + 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //Verificar Diagonal Esquerda Abaixo (Suldoeste)
        p.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExitente(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.definirValores(p.getLinha() + 1, p.getColuna() - 1);;
        }
        if (getTabuleiro().posicaoExitente(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
