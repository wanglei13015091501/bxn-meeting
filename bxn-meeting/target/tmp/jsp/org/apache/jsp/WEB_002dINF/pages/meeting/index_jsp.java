package org.apache.jsp.WEB_002dINF.pages.meeting;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_sec_authorize_ifAnyGranted;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_url_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_sec_authorize_ifNotGranted;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_sec_authorize_ifAnyGranted = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_url_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_sec_authorize_ifNotGranted = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_sec_authorize_ifAnyGranted.release();
    _jspx_tagPool_c_url_value_nobody.release();
    _jspx_tagPool_sec_authorize_ifNotGranted.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta name=\"decorator\" content=\"mis-index-blue\">\r\n");
      out.write("    <title>会议考勤系统</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body title=\"会议考勤系统\" text=\"统一 / 高效 / 协同 / 准确\">\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"content clearfix\">\r\n");
      out.write("\t\t");
      if (_jspx_meth_sec_authorize_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      if (_jspx_meth_sec_authorize_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t");
      if (_jspx_meth_sec_authorize_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      if (_jspx_meth_sec_authorize_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_sec_authorize_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sec:authorize
    org.springframework.security.taglibs.authz.JspAuthorizeTag _jspx_th_sec_authorize_0 = (org.springframework.security.taglibs.authz.JspAuthorizeTag) _jspx_tagPool_sec_authorize_ifAnyGranted.get(org.springframework.security.taglibs.authz.JspAuthorizeTag.class);
    _jspx_th_sec_authorize_0.setPageContext(_jspx_page_context);
    _jspx_th_sec_authorize_0.setParent(null);
    _jspx_th_sec_authorize_0.setIfAnyGranted("PERM_MEETING_ADMIN,PERM_MEETING_OWNER");
    int _jspx_eval_sec_authorize_0 = _jspx_th_sec_authorize_0.doStartTag();
    if (_jspx_eval_sec_authorize_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out.write("\r\n");
      out.write("\t\t<a href=\"../meeting/attendance/meeting/indexPage\" \r\n");
      out.write("\t\tbg=\"");
      if (_jspx_meth_c_url_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_0, _jspx_page_context))
        return true;
      out.write("\" \r\n");
      out.write("\t\tbgHover=\"");
      if (_jspx_meth_c_url_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_0, _jspx_page_context))
        return true;
      out.write("\">会议考勤管理</a>\r\n");
      out.write("\t\t");
    }
    if (_jspx_th_sec_authorize_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_0);
      return true;
    }
    _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_0);
    return false;
  }

  private boolean _jspx_meth_c_url_0(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_0 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_0.setPageContext(_jspx_page_context);
    _jspx_th_c_url_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_0);
    _jspx_th_c_url_0.setValue("/resources/images/attendance_bg.png");
    int _jspx_eval_c_url_0 = _jspx_th_c_url_0.doStartTag();
    if (_jspx_th_c_url_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_0);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_0);
    return false;
  }

  private boolean _jspx_meth_c_url_1(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_1 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_1.setPageContext(_jspx_page_context);
    _jspx_th_c_url_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_0);
    _jspx_th_c_url_1.setValue("/resources/images/attendance_hoverbg.png");
    int _jspx_eval_c_url_1 = _jspx_th_c_url_1.doStartTag();
    if (_jspx_th_c_url_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_1);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_1);
    return false;
  }

  private boolean _jspx_meth_sec_authorize_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sec:authorize
    org.springframework.security.taglibs.authz.JspAuthorizeTag _jspx_th_sec_authorize_1 = (org.springframework.security.taglibs.authz.JspAuthorizeTag) _jspx_tagPool_sec_authorize_ifAnyGranted.get(org.springframework.security.taglibs.authz.JspAuthorizeTag.class);
    _jspx_th_sec_authorize_1.setPageContext(_jspx_page_context);
    _jspx_th_sec_authorize_1.setParent(null);
    _jspx_th_sec_authorize_1.setIfAnyGranted("PERM_MEETING_ADMIN");
    int _jspx_eval_sec_authorize_1 = _jspx_th_sec_authorize_1.doStartTag();
    if (_jspx_eval_sec_authorize_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out.write("\r\n");
      out.write("\t\t<a href=\"../meeting/stat/indexPage\" \r\n");
      out.write("\t\tbg=\"");
      if (_jspx_meth_c_url_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_1, _jspx_page_context))
        return true;
      out.write("\" \r\n");
      out.write("\t\tbgHover=\"");
      if (_jspx_meth_c_url_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_1, _jspx_page_context))
        return true;
      out.write("\">考勤统计</a>\r\n");
      out.write("\t\t");
    }
    if (_jspx_th_sec_authorize_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_1);
      return true;
    }
    _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_1);
    return false;
  }

  private boolean _jspx_meth_c_url_2(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_2 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_2.setPageContext(_jspx_page_context);
    _jspx_th_c_url_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_1);
    _jspx_th_c_url_2.setValue("/resources/images/statistics_bg.png");
    int _jspx_eval_c_url_2 = _jspx_th_c_url_2.doStartTag();
    if (_jspx_th_c_url_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_2);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_2);
    return false;
  }

  private boolean _jspx_meth_c_url_3(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_3 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_3.setPageContext(_jspx_page_context);
    _jspx_th_c_url_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_1);
    _jspx_th_c_url_3.setValue("/resources/images/statistics_hoverbg.png");
    int _jspx_eval_c_url_3 = _jspx_th_c_url_3.doStartTag();
    if (_jspx_th_c_url_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_3);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_3);
    return false;
  }

  private boolean _jspx_meth_sec_authorize_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sec:authorize
    org.springframework.security.taglibs.authz.JspAuthorizeTag _jspx_th_sec_authorize_2 = (org.springframework.security.taglibs.authz.JspAuthorizeTag) _jspx_tagPool_sec_authorize_ifNotGranted.get(org.springframework.security.taglibs.authz.JspAuthorizeTag.class);
    _jspx_th_sec_authorize_2.setPageContext(_jspx_page_context);
    _jspx_th_sec_authorize_2.setParent(null);
    _jspx_th_sec_authorize_2.setIfNotGranted("PERM_MEETING_ADMIN");
    int _jspx_eval_sec_authorize_2 = _jspx_th_sec_authorize_2.doStartTag();
    if (_jspx_eval_sec_authorize_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out.write("\r\n");
      out.write("\t\t<a href=\"../meeting/stat/userDetailPage\" \r\n");
      out.write("\t\tbg=\"");
      if (_jspx_meth_c_url_4((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_2, _jspx_page_context))
        return true;
      out.write("\" \r\n");
      out.write("\t\tbgHover=\"");
      if (_jspx_meth_c_url_5((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_2, _jspx_page_context))
        return true;
      out.write("\">考勤统计</a>\r\n");
      out.write("\t\t");
    }
    if (_jspx_th_sec_authorize_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sec_authorize_ifNotGranted.reuse(_jspx_th_sec_authorize_2);
      return true;
    }
    _jspx_tagPool_sec_authorize_ifNotGranted.reuse(_jspx_th_sec_authorize_2);
    return false;
  }

  private boolean _jspx_meth_c_url_4(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_4 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_4.setPageContext(_jspx_page_context);
    _jspx_th_c_url_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_2);
    _jspx_th_c_url_4.setValue("/resources/images/statistics_bg.png");
    int _jspx_eval_c_url_4 = _jspx_th_c_url_4.doStartTag();
    if (_jspx_th_c_url_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_4);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_4);
    return false;
  }

  private boolean _jspx_meth_c_url_5(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_5 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_5.setPageContext(_jspx_page_context);
    _jspx_th_c_url_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_2);
    _jspx_th_c_url_5.setValue("/resources/images/statistics_hoverbg.png");
    int _jspx_eval_c_url_5 = _jspx_th_c_url_5.doStartTag();
    if (_jspx_th_c_url_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_5);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_5);
    return false;
  }

  private boolean _jspx_meth_sec_authorize_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sec:authorize
    org.springframework.security.taglibs.authz.JspAuthorizeTag _jspx_th_sec_authorize_3 = (org.springframework.security.taglibs.authz.JspAuthorizeTag) _jspx_tagPool_sec_authorize_ifAnyGranted.get(org.springframework.security.taglibs.authz.JspAuthorizeTag.class);
    _jspx_th_sec_authorize_3.setPageContext(_jspx_page_context);
    _jspx_th_sec_authorize_3.setParent(null);
    _jspx_th_sec_authorize_3.setIfAnyGranted("PERM_MEETING_ADMIN");
    int _jspx_eval_sec_authorize_3 = _jspx_th_sec_authorize_3.doStartTag();
    if (_jspx_eval_sec_authorize_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out.write("\r\n");
      out.write("\t\t<a href=\"../meeting/rule/indexPage\" \r\n");
      out.write("\t\tbg=\"");
      if (_jspx_meth_c_url_6((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_3, _jspx_page_context))
        return true;
      out.write("\" \r\n");
      out.write("\t\tbgHover=\"");
      if (_jspx_meth_c_url_7((javax.servlet.jsp.tagext.JspTag) _jspx_th_sec_authorize_3, _jspx_page_context))
        return true;
      out.write("\">考勤设置</a>\r\n");
      out.write("\t\t");
    }
    if (_jspx_th_sec_authorize_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_3);
      return true;
    }
    _jspx_tagPool_sec_authorize_ifAnyGranted.reuse(_jspx_th_sec_authorize_3);
    return false;
  }

  private boolean _jspx_meth_c_url_6(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_6 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_6.setPageContext(_jspx_page_context);
    _jspx_th_c_url_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_3);
    _jspx_th_c_url_6.setValue("/resources/images/config_bg.png");
    int _jspx_eval_c_url_6 = _jspx_th_c_url_6.doStartTag();
    if (_jspx_th_c_url_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_6);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_6);
    return false;
  }

  private boolean _jspx_meth_c_url_7(javax.servlet.jsp.tagext.JspTag _jspx_th_sec_authorize_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_7 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_7.setPageContext(_jspx_page_context);
    _jspx_th_c_url_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sec_authorize_3);
    _jspx_th_c_url_7.setValue("/resources/images/config_hoverbg.png");
    int _jspx_eval_c_url_7 = _jspx_th_c_url_7.doStartTag();
    if (_jspx_th_c_url_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_7);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_7);
    return false;
  }
}
