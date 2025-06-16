package felipe.nascimento.gui;

import java.awt.*;

public class GuiUtils {
    public GridBagConstraints montarConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
}