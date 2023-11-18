import javax.swing.*;
//esse import serve para fazer a janela (o ".*" serve para importar todas as classes do pacote)


public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        //Criando a janela
        JFrame frame = new JFrame("Snake.Game");
        
        //Tornando o frame visível
        frame.setVisible(true);
        //Definindo o tamnho(de acordo com as variaveis declaradas)
        frame.setSize(boardWidth, boardHeight);
        //Inicia a janela no meio da tela
        frame.setLocationRelativeTo(null);
        //Não permitir a alteração no tamnho da janela
        frame.setResizable(false);
        //Fazer com que o programa feche ao clicar no "X"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        //aqui, vamos definir o JPanel para suas dimensões completas e previnir que a barra da janela
        //fique contando como parte do programa
        frame.pack();
        snakeGame.requestFocus();




    }
}
