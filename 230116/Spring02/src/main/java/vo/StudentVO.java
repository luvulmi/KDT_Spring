package vo;

//** VO (Value Object) , DTO (Data Transfer Object)
//=> 자료의 구조를 표현하는 클래스이며, 자료의 전달에 이용됨
//=> 대부분 Table 별로 만들며, Table 과 동일한 필드명을 사용한다.
//=> Table과 무관하게 자료전달용으로만 정의한 경우 DTO 라 함.

//=> 맴버변수 : private
//=> 외부에서는 setter/getter 로 접근

public class StudentVO  {
	
	private String id;
	private String name;
	private int age;
	private int jno;
	private String info;
	private Double point;
	private String birthday;
	
	// ** static Test
	static String country="Korea";
	
	// ** 생성자 메서드
	// => class명과 이름동일, return값(void포함) 없음
	// => default 생성자
	//		-> 작성한 생성자가 없으면 컴파일러가 자동생성
	// 		-> 생성자를 1개라도 작성하면 지동생성은 안됨
	//		-> 매개변수가 없음
	/*
	public StudentVO() { System.out.println("** default 생성자 **"); }
	
	// => 모든컬럼을 초기화 하는 생성자
	public StudentVO(String id, String name, int age, int jno, String info, Double point, String birthday) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.jno = jno;
		this.info = info;
		this.point = point;
		this.birthday = birthday;
	}
	*/
	public String getId() { return id; }
	public void setId(String id) { this.id=id; }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getJno() {
		return jno;
	}
	public void setJno(int jno) {
		this.jno = jno;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "StudentVO [id=" + id + ", name=" + name + ", age=" + age + ", jno=" + jno + ", info=" + info
				+ ", point=" + point + ", birthday=" + birthday + "]";
	}
} //class
