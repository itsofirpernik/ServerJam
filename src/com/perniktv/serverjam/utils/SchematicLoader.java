package com.perniktv.serverjam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;

import com.perniktv.serverjam.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

public class SchematicLoader {

	public static boolean loadSchematic(World world, Location centerLocation, String schematicName) {
		File schematicFile = new File(Main.getPlugin().getDataFolder() + "/" + schematicName);
		if (!schematicFile.exists()) {
			return false;
		}

		ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);

		ClipboardReader reader = null;
		try {
			reader = format.getReader(new FileInputStream(schematicFile));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}

		if (null == reader) {
			return false;
		}

		Clipboard clipboard = null;
		try {
			clipboard = reader.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (null == clipboard) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		try { // Pasting Operation
				// We need to adapt our world into a format that worldedit accepts. This looks
				// like this:
				// Ensure it is using com.sk89q... otherwise we'll just be adapting a world into
				// the same world.
			com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(world);

			EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

			// Saves our operation and builds the paste - ready to be completed.
			Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(
					BlockVector3.at(centerLocation.getBlockX(), centerLocation.getBlockY(), centerLocation.getBlockZ()))
					.ignoreAirBlocks(true).build();

			try { // This simply completes our paste and then cleans up.
				Operations.complete(operation);
				editSession.flushSession();

			} catch (WorldEditException e) { // If worldedit generated an exception it will go here
				e.printStackTrace();
			}

		} catch (Exception e) {
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
