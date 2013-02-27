package main;

import models.CardTerritory;
import models.TerritoryID;

public class CardPaths {
    
    private static String[] paths;
    private static final String CARDS_PATH = "resources/images/cartas/";
    private static final String JOKER_PATH = CARDS_PATH+"coringa.png";
    
    public static String getPath(int i) {
        if (paths == null) {
            paths = new String[39];
            fillPaths();
        }
//        if (card.isJoker())
//            return JOKER_PATH;
//        else {
            return paths[i];
            //return paths[card.getTerritory().getIndex()];
        //}
    }
    
    private static void fillPaths() {
        paths[TerritoryID.ARVORE] = CARDS_PATH+"a-arvore.png";
        paths[TerritoryID.A_DADIVA] = CARDS_PATH+"a-dadiva.png";
        paths[TerritoryID.BARROWLANDS] = CARDS_PATH+"barrowlands.png";
        paths[TerritoryID.COLINAS_DE_NORVOS] = CARDS_PATH+"colinas-de-norvos.png";
        paths[TerritoryID.CAMPOS_DOURADOS] = CARDS_PATH+"campos-dourados.png";
        paths[TerritoryID.CAPE_KRAKENTT] = CARDS_PATH+"cape-krakentt.png";
        paths[TerritoryID.COSTA_BRAVOSIANA] = CARDS_PATH+"costa-bravosiana.png";
        paths[TerritoryID.COSTA_GELADA] = CARDS_PATH+"a-costa-gelada.png";
        paths[TerritoryID.COSTA_LARANJA] = CARDS_PATH+"a-costa-laranja.png";
        paths[TerritoryID.COSTA_PEDREGOSA] = CARDS_PATH+"costa-pedregosa.png";
        paths[TerritoryID.DESERTO_VERMELHO] = CARDS_PATH+"o-deserto-vermelho.png";
        paths[TerritoryID.DORNE] = CARDS_PATH+"dorne.png";
        paths[TerritoryID.FLORESTA_ASSOMBRADA] = CARDS_PATH+"a-floresta-assombrada.png";
        paths[TerritoryID.FLORESTA_DE_QOHOR] = CARDS_PATH+"floresta-de-qohor.png";
        paths[TerritoryID.FOOTPRINT] = CARDS_PATH+"the-footprint.png";
        paths[TerritoryID.GARGALO] = CARDS_PATH+"o-gargalo.png";
        paths[TerritoryID.GHISCAR] = CARDS_PATH+"ghiscar.png";
        paths[TerritoryID.ILHA_DOS_URSOS] = CARDS_PATH+"ilha-dos-ursos.png";
        paths[TerritoryID.JARDIM_DE_CIMA] = CARDS_PATH+"jardim-de-cima.png";
        paths[TerritoryID.MAR_DOTHRAKI] = CARDS_PATH+"o-mar-dothraki.png";
        paths[TerritoryID.MATA_DE_LOBOS] = CARDS_PATH+"mata-de-lobos.png";
        paths[TerritoryID.MATADERREI] = CARDS_PATH+"mataderrei.png";
        paths[TerritoryID.MONTANHAS_DA_LUA] = CARDS_PATH+"as-montanhas-da-lua.png";
        paths[TerritoryID.MONTE_CHIFRE] = CARDS_PATH+"monte-chifre.png";
        paths[TerritoryID.OROS] = CARDS_PATH+"oros.png";
        paths[TerritoryID.PENHASCO_SOMBRIO] = CARDS_PATH+"o-penhasco-sombrio.png";
        paths[TerritoryID.PORTO_REAL] = CARDS_PATH+"porto-real.png";
        paths[TerritoryID.ROCHEDO_CASTERLY] = CARDS_PATH+"rochedo-casterly.png";
        paths[TerritoryID.SEMPRE_INVERNO] = CARDS_PATH+"a-terra-de-sempre-inverno.png";
        paths[TerritoryID.SKAGOS] = CARDS_PATH+"skagos.png";
        paths[TerritoryID.TARTH] = CARDS_PATH+"tarth.png";
        paths[TerritoryID.TERRAS_DA_TEMPESTADE] = CARDS_PATH+"terras-da-tempestade.png";
        paths[TerritoryID.TERRAS_DISPUTADAS] = CARDS_PATH+"as-terras-disputadas.png";
        paths[TerritoryID.TERRAS_FLUVIAIS] = CARDS_PATH+"terras-fluviais.png";
        paths[TerritoryID.THE_FLATLANDS] = CARDS_PATH+"the-flatlands.png";
        paths[TerritoryID.THE_HILLS] = CARDS_PATH+"the-hills.png";
        paths[TerritoryID.VALE_DE_ARRYN] = CARDS_PATH+"o-vale-de-arryn.png";
        paths[TerritoryID.VALIRIA] = CARDS_PATH+"valiria.png";
        paths[TerritoryID.WINTERFELL] = CARDS_PATH+"winterfell.png";
    }
    
}
