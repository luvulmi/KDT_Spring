package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import model.MemberDAO;
import vo.MemberVO;

@Service
public class MemberService {
	
	//MemberDAO dao = new MemberDAO();
	// => IOC/DI 적용, @ 으로 생성
	@Autowired
	MemberDAO dao;
	
	// 1. selectList
	public List<MemberVO> selectList() {
		return dao.selectList();
	} //selectList
	
	// 2. selectOne : Detail
	public MemberVO selectOne(MemberVO vo) {
		return dao.selectOne(vo);
	} //selectOne
	
	// 3. insert
	public int insert(MemberVO vo) {
		return dao.insert(vo);
	} //insert
	
	// 4. update
	public int update(MemberVO vo) {
		return dao.update(vo);
	} //update
	
	// 5. delete
	public int delete(MemberVO vo) {
		return dao.delete(vo);
	} //delete

} //class
