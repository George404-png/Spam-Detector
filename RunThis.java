import java.io.File;
import java.io.IOException;

public class RunThis {

	public static void main(String[] args) throws IOException {

		//To change the text file change the nb.classifyFile
		String [] words ={"good", "bad"};
		NaiveBayes nb =new NaiveBayes(words);
		nb.trainClassifier(new File("traindata.txt"));
		nb.classifyFile(new File("newdata.txt"),new File("classifications.txt"));
		
		
		//To test  the accuracy. Still in work
		
//		String [] words ={"good", "bad"};
//		NaiveBayes nb =new NaiveBayes(words);
//		nb.trainClassifier(new File("traindata.txt"));
//		ConfusionMatrix cm = nb.computeAccuracy(new File("testdata.txt"));
//		System.out.println(cm.getTruePositives());
//		System.out.println(cm.getFalsePositives());
//		System.out.println(cm.getTrueNegatives());
//		System.out.println(cm.getFalseNegatives());
		
	}

}
