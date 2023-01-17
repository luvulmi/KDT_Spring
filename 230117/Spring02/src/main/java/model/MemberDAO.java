package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import vo.MemberVO;
import vo.StudentVO;

//** DAO(Data Access Object)
//=> SQL 구문 처리
//=> CRUD 구현 
// Create(insert), Read(select), Update, Detete

@Repository
public class MemberDAO {
	
	// ** 전역변수 정의
	private static Connection cn=DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	
	// 1. selectList
	public List<MemberVO> selectList() {
		sql="select * from member";
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			st=cn.createStatement();
			rs=st.executeQuery(sql);
			if (rs.next()) {
				do {
					MemberVO vo = new MemberVO(); 
					vo.setId(rs.getString(1));  
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setAge(rs.getInt(4));
					vo.setJno(rs.getInt(5));
					vo.setInfo(rs.getString(6));
					vo.setPoint(rs.getDouble(7));
					vo.setBirthday(rs.getString(8));
					vo.setRid(rs.getString(9));
					vo.setUploadfile(rs.getString(10));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** selectList : 출력자료가 1건도 없음 **");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception => "+e.toString());
			list=null;
		}
		return list;
	} //selectList
	
	// 2. selectOne : Detail
	public MemberVO selectOne(MemberVO vo) {
		sql = "select * from member where id=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			rs=pst.executeQuery();
			if ( rs.next() ) {
				// select 성공
				vo.setId(rs.getString(1));  
				vo.setPassword(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setAge(rs.getInt(4));
				vo.setJno(rs.getInt(5));
				vo.setInfo(rs.getString(6));
				vo.setPoint(rs.getDouble(7));
				vo.setBirthday(rs.getString(8));
				vo.setRid(rs.getString(9));
				vo.setUploadfile(rs.getString(10));
			}else {
				// select 실패
				vo=null;
			}
		} catch (Exception e) {
			System.out.println("** selectOne Exception => "+e.toString());
			vo=null;
		}
		return vo;
	} //selectOne

	// 3. insert
	public int insert(MemberVO vo) {
		sql="insert into member values(?,?,?,?,?,?,?,?,?,?)";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			pst.setString(2, vo.getPassword());
			pst.setString(3, vo.getName());
			pst.setInt(4, vo.getAge());
			pst.setInt(5, vo.getJno());
			pst.setString(6, vo.getInfo());
			pst.setDouble(7, vo.getPoint());
			pst.setString(8, vo.getBirthday());
			pst.setString(9, vo.getRid());
			pst.setString(10, vo.getUploadfile());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** insert Exception => "+e.toString());
			return 0;
		}
	} //insert
	
	// 4. update
	public int update(MemberVO vo) {
		sql="update member set password=?, name=?, age=?, jno=?, "
							 + "info=?, point=?, birthday=? where id=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getPassword());	
			pst.setString(2, vo.getName());	
			pst.setInt(3, vo.getAge());	
			pst.setInt(4, vo.getJno());	
			pst.setString(5, vo.getInfo());	
			pst.setDouble(6, vo.getPoint());
			pst.setString(7, vo.getBirthday());
			pst.setString(8, vo.getId());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception => "+e.toString());
			return 0;
		}
	} //update
	
	// 5. delete
	public int delete(MemberVO vo) {
		sql="delete from member where id=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** delete Exception => "+e.toString());
			return 0;
		}
	} //delete
	
} //class
