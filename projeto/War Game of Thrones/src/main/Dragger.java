package util;

import main.Main;
import main.Map;
import main.Scroll;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Dragger extends ImageMovementsComponent {

    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;
    private int previousMouseX, previousMouseY;
    private boolean mouseSet = false;
    Map m;
    Scroll s;

    public Dragger(Map m, Scroll s, String id, Image img) {
        super(id, img);
        this.m = m;
        this.s = s;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float positionX = owner.getPosition().getX();
        float positionY = owner.getPosition().getY();
        Input input = gc.getInput();
        int mouseX = 0;
        int mouseY = 0;
        int newMouseX = input.getMouseX();
        int newMouseY = (int)Main.windowH-input.getMouseY();
        if(mouseSet){
            mouseX = newMouseX - previousMouseX;
            mouseY = newMouseY - previousMouseY;
        }
        previousMouseX = newMouseX;
        previousMouseY = newMouseY;
        mouseSet = true;
        boolean betweenXCoords = (Mouse.getX() >= owner.getPosition().getX()) && (Mouse.getX() <= owner.getPosition().getX() + getImageWidth(owner.getScale()));
        boolean betweenYCoords = ((Main.windowH - Mouse.getY()) >= owner.getPosition().getY()) && ((Main.windowH - Mouse.getY()) <= owner.getPosition().getY() + getImageHeight(owner.getScale()));
        boolean holding = input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && betweenXCoords && betweenYCoords;
//        System.out.println(getId() + " update");
        System.out.println(owner.getPosition().getX());
        System.out.println(owner.getPosition().getY());


        if (scrolledLeft(holding, mouseX)) {
            positionX += mouseX;
            owner.setPosition(new Vector2f(positionX, positionY));
        }

        if (scrolledRight(holding, mouseX)) {
            positionX += mouseX;
            owner.setPosition(new Vector2f(positionX, positionY));
        }

        if (scrolledUp(holding, mouseY)) {
            positionY -= mouseY;
            owner.setPosition(new Vector2f(positionX, positionY));
        }

        if (scrolledDown(holding, mouseY)) {
            positionY -= mouseY;
            owner.setPosition(new Vector2f(positionX, positionY));
        }
        Vector2f myPos = owner.getPosition();
        Vector2f mapPos = m.getPosition();
        float posX = (myPos.x - mapPos.x) / s.getImageWidth(m.getScale());
        float posY = (myPos.y - mapPos.y) / s.getImageHeight(m.getScale());
        String p1 = ("" + posX).replace(',', '.') + "f";
        String p2 = ("" + posY).replace(',', '.') + "f";
        System.out.printf("%s,%s\n", p1, p2);
    }

}