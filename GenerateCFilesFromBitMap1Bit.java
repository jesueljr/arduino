import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;

public class GenerateCFilesFromBitMap1Bit {
	
	static int lineFeedCount = 0;
	static int totalLines = 0;
	static boolean exiting = false;
	
	
	
	public GenerateCFilesFromBitMap1Bit(){
		String args[]={"projectLogo","logo","48","72"};
		verifyArgs(args);
		readBitMap(args[0],args[2],args[3]);
		createHeaderFile(args[1]); 
		createCPreProcessorFile(args[1],args[2],args[3]);	    		
	}
	
	
	
	
	public static void main(String[] args) {
		verifyArgs(args);
		readBitMap(args[0],args[2],args[3]);
		createHeaderFile(args[1]); 
		createCPreProcessorFile(args[1],args[2],args[3]);	    				
	}

	
	
	
	private static void createCPreProcessorFile(String fileName, String columns, String rows) {
	    /*Second file*/
		System.out.println("/* "+"Create file " + fileName + ".cpp"+" */");
		System.out.println("#include "+ "\"" + fileName + ".h"+"\"");
	    System.out.println("PROGMEM const unsigned char " + fileName + "[] = {");
	    System.out.println(columns+","+rows);		
	}
	

	
	
	private static void createHeaderFile(String fileName) {
		/*First file*/
		System.out.println("/* "+"Creating file "+ fileName +".h"+" */");
		Writer writeFile = null;
		try {
			writeFile = new FileWriter(fileName+".h");

			writeFile.write("#include <avr/pgmspace.h>");
			writeFile.write("#ifndef "+ fileName.toUpperCase() + "_H");
			writeFile.write("#define "+ fileName.toUpperCase() + "_H");
			writeFile.write("extern const unsigned char " + fileName + "[];");
			writeFile.write("#endif");			
			
			writeFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			exiting = true;
		}
		System.out.println("#include <avr/pgmspace.h>");
		System.out.println("#ifndef "+ fileName.toUpperCase() + "_H");
		System.out.println("#define "+ fileName.toUpperCase() + "_H");
		System.out.println("extern const unsigned char " + fileName + "[];");
		System.out.println("#endif");
		
		System.out.println();
		System.out.println();		
	}

	
	

	private static void readBitMap(String fileName, String columns, String rows) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName+".bmp");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			exiting = true;
		}
		
		int size = 0;
		try {
			size = inputStream.available();
		} catch (IOException e) {
			e.printStackTrace();
			exiting = true;
		}
		
	    /*Read lines from bitmap*/
	    for(int i=0; i< size; i++){
	    	byte byteRead = 0;
			try {
				byteRead = (byte)inputStream.read();
			} catch (IOException e) {
				e.printStackTrace();
				exiting = true;
			}
	    	String hexConvertedFromByteRead = String.format("%8s", Integer.toBinaryString(byteRead & 0xFF)).replace(' ', '0');
	    	//System.out.print(hexConvertedFromByteRead+" "); //imprime tudo
	    	if ( i>61){
	    		lineFeedCount+=1;
	    		if (columns.equals("48")){
	    			handle48Columns(hexConvertedFromByteRead);
	    		}
	    		if (columns.equals("96")){
	    			handle96Columns(hexConvertedFromByteRead);
	    		}
	    	}
	    }
	    
	    System.out.println("};");
	    System.out.println();
	    if (Integer.parseInt(rows) != totalLines){
		    System.out.println("//Total lines ERROR: "+totalLines+", informed was "+rows);	
	    }
	    else{
		    System.out.println("//Total lines: "+totalLines);		    	
	    }
	    
	    try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			exiting = true;
		}

	}

	

	
	
	
	private static void handle96Columns(String lineRead) {		
		if (lineFeedCount<12){
    		System.out.print(",");
			if (new BigInteger(lineRead, 2).toString(16).length()==1){
    			System.out.print("0x0"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    				
			}
			else {
    			System.out.print("0x"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    					    				
			}
		}
		else if (lineFeedCount==12){
			totalLines +=1;
    		lineFeedCount=0;
    		System.out.print(",");
			if (new BigInteger(lineRead, 2).toString(16).length()==1){
    			System.out.print("0x0"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    				
			}
			else {
    			System.out.print("0x"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    					    				
			}
    		System.out.println();			
		}
		else {
			lineFeedCount =0;
		}
	}


	
	
	

	private static void handle48Columns(String lineRead) {	
		if (lineFeedCount<7){
    		System.out.print(",");
			if (new BigInteger(lineRead, 2).toString(16).length()==1){
    			System.out.print("0x0"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    				
			}
			else {
    			System.out.print("0x"+new BigInteger(lineRead, 2).toString(16).toUpperCase());	    					    				
			}
		}
		else if (lineFeedCount==7){
			System.out.println();
			totalLines +=1;
		}
		else {
			lineFeedCount =0;
		}
	}



	
	
	private static void verifyArgs(String[] args) {
		System.out.println("//"+args.length + " args: "+ args[0]+" "+args[1]+" "+args[2]+" "+args[3]);
		if (args.length!=4 || (!args[2].equals("48") && !args[2].equals("96"))){
			/*Instructions*/
			System.out.println("\nWrong number of args, 4 args are required:");
			System.out.println("      First arg <bitmapName> without extension");
			System.out.println("      Second arg <cFileName> without extension");
			System.out.println("i.e.: java GenerateCFilesFromBitMap1Bit <bitmapName> <cFileName>");
			System.out.println("e.g.: java GenerateCFilesFromBitMap1Bit qrInvertedFinal qrLogo\n");
			System.out.println("Exiting...");
			return;
		}		
	}
	
	
	
	
}
