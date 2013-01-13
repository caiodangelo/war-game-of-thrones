package communication;

public interface BackEndPlayer {
    
    public boolean isHuman();
    public int getCardsCount();
    public int getUnitsCount();
    public int getTerritoriesCount();
    public String getName();
    public Mission getMission();
}
