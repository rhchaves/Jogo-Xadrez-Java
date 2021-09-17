package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XadrezPartida {//chessMath

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private XadrezPeca enPassantVulneravel;
    private XadrezPeca promocao;

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

    public XadrezPeca getEnPassantVulneravel(){
        return enPassantVulneravel;
    }

    public XadrezPeca getPromocao(){
        return promocao;
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

        XadrezPeca pecaMovida = (XadrezPeca) tabuleiro.peca(destino);
        promocao = null;
        if (pecaMovida instanceof Peao){
            if ((pecaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7)){
                promocao = (XadrezPeca)tabuleiro.peca(destino);
                promocao = trocarPecaPromovida("Q");
            }
        }

        check = (testeCheck(oponente(jogadorAtual))) ? true : false;

        if (testeCheckMate(oponente(jogadorAtual))){
            checkMate = true;
        }
        else {
            proximoTurno();
        }

        //Movimento especial En Passant
        if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            enPassantVulneravel = pecaMovida;
        }
        else {
            enPassantVulneravel = null;
        }

        return (XadrezPeca)pecaCapturada;
    }

    public XadrezPeca trocarPecaPromovida(String tipo){
        if (promocao == null){
            throw new IllegalStateException("Não há peça a ser promovida");
        }
        if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
            return promocao;
        }

        Posicao pos = promocao.getXadrezPosicao().toPosicao();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);

        XadrezPeca novaPeca = novaPeca(tipo, promocao.getCor());
        tabuleiro.colocarPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private XadrezPeca novaPeca(String tipo, Cor cor){// instanciar a peça que será trocada na promoção
        if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
        if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
        if (tipo.equals("Q")) return new Rainha(tabuleiro, cor);
        return new Torre(tabuleiro, cor);
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        XadrezPeca p = (XadrezPeca)tabuleiro.removerPeca(origem);
        p.somaContaMovimento();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        //Movimento especial Roque pequeno
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(),  origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(),  origem.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca)tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.somaContaMovimento();
        }

         //Movimento especial Roque grande
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(),  origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(),  origem.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca)tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.somaContaMovimento();
        }

        //Movimento especial En Passant
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null){
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCA){
                    peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                }
                else {
                    peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(peaoPosicao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        XadrezPeca p = (XadrezPeca)tabuleiro.removerPeca(destino);
        p.subtraiContaMovimento();
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null){
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

        //Movimento especial Roque pequeno
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(),  origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(),  origem.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca)tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.subtraiContaMovimento();
        }

         //Movimento especial Roque grande
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(),  origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(),  origem.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca)tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.subtraiContaMovimento();
        }

        //Movimento especial En Passant
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel){
                XadrezPeca peao = (XadrezPeca)tabuleiro.removerPeca(destino);
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCA){
                    peaoPosicao = new Posicao(3 , destino.getColuna());
                }
                else {
                    peaoPosicao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.colocarPeca(peao, peaoPosicao);
            }
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

        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA,this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA,this));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA,  this));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA,this));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA,this));
    }

}
