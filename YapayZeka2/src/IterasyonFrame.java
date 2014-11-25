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
		
		btnAdimAdimIlerlet = new JButton("Sonraki");
		btnAdimAdimIlerlet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				iterasyonYap();
				IterasyonSonrasiGuiIslemleri();
				
			}
		});
		panel.add(btnAdimAdimIlerlet);
		
		btnYavaslatilmisGoster = new JButton("Yavaslatilmis Goster");
		panel.add(btnYavaslatilmisGoster);
		
		btnSonaGit = new JButton("Sona git");
		panel.add(btnSonaGit);
	}
	private void IterasyonSonrasiGuiIslemleri(){
		if(listIterasyon.size()>maxmemory){
			maxmemory = listIterasyon.size();
			lblHafiza.setText(String.valueOf(maxmemory+"Nodes"));
		}
		lbliterasyon.setText(String.valueOf(iterasyonsayisi));
		int index = YeniTahtayiGoruntule();
		if(index ==-1){
			JOptionPane.showMessageDialog(this, "Lokal Maksimum Noktasina Gelindi", "Bitiş", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int tehdit = listIterasyon.get(index).tehdit;
		iternode n = listIterasyon.get(index);
		
		n.tas.KonumAta(n.yeniyeri.x, n.yeniyeri.y);
		for (int i = 0; i < matris.length; i++) {
			for (int j = 0; j < matris.length; j++) {
				matris[i][j]=0;
			}
		}
		for (int i = 0; i < listTaslar.size(); i++) {
			taslar tas = listTaslar.get(i);
			
			if(tas instanceof vezir){
				matris[tas.KonumGetir().x][tas.KonumGetir().y]=-1;
			}else

				matris[tas.KonumGetir().x][tas.KonumGetir().y]=-2;
		}
		lbltehdit.setText(String.valueOf(tehdit));
		mainFrame.RemoveAllChildrenOfPanel(pnlCizim);
		mainFrame.MatrixToPanel(matris, pnlCizim);
		pnlCizim.repaint();
		pnlCizim.revalidate();
		listIterasyon.clear();
		
	}
	
	private int tablonuntehdidinihesapla(int mtr[][]){
		int count = 0;
		for(int i =0;i<listTaslar.size();i++){
			count +=listTaslar.get(i).kontrol();
		}
		return count;
	}
	private void TaslariListeyeEkle(){
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
	private void iterasyonYap() {// iterasyon yapilacak 
		if(iterasyonsayisi<1000){
			for (int i = 0; i < listTaslar.size(); i++) {
				if(listTaslar.get(i) instanceof vezir){
					/* HATA taşın hareketleri bakılmadan matristeki yeri sıfırlanıyor . Taşın gidebileceği her yer vezirHareketleri fonksiyonunda çağırılıyor
					 * fonksiyonda vezirin her hareketinde matristeki o harekete x,y atanıyor . Tahtanın tehdit sayısı bulunuyor ve listiterasyona ekleniyor.
					 * Bu işlem yapıldıktan sonra matristeki x,y değeri 0 lanıyor ve sonraki hamleye geçiyor. Hata şurada ki bu fonksiyon çağırıldıktan sonra vezirler siliniyor*/
					vezir vzr =(vezir) listTaslar.get(i);
					int x = vzr.x;
					int y = vzr.y;
					matris[x][y]=0;
					vezirHareketleri(vzr,vzr.x, vzr.y, matris);
					matris[x][y]=-1;
					vzr.x = x;
					vzr.y = y;
				}else{
					 //at att = (at)listTaslar.get(i);
				}
			}
			/*for (int i = 0; i < matris.length; i++) {
				for (int j = 0; j < matris.length; j++) {
					if(matris[i][j]==-1){
						System.out.println("Matristeki Vezirin Yeri : "+i+","+j);
					}
				}
			}
			System.out.println("Iterasyon Sayisi : "+listIterasyon.size());
			System.out.println(listTaslar.size());*/
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
			
			// düşük bir olasılıkta yanlış hamle yapmasını sağlar
		}
			
			
		else
			return -1;
		
	}
	private void vezirHareketleri(vezir vzr,int a,int b,int mtr[][]){
		
		int x = a;
		int y = b;
				x--;
				y++;
				while(x>=0 && y<=8)
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
					}
					x--;
					y++;		
				}
				x= a;
				y = b;
				x--;
				y--;
				
				while(x>=0 && y>=0)
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
					}
					x--;
					y--;			
				}
				x= a;
				y = b;
				x++;
				y--;
				
				while(x<=8 && y>=0)
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
					}
					x++;
					y--;			
				}
				x=a;
				y=b;
				x++;
				
				while(x<=8)//asagi
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
					}
					
					x++;			
				}	
				x=a;
				y=b;	
				x--;
				
				while(x>=0)//asagi
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
					}
					x--;			
				}	
				x=a;
				y=b;
				y--;
				
				while(y>=0)//asagi
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
					}
					
					y--;
		        }	
				x=a;
				y=b;
				y++;
				
				while(y<=8)//asagi
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
					}
					y++;			
				}		
				
				
	}
	private ArrayList<Point> atHareketleri(int a,int b,int mtr[][]){
	//	mtr[x][y]=0;
		return null;
	}
	class iternode{
		Point eskiyeri;// tasin hareketten onceki yeri
		Point yeniyeri;// tasin hareketten sonraki yeri
		int tastipi;// -1 ise vezir, -2 ise at
		int tehdit;// tahtada taş yeni yerine koyulduğundaki tehdit sayısını tutar
		taslar tas;
	}
	private void matrisiyazdir(){
		for (int i = 0; i < matris.length; i++) {
			for (int j = 0; j < matris.length; j++) {
				System.out.print(matris[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("*********");
	}
}
