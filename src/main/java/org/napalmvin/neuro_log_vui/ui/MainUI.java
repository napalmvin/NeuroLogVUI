package org.napalmvin.neuro_log_vui.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import static org.napalmvin.neuro_log_vui.PathConstants.Type.IMAGE;
import org.napalmvin.neuro_log_vui.error.CustomErrorHandler;
import org.napalmvin.neuro_log_vui.ui.vaadin.ValoMenuLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI()
@Theme("mytheme")
@Title("NeuroLog")
//@PreserveOnRefresh
public class MainUI extends UI {
    private static final Logger log = LoggerFactory.getLogger(MainUI.class);

    CssLayout menuItemsLayout = new CssLayout();
    CssLayout mainMenu = new CssLayout();
    private final ThemeIcon testIcon = new ThemeIcon(100);

    private Navigator navigator;

    @Autowired
    private SpringViewProvider viewProvider;

    private final LinkedHashMap<String, String> menuItemsStrings = new LinkedHashMap<>();
    
    
    @Autowired
    private ResourceBundle msg;
    
    public MainUI() {
    }

    @Override
    protected void init(VaadinRequest request) {
        log.info("Initializing MainUI");

        ValoMenuLayout root = new ValoMenuLayout();
        
         UI.getCurrent().setErrorHandler(new CustomErrorHandler(msg));

        setContent(root);
        root.setWidth("100%");

        root.addMenu(buildMenu());
        addStyleName(ValoTheme.UI_WITH_MENU);

        getPage().setTitle("NeuroLog");
        
        navigator = new Navigator(this, root.getContentContainer());
        navigator.addProvider(viewProvider);
        final String f = Page.getCurrent().getUriFragment();
        if (f == null || f.equals("")) {
            navigator.navigateTo("generalExams");
        }

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                for (final Iterator<Component> it = menuItemsLayout.iterator(); it
                        .hasNext();) {
                    it.next().removeStyleName("selected");
                }
                for (final Entry<String, String> item : menuItemsStrings.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (final Iterator<Component> it = menuItemsLayout
                                .iterator(); it.hasNext();) {
                            final Component c = it.next();
                            if (c.getCaption() != null
                                    && c.getCaption().startsWith(
                                            item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                mainMenu.removeStyleName("valo-menu-visible");
            }
        });

    }

    private CssLayout buildMenu() {
        // Add items
        addMenuitems();

        addApplicationNameComponent();

//        addIconSubMenu();
        addUserSubMenu();

        addItems();

        return mainMenu;
    }

    private void addMenuitems() {

        menuItemsStrings.put("doctors", msg.getString("doctors"));
        menuItemsStrings.put("patients", msg.getString("patients"));
        menuItemsStrings.put("generalExams", msg.getString("generalExams"));
    }

    private void addApplicationNameComponent() {
        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        mainMenu.addComponent(top);

        Label title = new Label("<h3><strong>NeuroLog</strong></h3>",
                ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
    }

    private void addIconSubMenu() {
        final Button iconSubmenu = new Button(msg.getString("menu"), new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (mainMenu.getStyleName().contains("valo-menu-visible")) {
                    mainMenu.removeStyleName("valo-menu-visible");
                } else {
                    mainMenu.addStyleName("valo-menu-visible");
                }
            }
        });
        iconSubmenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        iconSubmenu.addStyleName(ValoTheme.BUTTON_SMALL);
        iconSubmenu.addStyleName("valo-menu-toggle");
        iconSubmenu.setIcon(FontAwesome.LIST);
        mainMenu.addComponent(iconSubmenu);
    }

    private void addUserSubMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final MenuItem settingsItem = settings.addItem("Oleg Sukhonosov",
                new ExternalResource(IMAGE.getParentFolder() + "profile-pic-300px.jpg"),
                null);
        settingsItem.addItem("Edit Profile", null);
        settingsItem.addItem("Preferences", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", null);
        mainMenu.addComponent(settings);
    }

    private void addItems() {
        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        mainMenu.addComponent(menuItemsLayout);

        Label label = null;
        int count = -1;
        for (final Entry<String, String> item : menuItemsStrings.entrySet()) {
            String key = item.getKey();
            if (key.equals("doctors")) {
                label = new Label(msg.getString("tables"), ContentMode.HTML);
                label.setPrimaryStyleName("valo-menu-subtitle");
                label.addStyleName("h4");
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }

            final Button b = new Button(item.getValue(), new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    navigator.navigateTo(item.getKey());
                }
            });

//            b.setCaption(b.getCaption()
//                    + " <span class=\"valo-menu-badge\">123</span>");

            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName("valo-menu-item");
            b.setIcon(testIcon.get());
            menuItemsLayout.addComponent(b);
            count++;
        }
//        label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
//                + count + "</span>");
    }

}
