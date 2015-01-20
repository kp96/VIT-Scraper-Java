package login.attendance;



import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InitialTest {

    //The url of the website. This is just an example
    private static final String webSiteURL = "https://academics.vit.ac.in/parent/parent_login_submit.asp";
    private static final String attendanceURL = "https://academics.vit.ac.in/parent/attn_report.asp?sem=WS";
    //wdregno,wdpswd,vrfcd
    //The path of the folder that you want to save the images to
    private static final String folderPath = "somepath";

    public static void main(String[] args) throws IOException {
    	Response res = 	Jsoup.connect(webSiteURL).method(Method.GET).timeout(30000).execute();
    	Map<String,String> cook = res.cookies();
    	//String sessionid = res.cookie("ASPSESSIONIDSQHDAQRQ");
    	//System.out.println(res.cookies());
    	byte[] cap =  Jsoup.connect("https://academics.vit.ac.in/parent/captcha.asp")
                .cookies(res.cookies())
                .ignoreContentType(true)
                .method(Method.GET).timeout(30000).execute().bodyAsBytes();
    	
    	FileOutputStream out = new FileOutputStream(new java.io.File(folderPath,"captcha.bmp"));
    	out.write(cap);
    	out.close();
    	/*Response resultImageResponse = Jsoup.connect("https://academics.vit.ac.in/parent/captcha.asp").cookie("ASPSESSIONIDSQHDAQRQ", sessionid).ignoreContentType(true).execute();
    	System.out.println(resultImageResponse.body());
        // output here
        FileOutputStream out = (new FileOutputStream(new java.io.File("C:\\Users\\kp\\desktop\\scrape" + "krishna.asp")));
        out.write(resultImageResponse.bodyAsBytes());           // resultImageResponse.body() is where the image's contents are.
        out.close();*/
    	String captcha = new String();
    	Scanner sc = new Scanner(System.in);
    	String regno = new String();
    	System.out.println("Enter regno");
    	regno = sc.next();
    	System.out.println("Enter dob");
    	String password = new String();
    	password = sc.next();
    	System.out.println("Enter captcha");
    	captcha = sc.next();
    	
    	Response login = Jsoup.connect(webSiteURL)
    			.userAgent("Mozilla/5.0")
    			.cookies(res.cookies()).
    			data("message","")
    			.data("vrfcd",captcha).data("wdpswd",password).data("wdregno",regno)
    			.method(Method.POST).timeout(30000).execute();
    	Response attendance = Jsoup.connect(attendanceURL).userAgent("Mozilla/5.0")
    			.cookies(res.cookies()).timeout(30000).method(Method.GET).execute();
    	
    	Document att = attendance.parse();
    	Element table = att.getElementsByTag("table").get(2);
    	Elements entries = table.getElementsByTag("tr");
    	int i = 0;
    	for(Element entry : entries){
    			++i;
    			if(i==1)
    				continue;
    			System.out.print(entry.getElementsByTag("td").get(0).text() + "  ");
    			System.out.print(entry.getElementsByTag("td").get(1).text() + "  ");
    			System.out.print(entry.getElementsByTag("td").get(6).text() + "  ");
    			System.out.print(entry.getElementsByTag("td").get(7).text() + "  ");
    			System.out.print(entry.getElementsByTag("td").get(8).text() + "  ");
    		
    		System.out.println();
    	}
    	//System.out.println(Jsoup.connect("https://academics.vit.ac.in/parent/home.asp").cookie("ASPSESSIONIDSQHDAQRQ", sessionid).get());
    	/*Response res = Jsoup.connect(webSiteURL).data("txtUserLoginID","pavaone").data("txtPassword","kkp1996")
    			.data("hiddenAction","ValidateUser").method(Method.POST).execute();
    	String sessionid = 
    	   res.cookie("JSESSIONID");
    	System.out.print(sessionid);
    	System.out.println(res.parse());
    	*/
    }
    
}
