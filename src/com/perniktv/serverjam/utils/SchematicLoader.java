package com.perniktv.serverjam.utils;

import com.perniktv.serverjam.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.fabric.FabricAdapter;
import com.sk89q.worldedit.fabric.FabricWorld;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;

public class SchematicLoader {

    public static boolean loadSchematic(World world, Location centerLocation, String schematicName) {
        File schematicFile = new File(Main.getPlugin().getDataFolder() + "/schematics/" +
                schematicName);
        if (!schematicFile.exists()) {
            return false;
        }

//        StringBuilder stringBuilder = new StringBuilder();
//        try (Scanner scanner = new Scanner(schematicFile)) {
//            while (scanner.hasNextLine()) {
//                stringBuilder.append(scanner.nextLine());
//            }
//
//        } catch (FileNotFoundException e) {
//            return false;
//        }
//
//        JsonParser parser = new JsonParser();
//        JsonArray schematic = parser.parse(stringBuilder.toString()).getAsJsonArray();
//        for (int x = 0; x < schematic.size(); x++) {
//            JsonArray yArray = schematic.get(x).getAsJsonArray();
//            for (int y = 0; y < yArray.size(); y++) {
//                JsonArray zArray = yArray.get(y).getAsJsonArray();
//                for (int z = 0; z < zArray.size(); z++) {
//                    Material material = Material.getMaterial(zArray.get(z).getAsJsonObject().get("material").getAsString());
//                    if (material == null) {
//                        continue;
//                    }
//
//                    Location currentLocation = centerLocation.clone().add(schematic.size() / 2 + x, yArray.size() / 2 + y, zArray.size() / 2 + z);
//                    Block currentBlock = world.getBlockAt(currentLocation);
//                    currentBlock.setType(material);
//                }
//            }
//        }

        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
            clipboard = reader.read();
        } catch (Exception e) {
            return false;
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(FabricAdapter.adapt(world), -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(centerLocation.getBlockX(), centerLocation.getBlockY(), centerLocation.getBlockZ()))
                    .build();

            Operations.complete(operation);
        } catch (WorldEditException e) {
            return false;
        }

        return true;
    }
}
