package com.peaches.epicskyblock;

public class User {

    public String player;
    public int islandID;

    public User(String player) {
        this.player = player;
        this.islandID = 0;
        EpicSkyblock.getIslandManager().users.put(this.player, this);
    }

    public Island getIsland(){
        if(EpicSkyblock.getIslandManager().islands.containsKey(islandID)){
            return EpicSkyblock.getIslandManager().islands.get(islandID);
        }
        return null;
    }

    public static User getUser(String p) {
        return EpicSkyblock.getIslandManager().users.containsKey(p) ? EpicSkyblock.getIslandManager().users.get(p) : new User(p);
    }
}
