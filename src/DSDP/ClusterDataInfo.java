package DSDP;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 * <p>
 * Summary: Close the dialog. The message sender could be either a button or X
 * at the right top of respective dialog.
 * <p>
 * Author: <b>Henry</b> zhanghrswpu@163.com <br>
 * Copyright: The source code and all documents are open and free. PLEASE keep
 * this header while revising the program. <br>
 * Organization: <a href=http://www.fansmale.com/>Lab of Machine Learning</a>,
 * SouthWest Petroleum University, Sichuan 610500, China.<br>
 * Progress: OK. Copied from Hydrosimu.<br>
 * Written time: August 15, 2017. <br>
 * Last modify time: August 15, 2017.
 */
public class ClusterDataInfo {
	public final static int userNum = 943;
	public final static int itemNum = 1682;
	public final static int CHAN_LEN = 5;
	
	/**
	 * User training set
	 */
	public static int[][] uTrRatings; //The rating matrix for the user training 
	public static int[][] uTrRateInds;//The rating indices for the user training
	public static int[] uTrDgr;	//The degrees for the user training
	public static double[] uTrTotRatings;//The total ratings for the user training
	public static double[] uTrAveRatings;//The average ratings for the user training
	public static double[][] uChannels = new double[userNum][CHAN_LEN];
	
	/**
	 * Item training set
	 */
	public static int[][] iTrRatings;//The rating matrix for the item training
	public static int[][] iTrRateInds;//The rating indices for the item training
	public static int[] iTrDgr;  //The degrees for the item training
	public static double[] iTrTotRatings;//The total ratings for the item training
	public static double[] iTrAveRatings;//The average ratings for the item training
	public static double[][] iChannels = new double[itemNum][CHAN_LEN];
	/**
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public ClusterDataInfo() {
	}// Of the first constructor

	/**
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void setUserTrainSet(String paraFileName) throws Exception{
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		}// Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// 读文件的起始位置
		int tempBeginIndex = 0;
		// 将读文件的开始位置移到beginIndex位置。
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		double tempRating = 0;
		
		uTrDgr = new int[userNum];
		uTrRatings = new int[userNum][];
		uTrRateInds = new int[userNum][];
		uTrTotRatings = new double[userNum];
		uTrAveRatings = new double[userNum];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split("	");
			tempUserIndex = Integer.parseInt(tempStrArray[0]) - 1;

			uTrDgr[tempUserIndex] ++;
		}// Of while
		
		for(int i = 0; i < uTrDgr.length; i ++){
			uTrRatings[i] = new int[uTrDgr[i]];
			uTrRateInds[i] = new int[uTrDgr[i]];
			
			if(uTrDgr[i] > 1e-6){
				uTrAveRatings[i] = 
						uTrTotRatings[i] / uTrDgr[i];
			}//Of if
			
			uTrDgr[i] = 0;
		}//Of for i
		
		// 将读文件的开始位置移到beginIndex位置。
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split("	");
			tempUserIndex = Integer.parseInt(tempStrArray[0]) - 1;
			tempItemIndex = Integer.parseInt(tempStrArray[1]) - 1; 
			tempRating = Double.parseDouble(tempStrArray[2]); //*2
			
			uTrRatings[tempUserIndex][uTrDgr[tempUserIndex]] = (int) tempRating;
			uTrRateInds[tempUserIndex][uTrDgr[tempUserIndex]] = tempItemIndex;	
			uTrTotRatings[tempUserIndex] += tempRating;
			uChannels[tempUserIndex][(int)tempRating - 1] ++;
			uTrDgr[tempUserIndex] ++;
		}// Of while
		
		
		for(int i = 0; i < uChannels.length; i ++){
			for(int j = 0; j < uChannels[0].length; j ++){
				uChannels[i][j] /= uTrDgr[i];
			}//Of for j
		}//Of for i
		
		tempRanFile.close();
	}//Of setUserTrainSet
	
	/**
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void setItemTrainSet(String paraFileName) throws Exception{
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		}// Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// 读文件的起始位置
		int tempBeginIndex = 0;
		// 将读文件的开始位置移到beginIndex位置。
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		double tempRating = 0;
		
		iTrDgr = new int[itemNum];
		iTrRatings = new int[itemNum][];
		iTrRateInds = new int[itemNum][];
		iTrTotRatings = new double[itemNum];
		iTrAveRatings = new double[itemNum];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split("	");
			tempItemIndex = Integer.parseInt(tempStrArray[1]) - 1; 
			
			iTrDgr[tempItemIndex] ++;
		}// Of while
		
		for(int i = 0; i < iTrDgr.length; i ++){
			iTrRatings[i] = new int[iTrDgr[i]];
			iTrRateInds[i] = new int[iTrDgr[i]];
			
			if(iTrDgr[i] > 1e-6){
				iTrAveRatings[i] = 
						iTrTotRatings[i] / iTrDgr[i];
			}//Of if
			
			iTrDgr[i] = 0;
		}//Of for i
		
		// 将读文件的开始位置移到beginIndex位置。
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split("	");
			tempUserIndex = Integer.parseInt(tempStrArray[0]) - 1;
			tempItemIndex = Integer.parseInt(tempStrArray[1]) - 1; 
			tempRating = Double.parseDouble(tempStrArray[2]);//*2
			
			iTrRatings[tempItemIndex][iTrDgr[tempItemIndex]] = (int) (tempRating);
			iTrRateInds[tempItemIndex][iTrDgr[tempItemIndex]] = tempUserIndex;
			iTrTotRatings[tempItemIndex] += tempRating;
			iChannels[tempItemIndex][(int)tempRating - 1] ++;
			iTrDgr[tempItemIndex] ++;
		}// Of while
		
		for(int i = 0; i < iChannels.length; i ++){
			for(int j = 0; j < iChannels[0].length; j ++){
				iChannels[i][j] /= iTrDgr[i];
			}//Of for j
		}//Of for i
		
//		System.out.println("getItemTrainSet ");
//		SimpleTool.printMatrix(iTrRateInds);
		tempRanFile.close();
	}//Of setItemTrainSet
	
	/**
	 * 
	 * @throws Exception
	 */
	public void buildDataModel() throws Exception{
		String tempTrain = "D:/workspace/datasets/movielens/ml-100k/u1.base";
		setUserTrainSet(tempTrain);
		setItemTrainSet(tempTrain);
	//	getUserTestSet(tempTest);

	}//of buildDataModel
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {	
			ClusterDataInfo tempSoc = new ClusterDataInfo();
			tempSoc.buildDataModel();
		} catch (Exception ee) {
			ee.printStackTrace();
		} // Of try
	}// Of main
}// Of Class SocialDataModel
