package tests;

import javax.jws.soap.SOAPBinding;
import org.newdawn.slick.*;
public class TestObject {
    
    private float x, y, width = 100, height = 100;
    public float elapsed = 0;
    public static float SIN_DURATION = 2f, MAGNITUDE = 200f;
    
    public TestObject(){
        x = 400;
        y = 400;
    }
    
    public void update(float delta){
        elapsed += delta;
        if(elapsed >= SIN_DURATION)
            elapsed -= SIN_DURATION;
        x = (float)(400 + MAGNITUDE*Math.sin((elapsed/SIN_DURATION) * Math.PI * 2));
    }
    
    public void render(Graphics g){
        g.fillRect(x, y, width, height);
    }
}
