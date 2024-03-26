package org.nttdata.frontend.filters;

import org.nttdata.frontend.beans.UserBean;

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

        UserBean userBean = (session != null) ? (UserBean) session.getAttribute("userBean") : null;

        if (userBean == null || userBean.getLoggedUser() == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsf");
        } else {
            chain.doFilter(req, res);
        }
    }

}