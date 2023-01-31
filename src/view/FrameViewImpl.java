package view;

import controller.ImageUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.week2.ILayer;

/**
 * Represents a view with a GUI.
 */
public class FrameViewImpl extends JFrame implements IFrameView {

  /**
   * Names of the layers displayed.
   */
  private DefaultListModel<String> dataLayerName;

  /**
   * Submenus for the menuBar.
   */
  private final JMenu menuOfEverything;

  /**
   * Items in the menu.
   */
  private final JMenuItem blurItem;
  private final JMenuItem sharpenItem;
  private final JMenuItem grayscaleItem;
  private final JMenuItem sepiaItem;
  private final JMenuItem mosaicItem;
  private final JMenuItem saveItem;
  private final JMenuItem loadItem;
  private final JMenuItem openItem;
  private final JMenuItem exportItem;
  private final JMenuItem visItem;
  private final JMenuItem invisItem;
  private final JMenuItem createItem;
  private final JMenuItem removeItem;
  private final JMenuItem checkItem;
  private final JMenuItem resetItem;
  private final JMenuItem scriptItem;

  /**
   * JPanels in the view.
   */
  private final JPanel selectionListPanel;

  /**
   * List of the names of layers in the view.
   */
  private JList<String> listOfLayers;

  /**
   * All buttons in the view.
   */
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton grayscaleButton;
  private final JButton sepiaButton;
  private final JButton mosaicButton;
  private final JButton saveButton;
  private final JButton openButton;
  private final JButton checkerboardButton;
  private final JButton exportButton;
  private final JButton loadButton;
  private final JButton resetButton;
  private final JButton removeButton;
  private final JButton createButton;
  private final JButton visibleButton;
  private final JButton invisibleButton;
  private final JButton executeScriptButton;

  /**
   * JLabel for the image being displayed.
   */
  private final JLabel imageLabel;


