/*
 * The MIT License
 *
 * Copyright 2017 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.nest.bukkit.events.action;

import jp.llv.nest.NestAPI;
import jp.llv.nest.command.obj.NestValueAdapter;
import jp.llv.nest.command.obj.bukkit.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import jp.llv.nest.event.action.ItemSwapEvent;

/**
 *
 * @author toyblocks
 */
public class ItemSwapListener implements Listener {
    
    private final NestAPI api;
    
    public ItemSwapListener(NestAPI api) {
        this.api = api;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void on(PlayerSwapHandItemsEvent event) {
        if (api.getEventManager().fire(new BukkitItemSwapEvent(event))) {
            
        }
    }
    
    public static class BukkitItemSwapEvent extends NestValueAdapter<PlayerSwapHandItemsEvent> implements ItemSwapEvent<BukkitPlayer, PlayerSwapHandItemsEvent> {
        
        private boolean cancel = false;

        public BukkitItemSwapEvent(PlayerSwapHandItemsEvent value) {
            super(value);
        }

        @Override
        public BukkitPlayer getSender() {
            return new BukkitPlayer(super.value.getPlayer());
        }

        @Override
        public void cancel() {
            this.cancel = true;
        }

        @Override
        public void proceed() {
            this.cancel = false;
        }

        @Override
        public boolean isCanceled() {
            return this.cancel;
        }
        
    }
    
}
