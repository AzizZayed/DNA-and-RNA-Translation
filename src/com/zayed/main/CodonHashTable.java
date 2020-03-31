package com.zayed.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class CodonHashTable {

	private String halfTableStr;
	private String fullTableStr;
	private String compressedTableStr;

	private boolean compressed;
	private boolean half;

	private HashMap<String, String> halfTable;
	private HashMap<String, String> fullTable;
	private HashMap<String, String> compressedTable;

	public CodonHashTable() {
		setupHalfTable();
		setupFullTable();
		setupCompressedTable();

//		printTableToConsole();
	}

	private void setupHalfTable() {
		halfTableStr = fileToString("res/codon_table/codontable_half.txt");
		halfTable = populate(halfTableStr);

//		System.out.println(halfTable);

	}

	private void setupFullTable() {
		fullTableStr = fileToString("res/codon_table/codontable_full.txt");
		fullTable = populate(fullTableStr);

//		System.out.println(fullTable);
	}

	private void setupCompressedTable() {
		compressedTableStr = fileToString("res/codon_table/codontable_compressed.txt");
		compressedTable = populate(compressedTableStr);

//		System.out.println(compressedTable);
	}

	private String getAminoAcid(String s) {
		return s.substring(4);
	}

	private String getCodon(String s) {
		return s.substring(0, 3);
	}

	private HashMap<String, String> populate(String data) {
		BufferedReader reader = new BufferedReader(new StringReader(data));
		HashMap<String, String> table = new HashMap<String, String>();
		String line = "";

		while ((line = nextLineOfBuffer(reader)) != null) {
			String codon = getCodon(line);
			String aminoAcid = getAminoAcid(line);

			table.put(codon, aminoAcid);
		}

		return table;
	}

	private String nextLineOfBuffer(BufferedReader reader) {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String fileToString(String fileName) {
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void setToHalfTable() {
		compressed = false;
		half = true;
	}

	public void setToFullTable() {
		compressed = false;
		half = false;
	}

	public void setToCompressedTable() {
		compressed = true;
		half = false;
	}

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
