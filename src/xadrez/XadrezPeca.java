package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {

    private Cor cor;
    private int contaMovimento;

    public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContaMovimento() {
        return contaMovimento;
    }

    public void somaContaMovimento(){
        contaMovimento++;
    }

    public void subtraiContaMovimento(){
        contaMovimento--;
    }

    public XadrezPosicao getXadrezPosicao(){
        return XadrezPosicao.fromPosicao(posicao);
    }

    protected boolean existePecaOponente(Posicao posicao){
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }
}
