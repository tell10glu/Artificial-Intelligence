import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
public class mainFrame extends JFrame{
	private JPanel pnlCizim;
	private JTextField txtVezir;
	private JTextField txtAt;
	private int vezirsayisi=0,atsayisi=0;
	
	private int[][] mtr;
	public mainFrame() {
		setTitle("Yapay Zeka 2. Ödev");
		mtr = new int[9][9];
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblVezirSayisi = new JLabel("Vezir Sayisi ");
		panel.add(lblVezirSayisi);
		
		txtVezir = new JTextField();
		txtVezir.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				try {
					int sayi = Integer.parseInt(txtVezir.getText());
					vezirsayisi = sayi;
				} catch (Exception e2) {
					txtVezir.setText("");
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			
				
			}
		});
		panel.add(txtVezir);
		txtVezir.setColumns(10);
		
		JLabel lblAtSayisi = new JLabel("At Sayisi ");
		panel.add(lblAtSayisi);
		
		txtAt = new JTextField();
		txtAt.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				try {
					int sayi = Integer.parseInt(txtAt.getText());
					atsayisi = sayi;
				} catch (Exception e2) {
					txtAt.setText("");
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panel.add(txtAt);
		txtAt.setColumns(10);
		
		JButton btnTalarYerletir = new JButton("Taşları Yerleştir");
		btnTalarYerletir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RandomizeMatrixOlustur();
			}
		});
		panel.add(btnTalarYerletir);
		
		JButton btnMaximumYap = new JButton("Maximum Yap");
		btnMaximumYap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IterasyonFrame frmit = new IterasyonFrame(mtr);
				frmit.setVisible(true);
			}
		});
		panel.add(btnMaximumYap);
		
		pnlCizim = new JPanel();
		getContentPane().add(pnlCizim, BorderLayout.CENTER);
		pnlCizim.setLayout(new GridLayout(9, 9, 2, 2));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				mypnl pp = new mypnl(i, j);
				pp.setLayout(new BorderLayout());
				pnlCizim.add(pp);
			}
		}
	}
	private void RandomizeMatrixOlustur(){// this.mtr deki matrisi randomize olusturur.
		 int sayac=0;
		 Random rnd = new Random();
		 int x,y;
		 for(int i =0;i<9;i++){
			 for(int j =0;j<9;j++)
				 mtr[i][j]=0;
		 }
         while(sayac<vezirsayisi)//vezir
         {
             x = rnd.nextInt(9);
             y = rnd.nextInt(9);
             if(mtr[x][y]==0)
             {
                 mtr[x][y]=-1;
                 sayac++;
             }
         }
         sayac=0;
         while(sayac<atsayisi)//at
         {
             x = rnd.nextInt(9);
             y = rnd.nextInt(9);
             if(mtr[x][y]==0)
             {
                 mtr[x][y]=-2;
                 sayac++;
             }
         }

 		RemoveAllChildrenOfPanel(pnlCizim);
		MatrixToPanel(mtr, pnlCizim); // matrix randomize oluşturulduktan sonra panele çizdirilir.
	}
	private static final long serialVersionUID = 1L;
	
	
	public static void RemoveAllChildrenOfPanel(JPanel pnl){// resimler silinmeyip üst üste biniyor. !!!!!!!!!!!!!!!
		for(int i =0;i<pnl.getComponentCount();i++){
			if(pnl.getComponent(i) instanceof mypnl){
				mypnl pp = (mypnl)pnl.getComponent(i);
				pp.removeAll();
				pp.revalidate();
				pp.repaint();
				pp.validate();
			}
		}
		pnl.validate();
		pnl.repaint(50L);
	}
	public static void MatrixToPanel(int[][] matris,JPanel pnl){// matris randomize edildikten sonra bu fonksiyon çağırılıp panelde taşlar yerleştirilecek.
		Image at=null,vezir =null;// fonksiyon hatali ekleme yapıldıktan sonra panellerden fotoğraflar silinmiyor . Validate invalidate repaintler ile ilgili hata var
		BufferedImage img= null;
		int width = pnl.getWidth()/9;
		int height = pnl.getHeight()/9;
		try {// resimler yukleniyor
			String path = new File(".").getCanonicalPath();
			img = ImageIO.read(new File(path+"/Images/vezir.png"));
			vezir =  img.getScaledInstance(width, height,Image.SCALE_SMOOTH);
			img = ImageIO.read(new File(path+"/Images/at.png"));
			at = img.getScaledInstance(width, height,Image.SCALE_SMOOTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < matris.length; i++) {
			for (int j = 0; j < matris.length; j++) {
				int k = 0;
				boolean found = false;
				if(matris[i][j]==-1){
					while(k<pnl.getComponentCount() && !found){
						if(pnl.getComponent(k) instanceof mypnl){
							mypnl p = (mypnl)pnl.getComponent(k);
							if(p.row==i && p.column==j){
								found = true;
								JLabel lbl = new JLabel(new ImageIcon(vezir));
								p.add(lbl,BorderLayout.CENTER);
								p.repaint();
								p.revalidate();
							}
							else
								k++;
						}else
							k++;
					}
				}
				else if (matris[i][j]==-2){
					while(k<pnl.getComponentCount() && !found){
						if(pnl.getComponent(k) instanceof mypnl){
							mypnl p = (mypnl)pnl.getComponent(k);
							if(p.row==i && p.column==j){
								found = true;
								JLabel lbl = new JLabel(new ImageIcon(at));
								p.add(lbl,BorderLayout.CENTER);
								p.repaint();
								p.revalidate();
							}
							else
								k++;
						}else
							k++;
					}
					
				}
				
			}
		}
		pnl.repaint();
		pnl.revalidate();
	}
	public static void main(String[] args){
		mainFrame frm = new mainFrame();
		frm.setVisible(true);
	}
}
