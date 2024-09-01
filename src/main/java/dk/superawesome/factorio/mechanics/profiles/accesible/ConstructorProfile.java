package dk.superawesome.factorio.mechanics.profiles.accesible;

import dk.superawesome.factorio.building.Building;
import dk.superawesome.factorio.building.Buildings;
import dk.superawesome.factorio.gui.GuiFactory;
import dk.superawesome.factorio.gui.SingleStorageGui;
import dk.superawesome.factorio.gui.impl.ConstructorGui;
import dk.superawesome.factorio.mechanics.*;
import dk.superawesome.factorio.mechanics.impl.accessible.Constructor;
import dk.superawesome.factorio.mechanics.transfer.ItemCollection;
import dk.superawesome.factorio.util.Array;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static dk.superawesome.factorio.util.statics.MathUtil.getIncreaseDifference;
import static dk.superawesome.factorio.util.statics.MathUtil.ticksToMs;

public class ConstructorProfile implements GuiMechanicProfile<Constructor> {

    private final MechanicFactory<Constructor> factory = new ConstructorMechanicFactory();
    private final GuiFactory<Constructor, ConstructorGui> guiFactory = new ConstructorGuiFactory();

    @Override
    public String getName() {
        return "Constructor";
    }

    @Override
    public Building getBuilding() {
        return Buildings.CONSTRUCTOR;
    }

    @Override
    public MechanicFactory<Constructor> getFactory() {
        return factory;
    }

    @Override
    public StorageProvider<Constructor> getStorageProvider() {
        return StorageProvider.Builder.<Constructor>makeContext()
                .set(SingleStorageGui.CONTEXT, ConstructorGui.STORAGE_SLOTS, mechanic -> new Storage() {
                    @Override
                    public ItemStack getStored() {
                        return mechanic.getStorageType();
                    }

                    @Override
                    public void setStored(ItemStack stored) {
                        mechanic.setStorageType(stored);
                    }

                    @Override
                    public int getAmount() {
                        return mechanic.getStorageAmount();
                    }

                    @Override
                    public void setAmount(int amount) {
                        mechanic.setStorageAmount(amount);
                    }

                    @Override
                    public int getCapacity() {
                        return mechanic.getCapacity();
                    }
                })
                .build();
    }

    @Override
    public GuiFactory<Constructor, ConstructorGui> getGuiFactory() {
        return guiFactory;
    }

    @Override
    public MechanicLevel.Registry getLevelRegistry() {
        return MechanicLevel.Registry.Builder
                .make(5)
                .setDescription(2, Arrays.asList("§eLager: 12 stacks §f-> §e15 stacks", "§eHastighed: " + ticksToMs(20) + "ms §f-> §e" + ticksToMs(19) + "ms §f(§e"+ getIncreaseDifference(20, 19, true) +"% hurtigere§f)"))
                .setDescription(3, Arrays.asList("§eLager: 15 stacks §f-> §e22 stacks", "§eHastighed: " + ticksToMs(19) + "ms §f-> §e" + ticksToMs(18) + "ms §f(§e"+ getIncreaseDifference(20, 18, true) +"% hurtigere§f)"))
                .setDescription(4, Arrays.asList("§eLager: 22 stacks §f-> §e32 stacks", "§eHastighed: " + ticksToMs(18) + "ms §f-> §e" + ticksToMs(16) + "ms §f(§e"+ getIncreaseDifference(20, 16, true) +"% hurtigere§f)"))
                .setDescription(5, Arrays.asList("§eLager: 32 stacks §f-> §e64 stacks", "§eHastighed: " + ticksToMs(17) + "ms §f-> §e" + ticksToMs(14) + "ms §f(§e"+ getIncreaseDifference(20, 14, true) +"% hurtigere§f)"))

                .mark(MechanicLevel.XP_REQUIRES_MARK, Array.fromData(1000d, 2500d, 5000d, 10000d))
                .mark(MechanicLevel.LEVEL_COST_MARK, Array.fromData(4096d, 12288d, 20480d, 51200d))

                .mark(MechanicLevel.THINK_DELAY_MARK, Array.fromData(20, 19, 18, 16, 14))
                .mark(ItemCollection.CAPACITY_MARK, Array.fromData(12, 15, 22, 32, 64))
                .build();
    }

    @Override
    public int getID() {
        return 0;
    }

    private static class ConstructorMechanicFactory implements MechanicFactory<Constructor> {

        @Override
        public Constructor create(Location loc, BlockFace rotation, MechanicStorageContext context) {
            return new Constructor(loc, rotation, context);
        }
    }

    private static class ConstructorGuiFactory implements GuiFactory<Constructor, ConstructorGui> {

        @Override
        public ConstructorGui create(Constructor constructor, AtomicReference<ConstructorGui> inUseReference) {
            return new ConstructorGui(constructor, inUseReference);
        }
    }
}
