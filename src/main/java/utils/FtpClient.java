package utils;

import jakarta.servlet.http.Part;
import model.dto.UploadResponseDto;

import java.io.*;
import java.net.*;



public class FtpClient {
    public static UploadResponseDto Upload(Part filePart) {
        String server = "10.242.194.182"; // Endereço do servidor FTP
        int port = 21; // Porta padrão do FTP
        String username = "equipe"; // Nome de usuário
        String password = "equipe2024"; // Senha de acesso
        try {

            String[] aux = filePart.getSubmittedFileName().split("\\.");

            var extension = aux[aux.length - 1];

            byte[] file = filePart.getInputStream().readAllBytes();
            Socket controlSocket = new Socket(server, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            PrintWriter out = new PrintWriter(controlSocket.getOutputStream(), true);

            String response = in.readLine();

            out.println("USER " + username);
            response = in.readLine();


            out.println("PASS " + password);
            response = in.readLine();


            out.println("PASV");
            response = in.readLine();


            String[] pasvResponse = response.split("\\(")[1].split("\\)")[0].split(",");
            String pasvIp = pasvResponse[0] + "." + pasvResponse[1] + "." + pasvResponse[2] + "." + pasvResponse[3];
            int pasvPort = (Integer.parseInt(pasvResponse[4]) * 256) + Integer.parseInt(pasvResponse[5]);

            Socket dataSocket = new Socket(pasvIp, pasvPort);

            String filename = getFileName(extension);

            out.println("STOR " + filename);
            response = in.readLine();

            OutputStream dataOut = dataSocket.getOutputStream();
            dataOut.write(file);

            // Fechar o socket de dados
            dataOut.close();
            dataSocket.close();

            // Aguardar a resposta do servidor após o upload
            response = in.readLine();

            // Fechar a conexão de controle
            out.println("QUIT");


            // Fechar os fluxos e o socket de controle
            in.close();
            out.close();
            controlSocket.close();

            return new UploadResponseDto(response, filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFileName(String extension) {
        return System.currentTimeMillis() + "." + extension;
    }
}
