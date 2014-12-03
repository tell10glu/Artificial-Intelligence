
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class TilePanel extends JPanel{// Matrislerdeki her bir birimin çizilmesine yarayan işlem
	private static final long serialVersionUID = 1L;
	private Image img;
	public void Mark(){
		marked = true;
	}
	private boolean marked = false;// Bu birimin adam tarafından bilinip bilinmediğini tutar 
	public boolean isMarked(){
		return marked;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(marked)
			g.drawImage(img, 0, 0,this);// Eğer bu birim gözükmüş ise resim ekrana yazılır . Gözükmemiş ise background gözükür
	}
	private int type;
	public int getType(){
		return type;
	}
	private int cost;
	public int getCost(){
		return cost;
	}
	public TilePanel(Image img,int cost,int type){
		super();
		this.cost = cost;
		this.img = img;
		this.type = type;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);// Background siyah atanır ve tekrar boyanır.
		repaint();
		revalidate();
	}
}