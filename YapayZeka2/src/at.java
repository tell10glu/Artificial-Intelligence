import java.awt.Point;


public class at implements taslar {
	private int matris[][];
	public int x,y;
    public at(int matris [][])
    {
    	this.matris = matris;
    }
    public at(int x,int y,int matris [][])
    {
    	this.x = x;
    	this.y = y;
    	this.matris = matris;
    }
    public int kontrol(){
    	 int tehdit=1;
         if(x-2>=0 && y-1>=0 && (matris[x-2][y-1]!=-1 || matris[x-2][y-1]!=-2)) 
         {
             tehdit++;
         }
         if(x-2>=0 && y+1<=8 && (matris[x-2][y+1]!=-1 || matris[x-2][y+1]!=-2)) 
         {
             tehdit++;
         }
         if(x-1>=0 && y-2>=0 && (matris[x-1][y-2]!=-1 || matris[x-1][y-2]!=-2)) 
         {
             tehdit++;
         }       
         if(x-1>=0 && y+2<=8 && (matris[x-1][y+2]!=-1 || matris[x-1][y+2]!=-2)) 
         {
             tehdit++;
         }       
         if(x+1<=8 && y-2>=0 && (matris[x+1][y-2]!=-1 || matris[x+1][y-2]!=-2)) 
         {
             tehdit++;
         }
         if(x+1<=8 && y+2<=8 && (matris[x+1][y+2]!=-1 || matris[x+1][y+2]!=-2)) 
         {
             tehdit++;
         }
         if(x+2<=8 && y-1>=0 && (matris[x+2][y-1]!=-1 || matris[x+2][y-1]!=-2)) 
         {
             tehdit++;
         }
         if(x+2<=8 && y+1<=8 && (matris[x+2][y+1]!=-1 || matris[x+2][y+1]!=-2)) 
         {
             tehdit++;
         }
         return tehdit;
    }
    public int kontrol(int x,int y,int value)
    {
        int tehdit=1;
        if(x-2>=0 && y-1>=0 && (matris[x-2][y-1]!=-1 || matris[x-2][y-1]!=-2)) 
        {
            tehdit++;
        }
        if(x-2>=0 && y+1<=8 && (matris[x-2][y+1]!=-1 || matris[x-2][y+1]!=-2)) 
        {
            tehdit++;
        }
        if(x-1>=0 && y-2>=0 && (matris[x-1][y-2]!=-1 || matris[x-1][y-2]!=-2)) 
        {
            tehdit++;
        }       
        if(x-1>=0 && y+2<=8 && (matris[x-1][y+2]!=-1 || matris[x-1][y+2]!=-2)) 
        {
            tehdit++;
        }       
        if(x+1<=8 && y-2>=0 && (matris[x+1][y-2]!=-1 || matris[x+1][y-2]!=-2)) 
        {
            tehdit++;
        }
        if(x+1<=8 && y+2<=8 && (matris[x+1][y+2]!=-1 || matris[x+1][y+2]!=-2)) 
        {
            tehdit++;
        }
        if(x+2<=8 && y-1>=0 && (matris[x+2][y-1]!=-1 || matris[x+2][y-1]!=-2)) 
        {
            tehdit++;
        }
        if(x+2<=8 && y+1<=8 && (matris[x+2][y+1]!=-1 || matris[x+2][y+1]!=-2)) 
        {
            tehdit++;
        }
        return tehdit;
    }
    public int kontrol(int value)  {
        int tehdit=1;
        if(x-2>=0 && y-1>=0 && (matris[x-2][y-1]!=-1 || matris[x-2][y-1]!=-2)) 
        {
            tehdit++;
        }
        if(x-2>=0 && y+1<=8 && (matris[x-2][y+1]!=-1 || matris[x-2][y+1]!=-2)) 
        {
            tehdit++;
        }
        if(x-1>=0 && y-2>=0 && (matris[x-1][y-2]!=-1 || matris[x-1][y-2]!=-2)) 
        {
            tehdit++;
        }       
        if(x-1>=0 && y+2<=8 && (matris[x-1][y+2]!=-1 || matris[x-1][y+2]!=-2)) 
        {
            tehdit++;
        }       
        if(x+1<=8 && y-2>=0 && (matris[x+1][y-2]!=-1 || matris[x+1][y-2]!=-2)) 
        {
            tehdit++;
        }
        if(x+1<=8 && y+2<=8 && (matris[x+1][y+2]!=-1 || matris[x+1][y+2]!=-2)) 
        {
            tehdit++;
        }
        if(x+2<=8 && y-1>=0 && (matris[x+2][y-1]!=-1 || matris[x+2][y-1]!=-2)) 
        {
            tehdit++;
        }
        if(x+2<=8 && y+1<=8 && (matris[x+2][y+1]!=-1 || matris[x+2][y+1]!=-2)) 
        {
            tehdit++;
        }
        return tehdit;
    }
	@Override
	public void KonumAta(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}
	@Override
	public Point KonumGetir() {
		// TODO Auto-generated method stub
		return new Point(this.x,this.y);
	}
}
