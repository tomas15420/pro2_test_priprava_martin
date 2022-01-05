package DataModel;

import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Song")
public class Song {
    //@SerializedName()
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Author")
    private String author;
    @XmlElement(name = "Length")
    private int length;

    public Song(String name, String author, int length) {
        this.name = name;
        this.author = author;
        this.length = length;
    }

    public Song(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getLength() {
        return length;
    }
}
