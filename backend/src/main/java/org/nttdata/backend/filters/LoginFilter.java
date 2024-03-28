package org.nttdata.backend.filters;

import org.nttdata.backend.beans.AdminBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        AdminBean adminBean = (session != null) ? (AdminBean) session.getAttribute("adminBean") : null;

        if (adminBean == null || adminBean.getLoggedUser() == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsf");
        } else {
            chain.doFilter(req, res);
        }
    }

}