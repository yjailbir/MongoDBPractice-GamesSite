package edu.SE.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.mongodb.client.model.Filters.and;

@WebServlet(name = "SuccessServlet", value = "/success")
public class SuccessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<form method='post' action='main'>");
        writer.println("<input type='text' name='mrk'>");
        writer.println("<input type = 'hidden' name='game' value='" + request.getParameter("game") + "'>");
        writer.println("<input type = 'hidden' name='user' value='" + request.getParameter("user") + "'>");
        writer.println("<input type='hidden' name='operation' value='2'>");
        writer.println("<input type='submit' value='Change' class='btn btn-dark'><hr>");
        writer.println("</form>");
    }
}
