package com.zayed.main;

public class GenomeManager {

	public static void main(String[] args) {
		CodonHashTable table = new CodonHashTable();
		table.setToFullTable();

		Genome corona = new Genome("SARS-CoV-2");
		corona.translate(table);
		
		System.out.println(corona.getTranslation());
	}

}
