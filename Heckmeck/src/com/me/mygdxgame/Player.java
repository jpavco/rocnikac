package com.me.mygdxgame;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

public class Player {
	private Heckmeck game;
	private int ID,sum;
	public int picked,score;
	public MyButton topStone;
	private Array<Integer>  myStones;
	public int instructNumber;
	
	public Player(Heckmeck g,int id,int posun){
		game=g;
		ID=id;
		picked=sum=score=0;
		myStones= new Array<Integer>();
		getTopStone(posun);
	}
	
	public int getID(){
		return ID;
	}
	
	public int getPicked(){
		return picked;
	}
	
	public MyButton getTop(){
		return topStone;
	}
	
	public Array<Integer> getStones(){
		return myStones;
	}
	
	public void getScore(int x){
			if(x<25)
				score+=1;
			else if(x<29)
				score+=2;
			else if(x<32)
				score+=3;
			else
				score+=4;
	}
	
	public void reduceScore(int x){
		if(x<25)
			score-=1;
		else if(x<29)
			score-=2;
		else if(x<32)
			score-=3;
		else
			score-=4;
		
	}
	
	public int getHighest(){
		myStones.sort();
		return myStones.pop();
	}
	
	
	public void addStone(MyButton s){
		this.myStones.add(s.getValue());
		this.topStone.setValue(s.getValue());
		this.topStone.setStyle(s.getStyle());
		getScore(s.getValue());
	}
	
	public void addCubes(MyButton c, int number , int from){
		for(int i=from;i<from+number;i++){
			game.kocky.hracoveKocky.get(i).setValue(c.getValue());
			game.kocky.hracoveKocky.get(i).setStyle(c.getStyle());
		}
		this.picked+=number;
		if(c.getValue() !=6)
			this.sum+=number*c.getValue();
		else
			this.sum+=number*5;
	}
	
	public int getSum(){
		return this.sum;
	}
	
	public void hraj() {
		
		
	}
	
	public void hraj(int x){
		
		
		
	}
	
	public void failedMove(){
		if(myStones.size!=0){
				game.grill.otoc(topStone.getValue());
				game.grill.blockBiggest(topStone.getValue());
				this.changeTopStone();
		}
		
	}
	
	public void changeTopStone(){
		if(myStones.size!=0){
			reduceScore(myStones.peek());
			myStones.pop();
			if(myStones.size!=0){
				topStone.setValue(myStones.get(myStones.size-1));
				topStone.setStyle(game.grill.grillStyle.get(topStone.getValue()-21));
			}
			else{
				topStone.setValue(0);
				topStone.setStyle(game.grill.empty.getStyle());
			}
		}
	}
	
	public void getTopStone(int posun){
		ChangeListener cListener= new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(myStones.size!=0){
						if(game.board.verifyT(((MyButton)actor).getValue(), getID() )){
							game.board.hraci.get(game.board.onMove).addStone(((MyButton)actor));
							changeTopStone();
							game.board.nextOne();
							Drawman.roll.setVisible(true);
							//premazanie highlightu
							for(MyButton x : game.grill.grill){
								if(!x.isDisabled())
									x.setStyle(game.grill.grillStyle.get(x.getValue()-21));
								}
							for(Player x: game.board.hraci){
								if(x.getTop().getValue()!=0){
									x.getTop().setStyle(game.grill.grillStyle.get(x.getTop().getValue()-21));
									}						
						}
						}
						else
							System.out.println("Nemas dost bodov !");
							
						}
					else{
						System.out.println("Nic nemam !");
					}
				}
		};
		topStone=new MyButton(0, game.grill.empty.getStyle());
		topStone.setWidth(64);
		topStone.setHeight(128);
		if(ID < 2)
			topStone.setPosition(20, 300-posun);
		else
			topStone.setPosition(700, 300-posun);
		topStone.addListener(cListener);
	}
	
	public int getInsN(){
		return 0;
	}
	
	public boolean isBot(){
		return false;
	}
	
}
