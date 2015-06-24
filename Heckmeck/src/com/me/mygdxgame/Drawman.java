package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class Drawman {
	Heckmeck game;
	public static TextButton roll;
	public static TextButton skonci;
	TextButtonStyle s;
	public static TextButtonStyle h;
	public static TextButtonStyle botB;
	BitmapFont f;
	public Drawman(Heckmeck g){
		game=g;
		s= game.getButtonStyle();
		h= getButtonStyleH();
		botB= getButtonStyleBot();
		f= new BitmapFont();
		//--------------BUTTON PRE HADZ A SKONCI-----------
		getButtons();
		
		roll.setPosition(10, 35);
		skonci.setPosition(690, 35);
		game.stage.addActor(roll);
		game.stage.addActor(skonci);
		
		for(int i=0;i<game.board.playersTopStones.size;i++){
	    	game.stage.addActor(game.board.playersTopStones.get(i));
	    }
	}


public void draw(){
	Gdx.gl.glClearColor(0, 0.4f, 1, 1);
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
	game.batch.begin();
	f.draw(game.batch,"On move : "+ Integer.toString(game.board.onMove+1), 700, 28);
	
	for(int i=0;i< game.board.pocetHracov;i++){
		if(i<2)
			f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(i).score), 20, 450-(i*195));
		else
			f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(2).score), 700, 450-((i%2)*195));
	}
	/*
	f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(0).score), 20, 450);
	f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(1).score), 20, 255);
	f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(2).score), 700, 450);
	f.draw(game.batch, "score : "+Integer.toString(game.board.hraci.get(3).score), 700, 255);
	*/
	
	for(int i=0;i<game.board.pocetHracov;i++){
		if(i<2)
			f.draw(game.batch, "Player "+ (i+1) +":", 20, 470-(i*195));
		else
			f.draw(game.batch, "Player "+ (i+1) +":", 700, 470-((i%2)*195));
	}
	/*
	f.draw(game.batch, "Player 1", 20, 470);
	f.draw(game.batch, "Player 2", 20, 275);
	f.draw(game.batch, "Player 3:", 700, 470);
	f.draw(game.batch, "Player 4:", 700, 275);
	*/
	
	f.draw(game.batch,"Sum : "+ game.board.hraci.get(game.board.onMove).getSum(), 28, 30);
	game.batch.end();
}

private void getButtons(){
	    ChangeListener cListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			int temp=8-game.board.hraci.get(game.board.onMove).getPicked();
			if(temp!=0){
				game.kocky.shuffle(temp);
				for(int i=0;i<temp;i++){
					game.kocky.hodeneKocky.get(i).setFree();
				}
				((TextButton)actor).setVisible(false);
				//zrusim highlight
				zrusHighlight();
			}
			else;
			
		}
	    };
	    ChangeListener stener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//zrusim highlight
				if(game.s){
					game.server.getServer().sendToAllTCP(new EndTurnPacket());
					}else if(game.c){
						game.client.getClient().sendTCP(new EndTurnPacket());
					}				
				
				zrusHighlight();
				game.board.hraci.get(game.board.onMove).failedMove();
				game.board.nextOne();
				roll.setVisible(true);
				roll.setDisabled(false);
			}
		    };
	    roll= game.createButton("Roll !", h, 50,100, cListener);
	    roll.setName("roll");
	    skonci = game.createButton("End Turn", s, 50, 100, stener) ;   
		
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

public TextButtonStyle getButtonStyleH(){
	BitmapFont blackFont = new BitmapFont();
	TextureAtlas buttonTextureAtlas = new TextureAtlas("butt/button.pack");
	Skin buttonSkin = new Skin(buttonTextureAtlas);
	TextButtonStyle textButtonStyle = new TextButtonStyle();
	textButtonStyle.up = buttonSkin.getDrawable("highlight");
	textButtonStyle.down = buttonSkin.getDrawable("highlight");
	textButtonStyle.pressedOffsetX = 1;
	textButtonStyle.pressedOffsetY = -1;
	textButtonStyle.font = blackFont;
	return textButtonStyle;
}

public TextButtonStyle getButtonStyleBot(){
	BitmapFont blackFont = new BitmapFont();
	TextureAtlas buttonTextureAtlas = new TextureAtlas("butt/button.pack");
	Skin buttonSkin = new Skin(buttonTextureAtlas);
	TextButtonStyle textButtonStyle = new TextButtonStyle();
	textButtonStyle.up = buttonSkin.getDrawable("down");
	textButtonStyle.down = buttonSkin.getDrawable("down");
	textButtonStyle.checked = buttonSkin.getDrawable("down");
	textButtonStyle.pressedOffsetX = 1;
	textButtonStyle.pressedOffsetY = -1;
	textButtonStyle.font = blackFont;
	return textButtonStyle;
}

}