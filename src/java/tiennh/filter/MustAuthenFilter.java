/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.usertable.UserTableDAO;
import tiennh.usertable.UserTableDTO;
import tiennh.util.AccountHelper;

/**
 *
 * @author Will
 */
@MultipartConfig

public class MustAuthenFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public MustAuthenFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MustAuthenFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MustAuthenFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        System.out.println("--" + this.getClass().getSimpleName() + "--");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = ((HttpServletResponse) response);

        try {
            HttpSession ses = req.getSession();
            String username = null;
            String password = null;
            UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
            UserTableDTO loginDTO = null; // login result
            Integer userRole = null;
            
            if (user != null) {
                System.out.println("GET USER");
                username = user.getUsername();
                password = user.getPassword();
                userRole = user.getRole();
            } // has user dto
            else {
                System.out.println("GET COOKIE");
                Cookie[] browserCookies = req.getCookies();
                if (browserCookies != null) {
                    for (Cookie loginCookie : browserCookies) {
                        if (loginCookie.getName().equals("s2020l2")) {
                            System.out.println("GOT COOKIE");
                            String cookieValue = loginCookie.getValue();
                            int commaIndex = cookieValue.lastIndexOf('.');
                            username = cookieValue.substring(0, commaIndex);
                            password = cookieValue.substring(commaIndex + 1);
                            break;
                        }
                    } // iterate cookie list
                } // exit if no cookies
            } // no user dto
            if (username != null && password != null) {
                System.out.println("AUTHENTICATE: " + username + ", " + password);
                loginDTO = new UserTableDAO().checkLogin(username, password);
            } // has both username and password
            else if(userRole==AccountHelper.THIRD_PARTY_ROLE){
                loginDTO = user;
            }
            
            if (loginDTO != null) {
                System.out.println("login succ");
                Cookie loginCookie = new Cookie("s2020l1", username + '.' + password);
                loginCookie.setMaxAge(AccountHelper.COOKIE_AGE);
                rep.addCookie(loginCookie);
                loginDTO.setPassword(password);
                ses.setAttribute("SES_USER", loginDTO);
                chain.doFilter(request, rep);
            } // login successful 
            else if(userRole == AccountHelper.THIRD_PARTY_ROLE){
                System.out.println("login succ");
                chain.doFilter(request, rep);
            }
            else {
                System.out.println("login unsucc");
                rep.sendRedirect("login");
            } // login unsuccessful

        } catch (Throwable t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            System.out.println("------------------------");
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("MustAuthenFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("MustAuthenFilter()");
        }
        StringBuffer sb = new StringBuffer("MustAuthenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}