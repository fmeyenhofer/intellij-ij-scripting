import ij.IJ;

import net.imagej.ImageJ;

import org.scijava.command.Command;
import org.scijava.display.DisplayService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.script.ScriptService;
import org.scijava.ui.UIService;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;


/**
 * @author Felix Meyenhofer
 */
@Plugin(type = Command.class, menuPath = "Plugins > Open IntelliJ scripts")
public class LaunchImageJ implements Command {

    @Parameter
    private UIService ui;

    @Parameter
    private ScriptService script;

    @Parameter
    private LogService log;

    @Parameter
    private DisplayService disp;


    @Override
    public void run() {
        ScriptSelectionDialog dialog = ScriptSelectionDialog.createAndShow();
        String mode = dialog.getMode();

        for (File file : dialog.getSelection()) {
            try {
                IJ.open(file.getAbsolutePath());

                if (mode.equals("run")) {
                    script.run(file, true);
                }
            } catch (IOException e) {
                log.error("could not open " + file);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ImageJ ij = new ImageJ();
        ij.ui().showUI();
        ij.command().run(LaunchImageJ.class, true);
    }
}
