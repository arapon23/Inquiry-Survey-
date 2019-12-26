package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

// import com.example.demo.service.InquiryNotFoundException;

// 必要な時に自動的にインスタンス化される
@ControllerAdvice 
public class WebMvcControllerAdvice {

	/*
	 * こちらのメソッドを用意しておくと送信された空文字はnullに変換される
	 */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String handleException(EmptyResultDataAccessException e,Model model) {
		model.addAttribute("message", e);
		return "error/CustomPage";
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e,Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
   
}
