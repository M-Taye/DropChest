package com.narrowtux.DropChest;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DropChestInventory implements Inventory {
	
	private Inventory inventories[];

	public DropChestInventory(Inventory...inventories ){
		this.inventories = inventories;
	}
	
	@Override
	public HashMap<Integer, ItemStack> addItem(ItemStack... arg0) {
		HashMap<Integer, ItemStack> ret = new HashMap<Integer, ItemStack>();
		int i = 0;
		for(ItemStack item: arg0){
			ret.put(i++, item);
		}
		for(Inventory inv:inventories){
			if(ret.size()==0){
				return ret;
			} else {
				ItemStack items[] = (ItemStack[])ret.values().toArray();
				ret = inv.addItem(items);
			}
		}
		return ret;
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(int arg0) {
		return all(Material.getMaterial(arg0));
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(Material arg0) {
		HashMap<Integer, ItemStack> ret = new HashMap<Integer, ItemStack>();
		Inventory lastinv = null;
		for(Inventory inv:inventories){
			HashMap<Integer, ? extends  ItemStack> map = inv.all(arg0);
			for(Integer i:map.keySet()){
				if(lastinv!=null){
					Integer finali=i+lastinv.getSize();
					ret.put(finali, map.get(i));
				}
				lastinv=inv;
			}
		}
		return ret;
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(ItemStack arg0) {
		HashMap<Integer, ItemStack> ret = new HashMap<Integer, ItemStack>();
		Inventory lastinv = null;
		for(Inventory inv:inventories){
			HashMap<Integer, ? extends  ItemStack> map = inv.all(arg0);
			for(Integer i:map.keySet()){
				if(lastinv!=null){
					Integer finali=i+lastinv.getSize();
					ret.put(finali, map.get(i));
				}
				lastinv=inv;
			}
		}
		return ret;
	}

	@Override
	public void clear() {
		for(Inventory inv:inventories){
			inv.clear();
		}

	}

	@Override
	public void clear(int arg0) {
		mapFromInventory(arg0).clear(mapFromSlot(arg0));
	}

	@Override
	public boolean contains(int arg0) {
		return contains(Material.getMaterial(arg0));
	}

	@Override
	public boolean contains(Material arg0) {
		for(Inventory inv:inventories){
			if(inv.contains(arg0)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(ItemStack arg0) {
		for(Inventory inv:inventories){
			if(inv.contains(arg0)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(int arg0, int arg1) {
		contains(Material.getMaterial(arg0), arg1);
		return false;
	}

	@Override
	public boolean contains(Material arg0, int arg1) {
		for(Inventory inv:inventories){
			if(inv.contains(arg0, arg1)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(ItemStack arg0, int arg1) {
		for(Inventory inv:inventories){
			if(inv.contains(arg0, arg1)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int first(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int first(Material arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int first(ItemStack arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int firstEmpty() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack[] getContents() {
		//TODO
		return null;
	}

	@Override
	public ItemStack getItem(int arg0) {
		return mapFromInventory(arg0).getItem(mapFromSlot(arg0));
	}

	@Override
	public String getName() {
		return inventories[0].getName();
	}

	@Override
	public int getSize() {
		int ret = 0;
		for(Inventory inv: inventories){
			ret+=inv.getSize();
		}
		return ret;
	}

	@Override
	public void remove(int arg0) {
		for(Inventory inv: inventories){
			inv.remove(arg0);
		}
	}

	@Override
	public void remove(Material arg0) {
		for(Inventory inv: inventories){
			inv.remove(arg0);
		}
	}

	@Override
	public void remove(ItemStack arg0) {
		for(Inventory inv: inventories){
			inv.remove(arg0);
		}
	}

	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContents(ItemStack[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItem(int arg0, ItemStack arg1) {
		mapFromInventory(arg0).setItem(mapFromSlot(arg0), arg1);

	}
	
	private Inventory mapFromInventory(int slot){
		for(Inventory inv: inventories)
		{
			slot-=inv.getSize();
			if(slot<=0){
				return inv;
			}
		}
		return null;
	}
	
	private int mapFromSlot(int slot){
		for(Inventory inv: inventories)
		{
			slot-=inv.getSize();
			if(slot<0){
				return slot+inv.getSize();
			}
			if(slot==0){
				return 0;
			}
		}
		return -1;
	}
}