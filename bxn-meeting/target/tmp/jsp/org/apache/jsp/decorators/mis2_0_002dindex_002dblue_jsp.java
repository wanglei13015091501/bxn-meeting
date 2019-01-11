package org.apache.jsp.decorators;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.boxiao.bxn.common.util.PropertiesAccessorUtil;

public final class mis2_0_002dindex_002dblue_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(3);
    _jspx_dependants.add("/commons/taglibs.jsp");
    _jspx_dependants.add("/commons/resource.jsp");
    _jspx_dependants.add("/commons/config.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_scope_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_head_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_if_test;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_title_default_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_body_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_set_var_value_scope_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_head_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_title_default_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_body_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
    _jspx_tagPool_c_set_var_value_scope_nobody.release();
    _jspx_tagPool_decorator_head_nobody.release();
    _jspx_tagPool_c_if_test.release();
    _jspx_tagPool_decorator_title_default_nobody.release();
    _jspx_tagPool_decorator_body_nobody.release();
    _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.release();
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write('\n');

{ 
	org.springframework.security.core.Authentication auth=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication(); 
	cn.boxiao.bxn.base.client.rest.security.LoggedUser loginUser = null;
	if(auth!=null){
		Object principal=auth.getPrincipal(); 
		if(principal instanceof cn.boxiao.bxn.base.client.rest.security.LoggedUser){ 
			loginUser=(cn.boxiao.bxn.base.client.rest.security.LoggedUser)principal; 
		}else{ 
			principal=auth.getDetails(); 
			if(principal instanceof cn.boxiao.bxn.base.client.rest.security.LoggedUser){ 
				loginUser=(cn.boxiao.bxn.base.client.rest.security.LoggedUser)principal; 
			} 
		}	
	}
	pageContext.setAttribute("currentUser", loginUser);
}

      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <link rel=\"icon\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${customResourceUri }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/favicon.ico\" />\n");
      out.write("    ");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_set_1(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_2 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_2.setPageContext(_jspx_page_context);
      _jspx_th_c_set_2.setParent(null);
      _jspx_th_c_set_2.setVar("platformurl");
      _jspx_th_c_set_2.setValue(cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.base_platform_url") );
      int _jspx_eval_c_set_2 = _jspx_th_c_set_2.doStartTag();
      if (_jspx_th_c_set_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_2);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_2);
      out.write('\r');
      out.write('\n');
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_3 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_3.setPageContext(_jspx_page_context);
      _jspx_th_c_set_3.setParent(null);
      _jspx_th_c_set_3.setVar("uicurl");
      _jspx_th_c_set_3.setValue(cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.base_uic_url") );
      int _jspx_eval_c_set_3 = _jspx_th_c_set_3.doStartTag();
      if (_jspx_th_c_set_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_3);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_3);
      out.write('\r');
      out.write('\n');
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_4 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_4.setPageContext(_jspx_page_context);
      _jspx_th_c_set_4.setParent(null);
      _jspx_th_c_set_4.setVar("portalurl");
      _jspx_th_c_set_4.setValue(cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.portal_url") );
      int _jspx_eval_c_set_4 = _jspx_th_c_set_4.doStartTag();
      if (_jspx_th_c_set_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_4);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_4);
      out.write('\r');
      out.write('\n');
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_5 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_5.setPageContext(_jspx_page_context);
      _jspx_th_c_set_5.setParent(null);
      _jspx_th_c_set_5.setVar("helpsystemurl");
      _jspx_th_c_set_5.setValue(cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.help_system_url") );
      int _jspx_eval_c_set_5 = _jspx_th_c_set_5.doStartTag();
      if (_jspx_th_c_set_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_5);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_5);
      out.write('\r');
      out.write('\n');
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_6 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_6.setPageContext(_jspx_page_context);
      _jspx_th_c_set_6.setParent(null);
      _jspx_th_c_set_6.setVar("indexuri");
      _jspx_th_c_set_6.setValue(cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("system.module.index.uri") );
      int _jspx_eval_c_set_6 = _jspx_th_c_set_6.doStartTag();
      if (_jspx_th_c_set_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_6);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_6);
      out.write("\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/global.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/template.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/buttons.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/icons.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/form-layout.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/data-table.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/dialog.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/tab.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/tree.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/panel.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/pagination.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/component.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/reminder.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/tip-box.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/file-uploadify.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/error-page.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/excel-import.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/css/loading.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/uploadify/uploadify.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/sceditor/minified/themes/default.min.css\" />\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar version ='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${version}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("\tvar bxnStaticResRoot = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("\tvar jsCtx = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("    ");

        String currentAppKey = PropertiesAccessorUtil.getProperty("remote.current_app_key");
    
      out.write("\r\n");
      out.write("    var currentAppKey='");
      out.print(currentAppKey);
      out.write("';\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/jquery-1.10.2.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/json2.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/jquery.bgiframe.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/jquery.form.3.46.0.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/bxn-configs.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/jquery.bxn.tools.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/jquery.bxn.control.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/jquery.bxn.component.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/bxn-messages_zh.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/uploadify/jquery.uploadify.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/sceditor/minified/jquery.sceditor.bbcode.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/script/lib/sceditor/languages/cn.js\"></script>\r\n");
      out.write("<div id=\"BROWER-IE9-TIP\" style=\"display:none;z-index:9999;position:fixed;top:0;width:100%;height:62px;color:#FFF;background:url('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/images/brower-tip-bg.png');font-size:18px;text-align:center;\">\r\n");
      out.write("\t<img style=\"margin-right: 6px;\" align=\"absmiddle\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/images/brower-tip-error.png\"/>\r\n");
      out.write("\t<span style=\"margin: 18.5px 0px 18.5px;display: inline-block;\">\r\n");
      out.write("\t\t您的浏览器版本太低，请使用\"IE9以上浏览器\"或\"谷歌浏览器\"(<a style=\"color:red;text-decoration:none;\" target=\"_blank\" href=\"/resourcenormal/download/ChromeStandaloneSetup.exe\">点击下载 </a>)，\r\n");
      out.write("\t\t使用360浏览器<a style=\"color:yellow;text-decoration:none\" target=\"_blank\" href=\"http://help.boxiao.cn/static/360help.html\">点此查看360浏览器说明</a></span>\r\n");
      out.write("</div>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("$(function(){\r\n");
      out.write("\tvar browerinfo = $.bxn.tools.getBrowserInfo();\r\n");
      out.write("\tif(!browerinfo){\r\n");
      out.write("\t\t$(\"#BROWER-IE9-TIP\").show();\r\n");
      out.write("\t}\r\n");
      out.write("\tvar userId='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageScope.currentUser.id}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("\tvar userName='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageScope.currentUser.username}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("\tvar fullName='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageScope.currentUser.fullName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("\tif(userId!=''){\r\n");
      out.write("\t\tvar path=encodeURIComponent(document.location.href);\r\n");
      out.write("\t\tfullName=encodeURIComponent(fullName);\r\n");
      out.write("\t\tvar title=encodeURIComponent(document.title);\r\n");
      out.write("\t\tvar objMetaArray=document.getElementsByTagName(\"meta\");\r\n");
      out.write("\t\tvar metaInfos='{';\r\n");
      out.write("\t\tvar first=true;\r\n");
      out.write("\t\tfor(var i=0;i<objMetaArray.length;i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tvar metaName=objMetaArray[i].name;\r\n");
      out.write("\t\t\tvar metaContent=objMetaArray[i].content;\r\n");
      out.write("\t\t\tif(metaName.indexOf(\"bxn\")==0){\r\n");
      out.write("\t\t\t\tif(!first){\r\n");
      out.write("\t\t\t\t\tmetaInfos+=',';\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tmetaInfos+='\\\"'+metaName+'\\\":\\\"'+metaContent+'\\\"';\r\n");
      out.write("\t\t\t\tfirst=false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tmetaInfos+='}';\r\n");
      out.write("\t\tmetaInfos=encodeURIComponent(metaInfos);\r\n");
      out.write("\t\tvar stateContent=$.ajax({\r\n");
      out.write("\t\t\ttype: 'GET',\r\n");
      out.write("\t\t\tdataType: 'script',\r\n");
      out.write("\t\t\turl: '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${platformurl}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dynamic/resources/statusgather.js?userId='+userId+'&path='+path+'&userName='+userName+'&fullName='+fullName+'&title='+title+'&metaInfos='+metaInfos,\r\n");
      out.write("\t\t\tcache: true\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//unified help system\r\n");
      out.write("\t$(\".top-btn-help\").click(function(){\r\n");
      out.write("\t\tvar uniqueSign = window.location.href.substring($.bxn.tools.getProtocolAndHostName().length);\r\n");
      out.write("\t\tif(uniqueSign.indexOf('?')!=-1){\r\n");
      out.write("\t\t\tuniqueSign = uniqueSign.substring(0,uniqueSign.indexOf('?'));\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar help_url = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${helpsystemurl}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\tif(typeof(currentAppKey)!=\"undefined\" && currentAppKey.length>0){\r\n");
      out.write("\t\t\thelp_url += \"/\"+currentAppKey+\"/\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar help_width = document.body.offsetWidth-126;\r\n");
      out.write("\t\tvar help_height = $(window).height()-126;\r\n");
      out.write("\t\t$(\"<iframe class='help-iframe' src='\"+help_url+\"'></iframe>\").bxnDialog({\r\n");
      out.write("\t\t\ttitle:\"系统帮助\",\r\n");
      out.write("\t\t\twidth:help_width,\r\n");
      out.write("\t\t\theight:help_height,\r\n");
      out.write("\t\t\tico:\"ico-help\",\r\n");
      out.write("\t\t\tonClosed:function(jq){\r\n");
      out.write("\t\t\t\t$(jq).bxnDialog(\"destroy\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}).bxnDialog(\"show\");\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\t//unified quick links system\r\n");
      out.write("\tif(userId!=''){\r\n");
      out.write("\t\t$(\".top-btn-quick\").click(function(){\r\n");
      out.write("\t\t\tvar title=$.trim($('title:eq(0)').html());\r\n");
      out.write("\t\t\tvar uniqueSign = encodeURIComponent(window.location.href);\r\n");
      out.write("\t\t\t$(\"<iframe class='quick-iframe' src='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${portalurl}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/user/commonusedshortcut/form?unwrap=true&title=\"+title+\"&path=\"+uniqueSign+\"'></iframe>\").bxnDialog({\r\n");
      out.write("\t\t\t\ttitle:\"添加常用链接\",\r\n");
      out.write("\t\t\t\twidth:500,\r\n");
      out.write("\t\t\t\theight:250,\r\n");
      out.write("\t\t\t\tico:\"ico-query1\",\r\n");
      out.write("\t\t\t\tonClosed:function(jq){\r\n");
      out.write("\t\t\t\t\t$(jq).bxnDialog(\"destroy\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}).bxnDialog(\"show\");\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("});\r\n");
      out.write("function closeQuickLinkWindow(){\r\n");
      out.write("\t$(\".quick-iframe\").bxnDialog(\"close\");\r\n");
      out.write("}\r\n");
      out.write("</script>");
      out.write("\n");
      out.write("    <title>");
      if (_jspx_meth_decorator_title_0(_jspx_page_context))
        return;
      out.write('_');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${schoolName }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</title>\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta http-equiv=\"Cache-Control\" content=\"no-store\">\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n");
      out.write("    <meta name=\"renderer\" content=\"webkit\">\n");
      out.write("    <meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");
      out.write("    ");
      if (_jspx_meth_decorator_head_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("    <script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/layout-items.js\"></script>\n");
      out.write("    <script>\n");
      out.write("        $(function(){\n");
      out.write("            var licenseData=$.getJSON('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${platformurl}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/api/base-platform/validate-license?moduleName='+currentAppKey,function(licenseData){\n");
      out.write("                if(licenseData.status=='expired') {\n");
      out.write("                    $('#LICENSE-WARN-CONTENT').html('尊敬的客户您好，本模块的授权已到期。剩余使用日期' + licenseData.expired + '天');\n");
      out.write("                    $(\"#LICENSE-WARN\").show();\n");
      out.write("                }else if(licenseData.status=='outservice'){\n");
      out.write("                    $('#LICENSE-WARN-CONTENT').html('尊敬的客户您好，本模块的授权已到期,服务已停止');\n");
      out.write("                    $(\"#LICENSE-WARN\").show();\n");
      out.write("                }\n");
      out.write("            });\n");
      out.write("\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div id=\"LICENSE-WARN\" style=\"display:none;z-index:9999;position:fixed;top:0;width:100%;height:62px;color:red;background:url('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/images/brower-tip-bg.png');font-size:18px;text-align:center;\">\n");
      out.write("    <img style=\"margin-right: 6px;\" align=\"absmiddle\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/images/brower-tip-error.png\"/>\n");
      out.write("    <span style=\"margin: 18.5px 0px 18.5px;display: inline-block;\" id=\"LICENSE-WARN-CONTENT\"></span>\n");
      out.write("</div>\n");
      out.write("<div class=\"index-view-area\">\n");
      out.write("    <div class=\"index-page-head\">\n");
      out.write("        <div class=\"head-left\">\n");
      out.write("            <span>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${schoolName }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</span>\n");
      out.write("            <span class=\"product-name\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${platFormName }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</span>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"head-right\">\n");
      out.write("            <span class=\"system-name\">");
      if (_jspx_meth_decorator_getProperty_0(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("            <span class=\"system-desc\">");
      if (_jspx_meth_decorator_getProperty_1(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"index-page-main\">\n");
      out.write("        ");
      if (_jspx_meth_decorator_body_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("<div class=\"index-page-bg\">\n");
      out.write("    <img alt=\"\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${staticResRoot }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/skin/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${skinType}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/images/index-page-bg.jpg\" width=\"100%\" height=\"100%\">\n");
      out.write("    ");
      if (_jspx_meth_c_if_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("</div>\n");
      out.write("</body>\n");
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

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_scope_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_set_0.setScope("request");
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_scope_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_scope_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }

  private boolean _jspx_meth_c_set_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_scope_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_1.setPageContext(_jspx_page_context);
    _jspx_th_c_set_1.setParent(null);
    _jspx_th_c_set_1.setVar("ctx");
    _jspx_th_c_set_1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_set_1.setScope("request");
    int _jspx_eval_c_set_1 = _jspx_th_c_set_1.doStartTag();
    if (_jspx_th_c_set_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_scope_nobody.reuse(_jspx_th_c_set_1);
      return true;
    }
    _jspx_tagPool_c_set_var_value_scope_nobody.reuse(_jspx_th_c_set_1);
    return false;
  }

  private boolean _jspx_meth_decorator_title_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:title
    com.opensymphony.module.sitemesh.taglib.decorator.TitleTag _jspx_th_decorator_title_0 = (com.opensymphony.module.sitemesh.taglib.decorator.TitleTag) _jspx_tagPool_decorator_title_default_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.TitleTag.class);
    _jspx_th_decorator_title_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_title_0.setParent(null);
    _jspx_th_decorator_title_0.setDefault((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${platFormName }", java.lang.String.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_decorator_title_0 = _jspx_th_decorator_title_0.doStartTag();
    if (_jspx_th_decorator_title_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_title_default_nobody.reuse(_jspx_th_decorator_title_0);
      return true;
    }
    _jspx_tagPool_decorator_title_default_nobody.reuse(_jspx_th_decorator_title_0);
    return false;
  }

  private boolean _jspx_meth_decorator_head_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:head
    com.opensymphony.module.sitemesh.taglib.decorator.HeadTag _jspx_th_decorator_head_0 = (com.opensymphony.module.sitemesh.taglib.decorator.HeadTag) _jspx_tagPool_decorator_head_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.HeadTag.class);
    _jspx_th_decorator_head_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_head_0.setParent(null);
    int _jspx_eval_decorator_head_0 = _jspx_th_decorator_head_0.doStartTag();
    if (_jspx_th_decorator_head_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
      return true;
    }
    _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
    return false;
  }

  private boolean _jspx_meth_decorator_getProperty_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:getProperty
    com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag _jspx_th_decorator_getProperty_0 = (com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag) _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag.class);
    _jspx_th_decorator_getProperty_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_getProperty_0.setParent(null);
    _jspx_th_decorator_getProperty_0.setProperty("body.title");
    _jspx_th_decorator_getProperty_0.setWriteEntireProperty("false");
    int _jspx_eval_decorator_getProperty_0 = _jspx_th_decorator_getProperty_0.doStartTag();
    if (_jspx_th_decorator_getProperty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_0);
      return true;
    }
    _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_0);
    return false;
  }

  private boolean _jspx_meth_decorator_getProperty_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:getProperty
    com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag _jspx_th_decorator_getProperty_1 = (com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag) _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag.class);
    _jspx_th_decorator_getProperty_1.setPageContext(_jspx_page_context);
    _jspx_th_decorator_getProperty_1.setParent(null);
    _jspx_th_decorator_getProperty_1.setProperty("body.text");
    _jspx_th_decorator_getProperty_1.setWriteEntireProperty("false");
    int _jspx_eval_decorator_getProperty_1 = _jspx_th_decorator_getProperty_1.doStartTag();
    if (_jspx_th_decorator_getProperty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_1);
      return true;
    }
    _jspx_tagPool_decorator_getProperty_writeEntireProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_1);
    return false;
  }

  private boolean _jspx_meth_decorator_body_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:body
    com.opensymphony.module.sitemesh.taglib.decorator.BodyTag _jspx_th_decorator_body_0 = (com.opensymphony.module.sitemesh.taglib.decorator.BodyTag) _jspx_tagPool_decorator_body_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.BodyTag.class);
    _jspx_th_decorator_body_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_body_0.setParent(null);
    int _jspx_eval_decorator_body_0 = _jspx_th_decorator_body_0.doStartTag();
    if (_jspx_th_decorator_body_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
      return true;
    }
    _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
    return false;
  }

  private boolean _jspx_meth_c_if_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_0.setPageContext(_jspx_page_context);
    _jspx_th_c_if_0.setParent(null);
    _jspx_th_c_if_0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${version ne null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null)).booleanValue());
    int _jspx_eval_c_if_0 = _jspx_th_c_if_0.doStartTag();
    if (_jspx_eval_c_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("    \t<div style=\"position: absolute; bottom: 25px; right: 20px; color: #0071F0; font-style: italic;\">版本:");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${version}", java.lang.String.class, (PageContext)_jspx_page_context, null));
        out.write("</div>\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_if_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
      return true;
    }
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
    return false;
  }
}
