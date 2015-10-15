# arduino
Usage: java GenerateCFilesFromBitMap1Bit <bitmapName> <cFileName> <bitMapColumns> <bitMapLines>
	1st arg <bitmapName> without extension
	2nd arg <cFileName> without extension
	3rd arg <bitMapColumns>
	4th arg <bitMapLines>
	e.g. : java GenerateCFilesFromBitMap1Bit projectLogo logo 48 72
		Read projectLogo.bmp file and use 48 columns pattern
		Create logo.h header file
		Create logo.cpp preprocessor file
		Verify if 72 lines was generated. If not, display an error.
