/**
 * Horse class to instantiate new horse objects and 
 *  
 * @Lola Popoola(your name) 
 * @30/03/2024 (a version number or a date)
 */
	public class Horse
	{
		public static void main(String[] args) {
			//horse 1: white knight
		        Horse myHorse1 = new Horse('\u2658', "PIPPI LONGSTOCKING", 0.6);
		        System.out.println(myHorse1.getSymbol());
		        System.out.println(myHorse1.getName());
		        System.out.println("(Current confidence "+ myHorse1.getConfidence()+")");

		      //horse 2: black knight
		        Horse myHorse2 = new Horse('\u265E', "KIKOMO", 0.5);
		        System.out.println(myHorse2.getSymbol());
		        System.out.println(myHorse2.getName());
		        System.out.println("(Current confidence "+ myHorse2.getConfidence()+")");
		        
		      //horse 3: crown
		        Horse myHorse3 = new Horse('j', "EL JEFE", 0.4);
		        System.out.println(myHorse3.getSymbol());
		        System.out.println(myHorse3.getName());
		        System.out.println("(Current confidence "+ myHorse3.getConfidence()+")");
		        
	}
		 

	    //Fields of class Horse
	    private String horseName;
	    private char horseSymbol;
	    private int distance;
	    private boolean hasFallen;
	    private double horseConfidence;
	      
	    //Constructor of class Horse
	    
	    public Horse(char horseSymbol, String horseName, double horseConfidence)
	    {
	       this.horseSymbol=horseSymbol;
	       this.horseName=horseName;
	       this.horseConfidence=horseConfidence;
	    }
	  
	    //Other methods of class Horse
	    public void fall()
	    {
	        this.hasFallen=true;
	    }
	    
	    public double getConfidence()
	    {
	        return this.horseConfidence;
	    }
	    
	    public int getDistanceTravelled()
	    {
	        return this.distance;
	    }
	    
	    public String getName()
	    {
	        return this.horseName;
	    }
	    
	    public char getSymbol()
	    {
	        return this.horseSymbol;
	    }
	    
	    public void goBackToStart()
	    {
	        this.distance=0;
	    }
	    
	    public boolean hasFallen()
	    {
	        return this.hasFallen;
	    }
	
	    public void moveForward()
	    {
	        this.distance++;
	    }
	
	    public void setConfidence(double newConfidence)
	    {
	        this.horseConfidence=newConfidence;
	    }
	    
	    public void setSymbol(char newSymbol)
	    {
	        this.horseSymbol=newSymbol;
	    }
	    
	}
	 
	
