package main;

import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Dragger;
import util.Entity;
import util.ImageMovementsComponent;
import util.ImageRenderComponent;
import util.MainSceneAnimation;
import util.Scene;

public class MainScene extends Scene{

    private Nifty n;
    public static int id = 0;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }
    
    public String [] imgs = {
        //além da muralha
        "resources/images/territorios/alem-da-muralha/a-costa-gelada.png",
        "resources/images/territorios/alem-da-muralha/a-floresta-assombrada.png",
        "resources/images/territorios/alem-da-muralha/a-terra-de-sempre-inverno.png",
        "resources/images/territorios/alem-da-muralha/ilha-dos-ursos.png",
        "resources/images/territorios/alem-da-muralha/skagos.png",
        
        //cidades livres
        "resources/images/territorios/cidades-livres/a-costa-laranja.png",
        "resources/images/territorios/cidades-livres/as-terras-disputadas.png",
        "resources/images/territorios/cidades-livres/campos-dourados.png",
        "resources/images/territorios/cidades-livres/colinas-de-norvos.png",
        "resources/images/territorios/cidades-livres/costa-bravosiana.png",
        "resources/images/territorios/cidades-livres/florestade-de-qohor.png",
        "resources/images/territorios/cidades-livres/sem-nome-ainda.png",
        "resources/images/territorios/cidades-livres/the-flatlands.png",
        
        //o norte
        "resources/images/territorios/o-norte/a-dadiva.png",
        "resources/images/territorios/o-norte/barrowlands.png",
        "resources/images/territorios/o-norte/costa-perigosa.png",
        "resources/images/territorios/o-norte/mata-de-lobos.png",
        "resources/images/territorios/o-norte/o-norte.png",
        "resources/images/territorios/o-norte/o-penhasco-sombrio.png",
        "resources/images/territorios/o-norte/the-hills.png",
        
        //o sul
        "resources/images/territorios/sul/a-arvore.png",
        "resources/images/territorios/sul/a-campina.png",
        "resources/images/territorios/sul/dorne.png",
        "resources/images/territorios/sul/mata-do-rei.png",
        "resources/images/territorios/sul/tarth.png",
        "resources/images/territorios/sul/terras-da-tempestade.png",
        
        //o tridente
        "resources/images/territorios/tridente/as-montanhas-da-lua.png",
        "resources/images/territorios/tridente/a-terra-da-coroa.png",
        "resources/images/territorios/tridente/cape-kraken.png",
        "resources/images/territorios/tridente/o-gargalo.png",
        "resources/images/territorios/tridente/o-vale-de-arryn.png",
        "resources/images/territorios/tridente/terras-fluviais.png",
        "resources/images/territorios/tridente/westernlands.png",
        
        //o mar dothraki
        "resources/images/territorios/o-mar-dothraki/ghiscar.png",
        "resources/images/territorios/o-mar-dothraki/o-deserto-vermelho.png",
        "resources/images/territorios/o-mar-dothraki/o-mar-dothraki.png",
        "resources/images/territorios/o-mar-dothraki/oros.png",
        "resources/images/territorios/o-mar-dothraki/the-footprint.png",
    };
    
    public Vector2f [] pos = {
        //além da muralha
        new Vector2f(0.091004f,-0.000744f),
        new Vector2f(0.163180f,-0.000744f),
        new Vector2f(0.05910042f,0.0f),
        new Vector2f(0.10930962f,0.10491072f),
        new Vector2f(0.2662134f,0.013392857f),
        
        //cidades livres
        new Vector2f(0.4042887f,0.6808036f),
        new Vector2f(0.3498954f,0.59970236f),
        new Vector2f(0.4419456f,0.5714286f),
        new Vector2f(0.37395397f,0.36755952f),
        new Vector2f(0.36192468f,0.3764881f),
        new Vector2f(0.4837866f,0.36681548f),
        new Vector2f(0.53974897f,0.8489583f),
        new Vector2f(0.34832636f,0.48363096f),
        
        //o norte
        new Vector2f(0.16841005f,0.0639881f),
        new Vector2f(0.123953976f,0.23214285f),
        new Vector2f(0.07269874f,0.14136904f),
        new Vector2f(0.11087866f,0.10491072f),
        new Vector2f(0.2039749f,0.16815476f),
        new Vector2f(0.28608787f,0.12276786f),
        new Vector2f(0.08995816f,0.23883928f),
        
        //sul
        new Vector2f(0.072175734f,0.7589286f),
        new Vector2f(0.14225942f,0.53348213f),
        new Vector2f(0.13702929f,0.71800596f),
        new Vector2f(0.17677824f,0.5610119f),
        new Vector2f(0.3038703f,0.60863096f),
        new Vector2f(0.15899582f,0.53869045f),
        
        //o tridente
        new Vector2f(0.20188284f,0.34151787f),
        new Vector2f(0.18671548f,0.45610118f),
        new Vector2f(0.08158996f,0.31324404f),
        new Vector2f(0.1417364f,0.3013393f),
        new Vector2f(0.27405858f,0.37797618f),
        new Vector2f(0.27405858f,0.37797618f),
        new Vector2f(0.09257322f,0.46205357f),
        
        //o mar dothraki
        new Vector2f(0.6736402f,0.77529764f),
        new Vector2f(0.70920503f,0.6369048f),
        new Vector2f(0.46966526f,0.38988096f),
        new Vector2f(0.5465481f,0.68824404f),
        new Vector2f(0.7306485f,0.31473213f),
    };
    

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("startingScreen");
        Image mapImage;
        Image mapImage2;
        try {
            mapImage = new Image("resources/images/mapa.jpg");
//            mapImage = new Image("resources/images/logo_baratheon.png");
            Map map = new Map();
            Scroll s = new Scroll("", mapImage);
            map.addComponent(s);
            
//            Map map2 = new Map();
//            mapImage2 = new Image(imgs[imgs.length-2]);
//            map.setScale(0.5f);
//            map2.setScale(0.5f);
            
//            map2.addComponent(new Dragger(map, s, "animation2", mapImage2));
//            map2.addComponent(new Scroll("animation2", mapImage2));
            
//            map2.setPosition(new Vector2f(pos[pos.length-2].x * s.getImageWidth(map.getScale())+map.getPosition().x,pos[pos.length-2].y * s.getImageHeight(map.getScale())+map.getPosition().y));
////            map.addComponent(new MainSceneAnimation("animation", mapImage));
//            map.addComponent(new Dragger("animation", mapImage));
//            map.setPosition(new Vector2f(-600, -600));
//            map2.setPosition(new Vector2f(300, 300));
////            map.setScale(2);
//            //map2.setScale(2);
            addEntity(map);
//            for(int i = 0; i < imgs.length; i++){
//                Map map2 = new Map();
//                mapImage2 = new Image(imgs[i]);
//                map2.addComponent(new Scroll("", mapImage2));
//                map2.setPosition(new Vector2f(pos[i].x * s.getImageWidth(map.getScale())+map.getPosition().x,pos[i].y * s.getImageHeight(map.getScale())+map.getPosition().y));
//                addEntity(map2);
//            }
//            addEntity(map2);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void renderGame(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.renderGame(container, game, g);
        id = 0;
    }
}
