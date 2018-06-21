package sbpayment.jp.intro;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
	
	
	try {
		sendMessage();
	} catch (IOException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
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
public void sendMessage() throws IOException {
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

SlackSession session =
SlackSessionFactory.createWebSocketSlackSession(token);
System.out.println(token);
session.connect();

SlackChannel channel = session.findChannelByName("fy18-sys-training");
String message = "・・・の無料期間が明日終了します。";
session.sendMessage(channel, message);

session.disconnect();

}
//public static void main(String[] args) throws IOException {
//
//    // BotのAPI Tokenを設定
//    SlackSession session = SlackSessionFactory.createWebSocketSlackSession("トークン");
//    System.out.println(session);
//    session.connect();
//
//    SlackChannel channel = session.findChannelByName("fy18-sys-training");
//    String message = "Javaからの送信テスト　藤永";
//    session.sendMessage(channel, message);
//
//    session.disconnect();
//
//  }


// public static void main(String[] args) {
// try {
// //Fileクラスに読み込むファイルを指定する
// File file = new File("/Users/rfujinaga/Desktop/slack_tk.txt");
//
// //ファイルが存在するか確認する
// if(file.exists()) {
//
// //FileReaderクラスのオブジェクトを生成する
// FileReader filereader = new FileReader(file);
//
// //filereaderクラスのreadメソッドでファイルを1文字ずつ読み込む
// int data;
// while((data = filereader.read()) != -1) {
// System.out.print((char) data);
//
// }
//
// //ファイルクローズ
// filereader.close();
//
// } else {
// System.out.print("ファイルは存在しません");
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// }

// slackでメッセージを投げる

}
