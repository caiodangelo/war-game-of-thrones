package main;

import gui.InGameGUIController;
import models.Board;
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
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
        if (highlightedImage) {
            Territory tOwner = ((Territory) owner);
            highlightTerritoryImage.draw(pos.x, pos.y, scale);
            if (main.Main.isShowingTerritoriesNames()) {
                gr.setColor(Color.black);
                drawTerritoryName(gr, tOwner.getArmy(), tOwner.getBackEndTerritory().getName());
            }
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
        if (!dm.dicesOnScreen() && !PopupManager.isAnyPopupOpen() && mouseOver(mouseX, mouseY) && !imagePixelColorIsTransparent((int) (mouseX - owner.position.x), (int) (mouseY - owner.position.y), owner.getScale()) && !Mouse.isGrabbed()){
            highlightedImage = true;
            GameScene scene = (GameScene) owner.getScene();
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
                && y >= mapPos.y && y <= mapSize.y;
    }
    
    private void drawTerritoryName(Graphics gr, Army a, String name) {
        float armyXCenter = a.getPosition().x + a.getScaledWidth()/2f;
        float fontHeight = gr.getFont().getHeight(name);
        float fontXPos = armyXCenter - gr.getFont().getWidth(name)/2f;
        float fontYPos;
        if (a.getPosition().y > main.Main.getMapPos().y + main.Main.getMapSize().y/2f)
            fontYPos = a.getPosition().y - (1.5f * fontHeight);
        else
            fontYPos = a.getPosition().y + a.getScaledHeight() + (1.5f * fontHeight);
        gr.drawString(name, fontXPos, fontYPos);
    }
}
