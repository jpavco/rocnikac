package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.utils.Array;

public class BasicBot extends Player {
	private Heckmeck game;
	private int ID,sum;
	public int picked,score;
	public MyButton topStone;
	private Array<Integer>  myStones,possibleP;
	private Array<MyButton> possible;
	private Array<Integer> sucty;
	public int instructNumber;
	

	public BasicBot(Heckmeck g, int id, int posun) {
		super(g, id, posun);
		game=g;
		ID=id;
		picked=sum=score=instructNumber=0;
		myStones= new Array<Integer>();
		possible = new Array<MyButton>();
		possibleP= new Array<Integer>();
		sucty = new Array<Integer>();
		for(int i=0;i<7;i++)
			sucty.add(0);
		getTopStone(posun);
	}
	

	@Override
	public void hraj(int x){
		System.out.println("BOT !");
		Drawman.roll.setStyle(Drawman.botB);
		Drawman.roll.setDisabled(true);
		Drawman.skonci.setDisabled(true);
		for(int i=0;i<8;i++)
			game.kocky.hodeneKocky.get(i).setDisabled(true);
		
		int temp=8-picked;
		System.out.println(x + " mam zobratych "+picked);
		switch(x){
			case 0: game.kocky.shuffle(temp); this.instructNumber++; break;
			case 1: if(!game.kocky.pickedN.get(6) && game.kocky.hodeneKocky.get(0).getValue() == 6){
						int counter=0;
						for(int i=0;i<game.kocky.hodeneKocky.size;i++){
							if(game.kocky.hodeneKocky.get(i).getValue() ==6 ){
									counter++;
									game.kocky.hodeneKocky.get(i).setStyle(game.kocky.styl.get(0));
							}
						}
						
						this.addCubes(game.kocky.styl.get(6),6 , counter, picked);
						game.kocky.pickedN.set(6, true);
						
						
						//zrusi highlight kociek ktore si bot nevybral
						for(MyButton xx : game.kocky.hodeneKocky){
							if(xx.getStyle() !=game.kocky.styl.get(0))
								xx.setStyle(game.kocky.styl.get(xx.getValue()));
						}
						HighlightPossible();
						HighlightPossibleP();
						this.instructNumber=2;						
					}
				else if(CanPickSmthing()){
					System.out.println("viem nieco zobrat");
					for(MyButton xx : game.kocky.hodeneKocky){
						int tempi= sucty.get(xx.getValue());
						sucty.set(xx.getValue(), tempi+xx.getValue());
						}
					
					int maxi=0;
					int index=1;
					for(int i=1;i<sucty.size-1;i++){
						if(sucty.get(i)>= maxi && !game.kocky.pickedN.get(i)){
							maxi=sucty.get(i);
							index=i;
						}
					}
					
					
					
					
					for(int i=0;i<game.kocky.hodeneKocky.size;i++){
						if(game.kocky.hodeneKocky.get(i).getValue() ==index ){
								game.kocky.hodeneKocky.get(i).setStyle(game.kocky.styl.get(0));
						}
					}
					
					this.addCubes(game.kocky.styl.get(index),index , (maxi/index), picked);
					game.kocky.pickedN.set(index, true);

					
					//zrusi highlight kociek ktore si bot nevybral
					for(MyButton xx : game.kocky.hodeneKocky){
						if(xx.getStyle() !=game.kocky.styl.get(0))
							xx.setStyle(game.kocky.styl.get(xx.getValue()));
					}
					
					HighlightPossible();
					HighlightPossibleP();
					this.instructNumber=2;
					}
				else{
					System.out.println("neviem nic zobrat");
					this.instructNumber=3;
					
				} break;
			case 2:  //ked nieco mozem zobrat .. vezmem najvacsie
				if(possibleP.size !=0 && game.kocky.pickedN.get(6)){
					MyButton tempi=game.board.hraci.get(possibleP.get(0)).getTop();
					this.addStone(tempi);
					game.board.hraci.get(possibleP.get(0)).changeTopStone();
					this.picked=this.instructNumber=this.sum=0;
					zrusHighlight();
					Drawman.roll.setDisabled(false);
					Drawman.skonci.setDisabled(false);
					for(int i=0;i<8;i++)
						game.kocky.hodeneKocky.get(i).setDisabled(false);
					Drawman.roll.setStyle(Drawman.h);
					reset();
					game.board.nextOne();
					
				}
				else if(possible.size !=0 && game.kocky.pickedN.get(6)){
					MyButton tempi= possible.get(possible.size-1);
					this.addStone(tempi);
					(game.grill.grill.get(tempi.getValue()-21)).setStyle(game.grill.empty.getStyle());
					(game.grill.grill.get(tempi.getValue()-21)).setDisabled(true);
					if(game.grill.verify()){
						game.setEnd();
					}
					this.picked=this.instructNumber=this.sum=0;
					zrusHighlight();
					Drawman.roll.setDisabled(false);
					Drawman.skonci.setDisabled(false);
					for(int i=0;i<8;i++)
						game.kocky.hodeneKocky.get(i).setDisabled(false);
					Drawman.roll.setStyle(Drawman.h);
					reset();
					game.board.nextOne();
				}
				else{
					this.instructNumber=0;
					reset();
				} break;
			case 3: System.out.println("Koncim nvm preco mam zobratych kociek "+this.picked+"nahratych "+this.sum+"mohol so zobrat "+possible.size);this.BotfailedMove();break; 	 
				 
		}
		System.out.println("mam zobratych "+this.picked+"mam v sucte "+this.sum+"viem zobrat "+this.possible.size);
	}
	
