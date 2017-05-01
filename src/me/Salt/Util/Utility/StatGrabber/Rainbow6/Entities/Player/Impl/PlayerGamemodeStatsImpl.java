/*
 * Copyright (c) 2017 DevJake
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.Salt.Util.Utility.StatGrabber.Rainbow6.Entities.Player.Impl;

import me.Salt.Util.Utility.StatGrabber.Rainbow6.Entities.Player.PlayerGamemodeStats;
import me.Salt.Util.Utility.StatGrabber.Rainbow6.Util.Gamemode;

public class PlayerGamemodeStatsImpl implements PlayerGamemodeStats {
    private Gamemode gamemode;
    private boolean hasPlayed;
    private int wins;
    private int losses;
    private int kills;
    private int deaths;
    private int playtime;

    public PlayerGamemodeStatsImpl(Gamemode gamemode, boolean hasPlayed, int wins, int losses, int kills, int deaths, int playtime) {

        this.gamemode = gamemode;
        this.hasPlayed = hasPlayed;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.deaths = deaths;
        this.playtime = playtime;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public boolean isHasPlayed() {
        return hasPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPlaytime() {
        return playtime;
    }


}