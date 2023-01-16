package vo;

//** DTO (Data Transfer Object)
//=> jo 별 통계값 처리를 위한 DTO 
//=> private 맴버변수, getter/setter, toString()

public class GroupDTO {
	private int jno;
	private String jname;
	private int count;
	private int sum;
	private double avg;
	private int min;
	private int max;
	
	public int getJno() {
		return jno;
	}
	public void setJno(int jno) {
		this.jno = jno;
	}
	public String getJname() {
		return jname;
	}
	public void setJname(String jname) {
		this.jname = jname;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	@Override
	public String toString() {
		return "GroupDTO [jno=" + jno + ", count=" + count + ", sum=" + sum + ", avg=" + avg
				+ ", min=" + min + ", max=" + max + ", jname=" + jname + "]";
	}
} //class
