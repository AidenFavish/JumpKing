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
import com.badlogic.gdx.utils.Array;  
import java.util.*; 
public class Redcrown
{
    // instance variables - replace the example below with your own
    

    
    public static Rectangle[] r1()
    {
        Rectangle[] ans = new Rectangle[]{
            new Rectangle(0,0,128,176),
            new Rectangle(0,0,481,33),
            new Rectangle(352,0,128,176),
            new Rectangle(0,0,8,3600),
            new Rectangle(472,0,8,3600),
            new Rectangle(184,273,112,48)
        };
        return ans;
    }
    
    public static Rectangle[] r2()
    {
        Rectangle[] ans = new Rectangle[]{
            new Rectangle(295,328,98,34),
            new Rectangle(408,229,64,31),
            new Rectangle(255,230,74,32),
            new Rectangle(118,166,76,64),
            new Rectangle(8,163,73,85),
            new Rectangle(0,0,8,360),
            new Rectangle(472,360,8,360)
        };
        return ans;
    }
    
    public static Rectangle[] r3()
    {
        Rectangle[] ans = new Rectangle[]{
            new Rectangle(0,0,128,178),
            new Rectangle(0,0,481,33),
            new Rectangle(352,32,121,177),
            new Rectangle(0,0,8,360),
            new Rectangle(472,360,8,360),
            new Rectangle(184,87,112,48)
        };
        return ans;
    }
}
