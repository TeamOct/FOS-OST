package fosost;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.content.StatusEffects;
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
                if (survival && state.rules.mode() != Gamemode.survival) return;

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
            Events.on(WaveEvent.class, e -> {
                SpawnGroup bossSpawn = state.rules.spawns.find(group -> group.getSpawned(state.wave - 2) > 0 && group.effect == StatusEffects.boss);
                if (bossSpawn == null) return;

                if (bossSpawn.type == boss) {
                    control.sound.bossMusic = Seq.with(music);
                }
            });
        }
    }
}
