package models;

import java.util.HashMap;

/**
 * Contém todos os IDs estáticos dos territórios, que correspondem às suas respectivas
 * ordens no vetor de territórios da classe {@link Board}
 */
public final class TerritoryID {
    public static final int 
            COSTA_GELADA = 0,
            FLORESTA_ASSOMBRADA = 1,
            SEMPRE_INVERNO = 2,
            ILHA_DOS_URSOS = 3,
            SKAGOS = 4,
            
            COSTA_LARANJA = 5,
            TERRAS_DISPUTADAS = 6,
            CAMPOS_DOURADOS = 7,
            COLINAS_DE_NORVOS = 8,
            COSTA_BRAVOSIANA = 9,
            FLORESTA_DE_QOHOR = 10,
            THE_FLATLANDS = 11,
            VALIRIA = 12,
            
            A_DADIVA = 13,
            BARROWLANDS = 14,
            COSTA_PEDREGOSA = 15,
            MATA_DE_LOBOS = 16,
            PENHASCO_SOMBRIO = 17,
            THE_HILLS = 18,
            WINTERFELL = 19,
        
            ARVORE = 20,
            DORNE = 21,
            JARDIM_DE_CIMA = 22,
            MATADERREI = 23,
            MONTE_CHIFRE = 24,
            TARTH = 25,
            TERRAS_DA_TEMPESTADE = 26,
                    
            MONTANHAS_DA_LUA = 27,
            CAPE_KRAKENTT = 28,
            GARGALO = 29,
            VALE_DE_ARRYN = 30,
            PORTO_REAL = 31,
            ROCHEDO_CASTERLY = 32,
            TERRAS_FLUVIAIS = 33,
            
            GHISCAR = 34,
            DESERTO_VERMELHO = 35,
            MAR_DOTHRAKI = 36,
            OROS = 37,
            FOOTPRINT = 38;
    
    private static HashMap<String, Integer> regionIDs;

    /**
     * Retorna o ID de uma {@link Region}, passando o seu nome como parâmetro.
     *
     * Caso o hash de regiões for nulo, este será preenchido com as regiões padrão.
     */
    public static int getRegionID(String name){
        if(regionIDs == null){
            regionIDs = new HashMap<String, Integer>();
            regionIDs.put("Além da Muralha", 0);
            regionIDs.put("Cidades Livres", 1);
            regionIDs.put("O Norte", 2);
            regionIDs.put("Sul", 3);
            regionIDs.put("Tridente", 4);
            regionIDs.put("O Mar Dothraki", 5);
        }
        return regionIDs.get(name);
    }
}
