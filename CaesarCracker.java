import edu.duke.*;

public class CaesarCracker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
    //class mostCommon constructor - assumes 'e'
	char mostCommon;
    //declare 'e' as the mostCommon
    public CaesarCracker() {
        mostCommon = 'e';
    }

    // class constructor with c for mostCommon char parameter
    public CaesarCracker(char c) {
        mostCommon = c;
    }
		// TODO Auto-generated constructor stub
	
	//pass the message to countLetters and return an int array counts
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        //create integer array size 26
        int[] counts = new int[26];
        //for each character in the message (start at 0, increment +1, until reach the message length
        for(int k=0; k < message.length(); k++){
        	//dex is = to the location of the character in the message
            int dex = alph.indexOf(Character.toLowerCase(message.charAt(k)));
            //if dex exists (!= -1)
            if (dex != -1){
            	//then add one to that location
                counts[dex] += 1;
            }
        }
        //return array of counts at each letter
        return counts;
    }
    
    //maxIndex uses vals is an array of integers and returns the most frequent letter
    public int maxIndex(int[] vals){
    	//initialize maxDex integer as 0
    	int maxDex = 0;
    	//loop through the array of locations + counts
        for(int k=0; k < vals.length; k++){
        	//if the count is > than the maxDex 
        	if (vals[k] > vals[maxDex]){
        		//then make maxDex the count
                maxDex = k;
            }
        }
        //return integer for the letter that occurs most frequently
        return maxDex;
    }

    //pass encrypted message to getKey and return an integer of dkey
    public int getKey(String encrypted){
    	//initialize integer array freqs as countLetters(encrypted) - returns the number of each letter
        int[] freqs = countLetters(encrypted);
        //initialize maxDex as maxIndex using freqs array - returns the most frequent letter
        int maxDex = maxIndex(freqs);
        //initialize most common position position as distance from 'a' as an integer
        int mostCommonPos = mostCommon - 'a';
        //decrypt key will be the most frequent char in the encrypted message LESS the mostCommonPosition integer
        int dkey = maxDex - mostCommonPos;
        //if the mostfrequent char is less than the most common position, wrap alphabet
        if (maxDex < mostCommonPos) {
            dkey = 26 - (mostCommonPos-maxDex);
        }
        //return decyrpt key
        return dkey;
    }
    
    //decrypt method - pass encrypted string
    public String decrypt(String encrypted){
    	//key is getKey using encrypted message
    	int key = getKey(encrypted);
    	//call new instance of CaesarCipher class and pass it the decrypt key
        CaesarCipher cc = new CaesarCipher(key);
        // return the CaesarCipher decrypt method passing the encrypted message
        return cc.decrypt(encrypted);
        
    }
   

}
