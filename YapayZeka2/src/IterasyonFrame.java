import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;


public class IterasyonFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel lblHafiza,lbliterasyon,lbltehdit;
	private JPanel pnlCizim;
	private JPanel panel;
	private JButton btnAdimAdimIlerlet;
	private JButton btnYavaslatilmisGoster;
	private JButton btnSonaGit;
	public IterasyonFrame(int matris[][]){
		this.matris = matris;
		TaslariListeyeEkle();
		gui();
	}
	private void gui(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,500);
		
		JPanel pnlInformation = new JPanel();
		getContentPane().add(pnlInformation, BorderLayout.NORTH);
		
		JLabel lblMaksimumHafiza = new JLabel("Maksimum Hafiza : ");
		pnlInformation.add(lblMaksimumHafiza);
		
		lblHafiza = new JLabel("asd");
		lblHafiza.setForeground(Color.ORANGE);
		pnlInformation.add(lblHafiza);
		
		JLabel lblIterasyonnn = new JLabel("Iterasyon :");
		pnlInformation.add(lblIterasyonnn);
		
		lbliterasyon  = new JLabel("0");
		lbliterasyon.setForeground(Color.BLUE);
		pnlInformation.add(lbliterasyon);
		
		JLabel lblTehditSayisi = new JLabel("Tehdit Sayisi :");
		pnlInformation.add(lblTehditSayisi);
		
		lbltehdit = new JLabel("New label");
		pnlInformation.add(lbltehdit);
		
		pnlCizim = new JPanel();
		pnlCizim.setLayout(new GridLayout(9, 9, 2, 2));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				pnlCizim.add(new mypnl(i,j));
			}
		}
		getContentPane().add(pnlCizim, BorderLayout.CENTER);
		lbltehdit.setText(String.valueOf(tablonuntehdidinihesapla(this.matris)));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout());
		btnAdimAdimIlerlet = new JButton("Sonraki");
		btnAdimAdimIlerlet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				iterasyonYap();
				IterasyonSonrasiGuiIslemleri();
				
			}
		});
		panel.add(btnAdimAdimIlerlet,BorderLayout.CENTER);
	}
	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
		mainFrame.MatrixToPanel(matris, pnlCizim);
	}
	private int IterasyonSonrasiGuiIslemleri(){
		if(listIterasyon.size()>maxmemory){
			maxmemory = listIterasyon.size();
			lblHafiza.setText(String.valueOf(maxmemory+"Nodes"));
		}
		lbliterasyon.setText(String.valueOf(iterasyonsayisi));
		int index = YeniTahtayiGoruntule();// Eğer Yeni Tahtadan Gelen Deger -1 ise yani iterasyondaki değer maximum ise lokal maksimum bulunmuştur ve daha fazla iterasyon yapmaz.
		if(index ==-1){
			JOptionPane.showMessageDialog(this, "Lokal Maksimum Noktasina Gelindi", "Bitiş", JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}
		int tehdit = listIterasyon.get(index).tehdit;
		iternode n = listIterasyon.get(index); // Yeni durumdaki iterasyon alınır.
		
		n.tas.KonumAta(n.yeniyeri.x, n.yeniyeri.y);// Tasin yeni konumu atanır.
		for (int i = 0; i < matris.length; i++) {
			for (int j = 0; j < matris.length; j++) {
				matris[i][j]=0;// Tasin matristeki değeri 0lanır.
			}
		}
		for (int i = 0; i < listTaslar.size(); i++) {
			taslar tas = listTaslar.get(i);
			
			if(tas instanceof vezir){
				matris[tas.KonumGetir().x][tas.KonumGetir().y]=-1;// Tasin yeni konumu matriste yerine koyulur.
			}else

				matris[tas.KonumGetir().x][tas.KonumGetir().y]=-2;
		}
		lbltehdit.setText(String.valueOf(tehdit)); 
		mainFrame.RemoveAllChildrenOfPanel(pnlCizim);
		mainFrame.MatrixToPanel(matris, pnlCizim);// Tahtayı matrise çizer.
		pnlCizim.repaint();
		pnlCizim.revalidate();
		listIterasyon.clear(); // Listedeki tüm iterasyonları temizler.
		return 0;
	}
	
	private int tablonuntehdidinihesapla(int mtr[][]){//Her tasin tehdit ettigi kareleri toplar.
		int count = 0;
		for(int i =0;i<listTaslar.size();i++){
			count +=listTaslar.get(i).kontrol();
		}
		return count;
	}
	private void TaslariListeyeEkle(){ // Matris uzerinde bulunan taslari "(-1) Vezir,(-2) At " olacak şekilde matristen alıp listeye ekler.
		for(int i =0;i<9;i++)
			for(int j =0;j<9;j++)
				if(matris[i][j]==-1){
					listTaslar.add(new vezir(matris,i,j));
				}else if(matris[i][j]==-2){
					listTaslar.add(new at(i,j,matris));
				}
	}
	private ArrayList<taslar> listTaslar = new ArrayList<taslar>();// listedeki taşların tutulduğu liste . Bu listedeki her taşın olası hareketlerini yapıp listIterasyona eklenecek.
	private ArrayList<iternode> listIterasyon  = new ArrayList<iternode>(); //bütün iterasyonların tutulduğu liste.. Her iterasyonda maksimum bulunacak ve temizlenecek.
	private int matris[][];
	private int maxmemory;
	private int maxTehditSayisi ;
	private int iterasyonsayisi = 0;
	private void iterasyonYap() {
		if(iterasyonsayisi<1000){
			for (int i = 0; i < listTaslar.size(); i++) {//  Her Iterasyonda listede bulunan butun taslarin yapabileceği hamleler hesaplanır ve iterasyon listesine eklenir.
				if(listTaslar.get(i) instanceof vezir){
					vezir vzr =(vezir) listTaslar.get(i);
					int x = vzr.x;
					int y = vzr.y;
					matris[x][y]=0;// Tas hareket etmeden once matristeki durumu 0 lanır ve yapabileceği tüm hareketler denenir.
					vezirHareketleri(vzr,vzr.x, vzr.y, matris);
					matris[x][y]=-1;//Tum hareketler denendikten sonra tas tekrar yerine koyulur.
					vzr.x = x;
					vzr.y = y;
				}else{
					at att = (at)listTaslar.get(i);
					int x = att.x;
					int y = att.y;
					matris[x][y] = 0;
					atHareketleri(att, att.x,att.y, matris);
					matris[x][y]=-2;
					att.x = x;
					att.y = y;
				}
			}
			iterasyonsayisi++;
		}
	}
	private int YeniTahtayiGoruntule(){
		int max = -9999;
		int index = -1;
		for (int i = 0; i < listIterasyon.size(); i++) {
			if(listIterasyon.get(i).tehdit>max){
				max = listIterasyon.get(i).tehdit;
				index = i;
			}
		}
		
		if(max>maxTehditSayisi){
			maxTehditSayisi = max;
			return index;
		}
		else
			return -1;
		
	}
	private void vezirHareketleri(vezir vzr,int a,int b,int mtr[][]){// vezirin yapılabileceği tüm hamleleri yapar ve yapilacak iterasyona ekler.
		int x = a;
		int y = b;
				x--;
				y++;
				boolean cik = false;
				while(x>=0 && y<=8 && !cik)
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else{
						cik=true;
					}
					x--;
					y++;		
				}
				x= a;
				y = b;
				x--;
				y--;
				cik = false;
				while(x>=0 && y>=0&& !cik)
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else{
						cik=true;
					}
					x--;
					y--;			
				}
				x= a;
				y = b;
				x++;
				y--;
				cik = false;
				while(x<=8 && y>=0 && !cik)
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else{
						cik=true;
					}
					x++;
					y--;			
				}
				x=a;
				y=b;
				x++;
				cik = false;
				while(x<=8 && !cik)//asagi
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else
						cik=true;
					
					x++;			
				}	
				x=a;
				y=b;	
				x--;
				cik = false;
				while(x>=0 && !cik)//asagi
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else
						cik = true;
					x--;			
				}	
				x=a;
				y=b;
				y--;
				cik = false;
				while(y>=0 && !cik)//asagi
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else
					cik = true;
					y--;
		        }	
				x=a;
				y=b;
				y++;
				cik = false;
				while(y<=8 && !cik)//asagi
				{
					if(mtr[x][y]!=-1 && mtr[x][y]!=-2)
					{
						vzr.x = x;
						vzr.y = y;
						mtr[x][y]=-1;
						iternode node = new iternode();
						node.eskiyeri = new Point(a,b);
						node.yeniyeri = new Point(x,y);
						node.tastipi = -1;
						node.tas = vzr;
						node.tehdit = tablonuntehdidinihesapla(mtr);
						mtr[x][y]=0;
						listIterasyon.add(node);
					}else
						cik = true;
					y++;			
				}		
				
				
	}
	private void atHareketleri(at att,int a,int b,int mtr[][]){// atin yapabileceği tüm hamleleri yapar ve iterasyona ekler
		int x = a,y = b;
		  if(x-2>=0 && y-1>=0 && (matris[x-2][y-1]!=-1 && matris[x-2][y-1]!=-2)) 
	         {
			  att.x = x-2;
			  att.y = y-1;
			  mtr[x-2][y-1] = -2;
			  iternode node = new iternode();
			  node.eskiyeri = new Point(a,b);
			  node.yeniyeri = new Point(x-2,y-1);
			  node.tastipi = -2;
			  node.tas = att;
			  node.tehdit = tablonuntehdidinihesapla(mtr);
			  mtr[x-2][y-1] = 0;
			  listIterasyon.add(node);
	         }
	         if(x-2>=0 && y+1<=8 && (matris[x-2][y+1]!=-1 && matris[x-2][y+1]!=-2)) 
	         {
	        	 att.x = x-2;
				  att.y = y+1;
				  mtr[x-2][y+1] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x-2,y+1);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x-2][y+1] = 0;
				  listIterasyon.add(node);
	         }
	         if(x-1>=0 && y-2>=0 && (matris[x-1][y-2]!=-1 && matris[x-1][y-2]!=-2)) 
	         {
	        	  att.x = x-1;
				  att.y = y-2;
				  mtr[x-1][y-2] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x-1,y-2);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x-1][y-2] = 0;
				  listIterasyon.add(node);
	         }       
	         if(x-1>=0 && y+2<=8 && (matris[x-1][y+2]!=-1 && matris[x-1][y+2]!=-2)) 
	         {
	        	  att.x = x-1;
				  att.y = y+2;
				  mtr[x-1][y+2] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x-1,y+2);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x-1][y+2] = 0;
				  listIterasyon.add(node);
	         }       
	         if(x+1<=8 && y-2>=0 && (matris[x+1][y-2]!=-1 && matris[x+1][y-2]!=-2)) 
	         {
	        	  att.x = x+1;
				  att.y = y-2;
				  mtr[x+1][y-2] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x+1,y-2);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x+1][y-2] = 0;
				  listIterasyon.add(node);
	         }
	         if(x+1<=8 && y+2<=8 && (matris[x+1][y+2]!=-1 && matris[x+1][y+2]!=-2)) 
	         {
	        	 att.x = x+1;
				  att.y = y+2;
				  mtr[x+1][y+2] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x+1,y+2);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x+1][y+2] = 0;
				  listIterasyon.add(node);
	         }
	         if(x+2<=8 && y-1>=0 && (matris[x+2][y-1]!=-1 && matris[x+2][y-1]!=-2)) 
	         {
	        	  att.x = x+2;
				  att.y = y-1;
				  mtr[x+2][y-1] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x+2,y-1);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x+2][y-1] = 0;
				  listIterasyon.add(node);
	         }
	         if(x+2<=8 && y+1<=8 && (matris[x+2][y+1]!=-1 && matris[x+2][y+1]!=-2)) 
	         {
	        	 att.x = x+2;
				  att.y = y+1;
				  mtr[x+2][y+1] = -2;
				  iternode node = new iternode();
				  node.eskiyeri = new Point(a,b);
				  node.yeniyeri = new Point(x+2,y+1);
				  node.tastipi = -2;
				  node.tas = att;
				  node.tehdit = tablonuntehdidinihesapla(mtr);
				  mtr[x+2][y+1] = 0;
				  listIterasyon.add(node);
	         }
	}
	class iternode{ // Her bir iterasyonun listede tutulma sekli.
		Point eskiyeri; // tasin hareketten onceki yeri
		Point yeniyeri;// tasin hareketten sonraki yeri
		int tastipi;// -1 ise vezir, -2 ise at
		int tehdit;// tahtada taş yeni yerine koyulduğundaki tehdit sayısını tutar
		taslar tas;
	}
}
