import java.io.*; // Importa classes para manipulação de entrada e saída
import java.net.*; // Importa classes para manipulação de rede
import java.util.Scanner; // Importa a classe Scanner para leitura de entrada do usuário

public class Cliente {
    private final String nome; // Nome do cliente
    private Socket socket; // Socket para comunicação com o servidor
    private PrintWriter out; // Escritor para enviar mensagens ao servidor

    public Cliente(String nome) {
        this.nome = nome; // Inicializa o nome do cliente
    }

    public void iniciar() {
        try {
            socket = new Socket("localhost", 12345); // Conecta ao servidor na porta 12345
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); // Inicializa o escritor para enviar mensagens ao servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inicializa o leitor para receber mensagens do servidor

            out.println(nome); // Envia o nome do cliente ao servidor

            new Thread(() -> { // Cria uma nova thread para ler mensagens do servidor
                try {
                    String mensagem;
                    while ((mensagem = in.readLine()) != null) { // Lê mensagens do servidor
                        if (mensagem.equalsIgnoreCase("Você foi desconectado.")) { // Verifica se a mensagem é de desconexão
                            System.out.println(mensagem); // Imprime a mensagem de desconexão
                            break; // Sai do loop
                        }
                        System.out.println(mensagem); // Imprime a mensagem recebida
                    }
                    socket.close(); // Fecha o socket
                    System.exit(0); // Encerra o programa
                } catch (IOException e) {
                    System.err.println("Erro ao ler mensagem do servidor: " + e.getMessage()); // Mensagem de erro ao ler mensagem do servidor
                }
            }).start(); // Inicia a thread

            try (Scanner scanner = new Scanner(System.in)) { // Cria um Scanner para ler entrada do usuário
                while (true) {
                    out.println(scanner.nextLine()); // Envia a mensagem digitada pelo usuário ao servidor
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o cliente: " + e.getMessage()); // Mensagem de erro ao iniciar o cliente
        } finally {
            if (socket != null && !socket.isClosed()) { // Verifica se o socket não é nulo e não está fechado
                try {
                    socket.close(); // Fecha o socket
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o socket: " + e.getMessage()); // Mensagem de erro ao fechar o socket
                }
            }
        }
    }
}
