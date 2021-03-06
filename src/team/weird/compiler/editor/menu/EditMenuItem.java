package team.weird.compiler.editor.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.compiler.editor.implement.EditAction;
/**
 * 
 * @author Qian_Yang
 * @copyright All rights reserved by Qian_Yang.
 */
public class EditMenuItem {
	private JMenuBar menuBar = null; 
	private JTabbedPane contentPane = null;
	private JFrame frame = null;
	public EditMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame frame){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.frame = frame;
	}
	public void initEditMenuItem(){
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		EditAction undoTxt =new EditAction("Undo",contentPane, frame);
		EditAction redoTxt =new EditAction("Redo",contentPane, frame);
		EditAction cutTxt = new EditAction("Cut", contentPane, frame);
		EditAction copyTxt = new EditAction("Copy", contentPane, frame);
		EditAction pasteTxt = new EditAction("Paste", contentPane, frame);
		EditAction selectAll =new EditAction("Select All",contentPane, frame);
		EditAction delete =new EditAction("Delete",contentPane, frame);
		EditAction find =new EditAction("Find",contentPane, frame);
		EditAction replace =new EditAction("Replace",contentPane, frame);
		JMenuItem undoItem = new JMenuItem(undoTxt);
		JMenuItem redoItem = new JMenuItem(redoTxt);
		JMenuItem cutItem = new JMenuItem(cutTxt);
		JMenuItem copyItem = new JMenuItem(copyTxt);
		JMenuItem pasteItem = new JMenuItem(pasteTxt);
		JMenuItem selectItem = new JMenuItem(selectAll);
		JMenuItem deleteItem = new JMenuItem(delete);
		JMenuItem findItem = new JMenuItem(find);
		JMenuItem replaceItem = new JMenuItem(replace);
		undoItem.setActionCommand("Undo");
		redoItem.setActionCommand("Redo");
		cutItem.setActionCommand("Cut");
		copyItem.setActionCommand("Copy");
		pasteItem.setActionCommand("Paste");
		selectItem.setActionCommand("Select All");
		deleteItem.setActionCommand("Delete");
		findItem.setActionCommand("Find");
		replaceItem.setActionCommand("Replace");
		undoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		redoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		cutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		copyItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		selectItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
		deleteItem.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		findItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.addSeparator();
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.addSeparator();
		editMenu.add(selectItem);
		editMenu.add(deleteItem);
		editMenu.add(findItem);
		editMenu.add(replaceItem);
	}
}
