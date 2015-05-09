import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MemoryGame extends JFrame implements ActionListener
{
	public GameBoard board;
	MyMenu menu;
	public JDialog dialog;
	JLabel name;
	JComboBox setChooser;
	JComboBox cardNumberChooser;
	JButton confirm;
	
	JButton endGame;
	JButton giveUp;
	
	public MemoryGame()
	{
		super();
		setBounds(100,20,925,660);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		MyLabel d = new MyLabel();
		setContentPane(d);
		setBackground(Color.BLACK);
		menu=new MyMenu(this);
		name = new JLabel("Abdurrahman Ahmed");
		name.setBounds(680,580,200,50);
		name.setFont(new Font("Tohoma", Font.PLAIN, 18));
		add(name);
		repaint();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		switch(Integer.parseInt(e.getActionCommand()))
		{
			case 1: newGame((String)(setChooser).getSelectedItem(), Integer.parseInt((String)((cardNumberChooser).getSelectedItem())));
			dialog.dispose();
			(board.layout).layoutContainer(board); break;
			case 2: backToMenu(); break;
			case 3: giveUp(); break;
			default: return;
		}
	}
	
	public void newGameDialog()
	{
		dialog = new JDialog();
		dialog.setBounds(300,200,400,300);
		dialog.setLayout(null);
		
		JLabel l1= new JLabel("Choose set");
		l1.setBounds(80,40,200,50);
		
		JLabel l2= new JLabel("Choose number of cards");
		l2.setBounds(80,100,200,50);
		
		setChooser = new JComboBox(new String[]{"cats","Final Fantasy"});
		setChooser.setBounds(80,80,200,20);
		
		cardNumberChooser= new JComboBox(new String[]{"12","18","24","28"});
		cardNumberChooser.setBounds(80,140,200,20);
		
		confirm = new JButton("OK");
		confirm.setBounds(170,200,55,45);
		confirm.addActionListener(this);
		confirm.setActionCommand("1");
		
		dialog.add(l1);
		dialog.add(l2);
		dialog.add(setChooser);
		dialog.add(cardNumberChooser);
		dialog.add(confirm);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	public void newGame(String s, int no)
	{
		remove(menu);
		board=new GameBoard(s, no, this);
		add(board);
		((MyLabel)(getContentPane())).setImage(new ImageIcon("Back/b"+s+".jpg"));
		
		endGame = new JButton("Back to menu");
		endGame.setBounds(150,550,150,40);
		endGame.addActionListener(this);
		endGame.setActionCommand("2");
		add(endGame);
		
		giveUp = new JButton("Give up");
		giveUp.setBounds(600,550,150,40);
		giveUp.addActionListener(this);
		giveUp.setActionCommand("3");
		add(giveUp);
		
		repaint();
	}
	
	public void backToMenu()
	{
		remove(board);
		remove(endGame);
		remove(giveUp);
		add(menu);
		((MyLabel)(getContentPane())).setImage(new ImageIcon("Back/b3.jpg"));
		repaint();
	}
	
	public void giveUp()
	{
		board.flipAll();
		board.removeCardListeners();
		giveUp.setEnabled(false);
	}
	
	public static void main(String[] args)
	{
		MemoryGame game = new MemoryGame();
	}
}

class MyLabel extends JPanel
{
	ImageIcon image;
	public MyLabel()
	{
		super();
		setLayout(null);
		image = new ImageIcon("Back/b3.jpg");
		add(new MindGameImage());
		repaint();
	}
	public void setImage(ImageIcon m)
	{
		image=m;
	}
	public void paintComponent(Graphics g)
	{
		g.drawImage(image.getImage(),0,0,getWidth(),getHeight(),image.getImageObserver());
	}
}

class MyMenu extends JPanel implements MouseListener
{
	JLabel firstLabel;
	JLabel secondLabel;
	MemoryGame parent;
	
	public void mouseClicked(MouseEvent m)
	{
		if(m.getSource()==firstLabel) parent.newGameDialog();
		else if(m.getSource()==secondLabel) System.exit(0);
	}
	
	public void mousePressed(MouseEvent m)
	{
	}
	
	public void mouseReleased(MouseEvent m)
	{
	}
	
	public void mouseEntered(MouseEvent m)
	{
		if(m.getSource()==firstLabel||m.getSource()==secondLabel) enlarge((JLabel)m.getSource());
	}
	
	public void mouseExited(MouseEvent m)
	{
		if(m.getSource()==firstLabel||m.getSource()==secondLabel) reduce((JLabel)m.getSource());
	}
	
	public MyMenu(MemoryGame p)
	{
		super();
		setBounds(150,40,600,500);
		setLayout(null);
		setOpaque(false);
		
		parent=p;
		
		firstLabel= new JLabel("New Game");
		firstLabel.setFont(new Font("Matura MT Script Capitals",Font.ITALIC, 54));
		firstLabel.setBounds(150,290,320,50);
		firstLabel.addMouseListener(this);
		
		secondLabel= new JLabel("Exit");
		secondLabel.setFont(new Font("Matura MT Script Capitals",Font.ITALIC, 54));
		secondLabel.setBounds(250,390,115,50);
		secondLabel.addMouseListener(this);
		add(firstLabel);
		add(secondLabel);
		parent.add(this);
		parent.repaint();
	}
	
	public static void enlarge(JLabel l)
	{
		l.setBounds((int)((l.getLocation()).getX()-5),(int)((l.getLocation()).getY()-5),(int)((l.getSize()).getWidth()+10),(int)((l.getSize()).getHeight()+10));
		l.setFont(new Font("Matura MT Script Capitals",Font.ITALIC, 60));
	}
	
	public static void reduce(JLabel l)
	{
		l.setBounds((int)((l.getLocation()).getX()+5),(int)((l.getLocation()).getY()+5),(int)((l.getSize()).getWidth()-10),(int)((l.getSize()).getHeight()-10));
		l.setFont(new Font("Matura MT Script Capitals",Font.ITALIC, 54));
	}
}