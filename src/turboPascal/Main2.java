package turboPascal;
import java.util.Scanner;
/*小M想要宴请n个客人，她为他们准备了n个不同口味的披萨，分别盛装在n个大盘子中。但是由于她不知道每位客人喜欢什么口味，所以她希望每位客人都能品尝到这n种不同口味的披萨。于是她将每个披萨都均分成了n片，现在她想把这些分开后的披萨重组成n个包含n种不同口味的披萨。为此她取来了一个空的小盘子，小盘子只能容纳一片披萨。小M可以进行如下操作：
1. 将某个大盘子上的一片披萨放置到另一个大盘子的空余位置，注意任意时刻每个大盘子上至多只能有n片披萨；
2. 将某个大盘子上的一片披萨放置到小盘子中，如果此时小盘子是空的话；
3. 将小盘子上的披萨放置到某个大盘子的空余位置。
请你帮忙计算小M最少要进行多少次操作，才能完成目标。*/

public class Main2 {
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Scanner sc = new Scanner(System.in);
		Main2 testMain= new Main2();
		if (sc.hasNext()) {
			int n=sc.nextInt();
			int[][] anxls=new int[n][4];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 4; j++) {
					anxls[i][j]=sc.nextInt();					
				}
				
			}
			
		}// of if
		
	}
	
	
	public void judgeA(int[][] arr) {
		//3个线段分别表示3个线性函数----线性插值进行拟合
		int y1,y2,y3;
		int x = 0;
		for (int i = 0; i < arr.length; i=i+3) {
			y1=(arr[i][1])*(x-arr[i][2])/(arr[i][0]-arr[i][3]);
			y1=(arr[i+1][1])*(x-arr[i+1][2])/(arr[i+1][0]-arr[i+1][3]);
			y1=(arr[i+2][1])*(x-arr[i+2][2])/(arr[i+2][0]-arr[i+3][3]);
			
		}
	}
	
	
}

