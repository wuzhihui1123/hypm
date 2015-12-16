package cn.ifactory.hypm.filter;

import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.utils.ConstantData;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 拦截器--权限
 * @author yaha
 *
 */
public class AuthFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		HttpSession session = httpRequest.getSession();
		String servletPath = httpRequest.getServletPath();
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		if(access(servletPath) ||  currUser != null) {
			if(servletPath.startsWith("/admin") && !currUser.isAdmin()) {
				httpRequest.getRequestDispatcher("/error/403.jhtml").forward(request, response);
			}else {
				chain.doFilter(request, response);
			}
		}else {
			//String contextPath = ((HttpServletRequest)request).getContextPath();
			//((HttpServletResponse)response).sendRedirect(StringUtils.isBlank(contextPath) ? "/" : contextPath);
			((HttpServletResponse)response).sendRedirect("/login.jhtml");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	private boolean access(String servletPath) {
		String sp = servletPath == null ? StringUtils.EMPTY : servletPath.trim();
		boolean ret = false;
		if(sp.endsWith("/login.jhtml") || sp.endsWith("/register.jhtml")) {
			ret = true;
		}
		return ret;
	}
}
