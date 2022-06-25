package view.views.online_users;

import controller.ActionController;
import model.users.Guest;
import view.View;
import view.views.components.OnlineUserComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OnlineUsersView extends View {
    private ArrayList<OnlineUserComponent> onlineUserComponents;
    private JPanel onlineUsersLog;
    private ActionController actionController;

    public OnlineUsersView() {
        this.onlineUserComponents = new ArrayList<>();
        this.render();
    }

    private void render() {
        // Create a wrapper to avoid the list fully stretching from the start
        JPanel wrapper = new JPanel(new BorderLayout());

        // Create the main container and give it a vertical boxlayout
        this.onlineUsersLog = new JPanel();
        this.onlineUsersLog.setLayout(new BoxLayout(this.onlineUsersLog, BoxLayout.Y_AXIS));

        // Set the preferred size, mostly for the initial pack when there's no content
        wrapper.setPreferredSize(new Dimension(1000, 550));

        // Add the content to the north of the wrapper (once again, to avoid the stretching)
        wrapper.add(this.onlineUsersLog, BorderLayout.NORTH);

        // Add the wrapper to a scroll pane, and add it to the center
        this.add(new JScrollPane(wrapper, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.actionController = actionController;
    }

    public void addUser(Guest user) {
        OnlineUserComponent onlineUserComponent = new OnlineUserComponent(user, this.actionController);

        this.onlineUserComponents.add(onlineUserComponent);
        this.onlineUsersLog.add(onlineUserComponent);
        this.onlineUsersLog.revalidate();
    }

    public void removeUser(Guest user) {
        OnlineUserComponent onlineUserComponent = null;

        // Get the component of the online user that must be removed
        for(OnlineUserComponent component : this.onlineUserComponents) {
            if(component.getUser().getUsername().equals(user.getUsername())) {
                onlineUserComponent = component;
            }
        }

        // Remove component from list
        this.onlineUserComponents.remove(onlineUserComponent);

        // Update online users list on view
        this.onlineUsersLog.removeAll();
        this.onlineUserComponents.forEach(component -> this.onlineUsersLog.add(component));
        this.onlineUsersLog.revalidate();
        this.onlineUsersLog.repaint();
    }

    public ArrayList<OnlineUserComponent> getOnlineUserComponents() {
        return onlineUserComponents;
    }
}