Design Changes From Second Assignment
In order to extend our abstracted command function objects to the new GUIController 
to apply to their respective action events from the buttons, we made all of the command 
function objects extend the AbstAction rather than the AbstPhotoCommand. The AbstAction extends
the AbstPhotoCommand, which keeps all the original functionality of the commands intact, but also
introduces the applyAction() method for the GUIController based on the button pressed that correlates
to the command function objects. 

In the LayerPhotoController, we had a constructor that took in an Appendable, which made the ILayerModelImpl
interface tightly coupled to the ITextualView (the basic one, not the GUIController). Therefore, we deleted it
and created a constructor that simply gave it a view. 

The exportLayeredPhoto method was improved by being able to work with other directories besides the directory for this
project. Its original uses are still intact. 


Additions From Assignment 7

An IGUIController interface is implemented by the new GUIController. The interface extends the original IPhotoController, which 
allows for the GUI Controller to also act as a simple controller. The interface implements two new methods, run() which runs 
the GUI View, and handleReloadEvent(), which allows for the GUI View photo to be correctly updated with the topmost layer's photo (if any).
The GUIController is abstracted in the same way as the original controller, which is that it has a map of all the action commands correlated
to a command function object. The GUIController acts as the ActionEvent and ListSelection listener, and directly implements the necessary changes to
the view and model based off of the action events it is given as a listener.

As stated above, the function objects that handle the commands in the original simple controller now have an applyAction() method
that allows for the same command function objects to be used for the GUI Controller. New commands have been added: executeScriptCommand(), which allows
for the user in the GUI to load and execute a script for the model, the mosaicCommand, which allows for the user to call the mosaic filter on a layer's photo.


How To Use
MODEL
The model (ModelImpl) from the week1 package is able to manipulate a list of photos by adding the blur, sharpen, grayscale, or sepia filters. Additionally, it can create
a checkerboard image, and load/save photos that are of PPM format type.

The model (LayerModelImpl) from the week2 package is able to manipulate a collection of layers by loading/exporting photos from layers, making layers visible or invisible, adding
filters to the photos of the layers, saving/loading entire directories that have layers, and any operations from the week1 package. The model works for the ITextualView
implementing class views. 

The model (LayerModelImpl2) from the week3 package is able to do everything the model from the week2 package can do, except its contents can be shown through a GIU Controller and view.

CONTROLLER/VIEWS
The controller (LayerPhotoController) from assignment 6 is able to process model commands from a file, line by line, or interactively through the System.in stream. The controller 
can then be coupled with the ITextualView, which renders a simple message regarding the success or failure of the command. 

The controller (GUIController) from assignment 7 is able to process model commands through a file that holds a script when a button in the GUI view is called, or it can manipulate commands 
based upon the action commands that are sent through the buttons of the GUI view. This controller can be coupled to the IFrameView views, which are classes that extend the JFrame and allow
for the button calls to occur for the user as the controller listens to the events. 

All classes above are complete, yet available for new commands/filters to be added if necessary.

Citations
New York - https://unsplash.com/photos/WTPp4wgourk
