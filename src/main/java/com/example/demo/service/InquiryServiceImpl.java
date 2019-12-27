package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.InquiryDao;
import com.example.demo.entity.Inquiry;

@Service  // DIでSingletonとして登録される
public class InquiryServiceImpl implements InquiryService {

	private final InquiryDao dao;  // インターフェース名とする
	
	@Autowired InquiryServiceImpl(InquiryDao dao) {
		this.dao = dao;  // こちらには実装クラスのdao(Impl)が代入される
	}
	
	@Override
	public void save(Inquiry inquiry) {
		dao.insertInquiry(inquiry);
	}

	@Override
	public List<Inquiry> getAll() {
		return dao.getAll();
	}

}
