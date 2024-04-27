[![](https://jitpack.io/v/j1sk1ss/MenuFramework.PC.svg)](https://jitpack.io/#j1sk1ss/MenuFramework.PC)

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
</br>



<h1 align="center"> Buttons </h1>

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



<h1 align="center"> Slider </h1>

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



<h1 align="center"> Checkbox </h1>

**Checkboxes** can be created by next code:</br>

    var checkbox = new Checkbox(int firstSlot, int secondSlot, String name, String lore, Action action);

Here first slot and second slot works like in **Button** part. One difference in method *isChecked*.

    var check = checkbox.isChecked(event) // event -> InventoryClickEvent


<h1 align="center"> How to use it? </h1>

**Components** placed in **panels**. **Panels** placed in **Menu**. After all preparations we can start using all stuff what we make earlier.</br>

    public static MenuWindow Menu = ... // Create new menu with buttons, sliders, checkboxes and logic

Then you should create new **Listener** that will listen all player`s inventory clicks with simple switch. 

    public class ClickListener implements IWindowListener {
        @SuppressWarnings("deprecation")
        @EventHandler
        public void onClick(InventoryClickEvent event) {
            var windowTitle = event.getView().getTitle();
            if (windowTitle.contains("FirstMenu")) FMenu.getPanel("FirstMenu").click(event);
            if (windowTitle.contains("SecondMenu")) FMenu.getPanel("SecondMenu").click(event);
            if (windowTitle.contains("ThirdMenu")) FMenu.getPanel("ThirdMenu").click(event);
        }
    }

