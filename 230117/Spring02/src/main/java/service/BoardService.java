package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.BoardDAO;
import vo.BoardVO;

@Service
public class BoardService {
	@Autowired
	//=> 아래 생성문의 "=" 의 역할 (반드시 생성해야함)
	BoardDAO dao;
	//BoardDAO dao = new BoardDAO();
	
	// ** selectList
	public List<BoardVO> selectList() {
		return dao.selectList();
	}
	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		return dao.selectOne(vo);
	}
	// ** Insert
	public int insert(BoardVO vo) {
		return dao.insert(vo);
	}
	// ** Update
	public int update(BoardVO vo) {
		return dao.update(vo);
	}
	// ** Delete
	public int delete(BoardVO vo) {
		return dao.delete(vo);
	}
	// ** 조회수 증가
	public int countUp(BoardVO vo) {
		return dao.countUp(vo);
	}
	// ** Reply_Insert
	public int rinsert(BoardVO vo) {
		return dao.rinsert(vo);
	}

} //class
