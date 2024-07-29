[![](https://jitpack.io/v/j1sk1ss/MenuFramework.PC.svg)](https://jitpack.io/#j1sk1ss/MenuFramework.PC)

<p align="center">
  <img width="1920" height="600" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/main_cover.png">
</p>

# **Dependencies:**</br>
- ItemManager.PC</br>

# **Main info:**
This is a very simple basis for creating your own menu in Minecraft.</br>
To get started, you will need to create a new window: </br>

    public static MenuWindow TestMenu = new MenuWindow(new ArrayList<Panel>());

In array list should be placed **Panels**. Panels, in few words, is representation of separated pages in your future menu. Let`s create a simple page:

    var testPage = new Panel(new ArrayList<Component>(), "PanelName", MenuSizes panelSize, String UI, String color); // UI and color give ability to create custom GUI

As you can see, panels should take **Components**. **Components** is a UI objects in your menu. Let`s talk about them a little more:</br>
**Components** presented by next types of UI objects:</br>
- **Button**
- **Slider**
- **CheckBox**
- **Bar**
- **ClickArea**
- **ItemArea**
- **LittleButton**
- **Icon**
</br>



<h2 align="center"> Buttons </h2>

GUI **buttons** can be created by next code:</br>

    var button = new Button(Margin position, String name, String lore, Action action);

Position - is positions of button`s corners. Main idea you can see on image below, and usualy you will use next code:</br>
    
    var buttonPosition = new Margin(int row, int col, int h, int w); 
    
</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/ButtonCoordinates.jpg">
</p>
</br>

Actions for buttons presented as functions that can be added to button and invoked by *button.click(event)*, where *event* is *InventoryClickEvent* (Component click envokes ComponentClickEvent). </br>
Also, example of adding delegate to **button**:

    var button = new Button(new Margin(0, 0, 3, 3), "Name", "Lore",
        (event, menu) -> {
            var player = (Player)event.getWhoClicked();
            var title  = event.getView().getTitle();

            player.sendMessage("Button clicked!");
        });

<h2 align="center"> Slider </h2>

**Sliders** can be used as a default sliders from popular frameworks. It can be created by next code:</br>

    var slider = new Slider(Margin position, ArrayList<String> Options, String lore, String name, Action action);

In this case we can use another **margin** constructor. Like next:

    var sliderPosition = new Margin(int row, int col, int w, Direction dir); // Direction can be horizontal and vertical.
    
</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/SliderCover.png">
</br><text> Red slot - chosen option in slider </text>
</p>
</br>

Note, that slider has default delegate. This "default" delegate regenerate **slider** body on click when chosen parameter changes. </br>
If you need to take chosen param from **Slider**, you can use next code:</br>

    var currentChosenParameter = PanelWindow.getPanel("PanelWhereSlider").getComponent("SliderName", Slider.class).getChose(event);

This **slider`s** ability give us a opportunity to connect **Buttons** and **Sliders** like in example below:</br>

    public static MenuWindow Menu = new MenuWindow(List.of(
      new Panel(
        List.of(
            new Slider(new Margin(0, 0, 4, Direction.Horizontal), Arrays.asList(
                "100", "200", "300", "400", "500", "600"   
            ), "SliderLore", "Slider1", null),
        
            new Button(new Margin(1, 0, 2, 2), "TestButton", "Lore",
                (event, menu) -> {
                    var player = (Player)event.getWhoClicked();
                    var sliderValue = menu.getPanel("TestPanel").getComponent("Slider", Slider.class).getChose(event);
                    if (sliderChose.equals(Slider.None)) return;
        
                    player.sendMessage("Current slider value: " + sliderValue); // Will prints current slider parameter
                }),
        ),
      "TestPanel", MenuSizes.FiveLines)
    ));

<h2 align="center"> Checkbox </h2>

**Checkboxes** have same functions with buttons instead one addition. Checkboxes, as Sliders, have action on click. Checkbox regenerate himself on every click like checkboxes from popular frameworks. This component can be created by next code:</br>

    var checkbox = new Checkbox(Margin position, String name, String lore, Action action, int cdm, int ddm, Material cm, Material dm); 
    // cdm - Checked data model
    // ddm - Default data model
    // cm - Checked material
    // dm - Default material

</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/CheckBoxCover.png">
</p>
</br>

Again. remember, that checkbox has default delegate too. This "default" delegate regenerate **checkbox** body on click (Checked and unchecked). </br>
Here first slot and second slot works like in **Button** part. One difference in method *isChecked*.

    var check = MainWindow.getPanel("PanelWhereCheckBow").getComponent("checkBox1", CheckBox.class).isChecked(event) // event -> InventoryClickEvent

<h2 align="center"> Click area and Item area </h2>

**ClickArea** can be created by next code:</br>

    var clickArea = ClickArea(Margin position, action, name, lore);

**ClickArea** solves problem with generated (non-static) buttons. You can just put  **ClickArea** where will be generated non-static **Components** then just handle any clicks in action delegate. </br>
For example next code shoulde handle clicks on generated options in menu:

    new ClickArea(new Margin(0, 0, 4, 8),
        (event, menu) -> {
            var player = (Player) event.getWhoClicked();
            var option = event.getCurrentItem();
            if (option == null) return;
    
            player.sendMessage("You click " + option.getType().toString());
        }),

*Note*: **Component** class has his own **PDC**. You can use it with next methods:

    public void setDouble2Container(value, key);
    public void setInteger2Container(value, key);
    public double getDoubleFromContainer(key);
    public int getIntegerFromContainer(key);
    public void deleteKeyFromContainer(key);

</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/ClickAreaCover.png">
</p>
</br>

<h2 align="center"> Little buttons </h2>

If you want create traditional GUI without resoursepacks, you can use **littleButton**. This component can be created by next code:</br>

    var button = new LittleButton(Margin position, String name, String lore, Action action, Material material, int dataModel);

</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/LittleButtonCover.png">
</p>
</br>

*Note*: **LittleButton** works like **Button**.

<h2 align="center"> Icons </h2>

**Icon** looks like **LittleButtons**, but difference in action. **Icons** don`t have action on click, that`s why you can use them as decoration. This component can be created by next code:</br>

    var icon = Icon(Margin position, String name, String lore, Material material, int dataModel);

<h2 align="center"> How to use it? </h2>

**Components** placed in **panels**. **Panels** placed in **Menu**. After all preparations we can start using all stuff what we make earlier.</br>

    public static MenuWindow Menu = new MenuWindow(new Panel(List.of(new Button(...), new Slider(...))), "MenuName", new LocalizationManager("path")) // Create new menu with panels

After this, MenuFramework listen all inventory clicks and invoke functions that was linked to **components** in **panels**.

<h2 align="center"> Panel </h2>

**Panel** is a representation of every inventory that used as menu in your plugin. Creation of panel is simple:

    var panel = new Panel(Arrays.asList( ... ), "InventoryTitlePart", MenuSizes.SixLines, String UI, Strign color);

</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/PanelCover.png">
    </br><text> Example of panel with click area, stider, checkbox \ button and little button \ icon </text>
</p>
</br>

<h2 align="center"> Localization </h2>

**MenuFramework** support multi-language GUI. For this, you should download **CordellDB** plugin (It used for working with localization files), create **localization file** and insert into **MenuWindow** **LocalizationManager**.</br>
**Localization file** has next structure:</br>

    LNG_componentName:translatedName/translatedLore
    // For example:
    RU_button1:кнопка1/кнопка
    EN_кнп:button
    IT_button:- // - means, that name not translated

</br> Remember that **MenuFramework** will execute linked function in situation, when used inventory have same name with panel. (Or **panel** name is a part of inventory title). </br>
To place all components to inventory you should use next code:

    public void getView(player);
    public void getView(player, inventory);

    // Or

    public void getView(player, lore);
    public void getView(player, customLore, names);
    public void getViewWith(player, newComponents); // newComponents - non-static components
    public void getView(player, lore, inventory);
    public void getView(player, customLore, names, inventory); // customLore - lore for components with names from names
    public void getViewWith(player, newComponents, inventory); // newComponents - non-static components

