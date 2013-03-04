package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import models.AIPlayer;
import models.BackEndTerritory;
import models.Board;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.Scene;

public class Map extends Entity {

    private Scroll s;
    private Territory[] territories;

    public Territory[] getTerritories() {
        return territories;
    }
    private static final String[] imgs = {
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
        "resources/images/territorios/cidades-livres/floresta-de-qohor.png",
        "resources/images/territorios/cidades-livres/the-flatlands.png",
        "resources/images/territorios/cidades-livres/valiria.png",
        //o norte
        "resources/images/territorios/o-norte/a-dadiva.png",
        "resources/images/territorios/o-norte/barrowlands.png",
        "resources/images/territorios/o-norte/costa-pedregosa.png",
        "resources/images/territorios/o-norte/mata-de-lobos.png",
        "resources/images/territorios/o-norte/o-penhasco-sombrio.png",
        "resources/images/territorios/o-norte/the-hills.png",
        "resources/images/territorios/o-norte/winterfell.png",
        //o sul
        "resources/images/territorios/sul/a-arvore.png",
        "resources/images/territorios/sul/dorne.png",
        "resources/images/territorios/sul/jardim-de-cima.png",
        "resources/images/territorios/sul/mataderrei.png",
        "resources/images/territorios/sul/monte-chifre.png",
        "resources/images/territorios/sul/tarth.png",
        "resources/images/territorios/sul/terras-da-tempestade.png",
        //o tridente
        "resources/images/territorios/tridente/as-montanhas-da-lua.png",
        "resources/images/territorios/tridente/cape-krakentt.png",
        "resources/images/territorios/tridente/o-gargalo.png",
        "resources/images/territorios/tridente/o-vale-de-arryn.png",
        "resources/images/territorios/tridente/porto-real.png",
        "resources/images/territorios/tridente/rochedo-casterly.png",
        "resources/images/territorios/tridente/terras-fluviais.png",
        //o mar dothraki
        "resources/images/territorios/o-mar-dothraki/ghiscar.png",
        "resources/images/territorios/o-mar-dothraki/o-deserto-vermelho.png",
        "resources/images/territorios/o-mar-dothraki/o-mar-dothraki.png",
        "resources/images/territorios/o-mar-dothraki/oros.png",
        "resources/images/territorios/o-mar-dothraki/the-footprint.png",};
    private static final Vector2f[] territoryPositions = {
        //além da muralha
        new Vector2f(0.091004f, -0.000744f),
        new Vector2f(0.163180f, -0.000744f),
        new Vector2f(0.05910042f, 0.0f),
        new Vector2f(0.10930962f, 0.10491072f),
        new Vector2f(0.2662134f, 0.013392857f),
        //cidades livres
        new Vector2f(0.4042887f, 0.6808036f),
        new Vector2f(0.3498954f, 0.59970236f),
        new Vector2f(0.4419456f, 0.5714286f),
        new Vector2f(0.37395397f, 0.36755952f),
        new Vector2f(0.36192468f, 0.3764881f),
        new Vector2f(0.4837866f, 0.36681548f),
        new Vector2f(0.34832636f, 0.48363096f),
        new Vector2f(0.53974897f, 0.8489583f),
        //o norte
        new Vector2f(0.16841005f, 0.0639881f),
        new Vector2f(0.123953976f, 0.23214285f),
        new Vector2f(0.07269874f, 0.14136904f),
        new Vector2f(0.11087866f, 0.10491072f),
        new Vector2f(0.28608787f, 0.12276786f),
        new Vector2f(0.08995816f, 0.23883928f),
        new Vector2f(0.2039749f, 0.16815476f),
        //sul
        new Vector2f(0.072175734f, 0.7589286f),
        new Vector2f(0.13702929f, 0.71800596f),
        new Vector2f(0.14225942f, 0.53348213f),
        new Vector2f(0.17677824f, 0.5610119f),
        new Vector2f(0.077405855f, 0.6428869f),
        new Vector2f(0.3038703f, 0.60863096f),
        new Vector2f(0.15899582f, 0.53869045f),
        //o tridente
        new Vector2f(0.20188284f, 0.34151787f),
        new Vector2f(0.08158996f, 0.31324404f),
        new Vector2f(0.1417364f, 0.3013393f),
        new Vector2f(0.27405858f, 0.37797618f),
        new Vector2f(0.18671548f, 0.45610118f),
        new Vector2f(0.09257322f, 0.46205357f),
        new Vector2f(0.08455858f, 0.42097618f),
        //o mar dothraki
        new Vector2f(0.6736402f, 0.77529764f),
        new Vector2f(0.70920503f, 0.6369048f),
        new Vector2f(0.46966526f, 0.38988096f),
        new Vector2f(0.5465481f, 0.68824404f),
        new Vector2f(0.7306485f, 0.31473213f),};
    protected final static Vector2f[] armyPositions = {
        //além da muralha
        new Vector2f(0.13075313f, 0.015347515f),
        new Vector2f(0.2015238f, 0.018417018f),
        new Vector2f(0.07379137f, 0.012278012f),
        new Vector2f(0.11254149f, 0.10594705f),
        new Vector2f(0.2691575f, 0.047270346f),
        //cidades livres
        new Vector2f(0.44824007f, 0.7193877f),
        new Vector2f(0.41225028f, 0.65992993f),
        new Vector2f(0.4499815f, 0.5946916f),
        new Vector2f(0.44185477f, 0.48238245f),
        new Vector2f(0.37277764f, 0.45017615f),
        new Vector2f(0.54401934f, 0.46586642f),
        new Vector2f(0.38760647f, 0.54796976f),
        new Vector2f(0.577078f, 0.9161218f),
        //o norte
        new Vector2f(0.24004136f, 0.09350001f),
        new Vector2f(0.16341797f, 0.25205404f),
        new Vector2f(0.08772271f, 0.22599193f),
        new Vector2f(0.16632037f, 0.16617061f),
        new Vector2f(0.29022382f, 0.13022008f),
        new Vector2f(0.10072609f, 0.25866047f),
        new Vector2f(0.23365608f, 0.22480257f),
        //sul
        new Vector2f(0.07437915f, 0.7674639f),
        new Vector2f(0.20208581f, 0.7330074f),
        new Vector2f(0.1608717f, 0.56206626f),
        new Vector2f(0.22595693f, 0.57953643f),
        new Vector2f(0.13997442f, 0.6752012f),
        new Vector2f(0.30410144f, 0.61204416f),
        new Vector2f(0.23575366f, 0.62234986f),
        //o tridente
        new Vector2f(0.23749511f, 0.39855742f),
        new Vector2f(0.111633494f, 0.33360673f),
        new Vector2f(0.1666765f, 0.33497065f),
        new Vector2f(0.2851358f, 0.39462484f),
        new Vector2f(0.22704646f, 0.4968279f),
        new Vector2f(0.11733568f, 0.51912457f),
        new Vector2f(0.1370044f, 0.44578765f),
        //o mar dothraki
        new Vector2f(0.7260133f, 0.83314276f),
        new Vector2f(0.7939295f, 0.7447819f),
        new Vector2f(0.65925807f, 0.5498926f),
        new Vector2f(0.587859f, 0.78029144f),
        new Vector2f(0.8984159f, 0.57384086f),};

