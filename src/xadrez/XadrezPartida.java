package xadrez;


import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class XadrezPartida {//chessMath

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();//Pode ser instanciado no construtor
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);
        turno =1;
        jogadorAtual = Cor.BRANCA;
        iniciarJogo();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual(){
        return jogadorAtual;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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

    public XadrezPeca executarMovimentoXadrez(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao){//performChessMove
        Posicao origem = origemPosicao.toPosicao();
        Posicao destino = destinoPosicao.toPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem,destino);
        Peca pecaCapturada = fazerMovimento(origem, destino);

        if (testeCheck(jogadorAtual)){
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezExcecao("Você não pode se colocar em check");
        }

        check = (testeCheck(oponente(jogadorAtual))) ? true : false;

        if (testeCheckMate(oponente(jogadorAtual))){
            checkMate = true;
        }
        else {
            proximoTurno();
        }
        return (XadrezPeca)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        Peca p = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null){
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    private void validarPosicaoOrigem(Posicao posicao){
        if (!tabuleiro.existePeca(posicao)){
            throw new XadrezExcecao("Não existe peça na posição de origem");
        }
        if (jogadorAtual != ((XadrezPeca)tabuleiro.peca(posicao)).getCor()){
            throw new XadrezExcecao("Peça escolhida não é sua!");
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

    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }

    private Cor oponente(Cor cor){//retorna a cor do oponente
        return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }

    private XadrezPeca rei(Cor cor){//procurar o rei pela sua cor
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){
            if (p instanceof Rei){
                return (XadrezPeca)p;
            }
        }
        throw new IllegalStateException("Não existe rei " + cor + " no tabuleiro");//nunca pode ocorrer este erro, caso ocorra verificar funcionamento do jogo
    }

    private boolean testeCheck(Cor cor){//testar se o rei está em check
        Posicao reiPosicao = rei(cor).getXadrezPosicao().toPosicao();
        List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca p : pecasOponente) {
            boolean[][] mat = p.movimentosPossiveis();
            if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]){
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Cor cor) {//testar se é checkMate
        if (!testeCheck(cor)){
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){//procurar alguma peca que retire do check
            boolean[][] mat = p.movimentosPossiveis();
            for (int i=0; i< tabuleiro.getLinhas(); i++){
                for (int j=0; j< tabuleiro.getColunas(); j++){
                    if (mat[i][j]) {
                        Posicao origem = ((XadrezPeca)p).getXadrezPosicao().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(origem, destino);
                        boolean testeCheck = testeCheck(cor);
                        desfazerMovimento(origem,destino,pecaCapturada);
                        if (!testeCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private void colocarNovaPeca(char coluna, int linha, XadrezPeca peca){//placeNewPiece
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna,linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void iniciarJogo(){
        /*colocarNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETA));
        
         */

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
