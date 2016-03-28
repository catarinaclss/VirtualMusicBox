package com.ufcg.model;

import java.util.List;

/**
 * Created by st on 27/03/2016.
 */
public interface StorageSystem {

    public void add(Song song);

    public void edit(Song song);

    public void delete(Integer id);

    public Song getSong(int key);

    public List<Song> getPlaylist();

}
