package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
		return "inquiry/index";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model,
			// フラッシュスコープの値(データ)を受け取るために、引数を追加する
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";  // inquiryフォルダのform.htmlファイルの意。
	}
	
	@PostMapping("/form") // 「戻るボタン」でアクセスされる際の記述(Post)
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryform, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "inquiry/confirm";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		
		// データベース操作をする
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
	// セッションにデータが格納され、一度"Registered!"が表示されるとデータが破棄される(フラッシュスコープ)
	// 二重クリックを防ぐためにリダイレクトさせる
	//リダイレクトでは、model.addAttributeは使用不可の為(addFrashArrribute()を使用)	
		redirectAttributes.addFlashAttribute("complete", "Registered!");  
		return "redirect:/inquiry/form";  // リダイレクトの場合: HTMLファイルではなく、このときURLを指す
	}

}
