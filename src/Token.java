public class Token {
	
	private static Token endToken = new Token();
	
	public static Token getEndToken() {
		return endToken;
	}
	
	public String toString() {
		if (this == endToken) {
			return "<END>";
		} else {
			return "UNK";
		}
	}
}
