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
import java.util.*; 
public class Player
{
    // Instance variables
    private Texture[] rightwalk_sprite;
    private Texture[] leftwalk_sprite;
    private Texture[] king_sprite;
    private Rectangle hitbox;
    private Vector2 velocity;
    private double jumpCharge;
    public boolean canWalk;
    public boolean isFalling;
    public boolean facingLeft;

    // Constants
    public static final float GRAVITY = 1.0f;
    public static final float SIDE_DECAY = 0.1f; // how fast the player slows down as an x value
    public static final double TERMINAL_VELOCITY = 15.0;
    public static final double MAX_JUMP = 20.0; 
    public static final double MAX_SIDE_JUMP = 9;
    public static final double START_SIDE_JUMP = 1;
    public static final double START_JUMP = 5.0;

    public Player()
    {
        // sprites = new ArrayList<>(); 
        // sprites.add(new Texture(Gdx.files.internal("King_left_walk_rightside.png")));
        rightwalk_sprite = new Texture[5];
        rightwalk_sprite[0] = new Texture(Gdx.files.internal("King_left_walk_rightside.png"));
        rightwalk_sprite[1] = new Texture(Gdx.files.internal("King_middle_walk_rightside.png"));
        rightwalk_sprite[2] = new Texture(Gdx.files.internal("King_right_walk_rightside.png"));
        rightwalk_sprite[3] = new Texture(Gdx.files.internal("King_jump_rightside.png"));
        rightwalk_sprite[4] = new Texture(Gdx.files.internal("King_fall_rightside.png"));

        leftwalk_sprite = new Texture[5];
        leftwalk_sprite[0] = new Texture(Gdx.files.internal("King_left_walk_leftside.png"));
        leftwalk_sprite[1] = new Texture(Gdx.files.internal("King_middle_walk_leftside.png"));
        leftwalk_sprite[2] = new Texture(Gdx.files.internal("King_right_walk_leftside.png"));
        leftwalk_sprite[3] = new Texture(Gdx.files.internal("King_jump_leftside.png"));
        leftwalk_sprite[4] = new Texture(Gdx.files.internal("King_fall_leftside.png"));

        king_sprite = new Texture[4];
        king_sprite[0] = new Texture(Gdx.files.internal("King_idle_rightside.png"));
        king_sprite[1] = new Texture(Gdx.files.internal("King_fell.png"));
        king_sprite[2] = new Texture(Gdx.files.internal("King_charge_jump.png"));
        king_sprite[3] = new Texture(Gdx.files.internal("King_idle_leftside.png"));
        hitbox = new Rectangle(33,240,32,32);

        velocity = new Vector2(0, 0);
        jumpCharge = 0;
        canWalk = true;
        isFalling = false;
        facingLeft = true;
    }

    public Rectangle getHitBox(){
        return hitbox;
    }

    public Texture leftsprite(int y)
    {
        return leftwalk_sprite[y];
    }

    public Texture rightsprite(int y)
    {
        return rightwalk_sprite[y];
    }

    public Texture kingsprite(int y)
    {
        return king_sprite[y];
    }

    public void updateHitBox(float x, float y){
        hitbox.setX(x);
        hitbox.setY(y);
    }

    /**
     * called every time you hold the space bar
     * increments jump charge
     */
    public void crouch() {
        if (jumpCharge < MAX_JUMP) {
            // current jump charge function is exponential decay with the longer 
            //you hold the space bar the less additions you are making to the jump potential
            jumpCharge += 1.5f * Math.pow(2, -jumpCharge/3);
        } else {
            jumpCharge = MAX_JUMP;
        }
    }

    /**
     * performs the jump based on th jump charge
     * resets jumpCharge
     */
    public void jump(int direction) {
        jumpCharge += START_JUMP;
        velocity.set((float)
            (direction * (jumpCharge-START_JUMP)/MAX_JUMP * MAX_SIDE_JUMP + direction * START_SIDE_JUMP)
            , (float)(jumpCharge));
        
        //System.out.println("jump x: " + velocity.x + "\tjumpCharge: " + jumpCharge);
        
        jumpCharge = 0;
    }

    public double getJumpCharge() {
        return jumpCharge;
    }

    /**
     * Accelerations such as gravity and side decay are acted on the velocity
     */
    public void updateSpeed() {
        if (!canWalk) {
            if (velocity.y > -TERMINAL_VELOCITY)
            // performs gravity acceleration if terminal velocity is not reached
                velocity.y = velocity.y - GRAVITY; 

            if (velocity.x > 0) {
                // increments the x velocity closer to 0 by side_decay units
                velocity.x = velocity.x + SIDE_DECAY * -(velocity.x / Math.abs(velocity.x));
            }
        }
    }

    /**
     * based on the velocity, updates the position of the player
     */
    public void updateLocation() {
        hitbox.setX(hitbox.x + velocity.x);
        hitbox.setY(hitbox.y + velocity.y);
    }

    public void updateVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}

