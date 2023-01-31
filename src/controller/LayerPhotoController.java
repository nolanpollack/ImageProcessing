package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.week1.IPhoto;
import model.week1.ImageCreator.PhotoType;
import model.week2.ILayer;
import model.week2.ILayerModel;
import model.week2.Layer;
import view.IPhotoView;

/**
 * Represents a controller for a photo processing application.
 */
public class LayerPhotoController implements IPhotoController {

  /**
   * The model for the application.
   */
  protected ILayerModel model;

  /**
   * The view for the application.
   */
  protected final IPhotoView view;

  /**
   * A map of commands and the string used to call them.
   */
  protected Map<String, IPhotoCommand> commands;


  /**
   * Initializes a new controller.
   *
   * @param model the model for the program.
   * @param view  the view for the program.
   */
  public LayerPhotoController(ILayerModel model, IPhotoView view) {
    ImageUtil.checkNull(model, "model");
    ImageUtil.checkNull(view, "view");
    this.model = model;
    this.view = view;
    this.commands = setCommands();
  }

  /**
   * Sets all supported commands by placing the string used to call them and their function object
   * in a map.
   *
   * @return a Map containing the string to be used to call the command and the function object
   *     representing the command.
   */
  protected Map<String, IPhotoCommand> setCommands() {
    Map<String, IPhotoCommand> commands = new HashMap<>();
    commands.put("export", new ExportCommand(model));
    commands.put("create", new CreateCommand(model));
    commands.put("current", new CurrentCommand(model));
    commands.put("load", new LoadCommand(model));
    commands.put("open", new OpenCommand(model));
    commands.put("remove", new RemoveCommand(model));
    commands.put("save", new SaveCommand(model));
    commands.put("visible", new VisibleCommand(model));
    commands.put("invisible", new InvisibleCommand(model));
    commands.put("sepia", new SepiaCommand(model));
    commands.put("grayscale", new GrayscaleCommand(model));
    commands.put("sharpen", new SharpenCommand(model));
    commands.put("blur", new BlurCommand(model));
    commands.put("checkerboard", new CheckerBoardCommand(model));
    commands.put("reset", new ResetCommand(model));
    return commands;
  }


