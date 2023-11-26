package com.xmz.servlet;

import com.xmz.service.impl.DemoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xmz
 * @date 2023-11-26
 */
@WebServlet(urlPatterns = "/demo2")
public class DemoServlet extends HttpServlet {

    DemoServiceImpl demoServiceImpl = new DemoServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(demoServiceImpl.findAll().toString());
    }
}
