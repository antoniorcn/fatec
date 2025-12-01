package edu.curso;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns="/meuservlet")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("Você escreveu seu primeiro Servlet");
        out.println("</body>");
        out.println("</html>");

        System.out.println("O Servlet /meuservlet foi invocado");

    }
}    
