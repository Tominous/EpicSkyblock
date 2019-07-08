package com.peaches.epicskyblock.configs;

public class Missions {
    public class Mission {
        int amount;
        int reward;

        public Mission(int amount, int reward) {
            this.amount = amount;
            this.reward = reward;
        }

        public int getAmount() {
            return amount;
        }

        public int getReward() {
            return reward;
        }
    }

    public Mission treasureHunter = new Mission(1000, 15);
    public Mission competitor = new Mission(10000, 15);
    public Mission miner = new Mission(1000, 15);
    public Mission farmer = new Mission(5000, 15);
    public Mission hunter = new Mission(1000, 15);
    public Mission fisherman = new Mission(100, 15);
    public Mission builder = new Mission(10000, 15);
}
