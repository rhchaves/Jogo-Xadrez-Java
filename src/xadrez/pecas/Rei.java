package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

    private XadrezPartida xadrezPartida;
    public Rei(Tabuleiro tabuleiro, Cor cor, XadrezPartida  xadrezPartida) {
        super(tabuleiro, cor);
        this.xadrezPartida = xadrezPartida;
    }

    @Override
    public String toString(){
        return "K";
    }

    private boolean podeMover(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    private boolean testeTorreRoque(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContaMovimento()  == 0;
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

        //Movimento especial 'Roque'
        if (getContaMovimento() == 0 && !xadrezPartida.getCheck()){
            //roque ao lado do rei (Roque pequeno)
            Posicao posT1 =  new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeTorreRoque(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            //roque ao lado da rainha (Roque grande)
            Posicao posT2 =  new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeTorreRoque(posT2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null  && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
