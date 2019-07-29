package org.muzi.open.helper.ui;

import com.intellij.ide.util.BrowseFilesListener;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import org.muzi.open.helper.config.FileType;
import org.muzi.open.helper.model.KV;
import org.muzi.open.helper.util.PopUtil;
import org.muzi.open.helper.util.StringUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-18 10:56
 * @description:
 */
public abstract class BaseUI extends JFrame {
    private static final long serialVersionUID = 8262742911829403016L;
    private Project project;

    public BaseUI() throws HeadlessException {
    }

    public BaseUI(Project project) throws HeadlessException {
        this.project = project;
    }

    abstract protected JPanel panel();

    protected Project project() {
        return project;
    }


    protected void initUI() {

    }

    protected void initBind() {

    }

    public void show(int width, int height) {
        setContentPane(panel());
        setResizable(false);
        setAlwaysOnTop(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        initUI();
        initBind();
    }

    public void close() {
        dispose();
    }


    protected void cancel() {
        close();
    }


    /**
     * bind cancel for JButton
     *
     * @param btn
     * @param ui
     */
    protected void bindCancel(JButton btn, BaseUI ui) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.close();
            }
        });
    }

    /**
     * bind select opentions for JComboBox
     *
     * @param comboBox
     * @param options
     * @param reverse
     */
    protected void bindSelect(JComboBox comboBox, List<KV> options, boolean reverse) {
        for (KV option : options)
            if (reverse)
                comboBox.addItem(option.val());
            else
                comboBox.addItem(option.key());
    }

    protected void bindFileBrowser(final JTextField txt, final String title, final String description, final FileType... fileTypes) {
        int _type = FileType.FILE.value();
        if (null != fileTypes && fileTypes.length > 0)
            for (FileType fileType : fileTypes)
                _type += fileType.value();
        final int type = _type;
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(FileType.isFile(type), FileType.isFolder(type), FileType.isJar(type), true, true, true);
                    new BrowseFilesListener(txt, title, description, fileChooserDescriptor).actionPerformed(null);
                }
            }
        });
    }

    /**
     * bind click project to open file dialog on JTextField
     *
     * @param txt
     * @param parent
     * @param title
     * @param currentPath
     * @param isDirectory
     * @param ext         file extension
     */
    protected void bindTxtClickFileDialog(JTextField txt, Component parent, String title, String currentPath, boolean isDirectory, String ext) {
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    openFileDialog(parent, txt, title, currentPath, isDirectory, ext);
            }
        });
    }

    /**
     * show file dialog
     *
     * @param parent
     * @param txt
     * @param title
     * @param currentPath base path
     * @param isDirectory file or directory
     * @param ext         file extension
     */
    protected void openFileDialog(Component parent, JTextField txt, String title, String currentPath, boolean isDirectory, String ext) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileHidingEnabled(false);
        jfc.setAcceptAllFileFilterUsed(true);

        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return isDirectory == f.isDirectory() && (null == ext || f.getName().endsWith(ext));
            }

            @Override
            public String getDescription() {
                return isDirectory ? "Directory" : (null == ext ? "File" : "*." + ext);
            }
        });
        jfc.setBounds(0, 0, 100, 100);
        File currentDir = null;

        if (StringUtil.isEmpty(currentPath))
            currentDir = new File(project().getBasePath());
        else
            currentDir = new File(currentPath);

        jfc.setCurrentDirectory(currentDir);
        jfc.setDialogTitle(title);
        jfc.setFileSelectionMode(isDirectory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        jfc.showOpenDialog(parent);
        File file = jfc.getSelectedFile();
        if (null != file)
            txt.setText(file.getAbsolutePath());
    }

    protected void bindCopy(JButton button, JTextComponent txtComp) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(txtComp.getText());
                PopUtil.info(button, "copied");

            }
        });

    }

    protected void copy(String writeMe) {
        Clipboard clip = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

}
