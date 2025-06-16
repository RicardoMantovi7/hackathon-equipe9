package felipe.nascimento.gui;

import java.awt.*;

/**
 * Classe Utilitária para simplificar o uso do GridBagLayout.
 * GridBagLayout é poderoso para organizar componentes, mas é verboso.
 * Esta classe ajuda a reduzir a repetição de código.
 */
public class GuiUtils {
    /**
     * Cria e retorna um objeto GridBagConstraints com configurações padrão.
     * Facilita o posicionamento de componentes em uma grade (linhas e colunas).
     * @param x A coluna (posição horizontal) onde o componente será colocado.
     * @param y A linha (posição vertical) onde o componente será colocado.
     * @return Um objeto GridBagConstraints configurado.
     */
    public GridBagConstraints montarConstraints(int x, int y) {
        // Cria o objeto que guarda as "regras" de posicionamento
        GridBagConstraints c = new GridBagConstraints();
        // Define uma margem externa de 5 pixels em todos os lados do componente
        c.insets = new Insets(5, 5, 5, 5);
        // Define a célula da coluna (x)
        c.gridx = x;
        // Define a célula da linha (y)
        c.gridy = y;
        return c;
    }
}