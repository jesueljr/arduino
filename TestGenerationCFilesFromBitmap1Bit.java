public class TestGenerationCFilesFromBitmap1Bit {
	public static void main(String[] args) {
		
		System.out.println("=========================\nSuccess Test!\n=========================");
		String arguments[]={"photo96","logo","96","96"};
		GenerateCFilesFromBitMap1Bit generateCFilesFromBitMap1Bit = new GenerateCFilesFromBitMap1Bit(arguments);
		
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("=========================\nError Test!\n=========================");
		arguments[2]="48";
		GenerateCFilesFromBitMap1Bit generateCFilesFromBitMap1Bit2 = new GenerateCFilesFromBitMap1Bit(arguments);		
	}
}
