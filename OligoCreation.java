/*
 * This program creates oligos from gene sequences obtained in the OligoDeletionSites class
 * 
 * Code written by 
 *     Trevor Birenbaum (University of Miami '20)
 * Special thanks
 *     Dr. Richard S. Myers (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 *     Dr. Thomas K. Harris (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 * 
 * Date created: 06/20/16
 * Last updated: 06/20/16
 */

import java.util.Scanner;

public class OligoCreation {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		String gene = keyboard.nextLine();
		String oligoReverseComplement = keyboard.nextLine();
		String oligoString = reverseComplement(oligoReverseComplement);

		System.out.println(oligoString + "\n");
		
		for(int i = 0; i <= oligoString.length() - 90; i++) {
			String oligoSubstring = oligoString.substring(i, i + 90);
			String geneSubstring = gene.substring(i, i + 90);
			
			int oligoSubstringLength = oligoSubstring.length();
			if(oligoSubstring.substring(0, 15).equals(oligoSubstring.toLowerCase().substring(0, 15)) && oligoSubstring.substring(oligoSubstringLength - 15,  oligoSubstringLength).equals(oligoSubstring.toLowerCase().substring(oligoSubstringLength - 15,  oligoSubstringLength))) {
				System.out.println("Gene:  " + geneSubstring);
				System.out.println("Oligo: " + oligoSubstring + "\n");
			}
		}
		
		System.out.println("\n\nFOR COPYING");
		
		for(int i = 0; i <= oligoString.length() - 90; i++) {
			String oligoSubstring = oligoString.substring(i, i + 90);
			int oligoSubstringLength = oligoSubstring.length();
			
			if(oligoSubstring.substring(0, 15).equals(oligoSubstring.toLowerCase().substring(0, 15)) && oligoSubstring.substring(oligoSubstringLength - 15,  oligoSubstringLength).equals(oligoSubstring.toLowerCase().substring(oligoSubstringLength - 15,  oligoSubstringLength))) {
				System.out.println(oligoSubstring);
			}
		}
		
		System.out.println("\n");
		
		for(int i = oligoString.length() - 90; i >= 0; i--) {
			String geneSubstring = gene.substring(i, i + 90);
			String oligoSubstring = oligoString.substring(i, i + 90);
			int oligoSubstringLength = oligoSubstring.length();
			
			if(oligoSubstring.substring(0, 15).equals(oligoSubstring.toLowerCase().substring(0, 15)) && oligoSubstring.substring(oligoSubstringLength - 15,  oligoSubstringLength).equals(oligoSubstring.toLowerCase().substring(oligoSubstringLength - 15,  oligoSubstringLength))) {
				System.out.println(geneSubstring);
			}
		}
	}
	
	public static String reverseComplement(String dnaSequence) {
		String complement = "";
		char[] dnaSequenceCharacters = dnaSequence.toCharArray();
		for(int i = 0; i < dnaSequence.length(); i++) {
			char character = dnaSequenceCharacters[i];
			
			if(character == 'A') {
				complement += 'T';
			}
			else if(character == 'C') {
				complement += 'G';
			}
			else if(character == 'G') {
				complement += 'C';
			}
			else if(character == 'T') {
				complement += 'A';
			}
			else if(character == ' ') {
				complement += ' ';
			}
			else if(character == 'a') {
				complement += 't';
			}
			else if(character == 'g') {
				complement += 'c';
			}
			else if(character == 'c') {
				complement += 'g';
			}
			else if(character == 't') {
				complement += 'a';
			}
		}
		
		String reverseComplement = "";
		
		for(int i = complement.length() - 1; i >= 0; i--) {
			reverseComplement += complement.charAt(i);
		}
		
		return reverseComplement;
	}
}
