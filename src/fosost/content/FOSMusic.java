package fosost.content;

import arc.audio.Music;
import mindustry.Vars;

public class FOSMusic {
    public static Music
    /* Lumoni */ abandoned, slowdown, local, source,
    /* Uxerd */ dive,
    /* bosses */ livingSteam, uncountable, scavenger;

    public static void load() {
        abandoned = loadMusic("abadoned_");
        slowdown = loadMusic("SlowDown");
        local = loadMusic("local");
        source = loadMusic("source");

        dive = loadMusic("dive");

        livingSteam = loadMusic("LivingSteam");
        uncountable = loadMusic("Uncountable");
        scavenger = loadMusic("Scavenger");
    }

    static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
