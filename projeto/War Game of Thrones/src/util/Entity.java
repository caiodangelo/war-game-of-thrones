package util;

import java.util.ArrayList;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class Entity {
     
    public Vector2f position;
    private float scale;
    private float rotation;
    private Scene scene;
 
    private RenderComponent renderComponent = null;
     
    private ArrayList<Component> components = null;
     
    public Entity()
    {
        components = new ArrayList<Component>();
        position = new Vector2f(0,0);
        scale = 1;
        rotation = 0;
    }
    
    public Vector2f getPosition()
    {
	return position;
    }
    
    public void setPosition(Vector2f position) {
	this.position = position;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    protected void setScene(Scene s){
        this.scene = s;
    }
 
    public void addComponent(Component component)
    {
        if(RenderComponent.class.isInstance(component))
            renderComponent = (RenderComponent)component;
 
        component.setOwnerEntity(this);
        components.add(component);
    }
    
    public void removeComponent(Component comp){
        if(components.remove(comp) && renderComponent == comp){
            renderComponent = null;
        }
    }
    
    public void onAdded(){}
    public void onRemoved(){}
 
    public Component getComponent(String id)
    {
        for(Component comp : components)
            if ( comp.getId().equalsIgnoreCase(id) )
                return comp;
        return null;
    }
    
    public float getScale()
    {
        return scale;
    }
     
    public float getRotation()
    {
        return rotation;
    }
     
    public void setRotation(float rotate) {
        rotation = rotate;
    }
 
    public void setScale(float scale) {
        this.scale = scale;
    }
     
    public void update(GameContainer gc, StateBasedGame sb, float delta)
    {
        for(Component component : components)
        {
            component.update(gc, sb, delta);
        }
    }
 
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
    {
        if(renderComponent != null)
            renderComponent.render(gc, sb, gr);
    }
}
