package smartbi.ext.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import smartbi.config.SystemConfigService;
import smartbi.freequery.repository.FreeQueryDAOFactory;
import smartbi.freequery.repository.Report;
import smartbi.repository.SystemConfig;
import smartbi.usermanager.User;
import smartbi.usermanager.UserManagerModule;
import smartbi.util.StringUtil;

/**
 * 集成强制登录和集成报表
 * 
 * @author Administrator
 * 
 */
public class SystemIntegrationFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(SystemIntegrationFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 获取用户名
		String username = request.getParameter("username");
		// 获取时间戳
		String time = request.getParameter("datetime");
		// 获取token
		String token = request.getParameter("token");
		// 当前提交的时间戳
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 获取报表ID
		String resId = request.getParameter("resid");
		// 如果点击的是免密登录 resId的值为null  如果点击的是打开报表且resId没有输入值 那它的值就是为 ""
		if (resId == "") {
			request.getRequestDispatcher("loginerror.jsp").forward(request, response);
			return ;
		} else if (resId != null){
			// 查看是否存在该报表
			Report report = FreeQueryDAOFactory.getReportDAO().load(resId);
			if (report == null) {
				request.getRequestDispatcher("loginerror.jsp").forward(request, response);
				return;
			}
		}
		// 如果token 不为空就代表单点登录
		if (token != null) {
			// 超时的秒数
			long seconds = Long.parseLong(currentTime) - Long.parseLong(time);
			// 获取系统设置的超时时间
			SystemConfig timeOutSystemConfig = SystemConfigService.getInstance().getSystemConfig("LOGIN_TIME_OUT");
			// 判断是否存在该超时时间 不存在就创建
			long timeOut = getLoginTimeOut(timeOutSystemConfig);
			// 判断超时
			if (seconds <= timeOut) {
				// 获取当前用户是否存在smartbi数据库中
				User user = UserManagerModule.getInstance().getUserByName(username);
				if (!StringUtil.isNullOrEmpty(username) && user != null) {
					// 校验token
					String filterToken = DigestUtils.md5Hex(username + time);
					// 校验成功即强制登录
					if (token.equals(filterToken)) {
						UserManagerModule.getInstance().getStateModule().setCurrentUser(user);
						UserManagerModule.getInstance().setSystemId("DEFAULT_SYS");
						request.getSession().setAttribute("password", username);
						LOG.info("免密登录成功 用户名为:" + username);
					}
				}
			} else {
				LOG.error("访问资源失败，免密登录不成功");
				request.getRequestDispatcher("loginerror.jsp").forward(request, response);
			}
		}
		chain.doFilter(request, response);
	}

	// 设置默认超时时间 如果升级类没有插入数据
	private Long getLoginTimeOut(SystemConfig timeOutSystemConfig) {
		// 获取超时时间的值
		Long timeOut;
		// 如果不存在就使用默认
		if (timeOutSystemConfig != null) {
			timeOut = Long.parseLong(timeOutSystemConfig.getValue());
		}
		else {
			timeOut = (long) 30;
		}
		return timeOut;
	}

}
