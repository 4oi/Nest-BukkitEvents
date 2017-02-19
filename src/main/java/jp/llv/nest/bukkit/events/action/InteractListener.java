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
import jp.llv.nest.command.obj.bukkit.BukkitBlockLocation;
import jp.llv.nest.command.obj.bukkit.BukkitPlayer;
import jp.llv.nest.event.action.InteractEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author toyblocks
 */
public class InteractListener implements Listener {
    
    private final NestAPI api;

    public InteractListener(NestAPI api) {
        this.api = api;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void on(PlayerInteractEvent event) {
        InteractEvent<?, ?> nevent;
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
                nevent = new BukkitInteractEvent.Primary(event);
                break;
            case LEFT_CLICK_BLOCK:
                nevent = new BukkitInteractEvent.Primary.Block(event);
                break;
            case RIGHT_CLICK_AIR:
                nevent = new BukkitInteractEvent.Secondary(event);
                break;
            case RIGHT_CLICK_BLOCK:
                nevent = new BukkitInteractEvent.Secondary.Block(event);
                break;
            default:
                nevent = new BukkitInteractEvent(event);
                break;
        }
        if (this.api.getEventManager().fire(nevent)) {
            event.setCancelled(true);
        }
    }
    
    public static class BukkitInteractEvent extends NestValueAdapter<PlayerInteractEvent> implements InteractEvent<BukkitPlayer, PlayerInteractEvent>{

        private boolean canceled = false;
        
        public BukkitInteractEvent(PlayerInteractEvent value) {
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
        
        public static class Primary extends BukkitInteractEvent implements InteractEvent.Primary<BukkitPlayer, PlayerInteractEvent> {
            
            public Primary(PlayerInteractEvent value) {
                super(value);
            }
            
            public static class Block extends BukkitInteractEvent.Primary implements InteractEvent.Primary.Block<BukkitPlayer, Location, PlayerInteractEvent> {

                public Block(PlayerInteractEvent value) {
                    super(value);
                }

                @Override
                public BukkitBlockLocation getLocation() {
                    return new BukkitBlockLocation(super.value.getClickedBlock().getLocation());
                }
                
            }
            
        }
        
        public static class Secondary extends BukkitInteractEvent implements InteractEvent.Secondary<BukkitPlayer, PlayerInteractEvent> {
            
            public Secondary(PlayerInteractEvent value) {
                super(value);
            }
            
            public static class Block extends BukkitInteractEvent.Secondary implements InteractEvent.Primary.Block<BukkitPlayer, Location, PlayerInteractEvent> {

                public Block(PlayerInteractEvent value) {
                    super(value);
                }

                @Override
                public BukkitBlockLocation getLocation() {
                    return new BukkitBlockLocation(super.value.getClickedBlock().getLocation());
                }
                
            }
            
        }
        
    }
    
}
