/*
    OBJETIVO : CRIAR ARQUIVO PARA FACILITAR O DESENVOLVIMENTO DOS SERVLETS DO PROJETO:

    01 - ABRIR CONFIGURAÇÕES DO INTELIJ - CTRL + ALT + S;
    02 - IR PARA A ABA EDITOR;
    03 - ENCONTRAR A ABA "FILE AND CODES TEMPLATES";
    04 - CLICAR EM '+';
    05 - CRIAR UM NOVO ARQUIVO COM A EXTENSÃO JAVA COM O NOME QUE PREFERIR ("New servlet");
    06 - COLAR CODIGO ABAIXO NO TEXT AREA DISPONÍVEL.
*/




package servlets;import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import utils.FtpClient;

@WebServlet("/ftpServlet")
public class ftpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ftpServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    private void chooseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "upload":
                InputStream inputStream = request.getInputStream();
                FtpClient.Upload(inputStream.readAllBytes());

                break;
            case null, default:
                break;
        }
    }
}