package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KMeans {
	   String message = "Robert";	
	   
	   @Test
	   public void testPrintMessage() {	
	      System.out.println("Inside testPrintMessage()");    
	      assertEquals(message, "Robert");     
	   }
	   
	   @Test
	   public void another() {	
	      System.out.println("Inside second test");    
	      assertEquals(message, "Robert");     
	   }
}