  /**
   * Initializes a new FrameViewImpl. Sets the size of the frame, initializes all buttons and menus,
   * sets the list of layers to an empty list, and makes the frame visible.
   */
  public FrameViewImpl() {
    super();
    setSize(1920, 1080);

    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    dataLayerName = new DefaultListModel<>();
    //this is the choices of layers for current
    listOfLayers = new JList<>(dataLayerName);
    listOfLayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    //creates the menu bar
    JMenuBar menuBar = new JMenuBar();

    //builds the first menu
    menuOfEverything = new JMenu("All Options");
    JMenu filterMenu = new JMenu("Available filters");

    blurItem = new JMenuItem("Blur");
    filterMenu.add(blurItem);

    sharpenItem = new JMenuItem("Sharpen");
    filterMenu.add(sharpenItem);

    grayscaleItem = new JMenuItem("Grayscale");
    filterMenu.add(grayscaleItem);

    sepiaItem = new JMenuItem("Sepia");
    filterMenu.add(sepiaItem);

    mosaicItem = new JMenuItem("Mosaic");
    filterMenu.add(mosaicItem);
    menuOfEverything.add(filterMenu);

    saveItem = new JMenuItem("Save");
    menuOfEverything.add(saveItem);

    openItem = new JMenuItem("Open");
    menuOfEverything.add(openItem);

    checkItem = new JMenuItem("Checkerboard");
    menuOfEverything.add(checkItem);

    exportItem = new JMenuItem("Export");
    menuOfEverything.add(exportItem);

    loadItem = new JMenuItem("Load");
    menuOfEverything.add(loadItem);

    resetItem = new JMenuItem("Reset");
    menuOfEverything.add(resetItem);

    removeItem = new JMenuItem("Remove");
    menuOfEverything.add(removeItem);

    createItem = new JMenuItem("Create");
    menuOfEverything.add(createItem);

    visItem = new JMenuItem("Make visible");
    menuOfEverything.add(visItem);

    invisItem = new JMenuItem("Make invisible");
    menuOfEverything.add(invisItem);

    scriptItem = new JMenuItem("Execute script");
    menuOfEverything.add(scriptItem);

    menuBar.add(menuOfEverything);
    setJMenuBar(menuBar);

    JPanel script = new JPanel();
    script.setLayout(new BorderLayout());
    add(script, BorderLayout.SOUTH);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Topmost visible photo"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    add(imagePanel, BorderLayout.CENTER);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)));

    imageScrollPane.setPreferredSize(new Dimension(400, 400));
    imagePanel.add(imageScrollPane);

    JPanel toolBar = new JPanel();
    toolBar.setLayout(new FlowLayout());
    add(toolBar, BorderLayout.NORTH);

    //blur operation
    blurButton = new JButton("Blur");
    toolBar.add(blurButton);

    //sharpen operation
    sharpenButton = new JButton("Sharpen");
    toolBar.add(sharpenButton);

    //grayscale operation
    grayscaleButton = new JButton("Grayscale");
    toolBar.add(grayscaleButton);

    //sepia operation
    sepiaButton = new JButton("Sepia");
    toolBar.add(sepiaButton);

    //mosaic operation
    mosaicButton = new JButton("Mosaic");
    toolBar.add(mosaicButton);

    //save operation
    saveButton = new JButton("Save");
    toolBar.add(saveButton);

    //open operation
    openButton = new JButton("Open");
    toolBar.add(openButton);

    //checkerboard operation
    checkerboardButton = new JButton("Checkerboard");
    toolBar.add(checkerboardButton);

    //export operation
    exportButton = new JButton("Export");
    toolBar.add(exportButton);

    //load operation
    loadButton = new JButton("Load");
    toolBar.add(loadButton);

    //reset operation
    resetButton = new JButton("Reset");
    toolBar.add(resetButton);

    //remove operation
    removeButton = new JButton("Remove");
    toolBar.add(removeButton);

    //create operation
    createButton = new JButton("Create");
    toolBar.add(createButton);

    //visible operation
    visibleButton = new JButton("Make visible");
    toolBar.add(visibleButton);

    //invisible operation
    invisibleButton = new JButton("Make invisible");
    toolBar.add(invisibleButton);

    //execute script button
    executeScriptButton = new JButton("Execute script");
    script.add(executeScriptButton, BorderLayout.CENTER);

    //Selection lists
    selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Selection lists"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    add(selectionListPanel, BorderLayout.EAST);

    setFocusable(true);
    requestFocus();

    setVisible(true);

    //Makes the frame full screen.
    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  @Override
  public void registerViewEventListener(ActionListener e) {
    ImageUtil.checkNull(e, "action listener");
    JButton[] buttons = new JButton[]{blurButton, sharpenButton, grayscaleButton, sepiaButton,
        mosaicButton, saveButton, openButton, checkerboardButton, exportButton, loadButton,
        resetButton, removeButton, createButton, visibleButton, invisibleButton,
        executeScriptButton};

    JMenuItem[] menuItems = new JMenuItem[]{blurItem, sharpenItem, grayscaleItem, sepiaItem,
        mosaicItem, saveItem, openItem, checkItem, exportItem, loadItem, resetItem, removeItem,
        createItem, visItem, invisItem};

    for (JButton button : buttons) {
      button.addActionListener(e);
    }

    for (JMenuItem item : menuItems) {
      item.addActionListener(e);
    }

    scriptItem.addActionListener(e);
  }

  @Override
  public void registerViewListEventListener(ListSelectionListener e) {
    ImageUtil.checkNull(e, "list selection listener");
    //layer selection list
    listOfLayers.addListSelectionListener(e);
  }

  @Override
  public void registerMenuListener(MenuListener e) {
    menuOfEverything.addMenuListener(e);
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this, message, "Error",
        JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void renderErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public String getChosenCurrent() {
    if (this.listOfLayers.getSelectedValue() != null) {
      JOptionPane.showMessageDialog(this,
          "The current layer's name is " + this.listOfLayers.getSelectedValue(),
          "Selected Layer", JOptionPane.PLAIN_MESSAGE);
      return this.listOfLayers.getSelectedValue();
    } else {
      //TODO: we want this to throw exception here no?
      return null;
    }
  }

  @Override
  public void reloadImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
  }

  @Override
  public void resetSelection() {
    selectionListPanel.removeAll();
    dataLayerName.removeAllElements();
    setVisible(true);
  }

  @Override
  public void removeElement(String removingLayer) {
    dataLayerName.removeElement(removingLayer);
    selectionListPanel.add(listOfLayers);
    setVisible(true);
  }

  @Override
  public void addElement(String name) {
    dataLayerName.addElement(name);
    selectionListPanel.add(listOfLayers);
    setVisible(true);
  }

  @Override
  public String getPath(boolean open) {
    final JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retValue;
    if (open) {
      retValue = fileChooser.showOpenDialog(this);
    } else {
      retValue = fileChooser.showSaveDialog(this);
    }

    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      return null;
    }
  }

  @Override
  public ExportPair export() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String[] options = {"jpg", "png", "ppm"};
      int retvalue2 = JOptionPane.showOptionDialog(this, "Please choose "
              + "a file type", "Options", JOptionPane.YES_OPTION,
          JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
      return new ExportPair(f.getAbsolutePath(), options[retvalue2]);
    }
    return null;
  }

  @Override
  public void updateSelection(List<ILayer> layers) {
    for (ILayer layer : layers) {
      dataLayerName.addElement(layer.getName());
      selectionListPanel.add(listOfLayers);
    }
  }

  @Override
  public Color getColor() {
    JLabel colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true); //so that background color shows up
    colorChooserDisplay.setBackground(Color.WHITE);
    add(colorChooserDisplay);
    return JColorChooser.showDialog(this, "Choose a color", Color.WHITE);
  }

  @Override
  public String getLoadedPhoto() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG & PNG & PPM files", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }

  @Override
  public String getScriptFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "txt files", "txt");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }
}
