package com.ufcg.model;

/**
 * Created by Cathy on 27/03/2016.
 */
public class Song {

    private int id;
    private String title;
    private String artist;
    private int votes;


    public Song(int id, String title, String artist, int votes){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.votes = votes;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVotes() {
        return votes;
    }

    public void updateVotes(int myVote) {
        this.votes += myVote;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }



}
