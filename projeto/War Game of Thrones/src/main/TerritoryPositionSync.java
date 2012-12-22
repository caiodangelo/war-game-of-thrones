package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class TerritoryPositionSync extends Component{

    private Map map;
    private Vector2f relativePos;
    
    public TerritoryPositionSync(Map map, Vector2f relativePos){
        super();
        this.map = map;
        this.relativePos = relativePos;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float x = relativePos.x * map.getScaledWidth()+map.getPosition().x;
        float y = relativePos.y * map.getScaledHeight()+map.getPosition().y;
        Vector2f newPos = new Vector2f(x, y);
        owner.setPosition(newPos);
        owner.setScale(map.getScale());
    }
    
}
