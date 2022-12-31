package fosost.content;

import arc.audio.Music;
import mindustry.Vars;

public class FOSMusic {
    public static Music
    /* Lumoni */ abandoned, scavenger, slowdown,
    /* Uxerd */ dive,
    /* bosses */ livingSteam, uncountable;

    public static void load() {
        abandoned = loadMusic("abandoned");
        scavenger = loadMusic("scavenger");
        slowdown = loadMusic("slowdown");

        dive = loadMusic("dive");

        livingSteam = loadMusic("living-steam");
        uncountable = loadMusic("uncountable");
    }

    static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
