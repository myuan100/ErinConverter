import chemaxon.formats.*;
import chemaxon.struc.*;
import java.io.*;
import chemaxon.calculations.clean.CleanerUtil;

public class ErinConverter {

	public static void main(String[] args) {
		//Check if user passes in file location
		if (args.length < 1) {
			System.out.println("Input directory isn't found. Exiting converter.. ");
			System.exit(0);
		}

		//Print message to indicate the application starts
		printDots();
		
		//List all sdf files in the same directory
		File folder = new File(args[0]);

		FilenameFilter txtFileFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".sdf");
			}
		};

		File[] files = folder.listFiles(txtFileFilter);

		System.out.println("Discovered " + files.length + " SDF files in " + args[0] + " folder\n");

		//Convert the content in sdf files one-by-one
		for (File myfile : files) {
			convert(myfile.getAbsolutePath());
		}

		System.out.println("\nFile convertion completed!");

		//Stop the application
		System.exit(0);
	}

	private static void convert(String infile) {
		try {
			System.out.print("   Reading in " + infile);
			
			//read content from one sdf file
			MolImporter importer = new MolImporter(infile);
			Molecule mol = importer.read();
			new CleanerUtil().clean(mol, 2, null, null);
			
			//convert content from sdf to jpeg
			byte[] data = MolExporter.exportToBinFormat(mol, "jpeg:w400");
			
			//write jpeg stream to a file
			OutputStream out = new FileOutputStream(infile + ".jpg");
			out.write(data);
			out.close();
			
			System.out.println("  ==> Writing to " + infile + ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printDots() {
		try {
			System.out.print("\nStarting Erin's file converter .");
			Thread.sleep(1000);
			System.out.print("..");
			Thread.sleep(1000);
			System.out.print("..");
			Thread.sleep(1000);
			System.out.print("..");
			Thread.sleep(1000);
			System.out.print("..");
			Thread.sleep(1000);
			System.out.print("..");
			Thread.sleep(1000);
			System.out.println(".\n");
		} catch (InterruptedException e) {
		}
	}

}
