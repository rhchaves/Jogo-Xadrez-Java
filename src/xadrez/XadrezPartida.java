package xadrez;


import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.awt.*;

public class XadrezPartida {//chessMath
    
    private Tabuleiro tabuleiro;

    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);
        iniciarJogo();
    }

    //Métodos
    public XadrezPeca[][] getPeca(){
        XadrezPeca[][] matriz =  new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i=0; i<tabuleiro.getLinhas(); i++){
            for (int j=0; j<tabuleiro.getColunas(); j++){
                matriz[i][j] = (XadrezPeca) tabuleiro.peca(i,j);
            }
        }
    return matriz;
    }

    public boolean[][] movimentosPossiveis(XadrezPosicao origemPosition){
        Posicao posicao = origemPosition.toPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public XadrezPeca executarMovimentoXadrez(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao){
        Posicao origem = origemPosicao.toPosicao();
        Posicao destino = destinoPosicao.toPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem,destino);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        return (XadrezPeca)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.pecaPosicao(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao){
        if (!tabuleiro.existePeca(posicao)){
            throw new XadrezExcecao("Não existe peça na posição de origem");
        }
        if (!tabuleiro.peca(posicao).exiteMovimentoPossivel()) {
            throw new XadrezExcecao("Não existe movimento possível para a peça escolhida");
        }
    }
    private void validarPosicaoDestino(Posicao origem, Posicao destino){
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new XadrezExcecao("A peça escolhida não pode ser movimentada para a posição de destino");
        }
    }

    private void colocarNovaPeca(char coluna, int linha, XadrezPeca peca){//placeNewPiece
        tabuleiro.pecaPosicao(peca, new XadrezPosicao(coluna,linha).toPosicao());
    }

    private void iniciarJogo(){
        colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));

    }

}
