import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * @author Felix Meyenhofer
 */
public class ScriptSelectionDialog extends JPanel implements ActionListener {

    private final String TYPE_SELECTOR_NAME = "Script type";
    private final String SCRIPT_SELECTOR_NAME = "Available scripts";
    private final String MODE_SELECTOR_NAME = "Mode";
    
    private final String OK_BUTTON_NAME = "OK";
    private final String CANCEL_BUTTON_NAME = "Cancel";

    private JList<String> scriptSelector;
    private JComboBox<String> typeSelector;
    private JComboBox<String> modeSelector;

    private static Map<String, String> type2extension =
        Collections.unmodifiableMap(new HashMap<String, String>()
        {{
            put("groovy", ".groovy");
            put("jython", ".py");
        }});


    private ScriptSelectionDialog() {
        super();

        modeSelector = new JComboBox<>(new String[]{"open", "run"});
        modeSelector.setName(MODE_SELECTOR_NAME);

        typeSelector = new JComboBox<>(type2extension.keySet().toArray(new String[0]));
        typeSelector.setName(TYPE_SELECTOR_NAME);
        typeSelector.addActionListener(this);

        scriptSelector = new JList<>();
        scriptSelector.setVisibleRowCount(5);
        scriptSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scriptSelector.setName(SCRIPT_SELECTOR_NAME);
        JScrollPane scrollPane = new JScrollPane(scriptSelector);

        JButton button2 = new JButton(OK_BUTTON_NAME);
        button2.setName(OK_BUTTON_NAME);
        button2.addActionListener(this);
        JButton button1 = new JButton(CANCEL_BUTTON_NAME);
        button1.setName(CANCEL_BUTTON_NAME);
        button1.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        panel.add(button1);
        panel.add(button2);

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(new JLabel(TYPE_SELECTOR_NAME), new GridBagConstraints(0, 0,
                1, 1,
                0, 0,
                GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0),
                0, 0));
        this.add(typeSelector, new GridBagConstraints(1,0,
                1,1,
                0,0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(0,0,0, 0),
                0, 0));
        this.add(new JLabel(SCRIPT_SELECTOR_NAME), new GridBagConstraints(0,1,
                1,1,
                0,0,
                GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0, 5),
                0, 0));
        this.add(scrollPane, new GridBagConstraints(1,1,
                1,1,
                1,1,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0, 0),
                0, 0));
        this.add(new JLabel(MODE_SELECTOR_NAME), new GridBagConstraints(0,2,
                1,1,
                0,0,
                GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0, 0),
                0, 0));
        this.add(modeSelector, new GridBagConstraints(1, 2,
                1, 1,
                0, 0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0),
                0, 0));
        this.add(panel, new GridBagConstraints(1, 5,
                1, 1,
                1, 0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0),
                0, 0));
    }

    private File getScriptDirectory(String type) {
        return new File("src/main/" + type).getAbsoluteFile();
    }

    private String getType() {
        return (String) typeSelector.getSelectedItem();
    }

    String getMode() {
        return (String) modeSelector.getSelectedItem();
    }

    List<File> getSelection() {
        String type = getType();
        File dir = getScriptDirectory(type);

        List<File> paths = new ArrayList<>();
        for (String name : scriptSelector.getSelectedValuesList()) {
            paths.add(new File(dir, name));
        }

        return paths;
    }

    static ScriptSelectionDialog createAndShow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        ScriptSelectionDialog dialog = new ScriptSelectionDialog();
        dialog.update();

        JDialog frame = new JDialog();
        frame.setTitle("Select a SectionDataset");
        frame.add(dialog);
        frame.setModal(true);
        frame.setSize(new Dimension(500, 250));
        frame.setLocation(dim.width / 2 - 250, dim.height / 2 - 125);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.pack();

        return dialog;
    }

    private void updateScriptList() {
        String type = getType();
        File dir = getScriptDirectory(type);
        String extension = type2extension.get(type);

        File[] paths = dir.listFiles(path -> path.getAbsolutePath().endsWith(extension));

        if (paths == null) {
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
            JOptionPane.showMessageDialog(dialog, "No scripts found in " + dir);
            return;
        }

        DefaultListModel<String> list = new DefaultListModel<>();
        for (File file : paths) {
            list.addElement(file.getName());
        }

        scriptSelector.removeAll();
        scriptSelector.setModel(list);
        scriptSelector.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update(((Component) e.getSource()).getName());
    }

    private void update() {
        update(this.TYPE_SELECTOR_NAME);
    }

    private void update(String control) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);

        switch (control) {
            case TYPE_SELECTOR_NAME:
                updateScriptList();
                break;

            case SCRIPT_SELECTOR_NAME:
                break;

            case MODE_SELECTOR_NAME:
                break;

            case OK_BUTTON_NAME:
                dialog.dispose();
                break;

            case CANCEL_BUTTON_NAME:
                scriptSelector.setModel(new DefaultListModel<>());
                dialog.dispose();
                break;
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ScriptSelectionDialog dialog = createAndShow();
//                System.out.println(dialog.getType());
//                System.out.println(dialog.getMode());
//                for (File file : dialog.getSelection()) {
//                    System.out.println(file.getAbsolutePath());
//                }
//                System.exit(0);
//            }
//        });
//    }
}
