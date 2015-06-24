package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PlayGround implements Screen {
	Heckmeck game;
	Stage stage;
	Drawman d;
	public PlayGround(Heckmeck g, Stage s){
		game=g;
		stage=s;
		d= new Drawman(game);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		d.draw();		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		if(game.getEnd()){
			System.out.println("This is the end...");
			game.setScreen(new EndingScreen(game));
		}
		
		if(game.board.hraci.get(game.board.onMove).isBot() ){
			game.board.hraci.get(game.board.onMove).hraj(game.board.hraci.get(game.board.onMove).getInsN());
			long timer=System.nanoTime();
			boolean x=true;
			while(x){
				float elapsedtime = (System.nanoTime()-timer)/1000000000.0f;
				if(elapsedtime > 0.5f){
					x=false;
				}
			}
		}
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
