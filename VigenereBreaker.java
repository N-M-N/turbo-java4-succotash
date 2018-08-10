import java.io.File;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	VigenereBreaker VB = new VigenereBreaker (); 
    	VB.breakVigenere();
    	}
	
	String encrypted; 
	HashMap<String,HashSet<String>> languages = new HashMap<String,HashSet<String>> (); 
	HashSet<String> hs = new HashSet<String> (); 
	
    public void breakVigenere () {
    	//get encrypted message from FileResource
    	FileResource fr = new FileResource(); 
    	// make the message a String
    	encrypted = fr.asString(); 
    	// Get the folder with all the dictionaries 
    	DirectoryResource dr = new DirectoryResource();
    		// get each dictionary from DirectoryResource
			for(File f: dr.selectedFiles()) {
				//use FileResource to access the dictionary
				FileResource dt = new FileResource(f);
				// add the file name to languages key and build the languages HashMap for break for all languages
				languages.put(f.getName(), readDictionary(dt));
				// run breakFoaAllLanguages and pass each dictionary
			}
			breakForAllLanguages(encrypted, languages); 

    }
	
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder(); 
        for(int k = whichSlice; k < message.length(); k += totalSlices) {
        	sb.append(message.substring(k, k+1));
        }
    	String sliceMessage = sb.toString();
        return sliceMessage;
    }
/*Find the shift for each index in the key
 * String encrypted is the encrypted message
 * klength is the length of each slice key 
 * mostCommon is the letter most common in the language of the message 
 * Encrypted message = sliceString (1) x4 pass to 
 * CaesarCracker CR.decrypt 
 */
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
    	CaesarCracker CR = new CaesarCracker(mostCommon); 
    	int[] key = new int[klength];
    	String ccMessage = null; 
        for(int k = 0; k < klength; k++) {
        	ccMessage = sliceString(encrypted, k, klength); 
        	key[k] = CR.getKey(ccMessage); 
        }
        return key;
    }

    
    public HashSet<String> readDictionary (FileResource dt) {
    	hs.clear();
    	for(String word: dt.words()) {
    		String word1 = word.toLowerCase(); 
    		hs.add(word1);
    	}
    	return hs; 
    }
    
    public int countWords(String message, HashSet<String> dictionary) {
    	message = message.toLowerCase();
    	String [] wordsMessage = message.split("\\W");
    	int right = 0;
    	for(int i = 0; i< wordsMessage.length; i++) {
    		String iWord = wordsMessage[i];
    		if (hs.contains(iWord)) {
    			++right; 
    		}
    	}
    	return right;
    }

    public void breakForLanguage(String encrypted, HashSet<String> dictionary) {
    	int maxRight = 0;
    	int [] rightKey = null;
    	char c = mostCommonCharIn(dictionary); 
    	for(int i = 1; i <= 100; i++) {
    		int [] key = tryKeyLength(encrypted,i,c);
    		VigenereCipher VC = new VigenereCipher (key);
            String tryMessage = VC.decrypt(encrypted);
            countWords(tryMessage,dictionary);
            if(countWords(tryMessage,dictionary) > maxRight) {
            	maxRight = countWords(tryMessage,dictionary);
            	rightKey = key; 
            }
    	}
    	VigenereCipher VC = new VigenereCipher(rightKey); 
 //   	System.out.println(VC.decrypt(encrypted));
    	System.out.println("Correct Key: " + Arrays.toString(rightKey));
    	System.out.println("Correct Key Length: " + rightKey.length);
    	System.out.println("Most number of correct words: " + maxRight);
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
    	HashMap<Character, Integer> hm = new HashMap<Character, Integer> (); 
    	int maxLetter = 0;
    	char mostLetter = 0; 
    	for(String str : dictionary) {
    		for(int i = 0; i < str.length(); i++) {
    			char c = str.charAt(i); 
    			if(hm.containsKey(c)) {
    				hm.put(c, hm.get(c)+ 1); 
    			}
    			else {
    				hm.put(c, 1);
    			}
    		}
    	}
    	for(Character c : hm.keySet()) {
    		if(hm.get(c) > maxLetter) {
    			maxLetter = hm.get(c);
    			mostLetter = c;
    		}
    	}
    	return mostLetter;
    }
    /*encrypted is the encrypted message
     *languages HashMap (Language Name, All the words)
     * 	
     * use DirectoryResource to feed the dictionaries in one at a time
     *   use FileResource to iterate through the contents of the file
     *    add each directory name to the HashMap languages
     *    add the contents of the file to the HashSet in languages
     *     		readDictionary will build the HashSet
     *
     *BREAKFORALLLANGUAGES 
     * pass each HashSet dictionary + encrypted message to breakForLanguage
     *  iterate through each entry in languages (string + HashSet words)
     *    call breakForLanguage(encrypted message, 
     * print decrypted message + 
     * print language used
     */
    
    public void breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
    	for(String s : languages.keySet()) {
    		breakForLanguage(encrypted,languages.get(s));
    		System.out.println(s);
    	}
    }
}
