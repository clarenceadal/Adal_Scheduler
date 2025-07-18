import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GanttChartPanel extends JPanel {
    private List<String> chart;

    public void setChart(List<String> chart) {
        this.chart = chart;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (chart == null || chart.isEmpty()) return;

        int boxWidth = 50;
        int boxHeight = 40;
        int x = 10;
        int y = 20;

        for (int i = 0; i < chart.size(); i++) {
            String label = chart.get(i);
            g.setColor(getColor(label));
            g.fillRect(x, y, boxWidth, boxHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, boxWidth, boxHeight);
            g.drawString(label, x + 15, y + 25);
            x += boxWidth;
        }
    }

    private Color getColor(String label) {
        int hash = Math.abs(label.hashCode());
        return new Color((hash >> 16) & 0xFF, (hash >> 8) & 0xFF, hash & 0xFF).brighter();
    }
}
