package com.me.mygdxgame;

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

public class ChooseNPlayers implements Screen{
	Heckmeck game;

	OrthographicCamera camera;
	
	Stage stage;
	Texture logo;
	TextureRegion logo2,background;

	public ChooseNPlayers(Heckmeck gam) {
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
				game.setScreen(new Choose2Players(game));
				dispose();				
			}
		    };
		    TextButton button= game.createButton("Two Players", s, 50,150, cListener);
		    
		    ChangeListener cListener2 = new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.setScreen(new Choose3Players(game));
					dispose();				
				}
			    };
			    TextButton button2= game.createButton("Three Players", s, 50,150, cListener2);
			    
			    ChangeListener cListener3 = new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						game.setScreen(new Choose4Players(game));
						dispose();				
					}
				    };
				    TextButton button3= game.createButton("Four Players", s, 50,150, cListener3);
				    
				    ChangeListener cListener4 = new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							game.setScreen(new MultiPlayerMenu(game));
							dispose();			
						}
					    };
				TextButton button4= game.createButton("Back", s, 50,150, cListener4);
				    
				  //---------------
				    
				    
				    stage.addActor(button);
				    button.setPosition(30, 250);
				    stage.addActor(button2);
				    button2.setPosition(30, 200);
				    stage.addActor(button3);
				    button3.setPosition(30, 150);
				    stage.addActor(button4);
				    button4.setPosition(30, 100);
		
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
