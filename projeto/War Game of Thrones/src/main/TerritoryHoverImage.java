package main;

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
        float x = input.getAbsoluteMouseX();
        float y = input.getAbsoluteMouseY();
        if (x >= owner.position.x && x <= (owner.position.x + getImageWidth(owner.getScale())) && y >= owner.position.y && y <= (owner.position.y + getImageHeight(owner.getScale())) && !imagePixelColorIsTransparent((int) (x - owner.position.x), (int) (y - owner.position.y), owner.getScale())) {
            highlightedImage = true;
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (Map.selectedTerritory == null)
                    Map.selectedTerritory = (Territory) owner;
                else if (Map.selectedTerritory == owner)
                    Map.selectedTerritory = null;
                else {
                    ((ArmyRenderComponent) ((Territory) owner).getArmy().getComponent("army-renderer")).setMovingQuantity(3);
                    ((ArmyRenderComponent) ((Territory) owner).getArmy().getComponent("army-renderer")).setOrigin(Map.selectedTerritory.getArmy().getPosition());
                    ((ArmyRenderComponent) ((Territory) owner).getArmy().getComponent("army-renderer")).setDestiny(((Territory) owner).getArmy().getPosition());
                    Map.selectedTerritory = null;
                }
            }
        }
        else
            highlightedImage = false;
    }
    
    private boolean imagePixelColorIsTransparent(int x, int y, float scale) {
        System.out.println(y);
        return image.getScaledCopy(scale).getColor(x, y).a == 0f;
    }
    
}
