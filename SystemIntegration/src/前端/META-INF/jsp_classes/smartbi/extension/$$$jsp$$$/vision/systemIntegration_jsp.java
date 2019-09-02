package smartbi.extension.$$$jsp$$$.vision;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class systemIntegration_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n<script type=\"text/javascript\" src=\"js/md5.js\"></script>\r\n<title>系统集成</title>\r\n</head>\r\n<body>\r\n\t<form method=\"POST\" id=\"formLogin\" onsubmit=\"login();return false\">\r\n\t\t<p>\r\n\t\t\t<input type=\"submit\" value=\"");
      out.write("${inputTimeOutReg}");
      out.write("\" />\r\n\t\t</p>\r\n\t</form>\r\n\t\r\n\t<form method=\"POST\" id=\"formOpenData\" onsubmit=\"openSource();return false\">\r\n\t\t<p>\r\n\t\t\t<span>报表ID：</span>\r\n\t\t\t<span>\r\n\t\t\t\t<input type=\"text\" name=\"openID\" id=\"openID\" value=\"I8a8ae5d7016cdc4ddc4dd6a1016cdc529fed0041\"/>\r\n\t\t\t</span>\r\n\t\t\t<span style=\"margin-left: 10px;\">\r\n\t\t\t\t<input type=\"submit\" value=\"");
      out.write("${openReport }");
      out.write("\"/>\r\n\t\t\t</span>\r\n\t\t</p>\r\n\t</form>\r\n\r\n</body>\r\n<script type=\"text/javascript\">\r\n// 免密登录\r\nfunction login() {\r\n\t// 生成用户名\r\n\tvar username = \"admin\";\r\n\t// 生成现在的时间戳\r\n\tvar time = generateTimeRequestNumber(new Date());\r\n\t// 生成token\r\n\tvar token = hex_md5(username + time);\r\n\t// 拼接url\r\n    formLogin.action = \"http://localhost:8080/smartbi/vision/index.jsp?token=\" + token + \"&username=\" + username + \"&datetime=\" + time; \r\n    // 提交\r\n    formLogin.submit(); \r\n    return true; \r\n}\r\n\r\n// 打开报表\r\nfunction openSource() {\r\n\t// 生成用户名\r\n\tvar username = \"admin\";\r\n\t// 生成现在的时间戳\r\n\tvar time = generateTimeRequestNumber(new Date());\r\n\t// 生成token\r\n\tvar token = hex_md5(username + time);\r\n\t// 获取报表ID\r\n\tvar openId = document.getElementById(\"openID\").value;\r\n\t// 拼接url\r\n    formOpenData.action = \"http://localhost:8080/smartbi/vision/openresource.jsp?token=\" + token + \"&username=\" + username + \"&datetime=\" + time + \"&resid=\" + openId; \r\n    // 提交\r\n    formOpenData.submit(); \r\n    return true; \r\n}\r\n// 生成时间戳\r\nfunction generateTimeRequestNumber(date) {\r\n");
      out.write("\tvar year = date.getFullYear().toString();\r\n\tvar month = conversionTime(date.getMonth() + 1);\r\n\tvar today = conversionTime(date.getDate());\r\n\tvar hours = conversionTime(date.getHours());\r\n\tvar minutes = conversionTime(date.getMinutes());\r\n\tvar seconds = conversionTime(date.getSeconds());\r\n\tvar time = year + month + today + hours + minutes + seconds;\r\n    return time;\r\n}\r\nfunction conversionTime(n) {\r\n\treturn n < 10 ? '0' + n : n;\r\n}\r\n</script>\r\n</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
