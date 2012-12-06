//package tests;
//
//import java.awt.DisplayMode;
//import mdes.slick.sui.*;
//import mdes.slick.sui.event.MouseAdapter;
//import mdes.slick.sui.event.MouseEvent;
//import mdes.slick.sui.layout.RowLayout;
//import org.newdawn.slick.*;
//
//public class UITest extends BasicGame{
//
//    private TestObject obj;
//    Button b;
//    Label l;
//    Display d;
//    
//    
//    public UITest(){
//        super("Teste da slick");
//    }
//    
//    @Override
//    public void init(GameContainer gc) throws SlickException {
//        Color background = new Color(71,102,124);
//        gc.getGraphics().setBackground(background);
//        d = new Display(gc);
//        obj = new TestObject();
//        Container content = new Container();
//        content.setSize(155, 100); //sets panel size
//        content.setLocation(100, 100); //sets panel loc relative to parent (display)
//        content.setOpaque(true); //ensures that the background is drawn
//        content.setBackground(Color.darkGray); //sets the background color
//        
//        RowLayout layout = new RowLayout(true, RowLayout.LEFT, RowLayout.CENTER);
//        content.setLayout(layout);
//        
//         //add a button to our content panel
//        Button btn = new Button("Clicky");
//        btn.pack(); //pack the button to the text
//        btn.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mousePressed(MouseEvent e) {
//                System.out.println("pressed");
//            }
//        });
//        content.add(btn);
//        
//        //add a label to our content panel
//        Label label = new Label("This is a test");
//        label.setForeground(Color.white); //sets the foreground (text) color
//        label.pack(); //pack the label with the current text, font and padding
//        label.setHeight(btn.getHeight()); //set same size between the two components
//        content.add(label); //add the label to this display so it can be rendered
//
//        //add the content panel to the display so it can be rendered
//        d.add(content);
//    }
//
//    @Override
//    public void update(GameContainer gc, int i) throws SlickException {
//        obj.update(i / 1000f);
//        d.update(gc, i);
//    }
//
//    @Override
//    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
////        obj.render(grphcs);
//        d.render(gc, grphcs);
//    }
//
//    public static void main(String[] args) throws SlickException, Exception {
//        mdes.slick.sui.test.TextComponentTest.main(args);
////        AppGameContainer app = new AppGameContainer(new UITest());
////        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
////        int w = dm.getWidth(), h = dm.getHeight();
////        app.setDisplayMode(w, h, false);
////        app.setTargetFrameRate(60);
////        app.start();
//    }
//}
