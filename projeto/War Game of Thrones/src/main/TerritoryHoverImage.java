package main;

import gui.InGameGUIController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageRenderComponent;

public class TerritoryHoverImage extends ImageRenderComponent {
    
    private boolean highlightedImage;
    
    public TerritoryHoverImage(String id, Image img) {
        super(id, img);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (highlightedImage || Map.selectedTerritory == owner) {
            Vector2f pos = owner.position;
            float scale = owner.getScale();
            image.draw(pos.x, pos.y, scale);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Input input = gc.getInput();
        float mouseX = input.getAbsoluteMouseX();
        float mouseY = input.getAbsoluteMouseY();
        
        if (mouseOver(mouseX, mouseY) && !imagePixelColorIsTransparent((int) (mouseX - owner.position.x), (int) (mouseY - owner.position.y), owner.getScale())){
            highlightedImage = true;
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (Map.selectedTerritory == null){
                    Map.selectedTerritory = (Territory) owner;
                    InGameGUIController.handleTerritoryClick((Territory) owner);
                } else if (Map.selectedTerritory == owner)
                    Map.selectedTerritory = null;
                else {
                    ArmyRenderComponent comp = ((ArmyRenderComponent) ((Territory) owner).getArmy().getComponent("army-renderer"));
                    comp.setMovingQuantity(3);
                    comp.setOrigin(Map.selectedTerritory.getArmy().getPosition());
                    comp.setDestiny(((Territory) owner).getArmy().getPosition());
                    Map.selectedTerritory = null;
                }
            }
        }
        else
            highlightedImage = false;
    }
    
    private boolean mouseOver(float x, float y){
        float ownerX = owner.position.x;
        float ownerY = owner.position.y;
        float ownerScale = owner.getScale();
        return x >= ownerX && x <= (ownerX + getImageWidth(ownerScale)) && y >= ownerY && y <= (ownerY + getImageHeight(ownerScale));
    }
    
    private boolean imagePixelColorIsTransparent(int x, int y, float scale) {
        int scaleX = (int)(x / scale);
        int scaleY = (int)(y / scale);
        return image.getColor(scaleX, scaleY).a == 0f;
    }
}
