package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import vo.BoardVO;

//** DAO (Data Access Object)
// => CRUD 구현 
// C: create -> insert
// R: read   -> selectList, selectOne
// U: update -> update
// D: delete -> delete

@Repository
public class BoardDAO {
	// ** 전역변수 정의 
	Connection cn = DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	
	// ** selectList
	public List<BoardVO> selectList() {
		sql = "select seq, id, title, regdate, cnt, root, step, indent from board order by root desc, step asc";
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			st=cn.createStatement();
			rs=st.executeQuery(sql);
			// => 출력자료가 있는지 확인
			//    존재하면 요청한 객체로 전달
			if (rs.next()) {
				// => 출력자료를 1 row 씩 -> vo에 set -> list에 add
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setId(rs.getString(2));
					vo.setTitle(rs.getString(3));
					vo.setRegdate(rs.getString(4));
					vo.setCnt(rs.getInt(5));
					vo.setRoot(rs.getInt(6));
					vo.setStep(rs.getInt(7));
					vo.setIndent(rs.getInt(8));
					list.add(vo);
				}while(rs.next());
				return list;
			}else {
				System.out.println("** B selectList : 출력자료가 1건도 없음 **");
				return null;
			}
		} catch (Exception e) {
			System.out.println("** B selectList Exception => "+e.toString());
			return null;
		}
	} //selectList
	
	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		sql = "select * from board where seq=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			rs=pst.executeQuery();
			// => 결과확인
			if (rs.next()) {
				vo.setSeq(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setRoot(rs.getInt(7));
				vo.setStep(rs.getInt(8));
				vo.setIndent(rs.getInt(9));
				return vo;
			}else {
				System.out.println("** seq 에 해당하는 글자료는 없습니다. **");
				return null;
			}
		} catch (Exception e) {
			System.out.println("** B selectOne Exception => "+e.toString());
			return null;
		}
	} //selectOne
	
	// ** Insert
	// => 새글등록 -> Insert
	public int insert(BoardVO vo) {
		sql="insert into board(id,title,content) "
		   + "values(?,?,?)";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			pst.setString(2, vo.getTitle());
			pst.setString(3, vo.getContent());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** Board_insert Exception => "+e.toString());
			return 0;
		}
	} //insert
	
	// ** Update
	// => 글수정: Title,  Content 컬럼만 수정가능
	public int update(BoardVO vo) {
		sql="update board set title=?, content=? where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getTitle());
			pst.setString(2, vo.getContent());
			pst.setInt(3, vo.getSeq());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** Board_update Exception => "+e.toString());
			return 0;
		}
	} //update
	
	// ** Delete
	public int delete(BoardVO vo) {
		sql="delete from board where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** Member_delete Exception => "+e.toString());
			return 0;
		}
	} //delete
	
	// ** 조회수 증가
	public int countUp(BoardVO vo) {
		sql="update board set cnt=cnt+1 where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** Board_countUp Exception => "+e.toString());
			return 0;
		}
	} //countUp

} //class
