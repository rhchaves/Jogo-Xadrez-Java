package aplicacao;

import tabuleiro.Peca;
import xadrez.XadrezExcecao;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();
        List<XadrezPeca> capturada = new ArrayList<>();

        while (true) {
            try {
                UI.limparTela();
                UI.printPartida(xadrezPartida, capturada);
                System.out.println();
                System.out.print("Origem: ");
                XadrezPosicao origem = UI.lerXadrezPosicao(sc);

                boolean[][] movimentosPossiveis = xadrezPartida.movimentosPossiveis(origem);
                UI.limparTela();
                UI.printTabuleiro(xadrezPartida.getPeca(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                XadrezPosicao destino = UI.lerXadrezPosicao(sc);

                XadrezPeca pecaCapiturada = xadrezPartida.executarMovimentoXadrez(origem, destino);

                if (pecaCapiturada != null){
                    capturada.add(pecaCapiturada);
                }
            }
            catch (XadrezExcecao e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }


}
