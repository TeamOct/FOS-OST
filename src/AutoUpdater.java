import arc.util.*;
import arc.util.serialization.Jval;
import fosost.FOSOSTMod;

import static mindustry.Vars.*;
import static mindustry.mod.Mods.LoadedMod;

// stolen from ApsZoldat/MindustryMappingUtilities, thanks
public class AutoUpdater {
    public AutoUpdater() {
        LoadedMod mod = mods.getMod(FOSOSTMod.class);
        Http.get(ghApi + "/repos/" + mod.getRepo() + "/releases", result -> {
            var json = Jval.read(result.getResultAsString());
            Jval.JsonArray releases = json.asArray();

            if(releases.size == 0) return;

            String latest = releases.first().getString("tag_name");
            String current = mod.meta.version;
            float latestFloat = Float.parseFloat(latest.replace("v", ""));
            float currentFloat = Float.parseFloat(current.replace("v", ""));
            if(currentFloat >= latestFloat) {
                Log.info("[FOS OST] Mod is on the latest version.");
                return;
            }

            ui.showConfirm("@fosost.updateavailable.title", "@fosost.updateavailable.description", () -> {
                ui.mods.githubImportMod(mod.getRepo(), true);
            });
        }, this::error);
    }

    void error(Throwable e) {
        Log.err("[FOS OST] Failed to check for updates!\n@", e);
    }
}
