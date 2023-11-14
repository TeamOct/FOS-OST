package fosost.content;

import arc.audio.Music;
import mindustry.Vars;

public class FOSMusic {
    public static Music
    /* Lumoni ambient */ abandoned, slowdown, local, source,
    /* Lumoni dark */ ultraSaw,
    /* Uxerd */ dive,
    /* bosses */ livingSteam, uncountable, scavenger, unstableCore;

    public static void load() {
        abandoned = loadMusic("abadoned_");
        slowdown = loadMusic("SlowDown");
        local = loadMusic("local");
        source = loadMusic("source");

        ultraSaw = loadMusic("ultraSaw");

        dive = loadMusic("dive");

        livingSteam = loadMusic("LivingSteam");
        uncountable = loadMusic("Uncountable");
        scavenger = loadMusic("Scavenger");
        unstableCore = loadMusic("Unstable_core");
    }

    static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
