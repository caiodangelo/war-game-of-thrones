package util;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class ImageRenderComponent extends RenderComponent {
 
    protected Image image;
     
    public ImageRenderComponent(String id, Image img)
    {
        super(id);
        image = img;
    }
     
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
        image.draw(pos.x, pos.y, scale);
//        System.out.println("rendering " + (main.MainScene.id++));
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        image.rotate(owner.getRotation() - image.getRotation());
    }
    
    public float getImageWidth(float scale) {
        return this.image.getWidth() * scale;
    }
    
    public float getImageHeight(float scale) {
        return this.image.getHeight() * scale;
    }
}