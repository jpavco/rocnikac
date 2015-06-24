package com.me.mygdxgame;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

public class Heckmeck extends Game{
	SpriteBatch batch;
	BitmapFont font;
	Stage stage;
	OrthographicCamera camera;
	Kocky kocky;
	Board board;
	Grill grill;
	boolean c=false;
	boolean s=false;
	boolean endGame=false;
	public KryoServer server=null;
	public KryoClient client=null;
	
	@Override
	public void create(){
		//-----BASE-----------------
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new Intro(this));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		stage = new Stage(800,480,true);
		stage.clear();
		
		kocky=new Kocky(this);
	    grill = new Grill(this);
	    board= new Board(this);
	    
	    
	    
	    //------------GRILL IDE NA STAGE -------------------
	    for(int i=0;i<grill.grill.size;i++){
	    	stage.addActor(grill.grill.get(i));
	    }
	   
	    //-----------------KOCKY IDU NA STAGE--------------
	    kocky.shuffle(0);
	    for(int i=0;i<kocky.hodeneKocky.size;i++){
	    	stage.addActor(kocky.hodeneKocky.get(i));
	    }
	    
	    
	    //----------------UMIESTNENIE HRACOVYCH KOCIEK+VRCHNE KAMENE+ HODENIE NA STAGE----------------
	  
	    
	    kocky.setBlank(kocky.hracoveKocky);
	    for(int i=0;i<8;i++){
	    	stage.addActor(kocky.hracoveKocky.get(i));
	    }
	    setScreen(new Intro(this));
	}
	
	
	
	
	public Kocky getKocky(){
		return kocky;
	}
	 
	public Grill getGrill(){
		return grill;
	}
	public Board getBoard(){
		return board;
	}



	@Override
	public void resize(int width, int height) {
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
		stage.dispose();
		batch.dispose();
		font.dispose();
	}
	
	public void setEnd(){
		endGame=true;
	}
	
	public boolean getEnd(){
		return endGame;
	}
	
	public TextButton createButton(String name,TextButtonStyle style,float height,float width,ChangeListener cListener){
		TextButton button = new TextButton(name, style);
		button.setWidth(width);
		button.setHeight(height);
		button.addListener(cListener);
		return button;
	}
	
	public TextButtonStyle getButtonStyle(){
		BitmapFont blackFont = new BitmapFont();
		TextureAtlas buttonTextureAtlas = new TextureAtlas("butt/button.pack");
		Skin buttonSkin = new Skin(buttonTextureAtlas);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = buttonSkin.getDrawable("up");
		textButtonStyle.down = buttonSkin.getDrawable("down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = blackFont;
		return textButtonStyle;
	}


	public void setRoll(Array<Integer> kocky2) {
		kocky.setRoll(kocky2);		
	}


	public void endTurn() {
		for(MyButton x : grill.grill){
			if(!x.isDisabled()){
				x.setStyle(grill.grillStyle.get(x.getValue()-21));
			}
		}
		for(Player x: board.hraci){
			if(x.getTop().getValue()!=0){
				x.getTop().setStyle(grill.grillStyle.get(x.getTop().getValue()-21));
				}						
		}
		board.hraci.get(board.onMove).failedMove();
		board.nextOne();
		Drawman.roll.setVisible(true);
		Drawman.roll.setDisabled(false);
		Drawman.skonci.setDisabled(false);
		
	}
	

}
