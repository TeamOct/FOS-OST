package fosost;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.*;
import mindustry.game.*;
import mindustry.type.*;

import static mindustry.Vars.*;
import static mindustry.game.EventType.*;

public class MusicEntries {
    private static Seq<MusicEntry> all = new Seq<>();

    public static void applyAll() {
        for (var e : all) {
            e.apply();
        }
    }

    public abstract static class MusicEntry {
        public Music music;

        public void apply() {}
    }

    public static class AmbientMusicEntry extends MusicEntry {
        private Planet planet;
        private boolean dark;
        private boolean survival;

        public AmbientMusicEntry(Music music, String planet, boolean dark, boolean survival) {
            this.music = music;
            this.planet = content.planet("fos-" + planet);
            this.dark = dark;
            this.survival = survival;

            all.add(this);
        }

        @Override
        public void apply() {
            Events.on(WorldLoadEvent.class, e -> {
                if (state.rules.planet != planet) return;
                if (survival && (state.rules.mode() != Gamemode.survival || state.rules.waveTeam != Team.get(70))) return;

                if (dark) {
                    control.sound.darkMusic.add(music);
                } else {
                    control.sound.ambientMusic.add(music);
                }
            });
        }
    }

    public static class BossMusicEntry extends MusicEntry {
        private UnitType boss;

        public BossMusicEntry(Music music, String name) {
            this.music = music;
            boss = content.unit("fos-" + name);

            all.add(this);
        }

        @Override
        public void apply() {
            Events.on(UnitSpawnEvent.class, e -> {
                if (e.unit != null && e.unit.type == boss && e.unit.team == state.rules.waveTeam) {
                    Time.run(179f, () -> {
                        // this is my first time ever trying to DIRECTLY play music.
                        Reflect.invoke(control.sound, "playOnce", new Object[]{music}, Music.class);
                    });
                }
            });
        }
    }
}
