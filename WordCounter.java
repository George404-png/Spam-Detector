/**
 * This class contains multiple methods in order to determine certain aspects of focuswords by running sentences through
 * certain methods and determining certain spam and no spam counts to find out if a String should be classified as spam or no spam
 * 
 * @author George
 *
 */


public class WordCounter {


	private final String focusword;
	private int[][] countarray= new int[3][2];

	/*countarray is in form: [focus word no-spam count   ] [focus word-spam count     ]
	 * 					      [non-focus word spam  count] [non-focus no-spam count   ]
	 * 						  [total words in spamString ] [total words no-spam String]
	 * 
	 */
	


	/**
	 * This constructor stores the word that we would like to consider our "spam word"
	 * 
	 * @param focusword the word which we consider spam
	 */
	public WordCounter(String focusword)
	{
		this.focusword = focusword;


	}

	/**
	 * This method returns the focusword we used in the object as a String
	 * 
	 * @return The specific word which is being used as focuswod in this class
	 */

	public String getFocusWord()
	{
		return focusword;
	}

	/**
	 * This method searches through the document we input. It uses a search for spaces in order to determine when a word is formed
	 * After a word is found the substring is compared to our focusword. The requirements are stored into an array for easier use later on.
	 * The start and end of the word are updated to make sure the next word is found.
	 *  
	 * @param document the sentence being input(can be spam or no spam)
	 */

	public void addSample(String document)
	{
		int start = 0;
		int end = 0;
		int buffer = 1;
		document = document + " ";

		for(int i = 2; i<document.length(); i++)
		{
			if(document.charAt(i) == ' ' )
			{
				start = buffer+1;
				end = i;

				if(document.charAt(0) == '0')
				{
					countarray[1][0]++;

					if(document.substring(start,end).equals(focusword))
					{
						countarray[0][0]++;
					}
				
				}
				else if(document.charAt(0) == '1')
				{
					countarray[1][1]++;
					if(document.substring(start,end).equals(focusword))
					{
						countarray[0][1]++;
					}
					

				}

				buffer = i;
			}

		}

		if(document.charAt(0) == '0')
		{
			countarray[2][0]++;
		}
		else if(document.charAt(0)=='1')
		{
			countarray[2][1]++;
		}

	}







	//Probability methods


	/**
	 * This method checks whether our counter is trained by evaluating if certain requirements are fulfilled for it to be considered trained
	 * 
	 * @return true if the requirements are fulfilled
	 */
	public boolean isCounterTrained()
	{
		if((countarray[0][0]+countarray[0][1]) >= 1 && countarray[2][0] >=1 && countarray[2][1] >=1)
		{
			return true;
		}
		return false;

	}

	/**
	 * This method is used to calculate the conditional nospam score of the focusword and return this score
	 * The illegal state exception is thrown in order to ensure that the counter is trained otherwise this method will give innacurate results
	 * 
	 * @return The conditional nospam score
	 * @throws IllegalStateException if the counter is not trained
	 */

	public double getConditionalNoSpam() throws IllegalStateException  
	{
		double x =0;
		if(!isCounterTrained())
		{
			throw new IllegalStateException("Train the Counter");

		}
		else
		{
			x = (double)countarray[0][0] / (double)countarray[1][0];		
		}

		return x;
	}

	/**
	 * This method is used to calculate the conditional spam score of the focusword and return this score
	 * The illegal state exception is thrown in order to ensure that the counter is trained otherwise this method will give innacurate results.
	 * 
	 * @return The conditional spam score
	 * @throws IllegalStateException if the counter is not trained
	 */

	public double getConditionalSpam() throws IllegalStateException
	{
		double x =0;
		if(!isCounterTrained())
		{
			throw new IllegalStateException("Train counter");
		}
		else
		{
			x = (double) countarray[0][1]/(double) countarray[1][1];
		}

		return x;

	}




}
