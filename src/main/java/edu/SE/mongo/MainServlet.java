package edu.SE.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.BasicBSONObject;
import org.bson.Document;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.mongodb.client.model.Filters.and;

@WebServlet(name = "MainServlet", value = "/main")
public class MainServlet extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


    public void  doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MongoClient mongoClient = MongoClients.create();
        if(request.getParameter("operation").equals("1")){
            MongoDatabase database = mongoClient.getDatabase("games");
            MongoCollection<Document> users = database.getCollection("users");
            FindIterable<Document> result = users.find(new Document("login", new Document("$eq", request.getParameter("login"))));
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            boolean flag = false;
            for (Document document : result) {
                if (document.containsValue(request.getParameter("login"))) {
                    flag = true;
                    break;
                }
            }
            if (request.getParameter("button").equals("enter")) {
                if (!flag) {
                    writer.println("<h2>user don't exist!</h2>");
                    writer.println("<a href=../mongo_war_exploded>Register</a>");
                } else {
                    FindIterable<Document> games = database.getCollection("games").find();
                    MongoCollection<Document> marks = database.getCollection("marks");
                    for (Document game : games) {
                        Document user = users.find(new Document("login", request.getParameter("login"))).first();
                        assert user != null;
                        Document mark =  marks.find(and(new Document("game", game.get("_id").toString()), new Document("user", user.get("_id").toString()))).first();
                        writer.println("<form method='post' action='success'>");
                        writer.println("<h1>" + game.get("title").toString() + ", genre: " + game.get("genre").toString()+ ", " + game.get("year").toString() + "</h1>");
                        assert mark != null;
                        writer.println("<h3>Your mark: " + mark.get("mark").toString() + "</h3>");
                        writer.println("<input class='btn btn-dark' type='submit' value='Change mark'><hr>");
                        writer.println("<input type = 'hidden' name='game' value='" + mark.get("game") + "'>");
                        writer.println("<input type = 'hidden' name='user' value='" + mark.get("user") + "'>");
                        writer.println("</form>");
                    }
                }
            } else {
                if (flag) {
                    writer.println("<h2>user already exist!</h2>");
                    writer.println("<a href=../mongo_war_exploded>Log in</a>");
                } else {
                    users.insertOne(new Document(Map.of("login", request.getParameter("login"), "password",
                            request.getParameter("password"))));
                    FindIterable<Document> games = database.getCollection("games").find();
                    MongoCollection<Document> marks = database.getCollection("marks");
                    for (Document game : games) {
                        Document user = users.find(new Document("login", request.getParameter("login"))).first();
                        assert user != null;
                        marks.insertOne(new Document(Map.of("user", user.get("_id").toString(), "game", game.get("_id").toString(), "mark", "none")));
                        Document mark =  marks.find(and(new Document("game", game.get("_id").toString()), new Document("user", user.get("_id").toString()))).first();
                        writer.println("<form method='post' action='success'>");
                        writer.println("<h1>" + game.get("title").toString() + ", genre: " + game.get("genre").toString() + ", " + game.get("year").toString() + "<h1>");
                        assert mark != null;
                        writer.println("<h3>Your mark: " + mark.get("mark").toString() + "</h3>");
                        writer.println("<input class='btn btn-dark' type='submit' value='Change mark'><hr>");
                        writer.println("<input type = 'hidden' name='game' value='" + mark.get("game").toString() + "'>");
                        writer.println("<input type = 'hidden' name='user' value='" + mark.get("user").toString() + "'>");
                        writer.println("</form>");
                    }
                }
            }
        }
        else {
            MongoDatabase database = mongoClient.getDatabase("games");
            MongoCollection<Document> marks = database.getCollection("marks");
            BasicDBObject bson1 = new BasicDBObject("game", request.getParameter("game"));
            BasicDBObject bson2 = new BasicDBObject("user", request.getParameter("user"));
            String tmp = new Document("$and", Arrays.asList(bson1,bson2)).get("$and").toString().substring(1);
            tmp = tmp.substring(0, tmp.length() - 1);
            String[] tmpArr = tmp.split(", ");
            BasicDBObject condition = new BasicDBObject(Map.of("game", BasicDBObject.parse(tmpArr[0]).get("game"), "user", BasicDBObject.parse(tmpArr[1]).get("user")));
            BasicDBObject toUpdate = new BasicDBObject("$set", new BasicDBObject("mark", request.getParameter("mrk")));
            System.out.println(condition);
            System.out.println(toUpdate);
            marks.updateOne(condition,toUpdate);
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            writer.println("<script>location='../mongo_war_exploded'</script>");
        }
    }

    public void destroy() {
    }
}