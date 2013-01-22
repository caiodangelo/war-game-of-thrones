package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageMovementsComponent;

public class MainSceneAnimation extends ImageMovementsComponent {
    
    private final int TIME_TO_CHANGE_CONTINENT = 100;
    private final int TIME_TO_SHOW_LOGO = 500;
    private final int TIME_TO_TOGGLE_BUTTON_INSTRUCTION = 100;
    private final float LOGO_SPEED = 4;
    
    private float viewX = 0.5f, viewY = 0.5f;
    private int zoomCount;
    private int timer;
    private int instructionTimer;
    private int logoTimer;
    private Image logoWarImage;
    private Image logoOfThronesImage;
    private Image instructionImage;
    private float logoWarX;
    private float logoWarY;
    private float logoWarFinalX;
    private float logoOfThronesX;
    private float logoOfThronesY;
    private float logoOfThronesFinalX;
    private float instructionX;
    private float instructionY;
    private boolean showingInstruction;
    private boolean buttonsDisplayed;

    public MainSceneAnimation(String id, Image img, Image logoWar, Image logoOfThrones, Image instruction) {
        super(id, img);
        this.logoWarImage = logoWar;
        this.logoOfThronesImage = logoOfThrones;
        this.instructionImage = instruction;
        this.logoWarX = -1 * logoWarImage.getWidth();
        this.logoWarY = Main.windowH/2 - logoWarImage.getHeight();
        this.logoWarFinalX = Main.windowW/2 - logoWarImage.getWidth()/2;
        this.logoOfThronesX = Main.windowW;
        this.logoOfThronesY = Main.windowH/2;// + logoWarImage.getHeight()/2;
        this.logoOfThronesFinalX = Main.windowW/2 - logoOfThronesImage.getWidth()/2;
        this.instructionX = Main.windowW/2 - instructionImage.getWidth()/2;
        this.instructionY = logoOfThronesY + logoOfThrones.getHeight();
        this.instructionTimer = -100;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
        image.draw(pos.x, pos.y, scale);
        logoWarImage.draw(logoWarX, logoWarY);
        logoOfThronesImage.draw(logoOfThronesX, logoOfThronesY);
        if (showingInstruction && !buttonsDisplayed)
            instructionImage.draw(instructionX, instructionY);
            //gr.drawString("Pressione qualquer tecla para iniciar", logoWarFinalX, logoWarY);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float scale = owner.getScale();
        scale -= delta / 3f;
        Input input = gc.getInput();
        if (scale > 2) {
            owner.setScale(scale);
        } else {
            if (timer < TIME_TO_CHANGE_CONTINENT)
                timer++;
            else {
                timer = 0;
                owner.setScale(4);
                if (zoomCount == 0) {
                    viewX = 0.25f;
                    viewY = 0.21f;
                    zoomCount++;
                } else if (zoomCount == 1) {
                    viewX = 0.7f;
                    viewY = 0.7f;
                    zoomCount++;
                } else if (zoomCount == 2) {
                    viewX = 0.25f;
                    viewY = 0.71f;
                    zoomCount++;
                } else {
                    viewX = 0.5f;
                    viewY = 0.5f;
                    zoomCount = 0;
                }
            }
        }
        Vector2f position = new Vector2f();
        position.x = (main.Main.windowW / 2f) - viewX * getImageWidth(owner.getScale());
        position.y = (main.Main.windowH / 2f) - viewY * getImageHeight(owner.getScale());
        owner.setPosition(position);
        if (input.isKeyDown(Input.KEY_LCONTROL)) {
            logoWarX = logoWarFinalX;
            logoOfThronesX = logoOfThronesFinalX;
            MainScene.showButtons();
            buttonsDisplayed = true;
        }
        else if (logoTimer >= TIME_TO_SHOW_LOGO) {
            if (logoWarX < logoWarFinalX)
                logoWarX += LOGO_SPEED;
            else if (logoOfThronesX > logoOfThronesFinalX)
                logoOfThronesX -= LOGO_SPEED;
        }
        
        else
            logoTimer++;
        instructionTimer++;
        if (showingInstruction) {
            if (instructionTimer >= TIME_TO_TOGGLE_BUTTON_INSTRUCTION) {
                toggleInstruction();
                instructionTimer = 0;
            }
        }
        else {
            if (instructionTimer >= TIME_TO_TOGGLE_BUTTON_INSTRUCTION / 2) {
                toggleInstruction();
                instructionTimer = 0;
            }
        }
    }
    
    private void toggleInstruction() {
        showingInstruction = !showingInstruction;
    }

}