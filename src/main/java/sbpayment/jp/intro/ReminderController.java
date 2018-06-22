package sbpayment.jp.intro;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

@Controller
public class ReminderController {
	
@GetMapping("/top")
public String top(Model model) {
	
	Map<String, Object> difference = jdbc.queryForList("SELECT DateDiff(day,getdate(),t_period)FROM service").get(0);
	int dif = Integer.valueOf(difference.toString());
	List<Integer> datelist = new ArrayList<>();
	
	datelist.add(dif);

	System.out.println("datelist"+ datelist);
	if(Arrays.asList(datelist).contains(0)) {
		try {
			sendMessage2();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}else if(Arrays.asList(datelist).contains(1)) {
		try {
			sendMessage1();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}else {
	}
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

//期間終了日前日メッセージ送信メソッド
//外部ファイルからトークンを読み込む
public void sendMessage1() throws IOException {
File file = new File("/Users/rfujinaga/Desktop/slack_tk.txt");
String token = "";
if(file.exists()) {

FileReader filereader = new FileReader(file);

int data;
while((data = filereader.read()) != -1) {
System.out.print((char) data);
token = token + String.valueOf((char)data);
}
filereader.close();
}
//以降Slackでメッセージを送る
SlackSession session =
SlackSessionFactory.createWebSocketSlackSession(token);
System.out.println(token);
session.connect();

SlackChannel channel = session.findChannelByName("fy18-sys-training");
String message = "明日無料期間が終了するサービスがあります。解約するなら忘れずに！";
session.sendMessage(channel, message);

session.disconnect();
}

//期間終了日当日メッセージ送信メソッド
public void sendMessage2() throws IOException {
File file = new File("/Users/rfujinaga/Desktop/slack_tk.txt");
String token = "";
if(file.exists()) {

FileReader filereader = new FileReader(file);

int data;
while((data = filereader.read()) != -1) {
System.out.print((char) data);
token = token + String.valueOf((char)data);
}
filereader.close();
}
//以降Slackでメッセージを送る
SlackSession session =
SlackSessionFactory.createWebSocketSlackSession(token);
System.out.println(token);
session.connect();

SlackChannel channel = session.findChannelByName("fy18-sys-training");
String message = "本日が無料期間終了日のサービスがあります。解約するなら忘れずに！";
session.sendMessage(channel, message);

session.disconnect();
}
}
