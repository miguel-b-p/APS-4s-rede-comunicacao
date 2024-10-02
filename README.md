# FERRAMENTA PARA COMUNICAÇÃO EM REDE

## Descrição do Projeto
Este projeto implementa um sistema de chat simples em Java, permitindo que um servidor aceite múltiplas conexões de clientes. Os clientes podem se comunicar entre si através do servidor, que gerencia as mensagens e a entrada de novos usuários.

## Estrutura do Projeto
O projeto é composto por três classes principais:

- **Servidor**: Gerencia as conexões dos clientes e a comunicação entre eles.
- **Cliente**: Representa um usuário que se conecta ao servidor e envia/recebe mensagens.
- **App**: Classe principal que inicia o servidor ou o cliente com base na escolha do usuário.

## Bibliotecas Utilizadas
- `java.io.*`: Para manipulação de entrada e saída, permitindo a leitura e escrita de dados.
- `java.net.*`: Para manipulação de rede, incluindo a criação de sockets para comunicação entre o servidor e os clientes.
- `java.util.*`: Para uso de coleções, como `Set` e `HashSet`, que armazenam os nomes dos clientes e os escritores de mensagens.
- `java.text.*`: Para formatação de datas.

## Funcionamento do Socket
- **Socket**: Um socket é um ponto final de comunicação em uma rede. O servidor cria um `ServerSocket` que escuta por conexões em uma porta específica (12345). Quando um cliente se conecta, o servidor aceita a conexão e cria um novo socket para se comunicar com esse cliente.
- **InputStream e OutputStream**: Cada socket possui um fluxo de entrada e um fluxo de saída. O fluxo de entrada é usado para receber dados do cliente, enquanto o fluxo de saída é usado para enviar dados ao cliente.

## Funcionamento das Classes

### Classe Servidor
- **Iniciar**: O método `iniciar` cria um `ServerSocket` e aguarda conexões. Para cada conexão, um novo `ClienteHandler` é iniciado em uma nova thread.
- **ClienteHandler**: Esta classe interna gerencia a comunicação com um cliente específico, lê o nome do cliente, gerencia mensagens e comandos, e notifica todos os clientes sobre novas entradas e saídas.

### Classe Cliente
- **Iniciar**: O método `iniciar` conecta o cliente ao servidor e inicia uma thread para ler mensagens do servidor. O cliente também pode enviar mensagens digitadas pelo usuário.
- **Leitura de Mensagens**: Uma thread separada é criada para ouvir mensagens do servidor e exibi-las no console.

### Classe App
- **Main**: O método `main` pergunta ao usuário se ele deseja ser um servidor ou um cliente. Dependendo da escolha, ele inicia a instância apropriada.

## Comandos Disponíveis
Os clientes podem enviar comandos especiais que o servidor reconhece:
- `/dia`: Retorna a data e hora atual.
- `/usuarios`: Lista todos os usuários conectados.
- `/servidor`: Retorna o nome do servidor.
- `/sair`: Desconecta o cliente do chat.

## Conclusão
Este projeto é uma implementação básica de um sistema de chat em Java, utilizando sockets para comunicação em rede. Ele demonstra conceitos fundamentais de programação de rede, como gerenciamento de conexões, threads e manipulação de entrada e saída.
