import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is used to widen the functionality of our spam search by allowing me to 
 * pass multiple words as focus words and calculating the spam adn nospam scores of string sentences for multiple words. Later it also allows
 * for files to be passed instead of single string sentences which allows for easier and a larger amount of sentences to be 
 * analyzed at once
 * 
 * @author 504470gb
 *
 */

public class NaiveBayes {

	private WordCounter[] focuswords;
	private double totaldocs;
	private double totalnospam;
	private double totalspam;


	/**
	 * This constructor sets the word counter object array with the multiple focus words contained within
	 * the focus words string array. Each word is passed through into the WordCounter array
	 * 
	 * @param focusWords String array containing multiple focus words
	 */

	public NaiveBayes(String[] focusWords)
	{
		this.focuswords = new WordCounter[focusWords.length];

		for(int i=0; i<focusWords.length; i++)
		{
			this.focuswords[i] = new WordCounter(focusWords[i]);
		}

	}


	/**
	 * This method passes each element in the WordCounter object array through the addSample method 
	 * contained in the WordCounter class. This in turn allows each element in the WordCounter
	 * object array to get its respective spam and no spam counts.
	 * This method also counts the number of spam and no spam documents passed through it and also the total number of documents.
	 * 
	 * @param document contains a 'sentence' as a string
	 */
	public void addSample(String document)
	{


		for(WordCounter word : focuswords)
		{
			word.addSample(document);
		}

		if(document.charAt(0) == '0')
		{
			totalnospam++;
		}
		else if(document.charAt(0) == '1')
		{
			totalspam++;
		}


		totaldocs++;

	}

	/**
	 * The classify method takes in a unclassified document and assigns it a respective spam and no spam score through certain 
	 * conditions. It then returns true if the document is considered spam and false if not
	 * 
	 * @param unclassifiedDocument contains a string of an unclassified 'sentence'
	 * @return returns true if the document is considered spam and false if considered no spam
	 */

	public boolean classify(String unclassifiedDocument)
	{
		double spamscore = this.totalspam/this.totaldocs;
		double nospamscore = this.totalnospam/this.totaldocs;

		String[] sentence = unclassifiedDocument.split(" ");

		for(String word : sentence)
		{
			for(WordCounter focusword : focuswords)
			{
				if(word.contentEquals(focusword.getFocusWord()))
				{
					spamscore *= focusword.getConditionalSpam();
					nospamscore *= focusword.getConditionalNoSpam();
				}


			}


		}

		if(nospamscore < spamscore)
		{

			return true;
		}

		return false;


	}

	/**
	 * This Method is used to train the respective classifier with a file read as input . We read each line of the file
	 * within the while loop and and run the line through the addsample method of the naivebayes class which does all the work of 
	 * getting the various scores for each line and training the classifier
	 * 
	 * @param trainingFile This is a file of multiple strings on each line used to train our classifier
	 * @throws IOException If the file doesn't exist this is thrown
	 */

	public void trainClassifier(File trainingFile) throws IOException
	{	

		try(Scanner in = new Scanner(trainingFile))
		{
			String line = "";
			while(in.hasNextLine())
			{
				line = in.nextLine();
				addSample(line);
				line = "";
			}

		}
		catch(IOException e)
		{
			System.out.println("This training file doesn't exist");
		}

	}



	/**
	 * This is used to classify an unclassified file by reading each line, running it through the classify method of NaiveBayes,
	 * and then writing either 1 or 0(depending on if it is considered spam(1) or no spam(0)) to another file which is defined as output.
	 * 
	 * @param input a file to be classified
	 * @param output the classifications of each line in input are written to this file
	 * @throws IOException thrown if the file doesn't exist
	 */
	public void classifyFile(File input, File output) throws IOException
	{
		
		try(Scanner in = new Scanner(input);
			FileWriter filew = new FileWriter(output);
			PrintWriter printw = new PrintWriter(filew))
		{
			
			
			String line =""; 

			while(in.hasNextLine())
			{

				line = in.nextLine(); 
				if(classify(line))
				{
					printw.println("1");
				}
				else
				{
					printw.println("0");
				}

				line="";
			}

			
		}
		catch(IOException e)
		{
			System.out.println("The input file doesn't exist");
		}
	


	}

	/**
	 * This method is used to find the accuracy of our spam detector by testing a file that has classifications of spam or no spam. We classify each line
	 * and determine the accuracy based on if our classifier is right or not.
	 * 
	 * @param testdata file that contains data to be tested
	 * @return a Confusion matrix with with respective values
	 * @throws IOException thrown if the file doesnt exist
	 */
	public ConfusionMatrix computeAccuracy(File testdata) throws IOException
	{
		int count1 =0;
		int count2 =0;
		int count3 =0;
		int count4 =0;




		try(Scanner in = new Scanner(testdata))

		{

			String line ="";

			while(in.hasNextLine())
			{
				line = in.nextLine();

				if(classify(line.substring(2,line.length())) && (line.charAt(0)) == '1' )
				{
					count1++;
				}
				else if(classify(line.substring(2,line.length())) && (line.charAt(0)) == '0' )
				{
					count2++;
				}
				else if(!classify(line.substring(2,line.length())) && (line.charAt(0)) == '0' )
				{
					count3++;
				}
				else if(!classify(line.substring(2,line.length()))&& (line.charAt(0)) == '1' )
				{
					count4++;
				}
			}

		}
		catch(IOException e)
		{
			System.out.println("The testdata file doesn't exist");
		}




		return new ConfusionMatrix(count1, count2, count3, count4);
	}

	
	
	
}
