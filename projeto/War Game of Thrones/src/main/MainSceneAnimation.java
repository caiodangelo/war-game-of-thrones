package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageMovementsComponent;

public class MainSceneAnimation extends ImageMovementsComponent {
    
    private final int TIME_TO_CHANGE_CONTINENT = 100;
    private final int TIME_TO_SHOW_LOGO = 500;
    private final int DELAY_TO_SHOW_BUTTON_INSTRUCTION = -20;
    private final int TIME_TO_TOGGLE_BUTTON_INSTRUCTION = 100;
    //private final float LOGO_SPEED = 4;
    private final float GRADIENT_CHANGING_SPEED = 0.5f;
    
    private float viewX = 0.5f, viewY = 0.5f;
    private int zoomCount;
    private int timer;
    private int instructionTimer;
    private int logoTimer;
    private Image logoWarImage;
    private Image logoOfImage;
    private Image logoThronesImage;
    private Image instructionImage;
    private Color gradientWar;
    private Color gradientOf;
    private Color gradientThrones;
    private Color mapOpacity;
    //private float logoWarX;
    private float logoWarY;
    private float logoWarFinalX;
    //private float logoOfX;
    private float logoOfY;
    private float logoOfFinalX;
    //private float logoThronesX;
    private float logoThronesY;
    private float logoThronesFinalX;
    private float instructionX;
    private float instructionY;
    private boolean showingInstruction;
    private boolean buttonsDisplayed;

    public MainSceneAnimation(String id, Image img, Image logoWar, Image logoOf, Image logoThrones, Image instruction) {
        super(id, img);
        this.logoWarImage = logoWar;
        this.logoOfImage = logoOf;
        this.logoThronesImage = logoThrones;
        this.instructionImage = instruction;
        this.gradientWar = new Color(1f, 1f, 1f, 0);
        this.gradientOf = new Color(1f, 1f, 1f, 0);
        this.gradientThrones = new Color(1f, 1f, 1f, 0);
        //this.mapOpacity = new Color(1f, 1f, 1f, 1);
        //this.logoWarX = -1 * logoWarImage.getWidth();
        this.logoWarY = Main.windowH/3 - logoWarImage.getHeight();
        this.logoWarFinalX = Main.windowW/2 - logoWarImage.getWidth()/2;
        //this.logoOfX = Main.windowW;
        this.logoOfY = Main.windowH/3 - logoOfImage.getHeight() + logoWarImage.getHeight()/1.5f; //dividing for 1.5f is an estimation to ignore transparent pixels in image height
        this.logoOfFinalX = Main.windowW/2 - logoOfImage.getWidth()/2;
        //this.logoThronesX = Main.windowW;
        this.logoThronesY = Main.windowH/3 - logoThrones.getHeight() + logoWarImage.getHeight()/1.5f + logoOfImage.getHeight()/1.5f; //dividing for 1.5f is an estimation to ignore transparent pixels in image height
        this.logoThronesFinalX = Main.windowW/2 - logoThronesImage.getWidth()/2;
        this.instructionX = Main.windowW/2 - instructionImage.getWidth()/2;
        this.instructionY = logoThronesY + logoThrones.getHeight();
        this.instructionTimer = DELAY_TO_SHOW_BUTTON_INSTRUCTION;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.position;
        float scale = owner.getScale();
        gr.setBackground(Color.white);
        image.draw(pos.x, pos.y, scale);
        logoWarImage.draw(logoWarFinalX, logoWarY, gradientWar);
        logoOfImage.draw(logoOfFinalX, logoOfY, gradientOf);
        logoThronesImage.draw(logoThronesFinalX, logoThronesY, gradientThrones);
        if (showingInstruction && !buttonsDisplayed)
            instructionImage.draw(instructionX, instructionY);
            //gr.drawString("Pressione qualquer tecla para iniciar", logoWarFinalX, logoWarY);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float scale = owner.getScale();
        scale -= delta / 3f;
        Input input = gc.getInput();
        if (scale > 1.7f) {
            owner.setScale(scale);
        } else {
            if (timer < TIME_TO_CHANGE_CONTINENT)
                timer++;
            else {
                timer = 0;
                owner.setScale(3.5f);
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
        gc.setMouseGrabbed(!buttonsDisplayed);
        if (!buttonsDisplayed && (input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE))) {
            AudioManager.getInstance().playSound(AudioManager.START_GAME);
            gradientWar.a = 1;
            gradientOf.a = 1;
            gradientThrones.a = 1;
            MainScene.showButtons();
            buttonsDisplayed = true;
        }
        else if (logoTimer >= TIME_TO_SHOW_LOGO) {
            if (gradientWar.a < 1)
                gradientWar.a += delta * GRADIENT_CHANGING_SPEED;
            else if (gradientOf.a < 1)
                gradientOf.a += delta * GRADIENT_CHANGING_SPEED;
            else if (gradientThrones.a < 1)
                gradientThrones.a += delta * GRADIENT_CHANGING_SPEED;
//            if (logoWarX < logoWarFinalX)
//                logoWarX = updateLogoPosition(logoWarX, logoWarFinalX, true);
//            else if (logoThronesX > logoThronesFinalX) {
//                if (logoOfX > logoOfFinalX)
//                    logoOfX = updateLogoPosition(logoOfX, logoOfFinalX, false);
//                if (logoOfX < Main.windowW * 0.8)
//                    logoThronesX = updateLogoPosition(logoThronesX, logoThronesFinalX, false);
//            }
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
    
    //preventing logo images stop after their final position
//    private float updateLogoPosition(float logoX, float logoFinalX, boolean comingFromLeft) {
//        if (comingFromLeft) {
//            if (logoX + LOGO_SPEED > logoFinalX)
//                logoX = logoFinalX;
//            else
//                logoX += LOGO_SPEED;
//        }
//        else {
//            if (logoX - LOGO_SPEED < logoFinalX)
//                logoX = logoFinalX;
//            else
//                logoX -= LOGO_SPEED;
//        }
//        return logoX;
//    }

}