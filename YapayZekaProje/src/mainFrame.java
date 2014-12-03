
import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;


public class mainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int Grass = 0;
	public static final int Mud = 1;
	public static final int Rock = 2;
	public static final int Man = 3;
	int[][] mtr ; // Haritanın tutulduğu matris
	public mainFrame() {
		gui();
	}
	private Map mp;
	private void gui(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		setUndecorated(true);
		CreateRandomMap();
	}
	public void ShowMap(){
		mp = new Map(mtr,getWidth(),getHeight());
		this.add(mp,BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	private void CreateRandomMap(){// Rastgele haritayı oluşturur . 
		Random rnd = new Random();
		int wh = rnd.nextInt(10);
		wh +=20;
		mtr = new int[wh][wh];
		int random = new Random().nextInt(20)+30;//%30-%50 aralığında toprak veya çamur ekler .
		for(int i =0;i<random;i++){
			if(new Random().nextInt(2)==0){
				mtr[new Random().nextInt(wh)][new Random().nextInt(wh)] = mainFrame.Rock;
			}else{
				mtr[new Random().nextInt(wh)][new Random().nextInt(wh)] = mainFrame.Mud;
			}
		}
		int rw,rh;
		do{
			rw = new Random().nextInt(wh);
			rh = new Random().nextInt(wh);
		}while(mtr[rw][rh] != mainFrame.Grass);
		mtr[rw][rh] = mainFrame.Man;// Haritada çim olan bir yere adamımızı koyar.
	}
	
	public void StartWalking(){// Adamin yürüme işlemini başlatır.
		mp.wa.run();
	}
	public static void main(String[] args){
		mainFrame frm = new mainFrame();
		frm.pack();
		frm.setVisible(true);
		frm.ShowMap();
		frm.StartWalking();
		
	}
}
