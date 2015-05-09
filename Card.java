import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Card extends JLabel implements MouseListener
{
	private static ImageIcon back;
	private ImageIcon face;
	private boolean faceUp;
	private boolean onBoard;
	private CardEffectManager manager;
	private GameBoard carrier;
	
	//&&&&&&&&&&&&&& CONSTRUCTOR &&&&&&&&&&&&&&&&&&&&&&&&
	public Card(ImageIcon faceImage, GameBoard car)
	{
		super();
		setLocation(10,10);
		setSize(60,100);
		setBackground(Color.WHITE);
		//setOpaque(true);
		face=faceImage;
		faceUp=false;
		onBoard=false;
		carrier=car;
		addMouseListener(this);
		manager=new CardEffectManager(this);
	}
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
	//&&&&&&&&&&&&&& paintComponent &&&&&&&&&&&&&&&&&&&&&
	public void paintComponent(Graphics g)
	{
		if(faceUp)
		g.drawImage(face.getImage(),0,0,getWidth(),getHeight(),face.getImageObserver());
		else
		g.drawImage(back.getImage(),0,0,getWidth(),getHeight(),back.getImageObserver());
	}
	//&&&&&&&&&&&&&& MouseListener methods &&&&&&&&&&&&&&
	public void mouseClicked(MouseEvent m)
	{
		manager.flipUp();
		repaint();
		carrier.repaint();
		carrier.reportFlipUp(this);
	}
	
	public void mousePressed(MouseEvent m)
	{
	}
	
	public void mouseReleased(MouseEvent m)
	{
	}
	
	public void mouseEntered(MouseEvent m)
	{
		manager.enlarge();
		repaint();
	}
	
	public void mouseExited(MouseEvent m)
	{
		manager.reduce();
		repaint();
	}
	//&&&&&&&&&&&&&& Getters and Setters &&&&&&&&&&&&&&&&
	public GameBoard getCarrier()
	{
		return carrier;
	}
	
	public void setFacedUp(boolean b)
	{
		faceUp=b;
		repaint();
	}
	
	public void setOnBoard(boolean b)
	{
		onBoard=b;
	}
	
	public static void setBack(ImageIcon backImage)
	{
		back=backImage;
	}
	
	public boolean isFacedUp()
	{
		return faceUp;
	}
	
	public boolean isOnBoard()
	{
		return onBoard;
	}
	
	public void recreateManager()
	{
		manager=new CardEffectManager(this);
	}
	
	//&&&&&&&&&&&&&& Effects &&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public void flipUp()
	{
		manager.flipUp();
	}
	
	public void flipDown()
	{
		do
		{
			manager.flipDown();
		}
		while(faceUp);
	}
	
	public void throwAway()
	{
		do
		{
			manager.eraseCard();
		}
		while(onBoard);
	}
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
	public boolean equals(Card c)
	{
		return this.face==c.face;
	}
}