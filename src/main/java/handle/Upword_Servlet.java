package handle;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Mysql;
import util.RSAtool;


/**
 * 存放文本
 * 传入:文本,阅读限时
 * 传回:id
 *
 */
	public class Upword_Servlet extends HttpServlet{
	Mysql ms;
	RSAtool rsa;//已弃用
	public static String ERRALERT = "<html><head><meta charset=\"utf-8\"></head></html><script>alert(\"正常情况下,你不应该看见这个弹窗,你一定是做了不正常的事.\");var r=confirm(\"你想要访问这个项目吗? -> https://github.com/xmexg/OneffWord\");if(r){window.location.replace(\"https://github.com/xmexg/OneOffWord\");}else{window.location.replace(\"/OneOffWord\");}</script>";
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		ms = new Mysql();
		rsa = new RSAtool();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String word_c = request.getParameter("word");
		String time_c = request.getParameter("time");
		System.out.println("接收到文本:"+word_c+"\n接收到时间:"+time_c);
		String id = ms.setOneWord(word_c, time_c);
		if(id == null) {
			id = "保存文本失败,请稍后重试";
		}else {
			id = "密文id为:" + id;
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(id);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(ERRALERT);
		}

}
