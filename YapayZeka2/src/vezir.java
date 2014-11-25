import java.awt.Point;



public class vezir implements taslar{

	private int matris[][]; 
	int x,y;
	public vezir(int matris[][],int x,int y)
	{
        	this.matris = matris;
        	this.x = x;
        	this.y = y;
	}
	public int kontrol()
	{
		int value = 1;
		int tehdit=1;//kendi karesini hicbir karede kontrol etmiyor otomatik olarak tehdit sayisi 1
		tehdit += sag(x, y,value);
		tehdit += sol(x, y,value);
		tehdit += yukari(x, y,value);
		tehdit += asagi(x, y,value);
		tehdit += sagYukari(x, y,value);
		tehdit += sagAsagi(x, y,value);
		tehdit += solYukari(x, y,value);
		tehdit += solAsagi(x, y,value);		
		return tehdit;
	}
	public int kontrol(int x, int y,int value)
	{
		int tehdit=1;//kendi karesini hicbir karede kontrol etmiyor otomatik olarak tehdit sayisi 1
		tehdit += sag(x, y,value);
		tehdit += sol(x, y,value);
		tehdit += yukari(x, y,value);
		tehdit += asagi(x, y,value);
		tehdit += sagYukari(x, y,value);
		tehdit += sagAsagi(x, y,value);
		tehdit += solYukari(x, y,value);
		tehdit += solAsagi(x, y,value);		
		return tehdit;
	}
	public int sagYukari(int x, int y,int value)
	{
		int tehdit=0;
		x--;
		y++;
		while(x>=0 && y<=8)
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				tehdit++;
			}
			else
				return tehdit;
			x--;
			y++;		
		}
		return tehdit;		
	}
	public int sagAsagi(int x, int y,int value)
	{
		int tehdit=0;
		x++;
		y++;
		while(x<=8 && y<=8)
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else
				return tehdit;
			x++;
			y++;			
		}
		return tehdit;		
	}
	public int solYukari(int x, int y,int value)
	{
		int tehdit=0;
		x--;
		y--;
		while(x>=0 && y>=0)
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else
				return tehdit;
			x--;
			y--;			
		}
		return tehdit;		
	}
	public int solAsagi(int x, int y,int value)
	{
		int tehdit=0;
		x++;
		y--;
		while(x<=8 && y>=0)
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else
				return tehdit;
			x++;
			y--;			
		}
		return tehdit;		
	}
	public int asagi(int x,int y,int value)
	{
		int tehdit=0;
		x++;
		while(x<=8)//asagi
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else 
				return tehdit;
			
			x++;			
		}		
		return tehdit;
		
	}
	public int yukari(int x,int y,int value)
	{
		int tehdit=0;
		x--;
		while(x>=0)//asagi
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else 
				return tehdit;
			
			x--;			
		}		
		return tehdit;
		
	}
	public int sol(int x,int y,int value)
	{
		int tehdit=0;
		y--;
		while(y>=0)//asagi
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else 
				return tehdit;
			
			y--;
        }		
		return tehdit;
		
	}
	public int sag(int x,int y,int value)
	{
		int tehdit=0;
		y++;
		while(y<=8)//asagi
		{
			if(matris[x][y]!=-1 && matris[x][y]!=-2)
			{
				
				tehdit++;
			}
			else 
				return tehdit;
			
			y++;			
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
