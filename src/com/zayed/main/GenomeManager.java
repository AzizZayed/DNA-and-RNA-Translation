package com.zayed.main;

/**
 * main class, manages the classes to get result
 * 
 * @author Zayed
 *
 */
public class GenomeManager {

	public static void main(String[] args) {
		CodonHashTable table = new CodonHashTable();

		Genome corona = new Genome("SARS-CoV-2", table);

		System.out.println(corona.getTranslation());
	}

}
