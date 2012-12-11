package util;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class ImageRenderComponent extends RenderComponent {
 
    protected Image image;
     
    public ImageRenderComponent(String id, Image image)
    {
        super(id);
        this.image = image;
    }
     
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
//        gr.drawImage(image, pos.x, pos.y);
//        gr.scale(scale, scale);
//        gr.resetTransform();
        
        image.draw(pos.x, pos.y, scale);
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        image.rotate(owner.getRotation() - image.getRotation());
    }
    
//    public float getImageWidth() {
//        return this.image.getWidth() * owner.getScale();
//    }
//    
//    public float getImageHeight() {
//        return this.image.getHeight() * owner.getScale();
//    }
    
    public float getImageWidth(float scale) {
        return this.image.getWidth() * scale;
    }
    
    public float getImageHeight(float scale) {
        return this.image.getHeight() * scale;
    }
}