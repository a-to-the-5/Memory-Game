import java.util.*;

public class CardEffectManager extends TimerTask
{
	public Card card;
	public int effect;
	public static final int ERASE=0;
	public static final int FLIP_DOWN=1;
	public static final int RETORT_FLIP=2;
	private boolean busy;
	
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&& CONSTRUCTOR &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public CardEffectManager(Card c)
	{
		card=c;
		busy=false;
	}
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& RUN &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public void run()
	{
		switch (effect)
		{
			case ERASE: (card.getCarrier()).remove(card); (card.getCarrier()).repaint(); card.setOnBoard(false); break;
			case FLIP_DOWN: card.setFacedUp(false); break;
			//case RETORT_FLIP: card.getCarrier().reportFlipUp(card); break;
			default: break;
		}
		card.recreateManager();
	}
	
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  Effects  &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public void setEffect(int i)
	{
		effect=i;
	}
	
	public void enlarge()
	{
		card.setBounds((int)((card.getLocation()).getX()-5),(int)((card.getLocation()).getY()-5),(int)((card.getSize()).getWidth()+10),(int)((card.getSize()).getHeight()+10));
	}
	
	public void reduce()
	{
		card.setBounds((int)((card.getLocation()).getX()+5),(int)((card.getLocation()).getY()+5),(int)((card.getSize()).getWidth()-10),(int)((card.getSize()).getHeight()-10));
	}
	
	public void flipUp()
	{
		if(!card.isFacedUp())
		{
			card.setFacedUp(true);
		}
	}
	
	public void flipDown()
	{
		if(!busy)
		{
			busy=true;
			Timer timer = new Timer();
			effect=FLIP_DOWN;
			timer.schedule(this,500);
		}
	}
	
	public void eraseCard()
	{
		if(!busy)
		{
			busy=true;
			Timer timer = new Timer();
			effect=ERASE;
			timer.schedule(this,500);
		}
	}
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
}