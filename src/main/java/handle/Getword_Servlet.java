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

public class Getword_Servlet extends HttpServlet{
	Mysql ms;
	RSAtool rsa;
	
	public static String ERRALERT = "<html><head><meta charset=\"utf-8\"></head></html><script>alert(\"正常情况下,你不应该看见这个弹窗,你一定是做了不正常的事.\");var r=confirm(\"你想要访问这个项目吗? -> https://github.com/xmexg/OneffWord\");if(r){window.location.replace(\"https://github.com/xmexg/OneOffWord\");}else{window.location.replace(\"/OneOffWord\");}</script>";
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		ms = new Mysql();
		rsa = new RSAtool();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id_c = request.getParameter("id");
		System.out.println("id:"+id_c);
		String info[] = new String[3];
		info = ms.getOneWord(id_c);
		for(String s : info) {
			System.out.println("信息:"+s);
		}
		String result = null;
		if(info != null) {
//			result = "{\"" + info[0]+ "\",\"" + info[1] + "\",\"" + info[2] + "\"}";
			result = info[2] + "," + info[1] + "," + info[0];
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(ERRALERT);
		}
}
