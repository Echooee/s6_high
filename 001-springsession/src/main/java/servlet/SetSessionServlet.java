package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:SetSessionServlet
 * Package:servlet
 * Description: 描述信息
 *
 * @date:2021/07/29 11:09
 * @author:小怪兽
 */
@WebServlet(urlPatterns = "/set")
public class SetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //想session中存入数据
        req.getSession().setAttribute("url","http://www.bjpowernode.com");
        resp.getWriter().write("session save success...");
    }
}