	@Override
	public int getSum(){
		return this.sum;
	}
	
	
	public void addCubes(ButtonStyle s, int value, int number , int from){
		for(int i=from;i<from+number;i++){
			game.kocky.hracoveKocky.get(i).setValue(value);
			game.kocky.hracoveKocky.get(i).setStyle(s);
		}
		picked+=number;
		System.out.println("zatial mam " + sum);
		if(value !=6)
			this.sum+=number*value;
		else
			this.sum+=number*5;
		
	System.out.println("teraz mam " + sum);
	}
	
	private void zrusHighlight(){
		for(MyButton x : game.grill.grill){
			if(!x.isDisabled()){
				x.setStyle(game.grill.grillStyle.get(x.getValue()-21));
			}
		}
		for(Player x: game.board.hraci){
			if(x.getTop().getValue()!=0){
				x.getTop().setStyle(game.grill.grillStyle.get(x.getTop().getValue()-21));
				}						
		}
	}
	
	private void BotfailedMove(){
		this.picked=this.sum=this.instructNumber=0;
		reset();
		zrusHighlight();
		game.board.hraci.get(game.board.onMove).failedMove();
		game.board.nextOne();
		Drawman.roll.setDisabled(false);
		Drawman.skonci.setDisabled(false);
		for(int i=0;i<8;i++)
			game.kocky.hodeneKocky.get(i).setDisabled(false);
		Drawman.roll.setStyle(Drawman.h);
	}
	
	private void HighlightPossible(){
		//highlight mensich rovnych
		for(MyButton x : game.grill.grill){
			if(x.getValue() <= this.getSum() && game.kocky.pickedN.get(6)==true){
				if(!x.isDisabled()){
					x.setStyle(game.grill.hlgrillStyle.get(x.getValue()-21));
					possible.add(x);
				}
			}else{
				if(!x.isDisabled()){
					x.setStyle(game.grill.grillStyle.get(x.getValue()-21));
				}
			}
		}
		
		
	}
	
	private void HighlightPossibleP(){
		//hightlight playerovych vrchnych kamenoch
				for(Player x: game.board.hraci){
					if( game.board.hraci.get(game.board.onMove).getID() != x.getID() && x.getTop().getValue() == (game.board.hraci.get(game.board.onMove).getSum()) && game.kocky.pickedN.get(6)==true){
						x.topStone.setStyle(game.grill.hlgrillStyle.get(x.getTop().getValue()-21));
						possibleP.add(x.getID());
					}else{
						if(x.getTop().getValue()!=0){
						x.topStone.setStyle(game.grill.grillStyle.get(x.getTop().getValue()-21));
						}
					}						
				}
	}
	
	private boolean  CanPickSmthing(){
		for(int i=1;i<6;i++){
			if(!game.kocky.pickedN.get(i)){
				for(int j=0;j<8;j++){
					if(game.kocky.hodeneKocky.get(j).getValue() == i){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public int getInsN(){
		return this.instructNumber;
	}
	
	private void reset(){
		for(int i=0;i<7;i++)
			sucty.set(i, 0);
		possible.clear();
		possibleP.clear();
	}
	
	@Override
	public boolean isBot(){
		return true;
	}

}
