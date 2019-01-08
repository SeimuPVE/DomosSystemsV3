package routines;

import java.util.Observable;
import java.util.Observer;

public class DesktopLightsWithChairPressure implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        System.out.println("The current value is " + o);
    }
}
