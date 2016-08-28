/*
 * Copyright (C) 2016 toyblocks
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.llv.nest.bukkit.events.action;

import jp.llv.nest.NestAPI;
import jp.llv.nest.command.obj.NestValueAdapter;
import jp.llv.nest.command.obj.bukkit.BukkitPlayer;
import jp.llv.nest.event.Cancelable;
import jp.llv.nest.event.server.QuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author toyblocks
 */
public class QuitListener implements Listener {
    
    private final NestAPI api;

    public QuitListener(NestAPI api) {
        this.api = api;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void on(PlayerQuitEvent event) {
        if (api.getEventManager().fire(new BukkitJoinEvent(event)));
    }
    
    public static class BukkitJoinEvent extends NestValueAdapter<PlayerQuitEvent> implements QuitEvent<BukkitPlayer, PlayerQuitEvent>, Cancelable<PlayerQuitEvent> {

        private boolean canceled = false;
        
        public BukkitJoinEvent(PlayerQuitEvent value) {
            super(value);
        }

        @Override
        public BukkitPlayer getSender() {
            return new BukkitPlayer(super.value.getPlayer());
        }

        @Override
        public void cancel() {
            this.canceled = true;
        }

        @Override
        public void proceed() {
            this.canceled = false;
        }

        @Override
        public boolean isCanceled() {
            return this.canceled;
        }
        
    }
    
}
