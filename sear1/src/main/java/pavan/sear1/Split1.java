package pavan.sear1;



// Java program to split a file in multiple files. 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;

public class Split1 { 

    

	private static BufferedReader br;
	private static BufferedWriter bw;
	

	public static void main(String[] args) throws IOException {
		
		String line = "";
        try { 
        	
        	br = new BufferedReader(new FileReader("/Users/pavanpss/Desktop/cran/cran.all.1400/"));
        	int count = 1;
        	FileOutputStream fos = new FileOutputStream("/Users/pavanpss/Desktop/Splitp/"+count+".txt");
        	
        	bw = new BufferedWriter(new OutputStreamWriter(fos));
        	
        	while((line = br.readLine())!=null) {
        		String k = line.trim();
        		if(k.startsWith(".I " + ((count+1)))){
        			bw.flush();
        			bw.close();
        			fos.close();
        			fos = new FileOutputStream("/Users/pavanpss/Desktop/Splitp/"+count+".txt");
        			bw = new BufferedWriter(new OutputStreamWriter(fos));
        			
        			
        			
        			count++;
        			
        			
        			
        	}
        		bw.write(k);
        		bw.newLine();	
        		
        	}
            

            
       
        } catch (Exception e) { 
            System.err.println("Error: " + e.getMessage()); 
        } 

    }

}



