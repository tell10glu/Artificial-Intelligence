import java.awt.Color;

import javax.swing.JPanel;


public class mypnl  extends JPanel{
	
		private static final long serialVersionUID = 1L;
		int row,column;
		public mypnl(int row,int column){
			this.row = row;
			this.column = column;
			if((row+column)%2==0)
				this.setBackground(Color.blue);
			else
				this.setBackground(Color.magenta);
		}
}
