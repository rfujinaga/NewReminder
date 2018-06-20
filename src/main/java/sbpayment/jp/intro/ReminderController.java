package sbpayment.jp.intro;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReminderController {
	
@GetMapping("/top")
public String top(Model model) {
	return "top";
}

@GetMapping("/new")
public String registration(Model model) {
	return "new";
}
	
@GetMapping("/list")
public String list(Model model) {
	model.addAttribute("data",jdbc.queryForList("SELECT * from service"));
	return "list";
}

@GetMapping("/detail/{id}")
public String detail(@PathVariable("id") String id,RedirectAttributes attr) {
	attr.addFlashAttribute("id",jdbc.queryForList("SELECT * FROM service WHERE id = ?",id).get(0).get("id"));
	attr.addFlashAttribute("mailaddress",jdbc.queryForList("SELECT mailaddress FROM service WHERE id = ?",id).get(0).get("mailaddress"));
	attr.addFlashAttribute("card_bra",jdbc.queryForList("SELECT card_bra FROM service WHERE id = ?",id).get(0).get("card_bra"));
	attr.addFlashAttribute("card_num",jdbc.queryForList("SELECT card_num FROM service WHERE id = ?",id).get(0).get("card_num"));
	attr.addFlashAttribute("s_id",jdbc.queryForList("SELECT s_id FROM service WHERE id = ?",id).get(0).get("s_id"));
	attr.addFlashAttribute("password",jdbc.queryForList("SELECT password FROM service WHERE id = ?",id).get(0).get("password"));
	attr.addFlashAttribute("other",jdbc.queryForList("SELECT other FROM service WHERE id = ?",id).get(0).get("other"));
	return "redirect:/detail";
}

@GetMapping("/detail")
public String detail2() {
	return "detail";
}

@PostMapping("/delete")
public String delete(String id, RedirectAttributes attr) {
	attr.addFlashAttribute("id",id);
	jdbc.update("DELETE FROM service WHERE id = ?",id);
	return "redirect:/list";
}





//@PostMapping("/list")
//public String deleteGet(@RequestParam("id") String id,RedirectAttributes attr) {
//    jdbc.update("DELETE FROM service WHERE id = ?", id);
//    return "redirect:/list";
//}

@Autowired
private JdbcTemplate jdbc;


@PostMapping("/post")
public String Post(String id,String s_name, String r_date, String t_period, String mailaddress, String card_bra, String card_num, String s_id, String password, String other, RedirectAttributes attr){
	attr.addFlashAttribute("id",id);
	attr.addFlashAttribute("s_name", s_name);
    attr.addFlashAttribute("r_date", r_date);
    attr.addFlashAttribute("t_period", t_period);
    attr.addFlashAttribute("mailaddress", mailaddress);
	attr.addFlashAttribute("card_bra", card_bra);
	attr.addFlashAttribute("card_num", card_num);
	attr.addFlashAttribute("s_id", s_id);
	attr.addFlashAttribute("password", password);
	attr.addFlashAttribute("other", other);
    
    jdbc.update("INSERT INTO service (s_name, r_date, t_period,mailaddress,card_bra,card_num,s_id,password,other) VALUES (?,?,?,?,?,?,?,?,?)", s_name,r_date,t_period,mailaddress,card_bra,card_num,s_id,password,other);
    attr.addFlashAttribute("data",jdbc.queryForList("SELECT * from service")); 
    
    return "redirect:/list";
}
public class SimpleMessageSender {

	private static final String postUrl = "https://slack.com/api/chat.postMessage";

	public String send(String token, String channel, String text)
			throws UnsupportedEncodingException, IOException {

		byte[] postData = String.format("token=xoxp-22821387959-360716604117-383968597056-75a182173b369268ca0480f19d8c3725&channel=UALM2HS3F&text=テスト", token,
				channel, URLEncoder.encode(text, "utf-8")).getBytes();

		HttpURLConnection conn = (HttpURLConnection) new URL(postUrl).openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}

		try (Scanner s = new Scanner(conn.getInputStream())) {
			return s.useDelimiter("\\A").hasNext() ? s.useDelimiter("\\A").next() : "";
		}
	}
}
}




