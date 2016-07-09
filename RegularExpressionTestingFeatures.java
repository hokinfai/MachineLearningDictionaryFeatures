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
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches("^(Dr|Miss|Ms|Sir|Madam)$"))
				list.set(i, inputData + "\tB-NamedEntity_Title");
			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			System.out.println(inputData);
			String[] spilter = inputData.split("\t");
			String originalForm = spilter[3];
			String namedEntity = spilter[2];
			String chunk = spilter[5];
			if (originalForm.equals(namedEntity.trim()) && !chunk.equals("O") && spilter[4].matches("NNP\\w?")) {
				if (namedEntity.matches("[A-Z][a-z][a-z]+") || namedEntity.matches("[A-Z][A-Z]+"))
					list.set(i, inputData + "\t" + chunk.substring(0, 2) + "NamedEntity_ProperName");
				else
					list.set(i, inputData + "\tO");
			} else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String chunk = spilter[5];
			String namedEntity = spilter[2];
			if (namedEntity.matches("(16|17|18|19|20)\\d{2}(-\\d+)?") && !chunk.equals("O"))
				list.set(i, inputData + "\t" + chunk.substring(0, 2) + "Time_Year");
			else if (namedEntity.matches("(16|17|18|19|20)\\d{2}(-\\d+)?") && chunk.equals("O"))
				list.set(i, inputData + "\t" + "B-Time_Year");
			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String chunk = spilter[5];
			String namedEntity = spilter[2];
			if (namedEntity.matches("^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\w+?"))
				list.set(i, inputData + "\t" + chunk.substring(0, 2) + "Time_Month");
			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			String chunk = spilter[5];
			if (namedEntity.matches(
					"(current(ly)?|recent(ly)?|soon(\\w*)|after|before|night|day|annual(ly)?|early|earlier|later|old|spring|autumn|summer|winter)"))
				list.set(i, inputData + "\t" + chunk.substring(0, 2) + "Time_Phase");
//			else if (namedEntity.matches("(year(s)?|month(s)?|day(s)?|week(s)?)"))
//				list.set(i, inputData + "\tI-Time_Phase");
			else
				list.set(i, inputData + "\tO");
		}

		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			String color = "^gray|blue|pink|red|white|black|yellow|gold|orange|dark|brown|purple|violet|silver|green|lime|jade|navy$";
			if (namedEntity.matches(color))
				list.set(i, inputData + "\t" + "B-Color");
			else
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

		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches("\\d+-\\w+ed$"))
				list.set(i, inputData + "\t" + "B-Size_Description");
			else
				list.set(i, inputData + "\tO");
		}
		for (int i = 0; i < list.size(); i++) {
			String inputData = list.get(i);
			String[] spilter = inputData.split("\t");
			String namedEntity = spilter[2];
			if (namedEntity.matches("([sS]outh|[nN]orth|[wW]est|[eE]ast)(ern)?"))
				list.set(i, inputData + "\t" + "B-Cardinal_Direction");
			else
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
		File file = new File(
				"/Users/AlanHo/Documents/DissertationLibrary/NERsuite/bin/Testing Data/TestingData(dictionary).txt");
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
		String testingDataPath = "/Users/AlanHo/Documents/DissertationLibrary/NERsuite/bin/Testing Data/TestingData.txt";
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
