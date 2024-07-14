[![](https://jitpack.io/v/j1sk1ss/MenuFramework.PC.svg)](https://jitpack.io/#j1sk1ss/MenuFramework.PC)

<p align="center">
  <img width="1920" height="600" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/main_cover.png">
</p>

# **Dependencies:**</br>
- ItemManager.PC</br>

# **Main info:**
This is a very simple basis for creating your own menu in Minecraft.</br>
To get started, you will need to create a new inventory: </br>

    var window = Bukkit.createInventory(player, 27, Component.text("Test_Inventory"));

Then create a new **MenuWindow** object. This object is representation of your future menu: </br>

    public static MenuWindow TestMenu = new MenuWindow(new ArrayList<Panel>());

In array list should be placed **Panels**. Panels, in few words, is representation of separated pages in your future menu. Let`s create a simple page:

    var testPage = new Panel(new ArrayList<Component>());

As you can see, panels should take **Components**. **Components** is a UI objects in your menu. Let`s talk about them a little more:</br>
**Components** presented by next types of UI objects:</br>
- **Button**
- **Slider**
- **CheckBox**
- **Bar**
- **ClickArea**
- **LittleButton**
- **Icon**
</br>



<h2 align="center"> Buttons </h2>

**Buttons** can be created by next code:</br>

    var button = new Button(int firstPos, int secPos, String name, String lore, Action action);

First and second positions - is positions of button`s corners. Main idea you can see on image below:</br>
<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/ButtonCoordinates.jpg">
</p>

Actions for buttons presented as functions that can be added to button and invoked by *button.click(event)*, where *event* is *InventoryClickEvent* (Component click envokes ComponentClickEvent). </br>
Also, example of adding delegate to **button**:

    var button = new Button(6, 26, "Name", "Lore",
        (event) -> {
            var player = (Player)event.getWhoClicked();
            var title  = event.getView().getTitle();

            player.sendMessage(title);
        })



<h2 align="center"> Slider </h2>

**Sliders** can be created by next code:</br>

    var slider = new Slider(ArrayList<Integer> coordinates, ArrayList<String> Options, String lore, String name, Action action);

<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/SliderCover.png">
</br><text> Red slot - chosen option in slider </text>
</p>

Note, that slider has default delegate. This "default" delegate regenerate **slider** body on click when chosen parameter changes. </br>
If you need to take chosen param from **Slider**, you can use next code:</br>

    var slider = new Slider(panelWhereStoredSlider.getSliders("SliderName"), event.getInventory());
    var parameter = slider.getChose(event); // parameter is a String lore line from Options

This **slider`s** ability give us a opportunity to connect **Buttons** and **Sliders** like in example below:</br>

    public static MenuWindow Menu = new MenuWindow(Arrays.asList(
    var panel = new Panel(Arrays.asList(
        new Slider(Arrays.asList(
            0, 1, 2, 3, 4, 5
        ), Arrays.asList(
            "100", "200", "300", "400", "500", "600"   
        ), "", "Slider", null),
    
        new Button(9, 21, "TestButton", "Lore",
            (event) -> {
                var player = (Player)event.getWhoClicked();
                var sliderChose = Menu.getPanel("TestPanel").getSliders("Slider").getChose(event);
                if (sliderChose.equals("none")) return;
    
                player.sendMessage(sliderChose); // Will prints current slider parameter
            }),
    ), "TestPanel")));



<h2 align="center"> Checkbox </h2>

**Checkboxes** can be created by next code:</br>

    var checkbox = new Checkbox(int firstSlot, int secondSlot, String name, String lore, Action action);

<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/CheckBoxCover.png">
</p>

Note, that checkbox has default delegate too. This "default" delegate regenerate **checkbox** body on click (Checked and unchecked). </br>
Here first slot and second slot works like in **Button** part. One difference in method *isChecked*.

    var check = checkbox.isChecked(event) // event -> InventoryClickEvent

<h2 align="center"> Click area </h2>

**ClickArea** can be created by next code:</br>

    var clickArea = ClickArea(firstCoordinate, secondCoordinate, action, name, lore);
    var clickArea = ClickArea(coordinates, action, name, lore);

**ClickArea** solves problem with generated (non-static) buttons. You can just put  **ClickArea** where will be generated non-static **Components** then just handle any clicks in action delegate. </br>
For example next code shoulde handle clicks on generated options in menu:

    new ClickArea(0, 44,
        (event) -> {
            var player = (Player) event.getWhoClicked();
            var option = event.getCurrentItem();
            if (option == null) return;
    
            if (option.getLoreLines().size() < 2) return;
            var amount = option.getDoubleFromContainer("item-bank-value");
    
            func1(Math.abs(amount), player);
            openMenu(player, event.getInventory());
        }),

*Note*: **Component** class has his own **PDC**. You can use it with next methods:

    public void setDouble2Container(value, key);
    public void setInteger2Container(value, key);
    public double getDoubleFromContainer(key);
    public int getIntegerFromContainer(key);
    public void deleteKeyFromContainer(key);

<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/ClickAreaCover.png">
</p>

<h2 align="center"> Little buttons </h2>

**LittleButton** can be created by next code:</br>

    var button = new LittleButton(41, "LB", "\n" + Math.round(playerBalance * 100) / 100 + CashManager.currencySigh);

<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/LittleButtonCover.png">
</p>

**LittleButton** works like **Button**, but this component has onlu one position.

<h2 align="center"> Icons </h2>

**Icon** can be created by next code:</br>

    var icon = Icon(position, name, lore, material, dataModel);

<h2 align="center"> How to use it? </h2>

**Components** placed in **panels**. **Panels** placed in **Menu**. After all preparations we can start using all stuff what we make earlier.</br>

    public static MenuWindow Menu = ... // Create new menu with panels

After this, MenuFramework listen all inventory clicks and anvoke functions that was linked to **buttons** in **panels**.

<h2 align="center"> Panel </h2>

**Panel** is a representation of every inventory that used as menu in your plugin. Creation of panel is simple:

    var panel = new Panel(Arrays.asList( ... ), "InventoryTitlePart", MenuSizes.SixLines);

<p align="center">
  <img width="600" height="800" src="https://github.com/j1sk1ss/MenuFramework.PC/blob/master/covers/PanelCover.png">
    </br><text> Example of panel with click area, stider, checkbox \ button and little button \ icon </text>
</p>

Remember that **MenuFramework** will execute linked function in situation, when used inventory have same name with panel. (Or **panel** name is a part of inventory title). </br>
To place all components to inventory you shoukd use next code:

    public void getView(player);
    public void getView(player, inventory);

    // Or

    public void getView(player, lore);
    public void getView(player, customLore, names);
    public void getViewWith(player, newComponents); // newComponents - non-static components
    public void getView(player, lore, inventory);
    public void getView(player, customLore, names, inventory); // customLore - lore for components with names from names
    public void getViewWith(player, newComponents, inventory); // newComponents - non-static components

