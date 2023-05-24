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
public class Collisions
{
    // Right = 1, Left = 2, Top = 3, Bottom = 4

    public static int collide(Rectangle r1, Rectangle r2)
    {
        if (r1.x + r1.getWidth() > r2.x && r2.x > r1.x 
        && (r1.y >= r2.y && r1.y + r1.getHeight() <= r2.y + r2.getHeight())){//Right
            return 1;
        }
        else if (r2.x + r2.getWidth() > r1.x && r1.x + r1.getWidth() > r2.x + r2.getWidth()
        && (r1.y >= r2.y && r1.y + r1.getHeight() <= r2.y + r2.getHeight())){//Left
            return 2;
        }
        else if (r1.y <= r2.y + r2.getHeight() && r2.y + r2.getHeight() <= r1.y + r1.getHeight()){//Top
            return 3;
        }
        else if (r1.y < r2.y && r2.y < r1.y + r1.getHeight()){//Bot
            return 4;
        }
        return -1;
    }
    
    
}
