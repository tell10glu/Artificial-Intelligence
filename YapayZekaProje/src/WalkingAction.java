import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class WalkingAction implements Runnable{// yürüme işlemi burada Run fonksiyonunda yapılacak.
	private boolean isEnd = false;
	private WalkingMan Wman;
	private TilePanel[][] Tiles;
	private LocalMaximum LMaximum;
	public WalkingAction(WalkingMan wman, TilePanel[][] tiles) {
		super();
		Wman = wman;
		Tiles = tiles;
		Tiles[Wman.getLocationX()][Wman.getLocationY()].add(Wman);
		LMaximum = new LocalMaximum(Tiles, Wman);
	}
	public WalkingAction(boolean isEnd, WalkingMan wman, TilePanel[][] tiles) {
		super();
		this.isEnd = isEnd;
		Wman = wman;
		Tiles = tiles;
		Tiles[Wman.getLocationX()][Wman.getLocationY()].add(Wman);
		LMaximum = new LocalMaximum(Tiles, Wman);
	}
	int tmp = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!isEnd){
			try {
				Tiles[Wman.getLocationX()][Wman.getLocationY()].Mark();
				ArrayList<Point> pntList = Wman.getPath();
				int i = 0;
				while(i<pntList.size()){
					Tiles[pntList.get(i).x][pntList.get(i).y].Mark();
					Tiles[pntList.get(i).x][pntList.get(i).y].repaint();
					Tiles[pntList.get(i).x][pntList.get(i).y].revalidate();
					i++;
				}
				pntList.clear();
				pntList= null;
				// hesaplamalar yapılıp en iyi ihtimal bulunacak.
				
				Point index = LMaximum.getBestLocation();
			
				
				Thread.sleep(300);
				Tiles[Wman.getLocationX()][Wman.getLocationY()].remove(Wman);
				Tiles[Wman.getLocationX()][Wman.getLocationY()].repaint();
				Tiles[Wman.getLocationX()][Wman.getLocationY()].revalidate();
				Wman.Go(index.x, index.y);// en iyi ihtmale götürülecek .
				Tiles[Wman.getLocationX()][Wman.getLocationY()].add(Wman);
				Tiles[Wman.getLocationX()][Wman.getLocationY()].repaint();
				Tiles[Wman.getLocationX()][Wman.getLocationY()].revalidate();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
