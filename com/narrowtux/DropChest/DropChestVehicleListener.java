package com.narrowtux.DropChest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.server.EntityMinecart;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.craftbukkit.entity.CraftStorageMinecart;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class DropChestVehicleListener extends VehicleListener {
	private DropChest plugin;
	private boolean alreadyfixed = false;

	public DropChestVehicleListener(DropChest plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onVehicleMove(VehicleMoveEvent event)
	{
		if(event.getVehicle() instanceof Minecart)
		{
			if(event.getVehicle() instanceof StorageMinecart && !alreadyfixed){
				plugin.log.log(Level.INFO, "THEY FIXED MINECARTS :) Please tell narrowtux!");
				alreadyfixed = true;
			}
			EntityMinecart minecart = (EntityMinecart)((CraftMinecart)event.getVehicle()).getHandle();
			/*
			 * EntityMinecart.type means the type of the minecart
			 *  0 means a normal minecart
			 *  1 means a storage minecart
			 *  2 means a powered minecart
			 */
			if(minecart.type == 1){
				CraftStorageMinecart storage = new CraftStorageMinecart((CraftServer)plugin.getServer(), minecart);
				for(DropChestItem dci : plugin.getChests()){

					if(minecartNearBlock(dci.getBlock(), storage))
					{
						dci.minecartAction(storage);
					}
				}
			}

		}
	}
	
	public boolean minecartNearBlock(Block b, Minecart v)
	{
		if(Math.abs(v.getLocation().getBlockY()-b.getY())>1)
		{
			return false;
		}
		
		List<Block> facings = new ArrayList<Block>();
		facings.add(b.getFace(BlockFace.NORTH));
		facings.add(b.getFace(BlockFace.EAST));
		facings.add(b.getFace(BlockFace.SOUTH));
		facings.add(b.getFace(BlockFace.WEST));
		facings.add(b.getFace(BlockFace.UP));
		facings.add(b.getFace(BlockFace.DOWN));
		Location loc = v.getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		int y = loc.getBlockY();

		for(Block face:facings)
		{
			if(x==face.getX()&&z==face.getZ()&&y==face.getY()){
				return true;
			}
		}

		return false;
	}
}
