package bases;

import java.awt.event.WindowStateListener;

public interface WindowStaticType extends WindowType{
    String getName();
    int getWidth();
    int getHeight();
    int getRelativeX();
    int getRelativeY();
}

