package view.views.rankings;

import controller.ActionController;
import view.View;
import view.views.components.RankingUserComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RankingView extends View {
    private ArrayList<RankingUserComponent> rankingUserComponents;
    private JPanel rankingUsersLog;

    public RankingView() {
        this.rankingUserComponents = new ArrayList<>();
        this.render();
    }

    private void render() {
        // Create a wrapper to avoid the list fully stretching from the start
        JPanel wrapper = new JPanel(new BorderLayout());

        // Create the main container and give it a vertical boxlayout
        this.rankingUsersLog = new JPanel();
        this.rankingUsersLog.setLayout(new BoxLayout(this.rankingUsersLog, BoxLayout.Y_AXIS));

        // Set the preferred size, mostly for the initial pack when there's no content
        wrapper.setPreferredSize(new Dimension(250, 550));

        // Add the content to the north of the wrapper (once again, to avoid the stretching)
        wrapper.add(this.rankingUsersLog, BorderLayout.NORTH);

        // Add the wrapper to a scroll pane, and add it to the center
        this.add(new JScrollPane(wrapper, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    }

    @Override
    public void addActionController(ActionController actionController) {
    }

    public void updateDataset(Map<String, Integer> ranking) {
        int rankingPosition = 0;
        this.rankingUserComponents.clear();
        this.rankingUsersLog.removeAll();

        LinkedHashMap<String, Integer> sortedRanking = new LinkedHashMap<>();
        ranking.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> {
            sortedRanking.put(x.getKey(), x.getValue());
        });

        for (Map.Entry<String, Integer> entry : sortedRanking.entrySet()) {
            this.rankingUserComponents.add(new RankingUserComponent(entry.getKey(), rankingPosition, entry.getValue()));
            this.rankingUsersLog.add(this.rankingUserComponents.get(rankingPosition));
            rankingPosition++;
        }

        this.rankingUsersLog.revalidate();
    }
}