
public class deleteme {
	private String title;
	private String author;
	
	public deleteme(String title, String author) {
		System.out.println("howdy");
		this.title =title;
		this.author = author;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		if(obj.getClass() != this.getClass()) return false;
		return obj.title.equals(this.title) && obj.author.equals(this.author);
	}

	public static void main(String args[]) {
		
	}
}
