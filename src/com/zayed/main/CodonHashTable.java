package com.zayed.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The class to hold the codon table and it's properties
 * 
 * @author Zayed
 *
 */
public class CodonHashTable {

	private boolean compressed; // the amino acids names are compressed to 1 letter
	private boolean half; // the amino acids names are compressed to 3 letter

	// fast retrieval of the amino acids of all 64 possible codons
	private HashMap<String, String> halfTable;
	private HashMap<String, String> fullTable;
	private HashMap<String, String> compressedTable;

	/**
	 * Constructor
	 */
	public CodonHashTable() {
		// setup hash maps
		setupHalfTable();
		setupFullTable();
		setupCompressedTable();

		setToHalfTable(); // default setting at short names amino acids
	}

	/**
	 * setup the hash map for 3 letter amino acids, half names
	 */
	private void setupHalfTable() {
		String halfTableStr = fileToString("res/codon_table/codontable_half.txt");
		halfTable = populate(halfTableStr);
	}

	/**
	 * setup the hash map for complete named amino acids
	 */
	private void setupFullTable() {
		String fullTableStr = fileToString("res/codon_table/codontable_full.txt");
		fullTable = populate(fullTableStr);
	}

	/**
	 * setup the hash map for 1 letter amino acids, compressed names
	 */
	private void setupCompressedTable() {
		String compressedTableStr = fileToString("res/codon_table/codontable_compressed.txt");
		compressedTable = populate(compressedTableStr);
	}

	/**
	 * populate the hash maps for the codons
	 * 
	 * @param data - the string with the codon table from the text files
	 * @return the hashmap codon table
	 */
	private HashMap<String, String> populate(String data) {
		BufferedReader reader = new BufferedReader(new StringReader(data));
		HashMap<String, String> table = new HashMap<String, String>();

		String line;
		while ((line = nextLineOfBuffer(reader)) != null) {
			String codon = line.substring(0, 3);// get the codon from the line, should be after the first 3 letters
			String aminoAcid = line.substring(4);// get the amino acid from line, should be after the codon and the ':'

			table.put(codon, aminoAcid); // add codon as the key and amino acid as the value in the hashmap
		}

		return table;
	}

	/**
	 * set the table to half named amino acids (3 letters)
	 */
	public void setToHalfTable() {
		compressed = false;
		half = true;
	}

	/**
	 * set the table to fully named amino acids (all letters)
	 */
	public void setToFullTable() {
		compressed = false;
		half = false;
	}

	/**
	 * set the table to compressed names of amino acids (1 letter)
	 */
	public void setToCompressedTable() {
		compressed = true;
		half = false;
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
	 * print table to console with all amino acid naming settings
	 */
	public void printTableToConsole() {
		String[] keys = halfTable.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];

			System.out.print(key + ":");
			System.out.print(fullTable.get(key) + ",");
			System.out.print(halfTable.get(key) + ",");
			System.out.print(compressedTable.get(key));
			System.out.println();
		}
	}

	/**
	 * return the appropriate amino acid for the key (codon)
	 * 
	 * @param key - the codon in the RNA sequence
	 * @return the amino acid as a string
	 */
	public String get(String key) {
		if (compressed) {
			return compressedTable.get(key);
		} else if (half) {
			return halfTable.get(key);
		} else {
			return fullTable.get(key);
		}
	}

}
