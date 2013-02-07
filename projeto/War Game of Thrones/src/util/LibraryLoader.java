package util;

import java.lang.reflect.Field;

/*Sources from: 
http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 and
http://fahdshariff.blogspot.com.br/2011/08/changing-java-library-path-at-runtime.html*/

public class LibraryLoader {
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    public static void loadLibrary() throws Exception{
        String libraryPath = getLibraryPath();
        System.setProperty("java.library.path", libraryPath);
        Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }
    
    private static String getLibraryPath(){
        if(isMac())
            return "lib/lwjgl-2.8.5/native/macosx";
        if(isUnix())
            return "lib/lwjgl-2.8.5/native/linux";
        return "lib/lwjgl-2.8.5/native/windows";
    }
    
    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
}
