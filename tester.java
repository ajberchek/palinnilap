package palindrome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class tester {

	public static String reverseString(String s){
		if(s.length() == 1){
			return s;
		}
		else if(s.length() == 2){
			String temp = "";
			temp += s.charAt(1);
			temp += s.charAt(0);
			return temp;
			
		}
		else{
		char temp = 'a';
		char[] stringArray = s.toCharArray();
		for(int a = 0; a <= (int)stringArray.length/2; ++a){
			temp = stringArray[a];
			stringArray[a] = stringArray[s.length()-a-1];
			stringArray[s.length()-a-1] = temp;
		}
		String toReturn = "";
		for(int a = 0; a < stringArray.length; ++a){
			toReturn += stringArray[a];
		}
		return toReturn;
		}
	}
	
	public tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {

		// Sets up a palindrome tester
		improvedPalindrome p = new improvedPalindrome();
		// Requests the path of the text file with all the words. It needs to be
		// in the form of one word per line and it needs to be sorted.
		Scanner scan = new Scanner(System.in);
		System.out.print("What is the location of the words file: ");
		String filename = scan.nextLine();

		System.out.print("Where should the palindrome file be saved? ");
		String fileToSave = scan.nextLine();
		
		scan.close();

		// Sets up the writer along with the location to which it will write the
		// palindrome text file.
		PrintWriter writer = new PrintWriter(fileToSave);
		
		// Creates the reader object to read the words text document along with
		// some temporary varibales
		// to assist in the reading.
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String word = "lskdjaf";
		String line;
		boolean threeWords = false, fourWords = true, equalPalindrome = false;
		char letter = ' ';
		// Creates arrayLists to store positions of the beginning of different
		// letters of words, to store the words themselves
		// and store simple two worded palindromes.
		ArrayList<Integer> positions = new ArrayList<Integer>();
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> endPositions = new ArrayList<Integer>();
		ArrayList<String> endWords = new ArrayList<String>();
		ArrayList<String> first = new ArrayList<String>();
		ArrayList<String> palindromes = new ArrayList<String>();
		
		//Sets up an array list to store the palindromes that occur when the rightmost word has
		//one more letter than the leftmost word. Since there are only 26 sets and this situation of one letter more
		//occurs quite often, I thought it was a good idea to just store them to an array in order to save the list
		//that had been calculated already instead of calculating again.
		ArrayList<ArrayList<String>> oneLetter = new ArrayList<ArrayList<String>>();
		for (int t = 0; t < 26; ++t) {
			oneLetter.add(new ArrayList<String>());
		}

		boolean sameLengthValsRecorded = false;
		int count = 0;
		// Read in the text file with all the words.
		while (word != null) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			// Ensures it just stores the word and not the spaces.
			word = "";
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					word += line.charAt(i);
				}
			}
			
			words.add(word);
		}
		reader.close();
		System.out.println("Words in text file: " + words.size());
		
		//Sorts the words of that text file.
		Collections.sort(words);
		
		
		
		// Checks to see if the current word's first letter is different
		// than the last word's character
		// meaning it has transitioned from say "bzz" to "cat" and it stores
		// the place of the word with
		// that new character into an arrayList.
		for(int i = 0; i < words.size(); ++i){
			if (words.get(i).charAt(0) != letter) {
				positions.add(i);
				letter = words.get(i).charAt(0);
			}
		}
		
		
		positions.add(words.size());

		

		// Makes a copy of the words arrayList
		for (int i = 0; i < words.size(); i++) {
			endWords.add(words.get(i));
		}
		
		//Reverse the strings in the array
		for (int i = 0; i < endWords.size(); ++i){
			endWords.set(i, reverseString(endWords.get(i)));
		}
		//Sort those reversed strings
		Collections.sort(endWords);
		
		//Reverse the strings in the now sorted array back to their original format
		for (int i = 0; i < endWords.size(); ++i){
			endWords.set(i, reverseString(endWords.get(i)));
		}
		//Voila, the array is now sorted from last character to first in each word.
		
		// Gets the positions of the character changes in the end words list.
		letter = ' ';
		for (int i = 0; i < endWords.size(); i++) {
			if ((endWords.get(i)).charAt((endWords.get(i)).length() - 1) != letter) {
				endPositions.add(i);
				letter = (endWords.get(i)).charAt((endWords.get(i)).length() - 1);
			}
		}
		endPositions.add(endWords.size());
			
		
		
		//Palindrome finder for all four worded palindromes.
		if (fourWords) {
			int currentCharPosition = 0;
			System.out.println("FOUR WORDS WITHOUT A SPACE");
			System.out.print("Progress: ");
			double startTime = System.currentTimeMillis();
			//Word at index i if set is a palindrome will be the rightmost word.
			for (int i = 0; i < words.size(); i++) {
				//Used to print out the progress - prints another letter as soon as the first letter of the current word is
				//different than the last word's first character.
				if (i > positions.get(currentCharPosition)) {
					System.out.print(Character.toUpperCase(words.get(i).charAt(0)));
					currentCharPosition++;
				}
				
				//This is used to shorten the list of words searched for the leftmost word since the
				//first character of that first word needs to match the last character of the last word
				//in order for it to be a valid palindrome.
				int startPosition = 0, endPosition = 0;
				
				//Gets the indices for the beginning and end of the set of words that have the same first character as the last letter
				//of the word at index i in the words arrayList
				
				int temp = (int) ((words.get(i)).charAt((words.get(i)).length() - 1));
				temp = temp - 97;
				startPosition = positions.get(temp);
				endPosition = positions.get(temp + 1);
				
				//The word at index j corresponds to the rightmost word if the word set ends up being a palindrome.
				//it also only runs through the indices found above.
				for (int j = startPosition; j < endPosition; j++) {
					//concantenates the first and last word then finds the one that has the smaller length
					String both = words.get(j) + words.get(i);
					int iLength = (words.get(i)).length();
					int jLength = (words.get(j)).length();
					int upperBound = 0;
					if (iLength >= jLength)
						upperBound = jLength;
					else if (iLength < jLength)
						upperBound = iLength;
					
					//Checks to see if that set of words is a palindrome up to the index corresponding to the smaller length
					//found above.
					if (p.palindrome(both, upperBound)) {
						
						//If the last word is longer than the first word...
						if (iLength > jLength) {
							
							
							if (iLength - jLength != 1) {
								// the second word is longer than the first so we find the third word based on
								// its starting character
								// for example, lets say the word at index i is baha and at j is aha, the character
								// word 3 would need to start with is a and char position is the position of the rightmost
								// letter occurring when substring from the length of the word - jLength to the length of the word is
								// removed from that word. i.e baha - aha = b
								int charPosition = iLength - jLength - 1;
								int startPositionThird = 0, endPositionThird = 0;
								temp = (int) ((words.get(i)).charAt(charPosition));
								temp = temp - 97;
								startPositionThird = positions.get(temp);
								endPositionThird = positions.get(temp + 1);
								
								//Searches through the list with start and stop indices found above.
								for (int k = startPositionThird; k < endPositionThird; k++) {
									//Finds the length of the smaller of the two word sets after concantenating the first and
									//second words together.
									
									String firstSecond = words.get(j) + words.get(k);
									int iLengthThird = (words.get(i)).length();
									int firstSecondLength = (firstSecond).length();
									int upperBoundThird = 0;
									if (iLengthThird >= firstSecondLength)
										upperBoundThird = firstSecondLength;
									else if (iLengthThird < firstSecondLength)
										upperBoundThird = iLengthThird;
									String firstSecondThird = firstSecond + words.get(i);
									
									//Checks to see if the words found so far are palindromes up to the lower index.
									if (p.palindrome(firstSecondThird, upperBoundThird)) {
										upperBoundThird--;

										if (firstSecond.length() < words.get(i).length()) {

											int startPositionFourth = 0, endPositionFourth = 0;
											temp = (int) (words.get(i).charAt(upperBoundThird));
											temp = temp - 97;
											startPositionFourth = positions.get(temp);
											endPositionFourth = positions.get(temp + 1);
											

											for (int l = startPositionFourth; l < endPositionFourth; l++) {
												String group = words.get(j) + words.get(k) + words.get(l) + words.get(i);
												if (p.palindrome(upperBoundThird, group)) {
													palindromes.add(group);
													writer.write(words.get(j) + " " + words.get(k) + " " + words.get(l) + " "
															+ words.get(i) + "\r\n");

												}
											}
										}

										else if (firstSecond.length() > words.get(i).length()) {

											int startPositionFourth = 0, endPositionFourth = 0;
											
											temp = (int) (firstSecond.charAt(upperBoundThird));
											temp = temp - 97;
											startPositionFourth = endPositions.get(temp);
											endPositionFourth = endPositions.get(temp + 1);
											
											for (int l = startPositionFourth; l < endPositionFourth; l++) {
												String group = words.get(j) + words.get(k) + endWords.get(l) + words.get(i);
												if (p.palindrome(upperBoundThird, group)) {
													palindromes.add(group);
													writer.write(words.get(j) + " " + words.get(k) + " " + endWords.get(l) + " "
															+ words.get(i) + "\r\n");

												}
											}
										}

									}
								}
							}

							else if (iLength - jLength == 1) {

								char fLetter = words.get(i).charAt(0);
								int fLetterPos = (int) fLetter - (int) 'a';

								if (oneLetter.get(fLetterPos).size() >= 1) {
									for (int q = 0; q < oneLetter.get(fLetterPos).size(); ++q) {
										writer.write(words.get(j) + (oneLetter.get(fLetterPos)).get(q) + words.get(i) + "\r\n");
										String group = words.get(j);
										for (int a = 0; a < oneLetter.get(fLetterPos).get(q).length(); ++a) {
											char tempChar = oneLetter.get(fLetterPos).get(q).charAt(a);
											if (tempChar != ' ') {
												group += tempChar;

											}
										}
										group += words.get(i);
										palindromes.add(group);
										// System.out.println(words.get(j) +
										// (oneLetter.get(fLetterPos)).get(q) +
										// words.get(i));

									}

								}

								else if (oneLetter.get(fLetterPos).size() < 1) {

									// the second word is longer than the first
									// so we
									// find the third word based on
									// its starting character
									// System.out.println("Finding corresponding palindromes.");
									int charPosition = 0;
									int startPositionThird = 0, endPositionThird = 0;
									
									temp = (int) ((words.get(i)).charAt(charPosition));
									temp = temp - 97;
									startPositionThird = positions.get(temp);
									endPositionThird = positions.get(temp + 1);
									

									for (int k = startPositionThird; k < endPositionThird; k++) {

										String firstSecond = words.get(j) + words.get(k);
										int iLengthThird = (words.get(i)).length();
										int firstSecondLength = (firstSecond).length();
										int upperBoundThird = 0;
										if (iLengthThird >= firstSecondLength)
											upperBoundThird = firstSecondLength;
										else if (iLengthThird < firstSecondLength)
											upperBoundThird = iLengthThird;

										String firstSecondThird = firstSecond + words.get(i);

										if (p.palindrome(firstSecondThird, upperBoundThird)) {
											upperBoundThird--;

											if (firstSecond.length() == words.get(i).length()) {

												System.out.println("i Length = firstSecond");

												for (int l = 0; l < words.size(); ++l) {
													String group = words.get(j) + words.get(k) + words.get(l) + words.get(i);
													// ////////////////////////////////////////////////////
													if (p.palindrome(group)) {
														System.out.print(words.get(j) + " " + words.get(k) + " " + words.get(l) + " "
																+ words.get(i));
														oneLetter.get(fLetterPos).add(" " + words.get(k) + " " + words.get(l) + " ");
														palindromes.add(group);
														writer.write(words.get(j) + " " + words.get(k) + " " + words.get(l) + " "
																+ words.get(i) + "\r\n");

													}
												}
											}

											else if (firstSecond.length() > words.get(i).length()) {

												int startPositionFourth = 0, endPositionFourth = 0;
												int charPos = words.get(i).length();
												
													temp = (int) (firstSecond.charAt(charPos));
													temp = temp - (int) 'a';
													startPositionFourth = endPositions.get(temp);
													endPositionFourth = endPositions.get(temp + 1);
												

												for (int l = startPositionFourth; l < endPositionFourth; l++) {
													String group = words.get(j) + words.get(k) + endWords.get(l) + words.get(i);
													if (p.palindrome(group)) {
														oneLetter.get(fLetterPos).add(" " + words.get(k) + " " + endWords.get(l) + " ");
														// System.out.print(words.get(j)
														// + " " + words.get(k)
														// + " "
														// +endWords.get(l) +
														// " " +words.get(i));
														palindromes.add(group);
														writer.write(words.get(j) + " " + words.get(k) + " " + endWords.get(l) + " "
																+ words.get(i) + "\r\n");

													}
												}
											}

										}
									}

								}

							}

						}

						else if (iLength < jLength) {
							// the second word is longer than the first so we
							// find the third word based on
							// its starting character
							int charPosition = jLength - 1 - (jLength - iLength - 1);
							int startPositionThird = 0, endPositionThird = 0;
							
								temp = (int) ((words.get(j)).charAt(charPosition));
								temp = temp - 97;
								startPositionThird = endPositions.get(temp);
								endPositionThird = endPositions.get(temp + 1);
							

							for (int k = startPositionThird; k < endPositionThird; k++) {

								String secondThird = words.get(k) + endWords.get(i);
								int iLengthThird = (words.get(j)).length();
								int secondThirdLength = (secondThird).length();
								int upperBoundThird = 0;
								if (iLengthThird >= secondThirdLength)
									upperBoundThird = secondThirdLength;
								else if (iLengthThird < secondThirdLength)
									upperBoundThird = iLengthThird;
								String firstSecondThird = words.get(j) + secondThird;

								if (p.palindrome(firstSecondThird, upperBoundThird)) {
									upperBoundThird--;
									int charPositionFourth = secondThird.length() - upperBoundThird - 1;
									int startPositionFourth = 0, endPositionFourth = 0;
									
										temp = (int) (secondThird.charAt(charPositionFourth));
										temp = temp - 97;
										startPositionFourth = positions.get(temp);
										endPositionFourth = positions.get(temp + 1);
									

									for (int l = startPositionFourth; l < endPositionFourth; l++) {
										String group = words.get(j) + words.get(l) + endWords.get(k) + words.get(i);
										if (p.palindrome(upperBoundThird, group)) {
											palindromes.add(group);
											writer.write(words.get(j) + " " + words.get(l) + " " + endWords.get(k) + " " + words.get(i)
													+ "\r\n");

										}

									}
								}

							}
						}

						else {

							String firstLast = words.get(j) + words.get(i);
							if (p.palindrome(firstLast)) {

								if (!equalPalindrome) {
									for (int k = 0; k < words.size(); ++k) {

										int startPos = 0, endPos = 0;
										
											temp = (int) ((words.get(k)).charAt((words.get(k)).length() - 1));
											temp = temp - 97;
											startPos = positions.get(temp);
											endPos = positions.get(temp + 1);
										
										for (int l = startPos; l < endPos; ++l) {
											String secondThird = words.get(l) + words.get(k);
											if (p.palindrome(secondThird)) {
												first.add(words.get(l) + " " + words.get(k) + " ");
											}
										}

									}
									equalPalindrome = true;
								}

								if (equalPalindrome) {
									for (int l = 0; l < first.size(); l++) {
										writer.write(words.get(j) + " " + first.get(l) + words.get(i) + "\r\n");

									}
								}

							}
						}

					}
				}
			}
			double endTime = System.currentTimeMillis();
			double betterTimeDifference = endTime - startTime;
			System.out.println("The time of smart palindrome tester: " + ((double) betterTimeDifference) / 1000.0);
		}

		boolean issue = false;
		for (int i = 0; i < palindromes.size(); ++i) {
			if (!p.palindrome(palindromes.get(i))) {
				System.out.println(palindromes.get(i) + " is not a palindrome");
				issue = true;
			}
		}
		if (!issue) {
			System.out.println("Every word is a palindrome!");
			writer.write("Every word is a palindrome!");
		}
		writer.close();
	}
}
