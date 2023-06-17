package rs.ac.bg.fon.pracenjepolaganja.security.filter;

import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represent security filter that implements Filter class of Spring Security.
 * Provides verification that user has authorities for the given operation.
 *
 * @author Vuk Manojlovic
 */
public class AuthoritiesLoggingAfterFilter implements Filter {
    /**
     * Represent Log of class activities.
     */
    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            LOG.info("User " + authentication.getName() + " is successfully authenticated and "
                    + "has the authorities " + authentication.getAuthorities().toString());
        }
        chain.doFilter(request, response);
    }
}
