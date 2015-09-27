package palindrome;

public class improvedPalindrome {

	public improvedPalindrome() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean palindrome(String s){
		int count = 0;
		while(count < (s.length()-count-1)){
			if(s.charAt(count) != s.charAt(s.length()-count-1)){
				return false;
			}
			count++;
		}
		return true;
	}
	public boolean palindrome(String s, int upper){
		int count = 0;
		while(count < upper){
			if(s.charAt(count) != s.charAt(s.length()-count-1)){
				return false;
			}
			count++;
		}
		return true;
	}
	public boolean palindrome(int start, String s){
		int count = start;
		while(count < (s.length()-count-1)){
			if(s.charAt(count) != s.charAt(s.length()-count-1)){
				return false;
			}
			count++;
		}
		return true;
	}

}
