import com.badlogic.gdx.ApplicationAdapter; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Circle; 
import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.audio.*; 
import com.badlogic.gdx.graphics.*; 

//Link to API: https://javadoc.io/doc/com.badlogicgames.gdx/gdx/latest/index.html
//Use control + f to find the class you are looking for in the API
//Use control + f to find the TODO in this project to complete
public class Jumpking extends ApplicationAdapter//A Pong object IS A ApplicationAdapter
{
    //A Pong object HAS the following attributes:
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw shapes like a pen 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text), or images
    private GlyphLayout layout;//needed to position our text
    private Color penColor; 

    private Sound sound; 
    private Texture background; 

    private Texture[] redcrown_woods;
    private Player king;

    private Texture currentSprite;

    public static final float WORLD_WIDTH = 480; 
    public static final float WORLD_HEIGHT = 360;

    private float mouseX;
    private float mouseY;

    private int walkCount;
    private int direc;

    private boolean tempBool;
    @Override//called once when the game is started (kind of like our constructor)
    public void create(){
        camera = new OrthographicCamera(); //camera for our world use this to move up!
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(Gdx.files.internal("frenchScriptMT.fnt")); 
        batch = new SpriteBatch(); 
        layout = new GlyphLayout(font, "Press SPACE_BAR to start");
        penColor = Color.WHITE; 

        redcrown_woods = new Texture[]{
            new Texture(Gdx.files.internal("background1.png"))
        };

        mouseX = 0;
        mouseY = 0;

        direc = 0;
        walkCount = 0;

        king = new Player(); 
        currentSprite = king.kingsprite(0);
        //TODO: Get a map, do bounce, left right jump buttons.
    }

    @Override//this is called 60 times a second, all the drawing is in here, or helper
    //methods that are called from here
    public void render(){
        //these two lines wipe and reset the screen so when something action had happened
        //the screen won't have overlapping images
        Gdx.gl.glClearColor(0, 0, 0, 1);//sets the background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateMouse();
        background = redcrown_woods[0];

        if (Gdx.input.isKeyPressed(Keys.SPACE) && !king.isFalling) {
            // Charges up for the jump
            king.crouch();
            king.canWalk = false;
            currentSprite = king.kingsprite(2);

        } else if (king.getJumpCharge() > 0) { // Called when the user lets go of the space bar
            // Gets direction of player from input
            if (Gdx.input.isKeyPressed(Keys.D)) {
                direc = 1;
                king.facingLeft = false;
            } else if (Gdx.input.isKeyPressed(Keys.A)) {
                direc = -1;
                king.facingLeft = true;
            } else {
                direc = 0;
            }

            king.jump(direc); // executes the jump
            currentSprite = king.kingsprite(1);

        } else if (king.canWalk) {
            // Gets direction of player from input
            if (Gdx.input.isKeyPressed(Keys.D)) {
                king.facingLeft = false;
                currentSprite = updateWalkSprites();
                direc = 1;
                king.updateHitBox(king.getHitBox().x + (3*direc), king.getHitBox().y);
            } else if (Gdx.input.isKeyPressed(Keys.A)) {
                king.facingLeft = true;
                currentSprite = updateWalkSprites();
                direc = -1;
                king.updateHitBox(king.getHitBox().x + (3*direc), king.getHitBox().y);
            } else if (king.facingLeft) {
                currentSprite = king.kingsprite(3);
            } else if (!king.facingLeft) {
                currentSprite = king.kingsprite(0);
            }

        }
        king.updateSpeed();
        king.updateLocation();
        tempBool = collision();
        king.canWalk = tempBool;
        king.isFalling = !tempBool;
        if (king.isFalling)
            currentSprite = king.getFallSprite();

        Rectangle[] platform = Redcrown.r1();
        batch.begin();

        drawBackground(); 
        batch.draw(currentSprite, king.getHitBox().x,king.getHitBox().y,king.getHitBox().getWidth(),king.getHitBox().getHeight());
        batch.end();

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeType.Filled);
        // for(Rectangle rec : Redcrown.r1()){
        // renderer.rect(rec.x,rec.y,rec.width,rec.height);
        // }

        renderer.end();
        System.out.println(king.getHitBox().x + "\t" + king.getHitBox().y);

    }

    /**
     * Updates the mouse location at the start of every render() call
     */
    private void updateMouse() {
        mouseX = viewport.unproject(new Vector2(Gdx.input.getX(),
                Gdx.input.getX())).x;
        mouseY = viewport.unproject(new Vector2(Gdx.input.getX(),
                Gdx.input.getY())).y;
    }

    /**
     * Handles collisions with platforms
     * returns if the player is colliding with the floor or not
     */
    private boolean collision(){
        boolean isOnFloor = false;
        for(Rectangle temp : Redcrown.r1()){
            if(Intersector.overlaps(king.getHitBox(), temp)){
                if(Collisions.collide(king.getHitBox(), temp) == 1){
                    king.updateHitBox(temp.x - king.getHitBox().getWidth(), king.getHitBox().y);
                    king.updateVelocity(0, king.getVelocity().y);
                    //System.out.println("right side hit");
                }
                else if(Collisions.collide(king.getHitBox(), temp) == 2){
                    king.updateHitBox(temp.x + temp.getWidth(), king.getHitBox().y);
                    king.updateVelocity(0, king.getVelocity().y);
                    //System.out.println("left side hit");
                }
                else if(Collisions.collide(king.getHitBox(), temp) == 3){
                    king.updateHitBox(king.getHitBox().x,temp.y + temp.getHeight()-1);
                    isOnFloor = true;
                    king.updateVelocity(0, 0);
                    //System.out.println("top side hit");
                }
                else if(Collisions.collide(king.getHitBox(), temp) == 4){
                    king.updateHitBox(king.getHitBox().x,temp.y-king.getHitBox().getHeight());
                    king.updateVelocity(0, 0);
                    //System.out.println("bottom side hit");
                }
            }
        }

        // Extreme cases where no collisions occur and player flies off the map
        if (king.getHitBox().x > WORLD_WIDTH) {
            king.updateHitBox(WORLD_WIDTH - king.getHitBox().getWidth(), king.getHitBox().y);
            king.updateVelocity(0, king.getVelocity().y);
            System.out.println("\n\n\n\nhere1");
        } else if (king.getHitBox().x + king.getHitBox().width/2 < 0) {
            king.updateHitBox(0, king.getHitBox().y);
            king.updateVelocity(0, king.getVelocity().y);
        }

        return isOnFloor;

    }

    /**
     * Animates the player walking
     * 
     * walkCount: what walking position player is currently in
     * direc: which way player is facing
     */
    private Texture updateWalkSprites(){
        walkCount++;
        if(walkCount <= 5){
            if (direc == 1)
                return king.rightsprite(0);
            else
                return king.leftsprite(0);
        }else if(walkCount<= 10){
            if (direc == 1)
                return king.rightsprite(1);
            else
                return king.leftsprite(1);
        }else if(walkCount<= 10){
            if (direc == 1)
                return king.rightsprite(2);
            else
                return king.leftsprite(2);
        }else if(walkCount<= 10){
            if (direc == 1)
                return king.rightsprite(1);
            else
                return king.leftsprite(1);
        }else{
            walkCount = 0;
            if (direc == 1)
                return king.rightsprite(0);
            else
                return king.leftsprite(0);
        }

    }

    private void drawBackground()
    {
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);    
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); 
    }

    @Override
    public void dispose(){
        renderer.dispose(); 
        batch.dispose(); 
        font.dispose(); 
        sound.dispose(); 
    }

}

