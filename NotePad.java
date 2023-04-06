import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import javax.swing.undo.*; 
import java.util.*;
import java.applet.Applet;  
import java.awt.Font;  
import java.awt.Graphics;  
class NotePad implements KeyListener
{
	JFrame jframe,jframe_find,jframe_find_next,jframe_find_previous,jframe_color;
	JTextField jtextfield_find,jtextfield_find_next,jtextfield_find_previous;
	JLabel jlabel_find,jlabel_find_next,jlabel_find_previous;
	JButton jbutton_find,jbutton_find_next,jbutton_find_previous;
	JTextArea jtextarea;
	JMenuBar jmenubar;
	JMenu jmenu_file;
	JMenu jmenu_edit;
	JMenu jmenu_format;
	JMenu jmenu_font;
	JMenuItem jmenuitem_new,jmenuitem_open,jmenuitem_save,jmenuitem_save_as,jmenuitem_page_setup,jmenuitem_print,jmenuitem_exit;
	JMenuItem jmenuitem_undo,jmenuitem_cut,jmenuitem_copy,jmenuitem_paste,jmenuitem_delete,jmenuitem_find,jmenuitem_find_next,jmenuitem_find_previous,jmenuitem_replace,jmenuitem_goto,jmenuitem_select_all,jmenuitem_time_date;
	JMenuItem jmenuitem_color,jmenuitem_font_size_increase,jmenuitem_font_size_decrease;
	JMenuItem jmenuitem_arial,jmenuitem_times_new_roman,jmenuitem_courier,jmenuitem_serif;

	JCheckBoxMenuItem jcheckboxmenuitem_editable;

	JColorChooser jcolorchooser;

	JButton jbutton_foreground,jbutton_background;
	JScrollPane jscrollpane;
	String fullPath="";

	//undo manager 
	UndoManager undomanager = new UndoManager();

	//date
	Date date = new Date();

	//for find next
	int startingIndex;

	//Jcolor chooser previous saved color files path
	String foreground_color_path = "D://Java\\Java July\\Java Swings\\foregroundColorFile.txt";
	String background_color_path = "D://Java\\Java July\\Java Swings\\backgroundColorFile.txt";

	//file class object
	File file_foreground_info = new File(foreground_color_path);
	File file_background_info = new File(background_color_path);

