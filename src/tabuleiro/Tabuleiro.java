package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1){
            throw new TabuleiroExecao("Erro ao criar tabuleiro: É necessário ao menos 1 linha e 1 coluna");
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
            throw new TabuleiroExecao("Não existe essa posição");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao){
        if (!posicaoExitente(posicao)){
            throw new TabuleiroExecao("Não existe essa posição");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void pecaPosicao(Peca peca, Posicao posicao){
        if (pecaExistente(posicao)){
            throw new TabuleiroExecao("Já existe uma peça na posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    private boolean posicaoExistente(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna>= 0 & coluna < colunas;
    }

    private boolean posicaoExitente(Posicao posicao){
        return posicaoExistente(posicao.getLinha(), posicao.getColuna());
    }

    public boolean pecaExistente(Posicao posicao){
        if (!posicaoExitente(posicao)){
            throw new TabuleiroExecao("Não existe essa posição");
        }
        return peca(posicao) != null;
    }
}
