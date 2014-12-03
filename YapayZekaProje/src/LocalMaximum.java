import java.awt.Point;
import java.util.ArrayList;

public class LocalMaximum {
	private TilePanel[][] tiles;
	private WalkingMan man;
	private int MarkPoint = 500;
	public LocalMaximum(TilePanel[][] tiles, WalkingMan man) {
		super();
		this.tiles = tiles;
		this.man = man;
	}
	private float getShortestDistance(Point x1,Point x2){
		return (float) Math.sqrt(Math.abs(Math.pow((x1.x-x2.getX()),2)-Math.pow(x1.y-x2.y,2)));
	}
	public Point getBestLocation(){
		return BestLocation(man);
	}
	private Point BestLocation(WalkingMan man){
		ArrayList<Point> listPnt = man.getPath();
		int i =0;
		while(i<listPnt.size()){
			if(tiles[listPnt.get(i).x][listPnt.get(i).y].getType()==mainFrame.Rock)
				listPnt.remove(i);
			i++;
		}
		int bestPathIndex = 0;
		float mincount = 100000000;
		float count = 0;
		for (i = 0; i < listPnt.size(); i++) {// adamin gidebilecegi yollar
			count = 0;
		
			WalkingMan tmpMan = new WalkingMan(listPnt.get(i).x, listPnt.get(i).y, tiles.length);// adam gitti varsayılıyo
			for (int j = 0; j < tmpMan.getPath().size(); j++) {//adam gittikten sonra etrafında gorebiliyosa veya göremiyosa 
				Point p = tmpMan.getPath().get(j);
				if(!tiles[p.x][p.y].isMarked()){// görülmemişse - çıkartma yapılıyo
					count -= MarkPoint;
				}
			}
			for (int j = 0; j < tiles.length; j++) {
				for (int k = 0; k < tiles.length; k++) {
					count+=(int)getShortestDistance(new Point(tmpMan.getLocationX(),tmpMan.getLocationY()),new Point(j,k));
					if(tiles[j][k].isMarked()){
						count += 5;
					}
				}
			}
			
			System.out.println("min count :"+mincount);
			System.out.println("count :"+count);
			
			if(mincount>count){
				mincount = count;
				bestPathIndex = i;
			}
		}
		return listPnt.get(bestPathIndex);
	}
}
