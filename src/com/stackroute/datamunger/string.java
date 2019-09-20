import java.util.*;

 
class string 
{  

    public static String[] getSplitStrings(String queryString) {
			
		//queryString =queryString.toLowerCase();
		String[] SplitedString=queryString.split(" ");
		return SplitedString;
	}
      public static void main(String args[])  
   {  
      String str;
      System.out.println("enter");
      Scanner inputScanner = new Scanner(System.in);     
      str = inputScanner.nextLine(); 
      string.getSplitStrings(str);
      
   }  
    
}