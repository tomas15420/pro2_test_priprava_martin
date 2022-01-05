package Data;

import DataModel.Song;
import DataModel.SongListData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class DataManager {

    /**
     * Reads data from flat file source .txt
     * Required splitter: ;
     * Required order: Song name; Author name; Duration in seconds
     * @return data ArrayList<Song>
     */
    public static List<Song> readSongsFlatFile(){
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\songs.txt";
        List<Song> songs = new ArrayList<>();
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(path));
            while ((line = bufferedReader.readLine()) != null){
                String[] data = line.split(";");
                Song s = new Song(data[0],data[1],Integer.parseInt(data[2]));
                songs.add(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return  songs;
    }


    public static void rewriteFlatFile(List<Song> data){
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\songs.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (Song s: data) {
                String line = s.getName() + ";" + s.getAuthor() + ";" + s.getLength();
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Song> readSongsJson(String path){
        Gson gson = new GsonBuilder().create();
        try {
            JsonReader jsonReader = gson.newJsonReader(new FileReader(path));
            Song[] data = gson.fromJson(jsonReader, Song[].class);
            return Arrays.stream(data).toList();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeSongsJson(String path, List<Song> data){
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(data);
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Song> readSongsXML(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(SongListData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            SongListData songs = (SongListData) unmarshaller.unmarshal(new File(path));
            return songs.getSongs();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeSongsXML(String path, List<Song> data){
        try {
            JAXBContext context = JAXBContext.newInstance(SongListData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            SongListData songs = new SongListData();
            songs.setSongs(data);
            marshaller.marshal(songs,new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    public static List<Song> getFromHttp(){
        //String line;
        List<Song> songs = new ArrayList<>();
        try {
            URL url = new URL("http://localhost/data/songs_2.csv");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


            bufferedReader.lines().iterator().forEachRemaining((line)->{
                String[] lineData = line.split(";");
                songs.add(new Song(lineData[0],lineData[1],Integer.parseInt(lineData[2])));
                System.out.println(line);
            });

            /* while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                //songs.add(new Song(line[0],line[1],line[2]));
            }*/
            return songs;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
