package gui;

public class AIDifficulty {

    private int id;
    public static final AIDifficulty EASY = new AIDifficulty(0);
    public static final AIDifficulty MEDIUM = new AIDifficulty(1);
    public static final AIDifficulty HARD = new AIDifficulty(2);
    
    private AIDifficulty(int id){
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    @Override
    public String toString(){
        switch(id){
            case 0:
                return "Fácil";
            case 1:
                return "Médio";
            case 2:
                return "Difícil";
        }
        return null;
    }
    
    @Override
    public boolean equals(Object other){
        return other != null && other instanceof AIDifficulty && this.id == ((AIDifficulty)other).id;
    }
}
