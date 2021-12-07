package org.samo_lego.taterzens.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;
import org.samo_lego.taterzens.Taterzens;
import org.samo_lego.taterzens.api.TaterzensAPI;
import org.samo_lego.taterzens.compatibility.carpet.AdditionalFunctions;
import org.samo_lego.taterzens.compatibility.carpet.ScarpetProfession;
import org.samo_lego.taterzens.compatibility.carpet.ScarpetTraitCommand;
import org.samo_lego.taterzens.fabric.event.BlockInteractEventImpl;
import org.samo_lego.taterzens.npc.TaterzenNPC;

import java.io.File;

import static org.samo_lego.taterzens.Taterzens.*;

public class TaterzensFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        TATERZEN_TYPE = Registry.register(
                Registry.ENTITY_TYPE,
                NPC_ID,
                FabricEntityTypeBuilder
                        .create(MobCategory.MISC, TaterzenNPC::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 1.8F))
                        .build()
        );

        FabricDefaultAttributeRegistry.register(TATERZEN_TYPE, TaterzenNPC.createTaterzenAttributes());

        taterDir = new File(FabricLoader.getInstance().getConfigDir() + "/Taterzens/presets");

        DISGUISELIB_LOADED = FabricLoader.getInstance().isModLoaded("disguiselib");

        // Permissions
        LUCKPERMS_LOADED = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");

        FABRICTAILOR_LOADED = FabricLoader.getInstance().isModLoaded("fabrictailor");

        // server-translations-api
        SERVER_TRANSLATIONS_LOADED = FabricLoader.getInstance().isModLoaded("server_translations_api");

        // Common initialization
        Taterzens.onInitialize();


        // CarpetMod
        CARPETMOD_LOADED = FabricLoader.getInstance().isModLoaded("carpet");
        if (CARPETMOD_LOADED) {
            TaterzensAPI.registerProfession(ScarpetProfession.ID, new ScarpetProfession());
            AdditionalFunctions.init();
        }

        // Events
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            Taterzens.registerCommands(dispatcher, dedicated);
            ScarpetTraitCommand.register(dispatcher, dedicated);
        });
        UseBlockCallback.EVENT.register(new BlockInteractEventImpl());
    }
}