	//default font size
	int default_font_size=20;
	NotePad()
	{
		//main frame
		jframe = new JFrame("Untitled");
		jtextarea = new JTextArea();
		jmenubar = new JMenuBar();
		//menu
		jmenu_file = new JMenu("File");
		jmenu_edit = new JMenu("Edit");
		jmenu_format = new JMenu("Format");
		jmenu_font = new JMenu("Font");
		//jframe_find
		jframe_find = new JFrame("Find");
		jframe_find.setVisible(false);
		jlabel_find = new JLabel("Find What");
		jtextfield_find = new JTextField(20);
		jbutton_find = new JButton("Find");

		//jframe_find
		jframe_find_next = new JFrame("Find Next");
		jframe_find_next.setVisible(false);
		jlabel_find_next = new JLabel("Find Next What");
		jtextfield_find_next = new JTextField(20);
		jbutton_find_next = new JButton("Find Next");

		//jmenu items for file
		jmenuitem_new = new JMenuItem("New");
		jmenuitem_open = new JMenuItem("Open");
		jmenuitem_save = new JMenuItem("Save");
		jmenuitem_save_as = new JMenuItem("Save as");
		jmenuitem_page_setup = new JMenuItem("Page setup");
		jmenuitem_print = new JMenuItem("Print");
		jmenuitem_exit = new JMenuItem("Exit");

		//for edit option checkbox and items
		jcheckboxmenuitem_editable = new JCheckBoxMenuItem("Editable");
		jcheckboxmenuitem_editable.setSelected(true);//by default editable
		jmenuitem_undo = new JMenuItem("Undo");
		jmenuitem_cut = new JMenuItem("Cut");
		jmenuitem_copy = new JMenuItem("Copy");
		jmenuitem_paste = new JMenuItem("Paste");
		jmenuitem_delete = new JMenuItem("Delete");
		jmenuitem_find = new JMenuItem("Find");
		jmenuitem_find_next = new JMenuItem("Find Next");
		jmenuitem_find_previous = new JMenuItem("Find Previous");
		jmenuitem_replace = new JMenuItem("Replace");
		jmenuitem_goto = new JMenuItem("Go To");
		jmenuitem_select_all = new JMenuItem("Select All");
		jmenuitem_time_date = new JMenuItem("Time/Date");

		startingIndex = 0;//for find next

		//for format items
		jmenuitem_color = new JMenuItem("Color");
     	jmenuitem_font_size_increase = new JMenuItem("Font size ++");
     	jmenuitem_font_size_decrease = new JMenuItem("Font size --");

     	//for font item
		jmenuitem_arial = new JMenuItem("Arial");
		jmenuitem_times_new_roman = new JMenuItem("Times New Roman");
		jmenuitem_courier = new JMenuItem("Courier");
		jmenuitem_serif = new JMenuItem("Serif");
     	//text area set in scroll pane so we can't add textarea in jframe
     	jscrollpane = new JScrollPane(jtextarea);

		//set setMnemonic which active when key press with ALT and requried jmenuitem is open
		//for menu bar
		jmenu_file.setMnemonic(KeyEvent.VK_F);
		jmenu_edit.setMnemonic(KeyEvent.VK_T);
		jmenu_format.setMnemonic(KeyEvent.VK_M);

		//for file menu
		jmenuitem_new.setMnemonic(KeyEvent.VK_N);
		jmenuitem_open.setMnemonic(KeyEvent.VK_O);
		jmenuitem_save.setMnemonic(KeyEvent.VK_S);
		jmenuitem_save_as.setMnemonic(KeyEvent.VK_E);
		jmenuitem_page_setup.setMnemonic(KeyEvent.VK_G);
		jmenuitem_print.setMnemonic(KeyEvent.VK_P);
		jmenuitem_exit.setMnemonic(KeyEvent.VK_X);

		//for edit menu
		jcheckboxmenuitem_editable.setMnemonic(KeyEvent.VK_B);
		jmenuitem_undo.setMnemonic(KeyEvent.VK_Z);
		jmenuitem_cut.setMnemonic(KeyEvent.VK_U);
    	jmenuitem_copy.setMnemonic(KeyEvent.VK_C);
    	jmenuitem_paste.setMnemonic(KeyEvent.VK_A);
    	jmenuitem_delete.setMnemonic(KeyEvent.VK_D);
    	jmenuitem_find.setMnemonic(KeyEvent.VK_I);
    	jmenuitem_find_next.setMnemonic(KeyEvent.VK_H);
    	jmenuitem_find_previous.setMnemonic(KeyEvent.VK_R);
    	jmenuitem_replace.setMnemonic(KeyEvent.VK_Q);
    	jmenuitem_goto.setMnemonic(KeyEvent.VK_J);
    	jmenuitem_select_all.setMnemonic(KeyEvent.VK_A);
    	jmenuitem_time_date.setMnemonic(KeyEvent.VK_Y);

    	//for format menu
	    jmenuitem_color.setMnemonic(KeyEvent.VK_L);
	    //KeyStroke is used to make shortcut which are activated when we press key with CTRL
		//for menu control shortcut are not define so it's good to use Mnemonic for menu

	    //for file menu
		KeyStroke keystroke_new = KeyStroke.getKeyStroke("control N");
		jmenuitem_new.setAccelerator(keystroke_new);
		KeyStroke keystroke_open = KeyStroke.getKeyStroke("control O");
		jmenuitem_open.setAccelerator(keystroke_open);
		KeyStroke keystroke_save = KeyStroke.getKeyStroke("control S");
		jmenuitem_save.setAccelerator(keystroke_save);
		KeyStroke keystroke_save_as = KeyStroke.getKeyStroke("control shift S");
		jmenuitem_save_as.setAccelerator(keystroke_save_as);
		KeyStroke keystroke_page_setup = KeyStroke.getKeyStroke("control shift P");
		jmenuitem_page_setup.setAccelerator(keystroke_page_setup);
		KeyStroke keystroke_print = KeyStroke.getKeyStroke("control P");
		jmenuitem_print.setAccelerator(keystroke_print);
		KeyStroke keystroke_exit = KeyStroke.getKeyStroke("control K");
		jmenuitem_exit.setAccelerator(keystroke_exit);

		//for edit menu
		KeyStroke keystroke_editable = KeyStroke.getKeyStroke("control D");
		jcheckboxmenuitem_editable.setAccelerator(keystroke_editable);
		KeyStroke keystroke_undo = KeyStroke.getKeyStroke("control Z");
		jmenuitem_undo.setAccelerator(keystroke_undo);
		KeyStroke keystroke_cut = KeyStroke.getKeyStroke("control X");
		jmenuitem_cut.setAccelerator(keystroke_cut);
		KeyStroke keystroke_copy = KeyStroke.getKeyStroke("control C");
		jmenuitem_copy.setAccelerator(keystroke_copy);
		KeyStroke keystroke_paste = KeyStroke.getKeyStroke("control V");
		jmenuitem_paste.setAccelerator(keystroke_paste);
		KeyStroke keystroke_delete = KeyStroke.getKeyStroke("control shift D");
		jmenuitem_delete.setAccelerator(keystroke_delete);
		KeyStroke keystroke_find = KeyStroke.getKeyStroke("control F");
		jmenuitem_find.setAccelerator(keystroke_find);
		KeyStroke keystroke_find_next = KeyStroke.getKeyStroke("control shift F");
		jmenuitem_find_next.setAccelerator(keystroke_find_next);
		KeyStroke keystroke_find_previous = KeyStroke.getKeyStroke("control shift I");
		jmenuitem_find_previous.setAccelerator(keystroke_find_previous);
		KeyStroke keystroke_replace = KeyStroke.getKeyStroke("control H");
		jmenuitem_replace.setAccelerator(keystroke_replace);
		KeyStroke keystroke_goto = KeyStroke.getKeyStroke("control G");
		jmenuitem_goto.setAccelerator(keystroke_goto);
		KeyStroke keystroke_select_all = KeyStroke.getKeyStroke("control A");
		jmenuitem_select_all.setAccelerator(keystroke_select_all);	
		KeyStroke keystroke_time_date = KeyStroke.getKeyStroke("control T");
		jmenuitem_time_date.setAccelerator(keystroke_time_date);

		//for format menu
		KeyStroke keystroke_color = KeyStroke.getKeyStroke("control shift C");
		jmenuitem_color.setAccelerator(keystroke_color);		
     	//color chooser object
     	jcolorchooser = new JColorChooser();

     	//jcolor chooser buttons
     	jbutton_foreground = new JButton("Foreground Color");
     	jbutton_background = new JButton("Background Color");

     	//jscrollpane added
		jframe.add(jscrollpane);

		//jcolor chooser
		//all file items are added to jmenu_file
		jmenu_file.add(jmenuitem_new);
		jmenu_file.add(jmenuitem_open);
		jmenu_file.add(jmenuitem_save);
		jmenu_file.add(jmenuitem_save_as);
		jmenu_file.addSeparator();
		jmenu_file.add(jmenuitem_page_setup);
		jmenu_file.add(jmenuitem_print);
		jmenu_file.addSeparator();
		jmenu_file.add(jmenuitem_exit);

		//all edit items and checkboxes are added to jmenu_edit
		jmenu_edit.add(jcheckboxmenuitem_editable);
		jmenu_edit.add(jmenuitem_undo);
		jmenu_edit.addSeparator();
		jmenu_edit.add(jmenuitem_cut);
		jmenu_edit.add(jmenuitem_copy);
		jmenu_edit.add(jmenuitem_paste);
		jmenu_edit.add(jmenuitem_delete);
		jmenu_edit.addSeparator();
		jmenu_edit.add(jmenuitem_find);
		jmenu_edit.add(jmenuitem_find_next);
		jmenu_edit.add(jmenuitem_find_previous);
		jmenu_edit.add(jmenuitem_replace);
		jmenu_edit.add(jmenuitem_goto);
		jmenu_edit.addSeparator();
		jmenu_edit.add(jmenuitem_select_all);
		jmenu_edit.add(jmenuitem_time_date);

		//all format items are added to jmenu_format 
		jmenu_format.add(jmenuitem_color);
		jmenu_format.add(jmenuitem_font_size_increase);
		jmenu_format.add(jmenuitem_font_size_decrease);

		// all fonts are added to jemnu_font
		jmenu_font.add(jmenuitem_arial);
		jmenu_font.add(jmenuitem_times_new_roman);
		jmenu_font.add(jmenuitem_courier);
		jmenu_font.add(jmenuitem_serif);

		//all jmenus are added to jmenubar
		jmenubar.add(jmenu_file);
		jmenubar.add(jmenu_edit);
		jmenubar.add(jmenu_format); 
		jmenubar.add(jmenu_font);

		//finally jmenubar added to main jframe
		jframe.setJMenuBar(jmenubar);
		
		//prvious color styling applied
		try
		{
			if(file_foreground_info.exists())
			{
				FileReader filereader_foreground = new FileReader(foreground_color_path);
				int i;
				String foreground_color_info_temp = "";
				while((i = filereader_foreground.read())!=-1)
				{
					foreground_color_info_temp += ""+(char)i;
				}
				//System.out.println(foreground_color_info_temp);

				//extract rgb value from foreground_color_info_temp

				String foreground_color_info_temp_arr[] = foreground_color_info_temp.split(" ");
				int red = Integer.parseInt(foreground_color_info_temp_arr[0]);
				int green = Integer.parseInt(foreground_color_info_temp_arr[1]);
				int blue = Integer.parseInt(foreground_color_info_temp_arr[2]);

				Color color_foreground = new Color(red,green,blue);
				jtextarea.setForeground(color_foreground);
			}
			else
			{
				System.out.println("Foreground color info file is not exists do default styly applied");
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}

		try
		{
			if(file_background_info.exists())
			{
				FileReader filereader_background = new FileReader(background_color_path);
				int i;
				String background_color_info_temp = "";
				while((i = filereader_background.read())!=-1)
				{
					background_color_info_temp += ""+(char)i;
				}
				//System.out.println(background_color_info_temp);

				//extract rgb value from background_color_info_temp

				String background_color_info_temp_arr[] = background_color_info_temp.split(" ");
				int red = Integer.parseInt(background_color_info_temp_arr[0]);
				int green = Integer.parseInt(background_color_info_temp_arr[1]);
				int blue = Integer.parseInt(background_color_info_temp_arr[2]);

				Color color_background = new Color(red,green,blue);
				jtextarea.setBackground(color_background);
			}
			else
			{
				System.out.println("Background color info file is not exists do default styly applied");
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}


		//new file option
		jmenuitem_new.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													if(jtextarea.getText().length() > 0 && jframe.getTitle().charAt(0) == '*')
													{
														int result = JOptionPane.showConfirmDialog(jframe,"Do you want to save this file",null,JOptionPane.YES_NO_CANCEL_OPTION);
														
														if(result == JOptionPane.YES_OPTION)
														{
															if(!jframe.getTitle().equals("*Untitled"))
															{
																try
																{
																	//File file = new File(jframe.getTitle().substring(1,jframe.getTitle().length()));
																	FileOutputStream fileoutputstream = new FileOutputStream(fullPath);
																	String fileData = jtextarea.getText();
																	byte byteFileData[] = fileData.getBytes();

																	fileoutputstream.write(byteFileData);
																	fileoutputstream.close();

																	jtextarea.setText("");
																	jframe.setTitle("Untitled");

																}catch(Exception e)
																{
																	System.out.println(e);
																}
															}
															else 
															{
																JFileChooser jfilechooser = new JFileChooser();
																int choice = jfilechooser.showSaveDialog(jframe);	

																if(choice == jfilechooser.APPROVE_OPTION)
																{
																	try
																	{
																		File file = jfilechooser.getSelectedFile();
																		FileOutputStream fileoutputstream = new FileOutputStream(file);
																		String fileData = jtextarea.getText();
																		byte byteFileData[] = fileData.getBytes();

																		fileoutputstream.write(byteFileData);
																		fileoutputstream.close();

																		jtextarea.setText("");
																		jframe.setTitle("Untitled");
																	}catch(Exception e)
																	{
																		System.out.println(e);
																	}
																}
															}
																
														}
														else if(result == JOptionPane.NO_OPTION)
														{
															jtextarea.setText("");
															jframe.setTitle("Untitled");
														}
													}
													else
													{
														jtextarea.setText("");
														jframe.setTitle("Untitled");
													}
												}	
											}

									   );

