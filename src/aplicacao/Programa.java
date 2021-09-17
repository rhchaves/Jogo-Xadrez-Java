package aplicacao;

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

        while (!xadrezPartida.getCheckMate()) {
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

                if (xadrezPartida.getPromocao() != null){
                    System.out.println("Informe a peça para ser promovida (B/C/T/Q): ");
                    String tipo = sc.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")){
                        System.out.println("Valor inválido!!! \nInforme a peça para ser promovida (B/C/T/Q): ");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    xadrezPartida.trocarPecaPromovida(tipo);
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
        UI.limparTela();
        UI.printPartida(xadrezPartida, capturada);// mostrar a partida finalizada
    }


}
