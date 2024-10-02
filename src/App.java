import java.util.Scanner; // Importa a classe Scanner para leitura de entrada do usuário

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // Cria um Scanner para ler a entrada do usuário e garante que será fechado após o uso
            System.out.println("Deseja ser (1) Servidor ou (2) Cliente?"); // Pergunta ao usuário se ele deseja ser Servidor ou Cliente
            int escolha = scanner.nextInt(); // Lê a escolha do usuário (1 para Servidor, 2 para Cliente)
            scanner.nextLine(); // Consome a nova linha deixada pelo nextInt()
            switch (escolha) { // Verifica a escolha do usuário
                case 1 -> { // Caso o usuário escolha ser Servidor
                  System.out.print("Digite o nome do chat: "); // Solicita o nome do chat ao usuário
                  String nomeHost = scanner.nextLine(); // Lê o nome do chat
                  new Servidor().iniciar(nomeHost); // Inicia o servidor com o nome do chat fornecido
                }
                case 2 -> { // Caso o usuário escolha ser Cliente
                    System.out.print("Digite seu nome: "); // Solicita o nome do usuário
                    String nome = scanner.nextLine(); // Lê o nome do usuário
                    new Cliente(nome).iniciar(); // Inicia o cliente com o nome fornecido
                }
                default -> System.out.println("Escolha inválida."); // Mensagem de erro para escolha inválida
            }
        }
    }
}