		//open file option
		jmenuitem_open.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													if(jframe.getTitle().charAt(0) == '*')//current file is unsave
													{
														int result = JOptionPane.showConfirmDialog(jframe,"Do you want to save this file",null,JOptionPane.YES_NO_CANCEL_OPTION);
														
														if(result == JOptionPane.YES_OPTION)//user decided to save changes
														{
															if(jframe.getTitle().equals("*Untitled"))
															{
																//using filechooser user choose path where path need to save
																JFileChooser jfilechooser = new JFileChooser();
																int choice = jfilechooser.showSaveDialog(jframe);	

																if(choice == jfilechooser.APPROVE_OPTION)
																{
																	try
																	{
																		File file = jfilechooser.getSelectedFile();
																		FileOutputStream fileoutputstream = new FileOutputStream(file);
																		String fileData = jtextarea.getText();
																		byte byteFileData[] = fileData.getBytes();

																		fileoutputstream.write(byteFileData);
																		fileoutputstream.close();

																		//opening file which was chossen by the user
																		JFileChooser jfilechooser1 = new JFileChooser();
																		int choice1 = jfilechooser1.showOpenDialog(jframe);
																		if(choice1 == jfilechooser1.APPROVE_OPTION)
																		{
																			try
																			{
																				File file1 = jfilechooser1.getSelectedFile();
																				FileInputStream fileinputstream = new FileInputStream(file1);

																				int size = fileinputstream.available();
																				byte byteFileData1[] = new byte[size];
																				fileinputstream.read(byteFileData1);
																				String fileData1 = new String(byteFileData1);
																				jtextarea.setText(fileData1);
																				fullPath = file1.getAbsolutePath();
																				jframe.setTitle(file1.getName());
																				fileinputstream.close();
																			}catch(Exception e)
																			{
																				System.out.println(e);
																			}
																		}
																	}catch(Exception e)
																	{
																		System.out.println(e);
																	}
																}

															}
															else
															{
																//file already exist and only need to save on existing path
																try
																{
																	//File file = new File(jframe.getTitle().substring(1,jframe.getTitle().length()));
																	FileOutputStream fileoutputstream = new FileOutputStream(fullPath);
																	String fileData = jtextarea.getText();
																	byte byteFileData[] = fileData.getBytes();

																	fileoutputstream.write(byteFileData);
																	fileoutputstream.close();

																	jtextarea.setText("");
																	jframe.setTitle("Untitled");

																}catch(Exception e)
																{
																	System.out.println(e);
																}

																//opening file which was chossen by the user
																JFileChooser jfilechooser = new JFileChooser();
																int choice = jfilechooser.showOpenDialog(jframe);
																if(choice == jfilechooser.APPROVE_OPTION)
																{
																	try
																	{
																		File file = jfilechooser.getSelectedFile();
																		FileInputStream fileinputstream = new FileInputStream(file);

																		int size = fileinputstream.available();
																		byte byteFileData[] = new byte[size];
																		fileinputstream.read(byteFileData);
																		String fileData = new String(byteFileData);
																		jtextarea.setText(fileData);
																		fullPath = file.getAbsolutePath();
																		jframe.setTitle(file.getName());
																		fileinputstream.close();
																	}catch(Exception e)
																	{
																		System.out.println(e);
																	}
																}
															}
														}
														else if(result == JOptionPane.NO_OPTION)//user decided to not save changes 
														{
															//opening file which was chossen by the user
															JFileChooser jfilechooser = new JFileChooser();
															int choice = jfilechooser.showOpenDialog(jframe);
															if(choice == jfilechooser.APPROVE_OPTION)
															{
																try
																{
																	File file = jfilechooser.getSelectedFile();
																	FileInputStream fileinputstream = new FileInputStream(file);

																	int size = fileinputstream.available();
																	byte byteFileData[] = new byte[size];
																	fileinputstream.read(byteFileData);
																	String fileData = new String(byteFileData);
																	jtextarea.setText(fileData);
																	fullPath = file.getAbsolutePath();
																	jframe.setTitle(file.getName());
																	fileinputstream.close();
																}catch(Exception e)
																{
																	System.out.println(e);
																}
															}
														}
													}
													else//file is already saved so directly open new file as per user choice
													{
														JFileChooser jfilechooser = new JFileChooser();
														int choice = jfilechooser.showOpenDialog(jframe);
														if(choice == jfilechooser.APPROVE_OPTION)
														{
															try
															{
																File file = jfilechooser.getSelectedFile();
																FileInputStream fileinputstream = new FileInputStream(file);

																int size = fileinputstream.available();
																byte byteFileData[] = new byte[size];
																fileinputstream.read(byteFileData);
																String fileData = new String(byteFileData);
																jtextarea.setText(fileData);
																fullPath = file.getAbsolutePath();
																jframe.setTitle(file.getName());
																fileinputstream.close();
															}catch(Exception e)
															{
																System.out.println(e);
															}
														}
													} 
												}
											}
										);

		//save file option
		jmenuitem_save.addActionListener(
			                             	new ActionListener()
			                             	{
			                             		public void actionPerformed(ActionEvent ae)
			                             		{
			                             			if(jframe.getTitle().equals("Untitled") || jframe.getTitle().equals("*Untitled")) //file not exist so normal save file option
			                             			{
			                             				JFileChooser jfilechooser = new JFileChooser();
														int choice = jfilechooser.showSaveDialog(jframe);	

														if(choice == jfilechooser.APPROVE_OPTION)
														{
															try
															{
																File file = jfilechooser.getSelectedFile();
																FileOutputStream fileoutputstream = new FileOutputStream(file);
																String fileData = jtextarea.getText();
																byte byteFileData[] = fileData.getBytes();

																fileoutputstream.write(byteFileData);

																fullPath = file.getAbsolutePath();	
																jframe.setTitle(file.getName());

																fileoutputstream.close();
															}catch(Exception e)
															{
																System.out.println(e);
															}
														}	
			                             			}
			                             			else if(jframe.getTitle().charAt(0) == '*') //file exist but edited so changed need to save (&& title!= '*Untitled')
			                             			{
			                             				//file already exist and only need to save on existing path
														try
														{
															FileOutputStream fileoutputstream = new FileOutputStream(fullPath);
															String fileData = jtextarea.getText();
															byte byteFileData[] = fileData.getBytes();

															fileoutputstream.write(byteFileData);
															
															jframe.setTitle(jframe.getTitle().substring(1,jframe.getTitle().length()));

															fileoutputstream.close();
														}catch(Exception e)
														{
															System.out.println(e);
														}
			                             			}
			                             		}
			                             	}
										);
		//save as option
		jmenuitem_save_as.addActionListener(
												new ActionListener()
												{
													public void actionPerformed(ActionEvent ae)
													{
														JFileChooser jfilechooser = new JFileChooser();
														int choice = jfilechooser.showDialog(jframe,"save as");	

														if(choice == jfilechooser.APPROVE_OPTION)
														{
															try
															{
																File file = jfilechooser.getSelectedFile();
																FileOutputStream fileoutputstream = new FileOutputStream(file);
																String fileData = jtextarea.getText();
																byte byteFileData[] = fileData.getBytes();

																fileoutputstream.write(byteFileData);

																fullPath = file.getAbsolutePath();	
																jframe.setTitle(file.getName());

																fileoutputstream.close();
															}catch(Exception e)
															{
																System.out.println(e);
															}
														}	
													}
												}
										   );
		//page setup api
		jmenuitem_page_setup.addActionListener(
													new ActionListener()
													{
														public void actionPerformed(ActionEvent ae)
														{
															PrinterJob printerjob = PrinterJob.getPrinterJob();
															PageFormat pageformat = printerjob.pageDialog(printerjob.defaultPage());//page setup

														}
													}
											  );

		//print api
		jmenuitem_print.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													PrinterJob printerjob = PrinterJob.getPrinterJob();
													if(printerjob.printDialog())//show print page when print command active by pressing print button
													{
														try
														{
															printerjob.print();
														}catch(Exception e)
														{
															System.out.println(e);
														}
													}
												}
											}
										 );

		//exit file option
		jmenuitem_exit.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													//if data is modifiy
													if(jframe.getTitle().charAt(0) == '*')
													{
														//data modify and also file is not exist
														if(jframe.getTitle().equals("*Untitled"))
														{
															int result = JOptionPane.showConfirmDialog(jframe,"Do you want to save this file",null,JOptionPane.YES_NO_CANCEL_OPTION);
														
															if(result == JOptionPane.YES_OPTION)
															{
																
																JFileChooser jfilechooser = new JFileChooser();
																int choice = jfilechooser.showSaveDialog(jframe);	

																if(choice == jfilechooser.APPROVE_OPTION)
																{
																	try
																	{
																		File file = jfilechooser.getSelectedFile();
																		FileOutputStream fileoutputstream = new FileOutputStream(file);
																		String fileData = jtextarea.getText();
																		byte byteFileData[] = fileData.getBytes();

																		fileoutputstream.write(byteFileData);
																		fullPath = file.getAbsolutePath();
																		fileoutputstream.close();
																	}catch(Exception e)
																	{
																		System.out.println(e);
																	}
																	System.exit(0);
																}	
															}
															else if(result == JOptionPane.NO_OPTION)
															{
																System.exit(0);
															}
														}
														else
														{
															//file is exist but data is modified

															int result = JOptionPane.showConfirmDialog(jframe,"Do you want to save this file",null,JOptionPane.YES_NO_CANCEL_OPTION);
														
															if(result == JOptionPane.YES_OPTION)//user decide to save changes
															{
																//file already exist and only need to save on existing path
																try
																{
																	FileOutputStream fileoutputstream = new FileOutputStream(fullPath);
																	String fileData = jtextarea.getText();
																	byte byteFileData[] = fileData.getBytes();

																	fileoutputstream.write(byteFileData);
															
																	jframe.setTitle(jframe.getTitle().substring(1,jframe.getTitle().length()));

																	fileoutputstream.close();
																}catch(Exception e)
																{
																	System.out.println(e);
																}
																System.exit(0);
															}
															else if(result == JOptionPane.NO_OPTION)//user don't want to save changes
															{
																System.exit(0);
															}
														}
													}
													else//data is not modified so exit can perfomed
													{
														System.exit(0);
													}
												}
											}
										);

		//jtextarea editable or not option
		jcheckboxmenuitem_editable.addActionListener(
														new ActionListener()
														{
															public void actionPerformed(ActionEvent ae)
															{
																JCheckBoxMenuItem isClicked_or_not = (JCheckBoxMenuItem)ae.getSource();
																if(isClicked_or_not.isSelected())
																{
																	//user wants to edit text
																	jtextarea.setEditable(true);
																}
																else
																{
																	//user not want to edit any kind of text
																	jtextarea.setEditable(false);
																}
															}
														}
									  				);

		//undo
		jmenuitem_undo.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													try
													{
														undomanager.undo();	
													}catch(Exception e)
													{
														System.out.println(e);
													}
													
													//System.out.println("undo");
												}
											}
					  				   );

		  jtextarea.getDocument().addUndoableEditListener(
		  													new UndoableEditListener() 
		  													{  
            													public void undoableEditHappened(UndoableEditEvent e) 
            													{  
                													undomanager.addEdit(e.getEdit());  
            													}  
       												   });

		//cut
		jmenuitem_cut.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													jtextarea.cut();
													//System.out.println("cut");
												}
											}
					  					);

		//copy
		jmenuitem_copy.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													jtextarea.copy();
													//System.out.println("copy");
												}
											}
					  					);

		//paste
		jmenuitem_paste.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													jtextarea.paste();
													//System.out.println("paste");
												}
											}
					  					);

		//delete
		jmenuitem_delete.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													int start = jtextarea.getSelectionStart();//give index value of text which are selected
													int end = jtextarea.getSelectionEnd(); 
													String beforeDelete = jtextarea.getText();
													String afterDelete = ""; 
													int index = 0;
													while(index<beforeDelete.length())
													{
														if(!(index >= start && index <= end))
															afterDelete+=""+beforeDelete.charAt(index);
														index++;

													}
													jtextarea.setText(afterDelete);
													//System.out.println("delete");
												}
											}
					  					);

		//find
		jmenuitem_find.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													 jframe_find.setVisible(true);
													 jframe_find.setSize(300,200);
													 jframe_find.add(jlabel_find);
													 jframe_find.add(jtextfield_find);
													 jframe_find.add(jbutton_find);
													 jframe_find.setResizable(false);
													 jframe_find.setLayout(new FlowLayout());
													jbutton_find.addActionListener(
																						new ActionListener()
																						{
																							public void actionPerformed(ActionEvent a)
																							{
																								if(jtextarea.getText().contains(jtextfield_find.getText()))
																								{
																									jtextarea.select(jtextarea.getText().indexOf(jtextfield_find.getText()),jtextarea.getText().indexOf(jtextfield_find.getText())+jtextfield_find.getText().length());
																									jframe_find.setVisible(false);
																									startingIndex = jtextarea.getText().indexOf(jtextfield_find.getText())+jtextfield_find.getText().length();//save last find index so next time it will continue from here
																									// System.out.println("Next search index : "+startingIndex);
																								}
																								else
																								{
																									JOptionPane.showMessageDialog(jframe_find,"not found");
																								}
																								//System.out.println("Button find");
																							}
																						}
																				  );
													System.out.println("find");
												}
											}
					  					);

		//find next
		jmenuitem_find_next.addActionListener(
												new ActionListener()
												{
													public void actionPerformed(ActionEvent ae)
													{
														jframe_find_next.setVisible(true);
														jframe_find_next.setSize(300,200);
													 	jframe_find_next.add(jlabel_find_next);
													 	jframe_find_next.add(jtextfield_find_next);
													 	jframe_find_next.add(jbutton_find_next);
													 	jframe_find_next.setResizable(false);
													 	jframe_find_next.setLayout(new FlowLayout());
														System.out.println("find next");

														jbutton_find_next.addActionListener(
																								new ActionListener()
																								{	
																									public void actionPerformed(ActionEvent a)
																									{
																										String currentSearch = jtextarea.getText().substring(startingIndex);
																										//System.out.println(currentSearch);
																										
																										if(currentSearch.contains(jtextfield_find_next.getText()))//data is left for next search
																										{
																											// if(startingIndex == jtextarea.getText().length())
																											// {
																											// 	JOptionPane.showMessageDialog(jframe_find_next,"you reach at the end of the text"); 
																											// }
																											// else
																											// {
																												jframe_find_next.setVisible(false);

																												int starting_index_for_find_next = startingIndex + currentSearch.indexOf(jtextfield_find_next.getText());
																												int ending_index_for_find_next = starting_index_for_find_next + jtextfield_find_next.getText().length();

																												jtextarea.select(starting_index_for_find_next,ending_index_for_find_next);

																												startingIndex = ending_index_for_find_next;
																												System.out.println("remaning : "+currentSearch);
																												System.out.println("start index : "+starting_index_for_find_next);
																												System.out.println("end index : "+ending_index_for_find_next);
																												System.out.println("last index : "+startingIndex);
																												
																											// }
																										}	
																										else
																										{
																											JOptionPane.showMessageDialog(jframe_find_next,"nothing to find"); 
																										}
																										jframe_find_next.setVisible(false);																					
																									}								
																					       		}
																					       );
													}
												}
					  						);

		//find previous
		jmenuitem_find_previous.addActionListener(
													new ActionListener()
													{
														public void actionPerformed(ActionEvent ae)
														{
															System.out.println("find previous");
														}
													}
					  							);
		//replace
		jmenuitem_replace.addActionListener(
												new ActionListener()
												{
													public void actionPerformed(ActionEvent ae)
													{
														System.out.println("replace");
													}
												}
					  					   );

		//goto
		jmenuitem_goto.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													System.out.println("goto");
												}
											}
					  					);

		//select all
		jmenuitem_select_all.addActionListener(
							     					new ActionListener()
													{
														public void actionPerformed(ActionEvent ae)
														{
															jtextarea.select(0,jtextarea.getText().length());
															//System.out.println("select all");
														}
													}
					  							);

		//time and date
		jmenuitem_time_date.addActionListener(
													new ActionListener()
													{
														public void actionPerformed(ActionEvent ae)
														{
															int cursorPosition = jtextarea.getCaretPosition();
															// System.out.println(cursorPosition);
															String firstStr = jtextarea.getText().substring(0,cursorPosition);
															firstStr+=date;
															String secondStr = jtextarea.getText().substring(cursorPosition,jtextarea.getText().length());
															firstStr+=secondStr;
															jtextarea.setText(firstStr);
															System.out.println("time and date");
														}
													}
					  							);
		//foreground(text color) and background color chooser option
		jmenuitem_color.addActionListener(
											new ActionListener()
											{
												public void actionPerformed(ActionEvent ae)
												{
													jframe_color = new JFrame("Color");
													jframe_color.add(jcolorchooser);
													jframe_color.add(jbutton_foreground);
													jframe_color.add(jbutton_background);
													jframe_color.setVisible(true);
													jframe_color.setLayout(new FlowLayout());
													jframe_color.setSize(750,550);//(width,height)
													jframe_color.setResizable(false);	
												}
											}
										 );
		
		//foreground color
		jbutton_foreground.addActionListener(
												new ActionListener()
												{
													public void actionPerformed(ActionEvent ae)
													{
														//extract color value
														Color color = jcolorchooser.getColor();
														jtextarea.setForeground(color);//color applied to fonts
														String colorInfo = ""+color;
														String foregroundColor = "";
														colorInfo = colorInfo.substring(15,colorInfo.length()-1);
														String colorInfoArr[] = colorInfo.split(",");
														for (int i=0;i<3;i++)
														{
															String temp = colorInfoArr[i];
															foregroundColor += temp.substring(2);
															foregroundColor += " ";//rgb value store space separated manner
														}
														
														//save foreground color info
														try
														{
															FileWriter filewriter = new FileWriter(foreground_color_path);
															filewriter.write(foregroundColor);
															filewriter.close();
														}catch(Exception e)
														{
															System.out.println(e);
														}
													}
												}
											);

		//Backeground color
		jbutton_background.addActionListener(
												new ActionListener()
												{
													public void actionPerformed(ActionEvent ae)
													{
														//extract color value
														Color color = jcolorchooser.getColor();
														jtextarea.setBackground(color);//color applied to fonts
														String colorInfo = ""+color;
														String backgroundColor = "";
														colorInfo = colorInfo.substring(15,colorInfo.length()-1);
														String colorInfoArr[] = colorInfo.split(",");
														for (int i=0;i<3;i++)
														{
															String temp = colorInfoArr[i];
															backgroundColor += temp.substring(2);
															backgroundColor += " ";//rgb value store space separated manner
														}
														
														//save foreground color info
														try
														{
															FileWriter filewriter = new FileWriter(background_color_path);
															filewriter.write(backgroundColor);
															filewriter.close();
														}catch(Exception e)
														{
															System.out.println(e);
														}
													}
												}
											);
		//set size increase
		jmenuitem_font_size_increase.addActionListener(
															new ActionListener()
															{
																public void actionPerformed(ActionEvent ae)
																{
																	Font font = jtextarea.getFont();
																	//get current font size
																	default_font_size = font.getSize();
																	System.out.println(font);
																	default_font_size+=2;
																	jtextarea.setFont(new Font(font.getName(), Font.PLAIN,default_font_size));
																}
															}
									 					);
		
		//set size decrease
		jmenuitem_font_size_decrease.addActionListener(
													       new ActionListener()
													       {
													       		public void actionPerformed(ActionEvent ae)
													       		{
													       			Font font = jtextarea.getFont();
													       			//get current font size
																	default_font_size = font.getSize();
																	System.out.println(font);
																	if(default_font_size > 8)
																	{
																		default_font_size-=2;
																		jtextarea.setFont(new Font(font.getName(), Font.PLAIN,default_font_size));
																	}
																	
																}
															}
									 					);
		//set font
		jmenuitem_arial.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent ae)
											{
												Font font_size = jtextarea.getFont();
												Font font = new Font("Arial",Font.PLAIN,font_size.getSize());
												jtextarea.setFont(font);
												
											}
										}
									 );
		jmenuitem_times_new_roman.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent ae)
											{
												Font font_size = jtextarea.getFont();
												Font font = new Font("Times new Roman",Font.PLAIN,font_size.getSize());
												jtextarea.setFont(font);
												
											}
										}
									 );
		jmenuitem_courier.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent ae)
											{
												Font font = new Font("Courier", Font.PLAIN, 16);
												jtextarea.setFont(font);
												
											}
										}
									 );
		jmenuitem_serif.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent ae)
											{
												Font font = new Font("Serif", Font.PLAIN, 16);
												jtextarea.setFont(font);
												
											}
										}
									 );

		//for checking file is edited or not so as per title also upadated
		jtextarea.addKeyListener(this);

		// jframe.add(jtextarea);
		jframe.setSize(700,500);//(width,height)
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void keyPressed(KeyEvent ke)
	{

		System.out.println("key pressed");
	}

	public void keyReleased(KeyEvent ke)
	{
		if(jframe.getTitle().equals("Untitled") || jframe.getTitle().equals("*Untitled"))
		{
			if(jtextarea.getText().length() == 0)
			{
				jframe.setTitle("Untitled");
			}
			else
			{
				jframe.setTitle("*Untitled");
			}
			
		}
		else if(!(jframe.getTitle().charAt(0) == '*'))
		{
			String title = "*"+jframe.getTitle();
			jframe.setTitle(title);
		}
		System.out.println("key Released");
	}

	public void keyTyped(KeyEvent ke)
	{

		System.out.println("key Typed");
	}
	//main method
	public static void main(String args[])
	{
		NotePad notepad = new NotePad();
	}
}