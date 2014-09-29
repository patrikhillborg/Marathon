public class Person{

	private String namn;
	private String land;
	private int �lder;
	private int position;
	private double tiden;

	public Person(int position, String namn, String land, int �lder) { //int tidVar
		this.position = position;
		this.namn = namn;
		this.land = land;
		this.�lder = �lder;
		
	}
	
	public int getPosition(){
		return position;
	}

	public String getNamn() {
		return namn;
	}

	public String getLand() {
		return land;
	}

	public int get�lder() {
		return �lder;
	}
	
	public double getTiden(){
		return tiden;
	}
	
	public void setTiden(double tiden){
		this.tiden = tiden;
	}
	
	public String toString(){
		if(tiden < 1)
			return position + ", " + namn + ", " + land + ", " + �lder + ", --";
		else {
			return position + ", " + namn + ", " + land + ", " + �lder + ", " + tiden;
		}
	}
}