package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:GetSessionServlet
 * Package:servlet
 * Description: 描述信息
 *
 * @date:2021/07/29 11:14
 * @author:小怪兽
 */
@WebServlet(urlPatterns = "/get")
public class GetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session中获取数据
        String url=(String)req.getSession().getAttribute("url");
        String id = req.getSession().getId();

        if (url == null) {

            resp.getWriter().write("no session data ...");

        }else {

            resp.getWriter().write("sessionId :"+id+" sessionData : "+url);
        }
    }
}
