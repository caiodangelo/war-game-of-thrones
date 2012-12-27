package communication;

public class BEPImpl implements BackEndPlayer{
    
    private int cards, units, territories;
    
    public BEPImpl(){
        cards = (int)(Math.random() * 100);
        units = (int)(Math.random() * 100);
        territories = (int)(Math.random() * 100);
    }
    
    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public int getCardsCount() {
        return cards;
    }

    @Override
    public int getUnitsCount() {
        return units;
    }

    @Override
    public int getTerritoriesCount() {
        return territories;
    }

    @Override
    public String getName() {
        return "Nome";
    }

}
