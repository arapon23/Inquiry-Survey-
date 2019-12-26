package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")  // ドメイン以降のURLとなる
public class SampleController {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	// コンテナが自動生成したインスタンスが引数として、代入される
	public SampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@GetMapping("/test")
	public String test(Model model) {
		
		String sql = "SELECT id, name, email "
				+ "FROM inquiry WHERE id = 2";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		
		model.addAttribute("title", "Inquiry Form");  // HTMLに渡すデータを設定する
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));
		return "test";  //Stringを戻り値としたtest.htmlファイルをViewに渡す
	}
	
}
