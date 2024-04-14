package fosost;

import arc.Core;
import arc.util.*;
import arc.util.serialization.Jval;

import static mindustry.Vars.*;
import static mindustry.mod.Mods.LoadedMod;

// stolen from ApsZoldat/MindustryMappingUtilities, thanks
public class AutoUpdater {
    public AutoUpdater() {
        LoadedMod mod = mods.getMod(FOSOSTMod.class);
        Http.get(ghApi + "/repos/" + mod.getRepo() + "/releases", result -> {
            var json = Jval.read(result.getResultAsString());
            Jval.JsonArray releases = json.asArray();

            if (releases.size == 0) return;

            String latest = releases.first().getString("tag_name");
            String current = mod.meta.version;
            float latestFloat = Float.parseFloat(latest.replace("v", "").replaceFirst("[.]", ""));
            float currentFloat = Float.parseFloat(current.replace("v", "").replaceFirst("[.]", ""));

            Log.info("[FOS OST] Current version: @, latest version: @", currentFloat, latestFloat);

            if (currentFloat >= latestFloat) {
                Log.info("[FOS OST] Mod is on the latest version.");
                return;
            }

            ui.showConfirm("@fosost.updateavailable.title", Core.bundle.format("fosost.updateavailable.description", latest), () -> {
                ui.mods.githubImportMod(mod.getRepo(), true);
            });
        }, this::error);
    }

    void error(Throwable e) {
        Log.err("[FOS OST] Failed to check for updates!\nCause", e);
    }
}
