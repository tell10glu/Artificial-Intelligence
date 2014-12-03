import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;


public class WalkingMan extends JLabel{
	private static final long serialVersionUID = 1L;
	
	private int LocationX;
	public int getLocationX(){
		return LocationX;
	}
	private int LocationY;
	public int getLocationY(){
		return LocationY;
	}
	public ArrayList<Point> getPath(){// sağ sol alt üst noktalarını döndürür.
		ArrayList<Point> listPoints = new ArrayList<Point>();
		if(LocationY-1>=0){
			listPoints.add(new Point(LocationX,LocationY-1));
		}
		if(LocationY+1<MapLength){
			listPoints.add(new Point(LocationX,LocationY+1));
		}
		if(LocationX+1<MapLength){
			listPoints.add(new Point(LocationX+1,LocationY));
		}
		if(LocationX-1>=0){
			listPoints.add(new Point(LocationX-1,LocationY));
		}
		return listPoints;
	}
	public void Go(int X,int Y){
		this.LocationX = X;
		this.LocationY = Y;
	}
	private int MapLength;
	public WalkingMan(int locx,int locy,int mapLength){
		LocationX = locx;
		LocationY = locy;
		MapLength = mapLength;
	}
}
