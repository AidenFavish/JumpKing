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
    private Texture[] rightwalk_sprite;
    private Texture[] leftwalk_sprite;
    private Texture[] king_sprite;
    private Rectangle hitbox;
    // private List<Texture> sprites;

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
        hitbox = new Rectangle(x, y, 32, 32);
    }
}
