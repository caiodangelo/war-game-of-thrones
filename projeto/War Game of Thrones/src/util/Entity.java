package util;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Entity implements Comparable<Entity> {

    public Vector2f position;
    private float scale;
    private float rotation;
    private Scene scene;
    private RenderComponent renderComponent = null;
    private ArrayList<Component> components = null;
    private ArrayList<Component> componentsToAdd = null;
    private int layer = 0;

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public Entity() {
        components = new ArrayList<Component>();
        componentsToAdd = new ArrayList<Component>();
        position = new Vector2f(0, 0);
        scale = 1;
        rotation = 0;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Scene getScene() {
        return scene;
    }

    protected void setScene(Scene s) {
        this.scene = s;
    }

    public void addComponent(Component component) {
        componentsToAdd.add(component);
    }

    public void removeComponent(Component comp) {
        if (components.remove(comp) && renderComponent == comp) {
            renderComponent = null;
        }
    }

    public void onAdded() {
    }

    public void onRemoved() {
    }

    public Component getComponent(String id) {
        for (Component comp : components) {
            if (comp.getId() != null && comp.getId().equalsIgnoreCase(id)) {
                return comp;
            }
        }
        return null;
    }

    public float getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotate) {
        rotation = rotate;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        for (Component component : components) {
            component.update(gc, sb, delta);
        }
        for (Component component : componentsToAdd) {
            if (RenderComponent.class.isInstance(component))
                renderComponent = (RenderComponent) component;
            component.setOwnerEntity(this);
            components.add(component);
        }
        componentsToAdd.clear();
    }

    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (renderComponent != null) {
            renderComponent.render(gc, sb, gr);
        }
    }

    @Override
    public int compareTo(Entity o) {
        int myLayer = getLayer();
        int otherLayer = o.getLayer();
        if(myLayer < otherLayer)
            return -1;
        if(myLayer == otherLayer)
            return 0;
        return 1;
    }
}
