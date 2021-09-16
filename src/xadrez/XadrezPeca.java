package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {

    private Cor cor;

    public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public XadrezPosicao getXadrezPosicao(){
        return XadrezPosicao.fromPosicao(posicao);
    }

    protected boolean existePecaOponente(Posicao posicao){
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }
}
