import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel
{
	private String set;
	private int numberOfImages;
	private ImageIcon[] images;
	private Card[] cards;
	private Card currentlyFlipped;
	private boolean aCardIsFlipped;
	private MemoryGame game;
	public GridLayout layout;
	int track;
	
	public GameBoard(String s, int no , MemoryGame g)
	{
		super();
		game= g;
		set=s;
		numberOfImages=no/2;
		track=numberOfImages;
		switch(numberOfImages)
		{
			case 6:layout = new GridLayout(3,4,10,15);
			setBounds(80,100,760,400);
			setLayout(layout);
			break;
			
			case 9:layout = new GridLayout(3,6,10,15);
			setBounds(30,100,860,400);
			setLayout(layout);
			break;
			
			case 12:layout = new GridLayout(4,6,10,15);
			setBounds(20,85,860,450);
			setLayout(layout);
			break;
			
			case 14:layout = new GridLayout(4,7,10,15);
			setBounds(20,85,860,450);
			setLayout(layout);
			break;
		}
		setOpaque(false);
		createImages();
		createCards();
		aCardIsFlipped=false;
	}
	
	public void createImages()
	{
		images=new ImageIcon[numberOfImages];
		for (int i=1;i<=images.length;i++)
		{
			images[i-1]=new ImageIcon(set+"/p ("+i+").jpg");
		}
	}
	
	public void createCards()
	{
		Card.setBack(new ImageIcon(set+"/pp.jpg"));
		cards=new Card[images.length*2];
		for(int i=0, j=0; i<images.length; i++, j+=2)
		{
			cards[j]=new Card(images[i],this);
			cards[j+1]=new Card(images[i],this);
		}
		for(int i=0; i<cards.length;i++)
		{
			swap(i);
		}
		for(int i=0; i<cards.length;i++)
		{
			add(cards[i]);
		}
	}
	
	public void swap(int t)
	{
		int destination=(int)(Math.random()*cards.length);
		Card temp=cards[t];
		cards[t]=cards[destination];
		cards[destination]=temp;
	}
	
	public void flipAll()
	{
		for (int i=0; i< cards.length; i++)
		{
			cards[i].flipUp();
		}
	}
	
	public void reportFlipUp(Card c)
	{
		if(!aCardIsFlipped)
		{
			aCardIsFlipped=true;
			currentlyFlipped=c;
		}
		else if(currentlyFlipped.equals(c)&&currentlyFlipped!=c)
		{
			aCardIsFlipped=false;
			currentlyFlipped.throwAway();
			c.throwAway();
			track--;
			if (track==0) endGame();
		}
		else
		{
			aCardIsFlipped=false;
			CardEffectManager m = new CardEffectManager(c);
			m.setEffect(CardEffectManager.FLIP_DOWN);
			java.util.Timer t = new java.util.Timer();
			t.schedule(m,1000);
			m = new CardEffectManager(currentlyFlipped);
			m.setEffect(CardEffectManager.FLIP_DOWN);
			t.schedule(m,1000);
		}
	}
	
	public void endGame()
	{
		JOptionPane.showMessageDialog(this, "Congratulations!!");
		game.giveUp.setEnabled(false);
	}
	
	public void removeCardListeners()
	{
		for (Card c:cards)
		{
			c.removeMouseListener(c);
		}
	}
}