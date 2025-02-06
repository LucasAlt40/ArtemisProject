package utils;

import java.io.*;
import java.net.*;
import java.util.Date;

public class FtpClient {
    public static void Upload(byte[] file) {
        String server = "10.242.194.182"; // Endereço do servidor FTP
        int port = 21; // Porta padrão do FTP
        String username = "equipe"; // Nome de usuário
        String password = "equipe2024"; // Senha de acesso
        try {
            // Conectar ao servidor FTP
            Socket controlSocket = new Socket(server, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            PrintWriter out = new PrintWriter(controlSocket.getOutputStream(), true);

            // Receber a resposta do servidor (220 OK)
            String response = in.readLine();
            System.out.println(response);

            // Enviar o comando USER
            out.println("USER " + username);
            response = in.readLine();
            System.out.println(response);

            // Enviar o comando PASS
            out.println("PASS " + password);
            response = in.readLine();
            System.out.println(response);

            // Enviar comando PASV para entrar no modo passivo
            out.println("PASV");
            response = in.readLine();
            System.out.println(response);


            // Extrair o endereço IP e porta do comando PASV
            String[] pasvResponse = response.split("\\(")[1].split("\\)")[0].split(",");
            String pasvIp = pasvResponse[0] + "." + pasvResponse[1] + "." + pasvResponse[2] + "." + pasvResponse[3];
            int pasvPort = (Integer.parseInt(pasvResponse[4]) * 256) + Integer.parseInt(pasvResponse[5]);

            // Criar o socket de dados para a transferência
            Socket dataSocket = new Socket(pasvIp, pasvPort);


            String filename = System.currentTimeMillis() + ".jpeg";

            out.println("STOR " + filename); // Nome do arquivo no servidor
            response = in.readLine();
            System.out.println(response);


            // Enviar os dados diretamente (array de bytes)
            OutputStream dataOut = dataSocket.getOutputStream();
            dataOut.write(file); // Enviar os bytes

            // Fechar o socket de dados
            dataOut.close();
            dataSocket.close();

            // Aguardar a resposta do servidor após o upload
            response = in.readLine();
            System.out.println(response);

            // Fechar a conexão de controle
            out.println("QUIT");
            response = in.readLine();
            System.out.println(response);
            System.out.println(filename);

            // Fechar os fluxos e o socket de controle
            in.close();
            out.close();
            controlSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
