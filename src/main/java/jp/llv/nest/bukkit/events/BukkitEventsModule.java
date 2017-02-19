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
package jp.llv.nest.bukkit.events;

import javax.inject.Inject;
import jp.llv.nest.NestAPI;
import jp.llv.nest.NestBukkitPlugin;
import jp.llv.nest.bukkit.events.action.InteractListener;
import jp.llv.nest.bukkit.events.action.JoinListener;
import jp.llv.nest.bukkit.events.action.QuitListener;
import jp.llv.nest.event.EventsModule;
import jp.llv.nest.module.Module;
import org.bukkit.Server;

/**
 *
 * @author toyblocks
 */
@Module(name = "bukkit-events", author = "toyblocks", version = 2)
public class BukkitEventsModule {
    
    @Inject
    public BukkitEventsModule(NestAPI api, EventsModule events, NestBukkitPlugin plugin, Server server) {
        server.getPluginManager().registerEvents(new InteractListener(api), plugin);
        server.getPluginManager().registerEvents(new JoinListener(api), plugin);
        server.getPluginManager().registerEvents(new QuitListener(api), plugin);
    }
    
}
