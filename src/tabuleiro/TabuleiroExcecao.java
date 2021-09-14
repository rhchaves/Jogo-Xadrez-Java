package tabuleiro;

public class TabuleiroExcecao  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public TabuleiroExcecao(String msg){
        super(msg);
        System.out.println("Pressioner ENTER para continuar...");
    }
}
