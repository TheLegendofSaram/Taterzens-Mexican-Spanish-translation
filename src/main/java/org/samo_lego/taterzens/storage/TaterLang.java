package org.samo_lego.taterzens.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.samo_lego.taterzens.Taterzens.MODID;
import static org.samo_lego.taterzens.Taterzens.getLogger;

public class TaterLang {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static class Success {
        public String spawnedTaterzen = "Taterzen %s has been spawned successfully.";
        public String changedType = "Taterzen type was is now set to %s.";
        public String killedTaterzen = "Taterzen %s has been removed.";
        public String setCommandAction = "Command action was set successfully.";
        public String selectedTaterzen = "You have selected %s.";
        public String equipmentEditorEnter = "You've entered equipment editor for %s. Enter same command to exit.";
        public String equipmentEditorDesc = "Right click the Taterzen to equip it. \nTo put armor in hand, use shift right click. \nShift right click with empty hand to drop all equipment.";
        public String editorExit = "You've exited the editor.";
    }

    public static class Error {
        public String selectTaterzen = "You have to select Taterzen first.";
        public String noTaterzenFound = "No Taterzens were found.";
    }


    public TaterLang.Success success = new TaterLang.Success();
    public TaterLang.Error error = new TaterLang.Error();


    /**
     * Loads language file.
     *
     * @param file file to load the language file from.
     * @return TaterzenLanguage object
     */
    public static TaterLang loadLanguageFile(File file) {
        TaterLang language;
        if (file.exists()) {
            try (BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
            )) {
                language = gson.fromJson(fileReader, TaterLang.class);
            } catch (IOException e) {
                throw new RuntimeException(MODID + " Problem occurred when trying to load language: ", e);
            }
        }
        else {
            language = new TaterLang();
        }
        language.saveLanguageFile(file);

        return language;
    }

    /**
     * Saves the language to the given file.
     *
     * @param file file to save config to
     */
    public void saveLanguageFile(File file) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            getLogger().error("Problem occurred when saving language file: " + e.getMessage());
        }
    }
}