  /**
   * Runs the script for this IController from a given file.
   *
   * @param filename the file the script is being read from. Must be a .txt file.
   */
  @Override
  public void scriptWFile(String filename) {
    File file = new File(filename);

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(file));
      try {
        String st = br.readLine();
        Status status;
        while (st != null) {
          try {
            List<String> list = Arrays.asList(st.split(" "));
            List<String> actualList = ignoreComments(list);
            if (actualList.size() != 0) {
              if (actualList.get(0).equalsIgnoreCase("q")) {
                view.renderMessage("Process quit");
                return;
              }
            }
            status = runCommand(actualList);
          } catch (IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
            return;
          }
          if (status == Status.INVALID_INPUT) {
            view.renderMessage("invalid inputs for the commands");
            return;
          } else if (status == Status.QUIT) {
            view.renderMessage("Process quit");
            return;
          } else {
            st = br.readLine();
          }
        }
        view.renderMessage("End of file\n");
        return;
      } catch (IOException e) {
        throw new IllegalStateException("could not read next line");
      }
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("could not create reader for file");
    }
  }

  /**
   * Processes a list of inputs and removes any comments starting with "#".
   *
   * @param input the list of inputs being processed.
   * @return a new list of strings representing inputs without anything following the input "#".
   */
  private List<String> ignoreComments(List<String> input) {
    List<String> newInput = new ArrayList<>();
    for (String s : input) {
      if (s.contains("#")) {
        for (int i = 0; i < input.indexOf(s); i++) {
          newInput.add(input.get(i));
        }
        return newInput;
      }
    }
    return input;
  }

  /**
   * Runs a command.
   *
   * @param input the list of inputs representing a command and the data being given to it.
   * @return a Status representing the status of the command. Will return SUCCESS if the input is
   *     blank, INVALID_INPUT if the given command is not supported, or a Status representing the
   *     given command if it operates successfully.
   */
  protected Status runCommand(List<String> input) {
    if (input.size() == 0) {
      //just skip the line since it is empty
      return Status.SUCCESS;
    }
    IPhotoCommand command = commands.getOrDefault(input.get(0), null);
    if (command == null) {
      return Status.INVALID_INPUT;
    }

    StatusResult result;
    try {
      result = command.run(input);
    } catch (IllegalArgumentException e) {
      return Status.INVALID_INPUT;
    }
    if (result.getFirst() == Status.SUCCESS) {
      view.renderMessage("the " + command.toString() + " command was successful\n");
    } else if (result.getFirst() == Status.EXPORT) {
      this.exportLayeredPhoto(result.getSecond(), result.getThird());
      view.renderMessage("the save command was successful\n");
    } else if (result.getFirst() == Status.OPEN) {
      model.changeMultiLayeredImage(
          this.importLayeredPhoto(result.getSecond()));
      view.renderMessage("the open command was successful\n");
    }
    return result.getFirst();
  }

  @Override
  public void scriptInteractive(Readable rd) {
    Scanner scanner = new Scanner(rd);

    while (scanner.hasNextLine()) {
      String input = scanner.nextLine();
      List<String> listedInput = Arrays.asList(input.split(" "));
      List<String> newInput = ignoreComments(listedInput);
      try {
        if (newInput.get(0).equalsIgnoreCase("q")) {
          view.renderMessage("Process quit");
          return;
        }
        Status status = runCommand(newInput);
        if (status == Status.INVALID_INPUT) {
          view.renderMessage("invalid inputs for the commands\n");
        } else if (status == Status.QUIT) {
          view.renderMessage("Process quit");
          return;
        }
      } catch (IllegalArgumentException e) {
        view.renderMessage(e.getMessage() + "\n");
      }
    }
    view.renderMessage("process finished" + "\n");
    return;
  }

  @Override
  public List<ILayer> importLayeredPhoto(String direcLoc) {
    ExtendedImageUtil.checkNull(direcLoc, "direcLoc");
    Scanner sc;
    List<ILayer> answer = new ArrayList<>();
    try {
      String[] split = direcLoc.split("\\\\");
      sc = new Scanner(new FileInputStream(direcLoc + "\\" + split[split.length - 1] + ".txt"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(direcLoc + ".txt not found!");
    }
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      List<String> results = splitFileName(s);
      String name = results.get(0);
      String fileType = results.get(1);
      StringBuilder nameWithoutDirec = new StringBuilder();
      boolean pastDirec = false;
      for (int i = 0; i < name.length(); i++) {
        if (pastDirec) {
          nameWithoutDirec.append(name.charAt(i));
        } else if (name.charAt(i) == '/') {
          pastDirec = true;
        }
      }

      IPhoto photo = ExtendedImageUtil
          .readPhoto(name, PhotoType.SIMPLE_PHOTO, fileType);
      answer.add(new Layer(nameWithoutDirec.toString(), photo, true));
    }
    return answer;
  }

  /**
   * Splits a file into a list containing the file name and its file type.
   *
   * @param s the location of the file.
   * @return a list containing the file's name and type.
   */
  public static List<String> splitFileName(String s) {
    StringBuilder name = new StringBuilder();
    StringBuilder fileType = new StringBuilder();
    boolean fileTypeReached = false;
    for (int i = 0; i < s.length(); i++) {
      if (!fileTypeReached) {
        if (s.charAt(i) == '.') {
          fileTypeReached = true;
        } else {
          name.append(s.charAt(i));
        }
      } else {
        fileType.append(s.charAt(i));
      }
    }
    return new ArrayList<String>(Arrays.asList(name.toString(), fileType.toString()));
  }

  @Override
  public void exportLayeredPhoto(String name, String format) {
    ExtendedImageUtil.checkNull(name, "name");
    ExtendedImageUtil.checkNull(format, "format");
    File saveLoc = new File(name);
    saveLoc.mkdirs(); //creates the directory
    String[] split = name.split("\\\\");
    File textLoc = new File(name + "\\" + split[split.length - 1] + ".txt");
    try {
      textLoc.createNewFile();
      FileWriter writer = new FileWriter(name + "\\" + split[split.length - 1] + ".txt");

      for (ILayer layer : model.getAllLayers()) {
        if (layer.getPhoto() == null) {
          System.out.println(layer.getName());
        } else {
          ExtendedImageUtil.writePhoto(layer.getName(), layer.getPhoto(), format, saveLoc);
          writer.write(name + "/" + layer.getName() + "." + format + "\n");
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("could not export file");
    }

  }

  @Override
  public List<String> availableCommands() {
    List<String> answer = new ArrayList<>();
    for (Map.Entry<String, IPhotoCommand> entry : commands.entrySet()) {
      answer.add(entry.getKey());
    }
    return answer;
  }
}
