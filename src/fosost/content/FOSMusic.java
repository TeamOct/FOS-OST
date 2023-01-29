package fosost.content;

import arc.audio.Music;
import mindustry.Vars;

public class FOSMusic {
    public static Music
    /* Lumoni */ abandoned, scavenger, slowdown,
    /* Uxerd */ dive,
    /* bosses */ livingSteam, uncountable;

    public static void load() {
        abandoned = loadMusic("abadoned_");
        scavenger = loadMusic("Scavenger");
        slowdown = loadMusic("SlowDown");

        dive = loadMusic("dive");

        livingSteam = loadMusic("LivingSteam");
        uncountable = loadMusic("Uncountable");
    }

    static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
