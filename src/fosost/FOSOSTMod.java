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

            new AmbientMusicEntry(abandoned, "lumoni", AmbientMusicEntry.MusicType.ambient, false);
            new AmbientMusicEntry(slowdown, "lumoni", AmbientMusicEntry.MusicType.ambient, false);
            new AmbientMusicEntry(ultraSaw, "lumoni", AmbientMusicEntry.MusicType.dark, false);
            new AmbientMusicEntry(local, "lumoni", AmbientMusicEntry.MusicType.ambient, true);
            new AmbientMusicEntry(source, "lumoni", AmbientMusicEntry.MusicType.ambient, true);

            new AmbientMusicEntry(dive, "uxerd", AmbientMusicEntry.MusicType.ambient, false);

            new BossMusicEntry(livingSteam, "citadel");
            new BossMusicEntry(uncountable, "legion");
            //TODO: unstable core

            applyAll();
        });

        //clear/reset music lists on map load
        Events.on(WorldLoadBeginEvent.class, e -> {
            Log.info("music reset?");
            if (state.rules.planet != content.planet("fos-lumoni") && state.rules.planet != content.planet("fos-uxerd")) {
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            } else {
                control.ambientMusic.clear();
                control.darkMusic.clear();
                control.bossMusic.clear();
            }
        });
    }

    void reload() {
        FOSMusic.load();

        vAmbient = control.ambientMusic;
        vDark = control.darkMusic;
        vBoss = control.bossMusic;
    }
}
