package servlet;

import controller.AccountController;
import entity.Account;
import service.impl.AccountServiceImpl;
import service.interfaces.AccountService;


import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        AccountService accountService = new AccountServiceImpl();
        AccountController accountController = new AccountController(accountService);
        List<Account> result = accountController.findAll();
        PrintWriter pw = response.getWriter();
        pw.println("ALL ACCOUNTS");
        result.forEach(s -> System.out.println("<br>" + s));
    }
}