    public Map() {
        Vector2f mapSize = Main.getMapSize();
        setScale((mapSize.x * mapSize.y) / (1280 * 768));
        setPosition(Main.getMapPos());

        try {
            Image mapImage = new Image("resources/images/mapa.jpg");
            s = new Scroll("scroll", mapImage);
            addComponent(s);
        } catch (SlickException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Scroll getScroll() {
        return s;
    }

    public float getScaledWidth() {
        return s.getImageWidth(getScale());
    }

    public float getScaledHeight() {
        return s.getImageHeight(getScale());
    }

    @Override
    public void onAdded() {
        Scene scene = getScene();
        BackEndTerritory[] backEndTerritories = Board.getInstance().getTerritories();
        territories = new Territory[territoryPositions.length];
        for (int i = 0; i < territoryPositions.length; i++) {
            Territory t = new Territory(this, territoryPositions[i], imgs[i], backEndTerritories[i]);
            territories[i] = t;
            scene.addEntity(t);
            Army a = new Army(this, t, armyPositions[i], 0, s);
            scene.addEntity(a);
            t.setArmy(a);
        }
        if (Board.getInstance().getCurrentPlayer().isAIPlayer() && Board.getInstance().isOnInitialSetup()) {
            GameScene.getInstance().startAIDistributionAnim((AIPlayer) Board.getInstance().getCurrentPlayer(), TurnHelper.getInstance().getIaHelper());
        }
    }

    public Territory getFrontEndTerritory(BackEndTerritory tBack) {
        for (Territory tFront : territories) {
            if (tFront.getBackEndTerritory().equals(tBack))
                return tFront;
        }
        return null;
    }

    public Vector2f screenToMapPosition(float x, float y) {
        Vector2f myPos = getPosition();
        x = (x - myPos.x) / getScaledWidth();
        y = (y - myPos.y) / getScaledHeight();
        return new Vector2f(x, y);
    }

    public Vector2f getMouseRelativePosition(Input in) {
        return screenToMapPosition(in.getMouseX(), in.getMouseY());
    }
}
