package Gui;

import Data.DataManager;
import DataModel.Song;
import ViewModel.SongsTableModel;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {

    private JTable tblSongs;
    private JButton btnLoadFlatFile;
    private JButton btnAddSong;
    private JButton btnSaveFlatFile;
    private JButton btnShuffle;
    private JButton btnLoadJson;
    private JButton btnSaveJson;
    private JButton btnLoadXML;
    private JButton btnSaveXML;
    private JButton btnCanvas;
    private JButton btnHttpGet;
    private JTextField tfName;
    private JTextField tfAuthor;
    private JTextField tfLength;
    private JLabel lblName;
    private JLabel lblAuthor;
    private JLabel lblLength;
    private JPanel content;
    private JPanel pnlBottom;
    private JPanel pnlRight;
    private SongsTableModel tableModel;
    private JScrollPane scrPane;

    public MainFrame() throws HeadlessException {
        this.setPreferredSize(new Dimension(800,600));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.tableModel = new SongsTableModel();
        this.tableModel.fireTableDataChanged();
        this.initComponents();

        this.pack();
        this.setResizable(false);
    }

    private void initComponents(){
        content = new JPanel();
        content.setLayout(new BorderLayout());

        pnlRight = new JPanel();
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setBackground(Color.GRAY);
        pnlRight.setPreferredSize(new Dimension(150,400));
        btnLoadFlatFile = new JButton("Load FlatFile");
        btnSaveFlatFile = new JButton("Save to Flat File");
        btnShuffle = new JButton("Shuffle");
        btnLoadJson = new JButton("Load Json");
        btnSaveJson = new JButton("Export Json");
        btnLoadXML = new JButton("Load XML");
        btnSaveXML = new JButton("Save XML");
        btnCanvas = new JButton("Open canvas window");
        btnHttpGet = new JButton("Load from Web");
        pnlRight.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlRight.add(btnLoadFlatFile);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnSaveFlatFile);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnShuffle);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnLoadJson);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnSaveJson);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnLoadXML);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnSaveXML);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnHttpGet);
        pnlRight.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlRight.add(btnCanvas);

        btnShuffle.addActionListener(e -> {
            this.tableModel.shuffle();
        });

        btnLoadFlatFile.addActionListener(e -> {
            this.tableModel.setSongs(DataManager.readSongsFlatFile());
            this.tableModel.fireTableDataChanged();
        });

        btnSaveJson.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                DataManager.writeSongsJson(fc.getSelectedFile().getPath(),this.tableModel.getSongs());
            }
        });

        btnLoadJson.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                ArrayList<Song> data = new ArrayList<Song>(DataManager.readSongsJson(fc.getSelectedFile().getPath()));
                this.tableModel.setSongs(data);
            }
            this.tableModel.fireTableDataChanged();
        });

        btnSaveFlatFile.addActionListener(e -> {
            DataManager.rewriteFlatFile(this.tableModel.getSongs());
        });

        btnSaveXML.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                DataManager.writeSongsXML(fc.getSelectedFile().getPath(),this.tableModel.getSongs());
            }
        });

        btnLoadXML.addActionListener(e->{
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                ArrayList<Song> data = new ArrayList<Song>(DataManager.readSongsXML(fc.getSelectedFile().getPath()));
                this.tableModel.setSongs(data);
            }
            this.tableModel.fireTableDataChanged();
        });

        btnHttpGet.addActionListener(e ->{
           this.tableModel.setSongs(DataManager.getFromHttp());
           this.tableModel.fireTableDataChanged();
        });

        btnCanvas.addActionListener(e -> {
            new SecondFrame();
        });

        pnlBottom = new JPanel();
        pnlBottom.setLayout(new FlowLayout());
        pnlBottom.setBackground(Color.GRAY);
        pnlBottom.setPreferredSize(new Dimension(800,100));
        tfName = new JTextField(10);
        tfName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
        tfAuthor = new JTextField(10);
        tfLength = new JTextField(10);
        lblName = new JLabel("Enter song name:");
        lblName.setForeground(Color.WHITE);
        lblAuthor = new JLabel("Enter authors name:");
        lblAuthor.setForeground(Color.WHITE);
        lblLength = new JLabel("Enter song length in seconds:");
        lblLength.setForeground(Color.WHITE);
        btnAddSong = new JButton("Add to list");
        pnlBottom.add(lblName);
        pnlBottom.add(tfName);
        pnlBottom.add(lblAuthor);
        pnlBottom.add(tfAuthor);
        pnlBottom.add(lblLength);
        pnlBottom.add(tfLength);
        pnlBottom.add(btnAddSong);

        btnAddSong.addActionListener(e -> {
            this.tableModel.addSong(tfName.getText(), tfAuthor.getText(), Integer.parseInt(tfLength.getText()));
            tfName.setText("");
            tfAuthor.setText("");
            tfLength.setText("");
        });

        tblSongs = new JTable();
        tblSongs.setModel(tableModel);

        scrPane = new JScrollPane();
        scrPane.setPreferredSize(new Dimension(600,400));
        scrPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        scrPane.setViewportView(tblSongs);

        content.add(scrPane,BorderLayout.CENTER);
        content.add(pnlRight,BorderLayout.EAST);
        content.add(pnlBottom,BorderLayout.SOUTH);

        this.add(content);
        this.pack();
    }
}
