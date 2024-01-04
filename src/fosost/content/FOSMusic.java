package fosost.content;

import arc.audio.Music;
import mindustry.Vars;

public class FOSMusic {
    public static Music
    /* Lumoni ambient */ abandoned, slowdown, local, source, darkNest, alive,
    /* Lumoni dark */ ultraSaw, rustyBlood, infected,
    /* Uxerd */ dive,
    /* bosses */ livingSteam, tcfn, scavenger, unstableCore;

    public static void load() {
        abandoned = loadMusic("ambient/abadoned_");
        slowdown = loadMusic("ambient/SlowDown");
        local = loadMusic("ambient/local");
        source = loadMusic("ambient/source");
        darkNest = loadMusic("ambient/Dark_nest");
        alive = loadMusic("ambient/alive_");

        ultraSaw = loadMusic("dark/ultraSaw");
        rustyBlood = loadMusic("dark/Rusty_blood");
        infected = loadMusic("dark/Infected");

        dive = loadMusic("ambient/dive");

        livingSteam = loadMusic("boss/LivingSteam");
        tcfn = loadMusic("boss/they_come_from_nowhere");
        scavenger = loadMusic("boss/Scavenger");
        unstableCore = loadMusic("boss/Unstable_core");
    }

    static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
