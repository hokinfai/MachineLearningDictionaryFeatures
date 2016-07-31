package MachineLearningDictionaryFeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegularExpressionTestingFeatures {
	List<String> list = new ArrayList<String>();

	public RegularExpressionTestingFeatures(List<String> input) {
		this.list = input;
	}

	public void addFeatures() throws IOException {
		for (int i = 1; i < list.size(); i++) {
			String inputData = list.get(i - 1);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (i == 1)
				list.set(0, inputData + "\tO");
			if (namedEntity.matches("^(Dr|Miss|Ms|Sir|Madam|Mr(s)?)$")) {
				list.set(i - 1, inputData.substring(0, inputData.length() - 2) + "\tB-NamedEntity_Title");
				if (list.get(i).contains(".\t."))
					list.set(i, list.get(i).substring(0, list.get(i).length() - 2) + "\tI-NamedEntity_Title");
				else
					list.set(i, list.get(i) + "\tO");
			} else
				list.set(i, list.get(i) + "\tO");
		}
		for (int i = 1; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String originalForm = spilter[3];
			String namedEntity = spilter[2];
			String chunk = spilter[5];
			String previousData = list.get(i - 1);
			String[] spilter1 = previousData.split("\t");
			String term = spilter1[2];
			String chunking;
			if (i == 1 && !term.matches("[A-Z]\\w+"))
				list.set(0, list.get(0) + "\tO");
			else if (i == 1 && term.matches("[A-Z]+\\w+"))
				list.set(0, list.get(0) + "\tB-NamedEntity_ProperName");
			if (!list.get(i - 1).contains("NamedEntity_ProperName"))
				chunking = "B-";
			else
				chunking = "I-";
			if (originalForm.equals(namedEntity.trim()) && !chunk.equals("O") && spilter[4].matches("NNP\\w?")) {
				if (namedEntity.matches("[A-Z][a-z][a-z]+") || namedEntity.matches("[A-Z][A-Z]+")) {
					list.set(i, inputData + "\t" + chunking + "NamedEntity_ProperName");
				} else
					list.set(i, inputData + "\tO");
			} else if (originalForm.equals("-") && previousData.contains("NamedEntity_ProperName")) {
				list.set(i, inputData + "\tI-NamedEntity_ProperName");
			} else if (term.equals("-") && previousData.contains("NamedEntity_ProperName")) {
				list.set(i, inputData + "\tI-NamedEntity_ProperName");
			} else
				list.set(i, inputData + "\tO");
		}
		for (

		int i = 0; i < list.size(); i++)

		{
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches("(16|17|18|19|20)\\d{2}(-\\d+)?")
					&& !(list.get(i - 1).contains(",") || list.get(i - 1).contains(".")) && i != 1) {
				if (list.get(i - 1).contains("mid") || list.get(i - 1).contains("early")
						|| list.get(i - 1).contains("late")) {
					list.set(i - 1, list.get(i - 1).substring(0, list.get(i - 1).length() - 2) + "\t" + "B-Time_Year");
					list.set(i, inputData + "\t" + "I-Time_Year");
				} else
					list.set(i, inputData + "\t" + "B-Time_Year");
			} else
				list.set(i, inputData + "\tO");
		}
		for (

		int i = 0; i < list.size(); i++)

		{
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches(
					"^(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(tember)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)"))
				list.set(i, inputData + "\t" + "B-Time_Month");

			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches(
					"^(arid|breezy|calm|chilly|cloudy|cold|cool|damp|dark|dry|foggy|freezing|frosty|hot|humid|icy|mild|overcast|rainy|smoggy|warm|windy|sunny)"))
				list.set(i, inputData + "\t" + "B-WeatherCondition");

			else
				list.set(i, inputData + "\tO");
		}

		for (

		int i = 0; i < list.size(); i++)

		{
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			String chunk = spilter[5];
			String chunking;
			if (namedEntity.matches(
					"(current(ly)?|Paleocene|recent(ly)?|soon(\\w*)|after|before|night|day|annual(ly)?|early|earlier|later|old|spring|autumn|summer|winter)")) {
				if (i != 0) {
					if (!list.get(i - 1).contains("Time_Phase"))
						chunking = "B-";
					else
						chunking = chunk.substring(0, 2);
				} else
					chunking = chunk.substring(0, 2);

				list.set(i, inputData + "\t" + chunking + "Time_Phase");
			} else
				list.set(i, inputData + "\tO");
		}

		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.trim().matches("^cm|mm|meters$")) {
				list.set(i - 1, list.get(i - 1).substring(0, list.get(i - 1).length() - 1) + "B-Object_size");
				list.set(i, inputData + "\t" + "I-Object_size");
			} else
				list.set(i, inputData + "\tO");
		}

		for (

		int i = 0; i < list.size(); i++)

		{
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches("\\d+-\\w+ed$"))
				list.set(i, inputData + "\t" + "B-Size_Description");
			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 1; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String term = spilter[2];
			String previousData = list.get(i - 1);
			String[] spilter1 = previousData.split("\t");
			String namedEntity = spilter1[2];
			if (i == 1)
				list.set(0, previousData + "\tO");
			if (term.matches("[A-Z][a-z][a-z]+") || term.matches("[A-Z][A-Z]+")) {
				if ((namedEntity.toLowerCase().matches("^north\\w*") || namedEntity.toLowerCase().matches("^south\\w*")
						|| namedEntity.toLowerCase().matches("^west\\w*")
						|| namedEntity.toLowerCase().matches("^central\\w*")
						|| namedEntity.toLowerCase().matches("^east\\w*")) && !namedEntity.contains("ward")) {
					list.set(i - 1, previousData.substring(0, previousData.length() - 1) + "B-GeographicalPosition");
					list.set(i, inputData + "\t" + "I-GeographicalPosition");
				} else
					list.set(i, inputData + "\tO");
			} else
				list.set(i, inputData + "\tO");
		}
		toPrint();

	}

	public void toPrint() throws IOException {
		StringBuilder output = new StringBuilder();
		for (String ele : list) {
			System.out.println(ele);
			output.append(ele + "\n");
		}
		String printing = output.toString();

		// different dataset, change the path from "Testing Data" to "Testing
		// Data1"
		File file = new File(
				"/Users/AlanHo/Documents/DissertationLibrary/NERsuite/bin/Testing Data1/TestingData(dictionary).txt");
		// creates the file
		file.createNewFile();
		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);
		// Writes the content to the file
		writer.write(printing.toString());
		writer.flush();
		writer.close();
	}

	public static void main(String args[]) throws IOException {
		// different dataset, change the path from "Testing Data" to "Testing
		// Data1"
		String testingDataPath = "/Users/AlanHo/Documents/DissertationLibrary/NERsuite/bin/Testing Data1/No features/TestingData.txt";
		BufferedReader br = new BufferedReader(new FileReader(testingDataPath));
		List<String> input = new ArrayList<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				if (line != null)
					input.add(line);
				line = br.readLine();
			}
		} finally {
			br.close();
			input.removeAll(Arrays.asList(null, ""));
			new RegularExpressionTestingFeatures(input).addFeatures();
		}

	}
}
