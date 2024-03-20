package fosost;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import fosost.content.FOSMusic;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.game.EventType.*;
import mindustry.mod.*;

import static fosost.MusicEntries.*;
import static fosost.content.FOSMusic.*;
import static mindustry.Vars.*;

public class FOSOSTMod extends Mod {
    public Seq<Music> vAmbient, vDark, vBoss;

    private SoundControl control = Vars.control.sound;

    public FOSOSTMod() {
        Mods.LoadedMod fos = mods.locateMod("fos");
        if (fos == null) {
            Log.err("[FOS-OST] Base mod disabled or not found. Skipping.");
            return;
        }

        Events.on(MusicRegisterEvent.class, e -> {
            reload();

            //TODO: this code looks kinda repetitive

            new AmbientMusicEntry(abandoned, "lumoni", false, false);
            new AmbientMusicEntry(slowdown, "lumoni", false, false);
            new AmbientMusicEntry(local, "lumoni", false, false);
            new AmbientMusicEntry(source, "lumoni", false, false);
            new AmbientMusicEntry(darkNest, "lumoni", false, true);
            new AmbientMusicEntry(alive, "lumoni", false, true);

            new AmbientMusicEntry(ultraSaw, "lumoni", true, false);
            new AmbientMusicEntry(rustyBlood, "lumoni", true, true);
            new AmbientMusicEntry(infected, "lumoni", true, true);
            new AmbientMusicEntry(reflectionOverdrive, "lumoni", true, false);
            new AmbientMusicEntry(deadlyWeight, "lumoni", true, false);

            new AmbientMusicEntry(dive, "uxerd", false, false);

            new BossMusicEntry(livingSteam, "citadel");
            new BossMusicEntry(tcfn, "legion");
            //TODO: unstable core

            applyAll();
        });

        //clear/reset music lists on map load
        Events.on(WorldLoadEvent.class, e -> {
            if (state.rules.planet.isVanilla()) {
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            } else if (state.rules.planet.minfo.mod.name.equals("fos")) {
                control.ambientMusic.clear();
                control.darkMusic.clear();
                control.bossMusic.clear();
            }
        });
    }

    void reload() {
        FOSMusic.load();

        vAmbient = control.ambientMusic.copy();
        vDark = control.darkMusic.copy();
        vBoss = control.bossMusic.copy();
    }
}
