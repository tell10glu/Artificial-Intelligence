
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Map extends JPanel{

	private int[][] mtr;// Haritanın matrisi
	private TilePanel[][] pnlTile;// Haritanın matrisindeki her bir birimi TilePanellere döküyoruz .
	private WalkingMan Wman;// Haritada dolaşacak adamımız.
	private Image grass = null,rock = null,mud = null,character =null;
	WalkingAction wa;// Yürütme işlemimiz . Bu işlem harita bulunana kadar devam eder . 
	private static final long serialVersionUID = 1L;
	public Map(int[][] matrix,int width,int height){
		mtr = matrix;
		
		this.setSize(width, height);
		init();
	}
	public void init(){
		setLayout(new GridLayout(mtr.length,mtr.length));
		ReadImages();// Resimler file lardan okunur ve Image değişkenlerine atilir .
		pnlTile = new TilePanel[mtr.length][mtr.length];
		for (int i = 0; i < pnlTile.length; i++) {
			for (int j = 0; j < pnlTile.length; j++) {
				// Haritadaki değerlere göre resimler atanır . 
				if(mtr[i][j]==mainFrame.Grass){
					BufferedImage copyOfImage = 
							   new BufferedImage(getWidth()/mtr.length,getHeight()/mtr.length, BufferedImage.TYPE_INT_RGB);
							Graphics g = copyOfImage.createGraphics();
							g.drawImage(grass, 0, 0, null);
							pnlTile[i][j]=new TilePanel(copyOfImage,0,mainFrame.Grass);
				}
				else if(mtr[i][j]==mainFrame.Mud){
					BufferedImage copyOfImage = 
							   new BufferedImage(getWidth()/mtr.length,getHeight()/mtr.length, BufferedImage.TYPE_INT_RGB);
							Graphics g = copyOfImage.createGraphics();
							g.drawImage(mud, 0, 0, null);
					pnlTile[i][j]=new TilePanel(copyOfImage,101,mainFrame.Mud);
				}
				else if(mtr[i][j]==mainFrame.Rock){
					BufferedImage copyOfImage = 
							   new BufferedImage(getWidth()/mtr.length,getHeight()/mtr.length, BufferedImage.TYPE_INT_RGB);
							Graphics g = copyOfImage.createGraphics();
							g.drawImage(rock, 0, 0, null);
					pnlTile[i][j]=new TilePanel(copyOfImage,99999999,mainFrame.Rock);
				}else if(mtr[i][j]==mainFrame.Man){
					 Wman = new WalkingMan(i, j, mtr.length);
					 BufferedImage copyOfImage = 
							   new BufferedImage(getWidth()/mtr.length,getHeight()/mtr.length, BufferedImage.TYPE_INT_RGB);
							Graphics g = copyOfImage.createGraphics();
							g.drawImage(grass, 0, 0, null);
							pnlTile[i][j]=new TilePanel(copyOfImage,0,mainFrame.Grass);
				}
				add(pnlTile[i][j]);
			}
		}
		Wman.setIcon(new ImageIcon(character));//Adamimizin resmi atanır . 
		wa  = new WalkingAction(Wman, pnlTile);// Adami yürütme işlemleri hazırlanır ve run edilmeyi bekler. 
	}
	private void ReadImages(){
		BufferedImage img;
		try {
			System.out.println("Read Images icindeyim");
			String path = new File(".").getCanonicalPath();
			img = ImageIO.read(new File(path+"/Images/grass.jpg"));
			grass = img.getScaledInstance(getWidth()/mtr.length,getHeight()/mtr.length, Image.SCALE_SMOOTH);
			img = ImageIO.read(new File(path+"/Images/rock.jpg"));
			rock = img.getScaledInstance(getWidth()/mtr.length,getHeight()/mtr.length, Image.SCALE_SMOOTH);
			img = ImageIO.read(new File(path+"/Images/mud.jpg"));
			mud =img.getScaledInstance(getWidth()/mtr.length,getHeight()/mtr.length, Image.SCALE_SMOOTH);
			img =ImageIO.read(new File(path+"/Images/character.png"));
			character = img.getScaledInstance(getWidth()/mtr.length,getHeight()/mtr.length, Image.SCALE_SMOOTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
