/*
 * This program creates modifications in DNA sequences to automatically produce an oligo that has the desired mutations
 * 
 * Code written by
 *     Trevor Birenbaum (University of Miami '20)
 * Special thanks
 *     Dr. Richard S. Myers (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 *     Dr. Thomas K. Harris (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 *     James McLaughlin (California Institute of Technology '20)
 * 
 * Date created: 06/22/16
 * Last updated: 06/22/16
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class RegexReplacement {
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		String regexPattern = "(((((([ACGT]{0,2}?)(C[ACGT\\s]{1,5}?))([ACG](AA|AG|GA)\\s))|((([ACGT]{0,2}?)(C[ACGT\\s]{1,3}?))(T(C[AG]|GG|T[AG])\\s))|((([ACGT]{1,2}?)(C[ACGT\\s]{1,2}?))(T(A[GT]|G[CGT])\\s)))(([ACGT]{3}\\s){0,3}?)){2})";
		/*
		 * REGEX EXPLANATION
		 *                           [ACGT]{0,2}?  C[ACGT\\s]{1,5}?                           [ACGT]{0,2}?  C[ACGT\\s]{1,3}?                             [ACGT]{1,2}?  C[ACGT\\s]{1,2}?                                                         Step 1/4: Finding CXX, XCX, or XXC
		 *                                                            [ACG](AA|AG|GA)\\s                                       T(C[AG]|GG|T[AG])\\s                                       T(A[GT]|G[CGT])\\s                                    Step 2/5: Finding codons that are one different from a stop codon (TAA/TAG/TGA)
		 *                                                                                                                                                                                                      ([ACGT]{3}\\s){0,3}?            Step 3:   Separating steps 1 and 2 from steps 4 and 5 by up to 3 codons
		 */
		
		String geneSequence = keyboard.nextLine(); //takes gene sequence as input
		
		Pattern regex = Pattern.compile(regexPattern);
		Matcher matcher = regex.matcher(geneSequence);
		
		List<String> matches = new ArrayList<>();
		List<Integer> matchedIndexes = new ArrayList<>();
		
		while (matcher.find()) {
			matches.add(matcher.group());
			matchedIndexes.add(matcher.start());				
		}
		
		for (int i = 0; i < matches.size(); i++) {
			String match = matches.get(i);
			System.out.println(match);
			
			//find last C in first codon
			char[] matchCharacters = match.toCharArray();
			if(match.charAt(2) == 'C') {
				matchCharacters[2] = 'G';
			}
			else if(match.charAt(1) == 'C') {
				matchCharacters[1] = 'G';
			}
			else if(match.charAt(0) == 'C') {
				matchCharacters[0] = 'G';
			}
			
			String newMatch1 = "";
			for(int j = 0; j < match.length(); j++) {
				newMatch1 += matchCharacters[j];
			}
			
			String secondCodon = match.substring(4, 7);
			
			//change to stop codon
			int firstStopCodon;
			if(levenshtein(secondCodon, "TAA") == 1) {
				matchCharacters[4] = 'T';
				matchCharacters[5] = 'A';
				matchCharacters[6] = 'A';
				firstStopCodon = 2;
			}
			else if(levenshtein(secondCodon, "TGA") == 1) {
				matchCharacters[4] = 'T';
				matchCharacters[5] = 'G';
				matchCharacters[6] = 'A';
				firstStopCodon = 2;
			}
			else if(levenshtein(secondCodon, "TAG") == 1) {
				matchCharacters[4] = 'T';
				matchCharacters[5] = 'A';
				matchCharacters[6] = 'G';
				firstStopCodon = 2;
			}
			else {
				matchCharacters[8] = 'T';
				firstStopCodon = 3;
			}
			
			int l = match.length() - 1;
			
			//start at other side
			String lastCodon = match.substring(l - 3, l);
			
			int lastStopCodon = l / 4 + 1;
			if(levenshtein(lastCodon, "TAA") == 1) {
				matchCharacters[l - 3] = 'T';
				matchCharacters[l - 2] = 'A';
				matchCharacters[l - 1] = 'A';
			}
			else if(levenshtein(lastCodon, "TGA") == 1) {
				matchCharacters[l - 3] = 'T';
				matchCharacters[l - 2] = 'G';
				matchCharacters[l - 1] = 'A';
			}
			else if(levenshtein(lastCodon, "TAG") == 1) {
				matchCharacters[l - 3] = 'T';
				matchCharacters[l - 2] = 'A';
				matchCharacters[l - 1] = 'G';
			}
			
			if(lastStopCodon - firstStopCodon == 6) {
				matchCharacters[l - 9] = 'G';
			}
			else {
				for(int j = l - 5; j >= l - 9; j--) {
					if(match.charAt(j) == 'C') {
						matchCharacters[j] = 'G';
						break;
					}
				}
			}
			
			String formattedMatch = "";
			for(int j = 0; j < match.length(); j++) {
				formattedMatch += matchCharacters[j];
			}
			System.out.println(formattedMatch + "\n");
		}
	}
	
	public static int levenshtein(String string1, String string2) {
		if(string1.length() == 0) {
			return string2.length();
		}
		else if(string2.length() == 0) {
			return string1.length();
		}
		
		if(string1.charAt(0) == string2.charAt(0)) {
			return levenshtein(string1.substring(1), string2.substring(1));
		}
		else {
			/* change   */ int change = levenshtein(string1.substring(1), string2.substring(1));
			/* removal  */ int string1Removal = levenshtein(string1.substring(1), string2);
			/* addition */ int string2Removal = levenshtein(string1, string2.substring(1));
			
			return Math.min(change, Math.min(string1Removal, string2Removal)) + 1;
		}
	}
}
