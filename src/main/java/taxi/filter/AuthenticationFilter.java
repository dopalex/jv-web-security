package taxi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    private List<String> allowedUrls;

    public void init(FilterConfig config) {
        allowedUrls = new ArrayList<>();
        allowedUrls.add("/login");
        allowedUrls.add("/drivers/add");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        Long driverId = (Long) session.getAttribute("driver_id");
        if (driverId != null || allowedUrls.contains(req.getServletPath())) {
            chain.doFilter(req, resp);
            return;
        }
        resp.sendRedirect("/login");

    }
}
