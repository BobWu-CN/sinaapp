package webapp;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/mmm")
public class mmm extends HttpServlet {

    //private String message;

    /*
    public void init() throws ServletException {
        // 执行必需的初始化
        message = "Hello World";
    }
    */


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html");

        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.println("<h1>oh yeah： " + System.getenv("ACCESSKEY") + "</h1>");


        // 处理中文
        String name =new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
        out.println("<h1>" +name + "</h1>");


        String driver = "com.mysql.jdbc.Driver";
        String username = System.getenv("ACCESSKEY");
        String password = System.getenv("SECRETKEY");
        String dbName = System.getenv("MYSQL_DB");
        String host = System.getenv("MYSQL_HOST");
        String port = System.getenv("MYSQL_PORT");
        String dbUrl = "jdbc:mysql://"+host+":"+port + "/" +dbName;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl,username,password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show status");
            //ResultSet rs = stmt.executeQuery("select * from `test`");
            out.println("<h1>正在执行SQL</h1>");
            while(rs.next()){
                response.getWriter().println(rs.getString("Variable_name") + " : " +rs.getString("value"));
                out.println(rs.getString("Variable_name") + " : " +rs.getString("value"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>系统出错A</h1>");
            out.println(e.toString() );
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>系统出错B</h1>");
            out.println(e.toString() );
        }

    }

    public void destroy() {
        // 什么也不做
    }
}