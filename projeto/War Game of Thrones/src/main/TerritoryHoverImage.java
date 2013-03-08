package main;

import gui.InGameGUIController;
import models.Board;
import models.Player;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import sun.security.action.GetBooleanAction;
import util.ImageRenderComponent;
import util.PopupManager;

public class TerritoryHoverImage extends ImageRenderComponent {
    
    private boolean highlightedImage;
    private boolean owned;
    private Image notOwnedTerritoryImage;
    private Image highlightTerritoryImage;
    
    public TerritoryHoverImage(String id, Image highlightImg, Image notOwnedImg, Image ownedImg) {
        super(id, ownedImg);
        notOwnedTerritoryImage = notOwnedImg;
        highlightTerritoryImage = highlightImg;
    }
    
    public boolean isHighlightedImage() {
        return highlightedImage;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
        if (highlightedImage) {
            Territory tOwner = ((Territory) owner);
            highlightTerritoryImage.draw(pos.x, pos.y, scale);
        }
        else if (owned)
            image.draw(pos.x, pos.y, scale);
        else
            notOwnedTerritoryImage.draw(pos.x, pos.y, scale);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Input input = gc.getInput();
        float mouseX = input.getAbsoluteMouseX();
        float mouseY = input.getAbsoluteMouseY();
        DiceManager dm = DiceManager.getInstance();
        Territory tOwner = (Territory) owner;
        owned = tOwner.getBackEndTerritory().getOwner() == Board.getInstance().getCurrentPlayer();
        Player currPlayer = Board.getInstance().getCurrentPlayer();
        
        if (!dm.dicesOnScreen() && !PopupManager.isAnyPopupOpen() && mouseOver(mouseX, mouseY) && !imagePixelColorIsTransparent((int) (mouseX - owner.position.x), (int) (mouseY - owner.position.y), owner.getScale()) && !Mouse.isGrabbed() && !currPlayer.isAIPlayer()){
            highlightedImage = true;
            GameScene scene = (GameScene) owner.getScene();
            scene.setHighlightedTerritory(tOwner);
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
                scene.handleTerritoryClick(tOwner);
        }
        else
            highlightedImage = false;
    }
    
    private boolean mouseOver(float x, float y){
        float ownerX = owner.position.x;
        float ownerY = owner.position.y;
        float ownerScale = owner.getScale();
        return x >= ownerX && x <= (ownerX + getImageWidth(ownerScale))
                && y >= ownerY && y <= (ownerY + getImageHeight(ownerScale))
                && mouseInsideMapArea(x,y);
    }
    
    private boolean imagePixelColorIsTransparent(int x, int y, float scale) {
        int scaleX = (int)(x / scale);
        int scaleY = (int)(y / scale);
        return image.getColor(scaleX, scaleY).a == 0f;
    }

    private static boolean mouseInsideMapArea(float x, float y) {
        Vector2f mapPos = Main.getMapPos(), mapSize = Main.getMapSize();
        return x >= mapPos.x && x <= mapPos.x + mapSize.x 
                && y >= mapPos.y && y <= mapPos.y + mapSize.y;
    }
}
