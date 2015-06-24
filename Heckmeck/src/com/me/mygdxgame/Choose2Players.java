package com.me.mygdxgame;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class Choose2Players implements Screen{
	Heckmeck game;

	OrthographicCamera camera;
	
	Stage stage;
	Texture logo;
	TextureRegion logo2,background;
	
	KryoServer server;
	
	public Choose2Players(Heckmeck gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		stage = new Stage(800,480,true);
		stage.clear();
	    Gdx.input.setInputProcessor(stage);
	    
	    logo= new Texture(Gdx.files.internal("data/heckmeck2.png"));
	    Texture b=new Texture(Gdx.files.internal("data/back2.png"));
	    logo2 = new TextureRegion(b, 0, 0, 292, 226);
	    
	    Texture temp= new Texture(Gdx.files.internal("data/background.png"));
		background = new TextureRegion(temp, 0, 320, 800, 480);
	    
	    
	    //-----------------
		 TextButtonStyle s= game.getButtonStyle();
		    ChangeListener cListener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.board.pocetHracov=2;
				game.board.preparePlayers(2,0);
				game.setScreen(new PlayGround(game, game.stage));
				
				dispose();				
			}
		    };
		    TextButton button= game.createButton("Two Players", s, 50,200, cListener);
		    
		    ChangeListener cListener2 = new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.board.pocetHracov=2;
					game.board.preparePlayers(1,1);
					game.setScreen(new PlayGround(game, game.stage));
					dispose();				
				}
			    };
			TextButton button2= game.createButton("One Player vs One Bot", s, 50,200, cListener2);
			    
			 
			
			ChangeListener cListener5 = new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					try {
						server = new KryoServer();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					server.setGame(game);
	                System.out.println("Server started.");
	                game.board.pocetHracov=2;
    				game.board.preparePlayers(2,0);
    				game.setScreen(new PlayGround(game, game.stage));
    				Drawman.roll.setDisabled(true);
    				Drawman.skonci.setDisabled(true);
    				game.s=true;
    				game.server=server;
					dispose();				
					  		
				}
			    };				
			TextButton buttonServer = game.createButton("Two players - LAN",s, 50, 200, cListener5);
			
				
			
			
			
			
			
			ChangeListener cListener4 = new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						game.setScreen(new MultiPlayerMenu(game));
						dispose();			
					}
				    };				    
				    
				    
			TextButton button4= game.createButton("Back", s, 50,200, cListener4);
			    
				    
				  //---------------
				    
				    
				    stage.addActor(button);
				    button.setPosition(230, 260);
				    stage.addActor(button2);
				    button2.setPosition(230, 190);
				    stage.addActor(buttonServer);
				    buttonServer.setPosition(230, 120);
				    stage.addActor(button4);
				    button4.setPosition(230, 50);
				    
		
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.4f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.draw(logo, 200, 170);
	    game.batch.draw(logo2, 400, 20);
	    game.batch.end();
	    
	    stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		camera.update();	
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
