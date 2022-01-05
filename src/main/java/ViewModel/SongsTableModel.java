package ViewModel;

import DataModel.Song;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SongsTableModel extends AbstractTableModel {

    private List<Song> songs = new ArrayList<>();

    public SongsTableModel(List<Song> songs) {
        this.songs = songs;
    }

    public SongsTableModel(){}

    @Override
    public int getRowCount() {
        return songs.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
        switch (columnIndex){
            case 0 -> {
                return song.getName();
            }
            case 1 -> {
                return song.getAuthor();
            }
            case 2 -> {
                return song.getLength();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0 -> {
                return "Song";
            }
            case 1 -> {
                return "Author";
            }
            case 2 -> {
                return "Length";
            }
            default -> {
                return null;
            }
        }
    }

    public void refresh(){
        this.fireTableDataChanged();
    }
    public void shuffle(){
        Collections.shuffle(this.songs);
        this.fireTableDataChanged();
    }

    public void addSong(String name, String author, int length){
        this.songs.add(new Song(name,author,length));
        this.fireTableDataChanged();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
