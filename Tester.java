import edu.duke.*;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Tester TE = new Tester();
		VigenereBreaker VB = new VigenereBreaker (); 
        FileResource fr = new FileResource(); 
    	FileResource dt = new FileResource(); 
    	VB.encrypted = fr.asString(); 
		VB.readDictionary(dt); 
		
	}

	
	
}
