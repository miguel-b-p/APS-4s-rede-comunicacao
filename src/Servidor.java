import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor {
    private static final int PORTA = 12345;
    private final Set<String> nomesClientes = Collections.synchronizedSet(new HashSet<>());
    private final Set<PrintWriter> clientesEscritores = Collections.synchronizedSet(new HashSet<>());

    public void iniciar(String nomeServidor) {
        System.out.println("Esperando alguém entrar...");
        try (ServerSocket servidorSocket = new ServerSocket(PORTA)) {
            while (true) {
                new ClienteHandler(servidorSocket.accept(), nomeServidor).start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private class ClienteHandler extends Thread {
        private final Socket socket;
        private String nome;
        private PrintWriter out;
        private final String nomeServidor;

        public ClienteHandler(Socket socket, String nomeServidor) {
            this.socket = socket;
            this.nomeServidor = nomeServidor;
        }

        @Override
        public void run() {
            System.out.println("Servidor iniciado...");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Bem-vindo ao chat " + nomeServidor + "!");

                while (true) {
                    nome = in.readLine();
                    if (nome == null || nomesClientes.contains(nome)) {
                        out.println("Nome inválido ou já em uso. Tente novamente.");
                    } else {
                        nomesClientes.add(nome);
                        break;
                    }
                }
                clientesEscritores.add(out);

                enviarParaTodos(nome + " entrou no chat.");
                System.out.println(nome + " entrou no chat.");

                String mensagem;
                while ((mensagem = in.readLine()) != null) {
                    System.out.println(nome + ": " + mensagem);
                    if (mensagem.startsWith("/")) {
                        processarComando(mensagem);
                    } else {
                        enviarParaTodos(nome + ": " + mensagem);
                    }
                }
            } catch (IOException e) {
                if (!e.getMessage().equals("Connection reset")) {
                    System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
                }
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o socket: " + e.getMessage());
                }
                removerCliente(nome, out);
            }
        }

        private void processarComando(String mensagem) {
            if (mensagem.equalsIgnoreCase("/dia")) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                out.println(" ~ " + dateFormat.format(new Date()));
                return;
            }
            if (mensagem.equalsIgnoreCase("/usuarios")) {
                for (String Nome : nomesClientes) {
                    out.printf(" - %s\n", Nome);
                }
                return;
            }
            if(mensagem.equalsIgnoreCase("/servidor")) {
              out.println(" - " + nomeServidor);
              return;
            }
            if (mensagem.equalsIgnoreCase("/sair")) {
                removerCliente(nome, out);
                return;
            }
            out.println("Comando não reconhecido: " + mensagem);
        }

        private void enviarParaTodos(String mensagem) {
            for (PrintWriter writer : clientesEscritores) {
                writer.println(mensagem);
            }
        }

        private void removerCliente(String nome, PrintWriter out) {
            nomesClientes.remove(nome);
            clientesEscritores.remove(out);
            enviarParaTodos(nome + " saiu do chat.");
            System.out.println(nome + " saiu do chat.");
        }
    }
}
