package com.zayed.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * class to hold DNA or genetic information of a virus, living being, etc
 * 
 * @author Zayed
 *
 */
public class Genome {

	private String fullName, name; // names of the target (virus or living being)
	private String geneticCode = ""; // the DNA or RNA
	private String translation = ""; // the translation into a protein

	/**
	 * Constructor
	 * 
	 * @param name  - name of the target, should be the name on the file containing
	 *              the genetic material
	 * @param table - the codon table with respective amino acids for translation
	 */
	public Genome(String name, CodonHashTable table) {
		this.name = name;

		loadGeneticCode(name); // load it from file
		translate(table); // translate it
	}

	/**
	 * load genetic code form file with same name as the target
	 * 
	 * @param fileName - name of the target and file
	 */
	private void loadGeneticCode(String fileName) {
		String filePath = "res/genome_data/" + fileName + ".txt";
		String data = fileToString(filePath);

		BufferedReader reader = new BufferedReader(new StringReader(data));
		String line = "";

		// get full name from first line
		if ((line = nextLineOfBuffer(reader)) != null)
			fullName = line;

		// genetic material from the rest of the file
		StringBuilder geneticCodeBuilder = new StringBuilder();
		while ((line = nextLineOfBuffer(reader)) != null) {
			String newData = line;

			// remove numbers
			for (int i = 0; i < 10; i++)
				newData = newData.replaceAll(Integer.toString(i), "");

			newData = newData.replace('T', 'U'); // turn into RNA if needed
			newData = newData.replace(" ", ""); // remove spaces

			geneticCodeBuilder.append(newData);
		}

		geneticCode = geneticCodeBuilder.toString();
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation;
	}

	/**
	 * translate genetic material to amino acids (a protein)
	 * 
	 * @param table - codon table to provide respective amino acids
	 */
	private void translate(CodonHashTable table) {
		StringBuilder translationBuilder = new StringBuilder();
		int length = geneticCode.length();

		for (int i = 0; i < length; i += 3) {
			String codon = geneticCode.substring(i, i + 3);

			String protein = table.get(codon);
			translationBuilder.append(protein).append('-');
		}

		translation = translationBuilder.toString();
	}

	/**
	 * get next line from the BufferdReader that is reading the string with the
	 * codon table
	 * 
	 * @param reader - reader that is reading the string
	 * @return the next line as a string
	 */
	private String nextLineOfBuffer(BufferedReader reader) {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * convert the entire file in a string
	 * 
	 * @param fileName - name of the file on the computer
	 * @return the file content as a string
	 */

	private String fileToString(String fileName) {
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) {
		CodonHashTable table = new CodonHashTable();

		Genome corona = new Genome("SARS-CoV-2", table);

		String fname = corona.getFullName();
		String name = corona.getName();
		String translation = corona.getTranslation();

		System.out.println("Amino Acid sequence of " + fname + ". Officially named " + name + ":");
		System.out.println(translation);
	}
}
