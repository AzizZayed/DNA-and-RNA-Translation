package com.zayed.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Genome {

	private String fullName, name;
	private String geneticCode = "";
	private String translation = "";
	private boolean isDNA = true;

	public Genome(String name) {
		this.name = name;

		loadGeneticCode(name);
	}

	private void loadGeneticCode(String fileName) {
		String filePath = "res/genome_data/" + fileName + ".txt";
		String data = fileToString(filePath);
		BufferedReader reader = new BufferedReader(new StringReader(data));
		String line = "";

		// get full name from first line
		if ((line = nextLineOfBuffer(reader)) != null)
			fullName = line;

		// get genetic material type from second line
		if ((line = nextLineOfBuffer(reader)) != null)
			isDNA = line.equals("DNA");

		// genetic material from the rest of the file
		while ((line = nextLineOfBuffer(reader)) != null) {
			String newData = line;

			// remove numbers
			for (int i = 0; i < 10; i++)
				newData = newData.replaceAll(Integer.toString(i), "");

			// remove spaces and concatenate
			geneticCode += newData.replace(" ", "");
		}

//		System.out.println(geneticCode);

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
	 * @return the geneticCode
	 */
	public String getGeneticCode() {
		return geneticCode;
	}

	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation;
	}

	public boolean translate(CodonHashTable table) {

		int length = geneticCode.length();
		for (int i = 0; i < length - 3; i += 3) {
			String codon = geneticCode.substring(i, i + 3);

			// Transcription into RNA if we have DNA
			if (isDNA)
				codon = codon.replace("T", "U");

			String protein = table.get(codon);
			translation += protein + "-";
		}

//		System.out.println(translation);
		return true;
	}

}
