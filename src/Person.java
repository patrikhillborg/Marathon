public class Person{

	private String namn;
	private String land;
	private int Âlder;
	private int position;
	private double tiden;

	public Person(int position, String namn, String land, int Âlder) { //int tidVar
		this.position = position;
		this.namn = namn;
		this.land = land;
		this.Âlder = Âlder;
		
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

	public int get≈lder() {
		return Âlder;
	}
	
	public double getTiden(){
		return tiden;
	}
	
	public void setTiden(double tiden){
		this.tiden = tiden;
	}
	
	public String toString(){
		if(tiden < 1)
			return position + ", " + namn + ", " + land + ", " + Âlder + ", --";
		else {
			return position + ", " + namn + ", " + land + ", " + Âlder + ", " + tiden;
		}
	}
}