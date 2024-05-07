[![](https://jitpack.io/v/j1sk1ss/MenuFramework.PC.svg)](https://jitpack.io/#j1sk1ss/MenuFramework.PC)

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

Note, that slider has default delegate. This "default" delegate regenerate **slider** body on click when chosen parametr changes. </br>
If you need to take chosen param from **Slider**, you can use next code:</br>

    var slider = new Slider(panelWhereStoredSlider.getSliders("SliderName"), event.getInventory());
    var parameter = slider.getChose(event); // parameter is a String lore line from Options

This **slider`s** ability give us a opportunity to connect **Buttons** and **Sliders** like in example below:</br>

    var panel = new Panel(Arrays.asList(
        new Slider(Arrays.asList(
            0, 1, 2, 3, 4, 5
        ), Arrays.asList(
            "100", "200", "300", "400", "500", "600"   
        ), "", "Slider", null),
    
        new Button(9, 21, "TestButton", "Lore",
            (event) -> {
                var player = (Player)event.getWhoClicked();
                var slider = new Slider(Menu.getPanel("TestPanel").getSliders("Slider"), event.getInventory());
                if (slider.getChose(event).equals("none")) return;
    
                player.sendMessage(slider.getChose(event)); // It will prints current slider parameter
            }),
    ), "TestPanel"),



<h2 align="center"> Checkbox </h2>

**Checkboxes** can be created by next code:</br>

    var checkbox = new Checkbox(int firstSlot, int secondSlot, String name, String lore, Action action);

Here first slot and second slot works like in **Button** part. One difference in method *isChecked*.

    var check = checkbox.isChecked(event) // event -> InventoryClickEvent


<h2 align="center"> How to use it? </h2>

**Components** placed in **panels**. **Panels** placed in **Menu**. After all preparations we can start using all stuff what we make earlier.</br>

    public static MenuWindow Menu = ... // Create new menu with panels

After this, MenuFramework listen all inventory clicks and anvoke functions that was linked to **buttons** in **panels**.

<h2 align="center"> Panel </h2>

**Panel** is a representation of every inventory that used as menu in your plugin. Creation of panel is simple:

    var panel = new Panel(Arrays.asList( ... ), "InventoryTitlePart");

Remember that **MenuFramework** will execute linked function in situation, when used inventory have same name with panel. (Or **panel** name is a part of inventory title).

