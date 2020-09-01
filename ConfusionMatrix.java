/**
 * This class is used to create the confusion Matrix which returns the values which are entered
 * through the constructor. These values are determined by the confucion matrix method in Naive Bayes
 *
 * @author 504470gb
 *
 */


public class ConfusionMatrix {

	private int truepositivecount;
	private int falsepositivecount;
	private int truenegativecount;
	private int falsenegativecount;

	/**
	 * This constructor sets each True-positive, false-positive, true-negative and false-negative value
	 * 
	 * @param x1 the entered TruePositive count
	 * @param x2 the entered FalsePositive count
	 * @param x3 the entered TrueNegative count
	 * @param x4 the entered FalseNegative count
	 */

	public ConfusionMatrix(int x1, int x2, int x3, int x4)
	{
		this.truepositivecount = x1;
		this.falsepositivecount = x2;
		this.truenegativecount = x3;
		this.falsenegativecount = x4;
	}

	/**
	 * This method returns the True-Negative count
	 * 
	 * @return the True Negative count
	 */

	public int getTrueNegatives()
	{
		return truenegativecount;
	}
	/**
	 * This method returns the True-Positive count
	 * 
	 * @return the True Positive count
	 */

	public int getTruePositives() 
	{
		return truepositivecount;
	}

	/**
	 * This method returns the False-Negative count
	 * 
	 * @return the False Negative count
	 */
	public int getFalseNegatives()
	{
		return falsenegativecount;
	}

	/**
	 * This method returns the False-Positive count
	 * 
	 * @return the False Positive count
	 */

	public int getFalsePositives()
	{
		return falsepositivecount;
	}





}
