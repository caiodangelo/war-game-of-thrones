package util;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import org.lwjgl.input.Mouse;

public class PopupManager {
    
    private static int openPopups = 0;
    private static boolean zeroDelayElapsed = true;
    
    public static void showPopup(Nifty n, Screen s, Element popup){
        Mouse.setGrabbed(false);
        n.showPopup(s, popup.getId(), null);
        openPopups++;
        zeroDelayElapsed = false;
    }
    
    public static void closePopup(Nifty n, Element popup){
        n.closePopup(popup.getId());
        openPopups--;
        if(openPopups == 0){
            new Thread(){
                @Override
                public void run(){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {  }
                    if(openPopups == 0)
                        zeroDelayElapsed = true;
                }
            }.start();
        }
   }
    
    public static boolean isAnyPopupOpen(){
        return openPopups > 0 || !zeroDelayElapsed;
    }
}
