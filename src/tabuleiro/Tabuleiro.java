package tabuleiro;

public class Tabuleiro { //Board

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1){
            throw new TabuleiroExcecao("Erro ao criar tabuleiro: É necessário ao menos 1 linha e 1 coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    //métodos
    public Peca peca(int linha, int coluna){
        if (!posicaoExistente(linha,coluna)){
            throw new TabuleiroExcecao("Não existe essa posição");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao){
        if (!posicaoExitente(posicao)){
            throw new TabuleiroExcecao("Não existe essa posição");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void colocarPeca(Peca peca, Posicao posicao){//placePiece()
        if (existePeca(posicao)){
            throw new TabuleiroExcecao("Já existe uma peça na posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removerPeca(Posicao posicao){//RemovePiece
        if (!posicaoExitente(posicao)){
            throw new TabuleiroExcecao("Não existe essa posição");
        }
        if (peca(posicao) == null){
            return null;
        }
        Peca aux = peca(posicao);
        aux.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return aux;
    }

    private boolean posicaoExistente(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna>= 0 & coluna < colunas;
    }

    public boolean posicaoExitente(Posicao posicao){
        return posicaoExistente(posicao.getLinha(), posicao.getColuna());
    }

    public boolean existePeca(Posicao posicao){//thereIsAPiece
        if (!posicaoExitente(posicao)){
            throw new TabuleiroExcecao("Não existe essa posição");
        }
        return peca(posicao) != null;
    }


}
