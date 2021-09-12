package xadrez;

import tabuleiro.Peca;
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


}
