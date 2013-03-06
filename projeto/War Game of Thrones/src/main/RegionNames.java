package main;

import org.newdawn.slick.geom.Vector2f;
import util.Entity;

public class RegionNames extends Entity{

    public RegionData [] regions;
    
    public RegionNames(Vector2f [] territoryCenters, Map m){
        addComponent(new RegionNamesRenderer(this, m));
        setLayer(2);
        //fill region data
        regions = new RegionData[6];
        
        Vector2f [] centers = {
            getCenter(territoryCenters, 0, 4),
            getCenter(territoryCenters, 5, 12),
            getCenter(territoryCenters, 13, 19),
            getCenter(territoryCenters, 20, 26),
            getCenter(territoryCenters, 27, 33),
            getCenter(territoryCenters, 34, 38)
        };
        
        //make adjustments
        centers[0].y /= 3 - 0.1f;
        centers[1].y -= 0.01f;
        centers[2].x += 0.05f;
        centers[2].y -= 0.019f;
        centers[4].x += 0.03f;
        centers[4].y += 0.06f;
        centers[5].y -= 0.05f;
        
        
        regions[0] = new RegionData(centers[0], "Além da Muralha");
        regions[1] = new RegionData(centers[1], "Cidades Livres");
        regions[2] = new RegionData(centers[2], "O Norte");
        regions[3] = new RegionData(centers[3], "O Sul");
        regions[4] = new RegionData(centers[4], "Tridente");
        regions[5] = new RegionData(centers[5], "Mar Dothraki");
        
    }

    private static Vector2f getCenter(Vector2f[] territoryCenters, int start, int end) {
        float sumX = 0;
        float sumY = 0;
        for(int i = start; i <= end; i++){
            Vector2f center = territoryCenters[i];
            sumX += center.x;
            sumY += center.y;
        }
        int count = end - start + 1;
        sumX /= count;
        sumY /= count;
        return new Vector2f(sumX, sumY);
    }
    
    protected class RegionData{
        Vector2f regionCenter;
        String name;
        public RegionData(Vector2f regionCenter, String name){
            this.regionCenter = regionCenter;
            this.name = name;
        }
    }
}
