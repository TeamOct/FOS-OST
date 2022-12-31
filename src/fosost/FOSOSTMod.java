package fosost;

import arc.*;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.*;
import fosost.content.FOSMusic;
import mindustry.*;
import mindustry.audio.SoundControl;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.game.SpawnGroup;
import mindustry.mod.*;
import mindustry.type.*;

import static fosost.content.FOSMusic.*;
import static mindustry.Vars.*;

public class FOSOSTMod extends Mod {
    public Seq<Music> uxerdAmbient = new Seq<>();
    public Seq<Music> lumoniAmbient = new Seq<>();

    public Seq<Music> vAmbient, vDark, vBoss;

    protected Planet curPlanet;

    protected SoundControl control = Vars.control.sound;

    //mod planets
    private Planet lumoni, uxerd;

    //mod bosses
    private UnitType citadel, legion;

    public FOSOSTMod() {
        Events.on(ContentInitEvent.class, e -> {
            lumoni = content.planet("fos-lumoni");
            uxerd = content.planet("fos-uxerd");

            citadel = content.unit("fos-citadel");
            legion = content.unit("fos-legion");
        });
        Mods.LoadedMod fos = mods.locateMod("fos");
        if (fos == null) {
            Log.err("[FOS-OST] Base mod disabled or not found. Skipping.");
            return;
        }

        Events.on(ClientLoadEvent.class, e -> reload());
        //change the music to modded OST
        Events.on(WorldLoadEvent.class, e -> {
            Sector sector = state.rules.sector;
            if (sector != null) curPlanet = sector.planet;
            else return;

            if (curPlanet == uxerd) {
                control.ambientMusic = control.darkMusic = uxerdAmbient;
            } else if (curPlanet == lumoni) {
                control.ambientMusic = control.darkMusic = lumoniAmbient;
            }
        });
        Events.on(WaveEvent.class, e -> {
            SpawnGroup boss = state.rules.spawns.find(group -> group.getSpawned(state.wave - 2) > 0 && group.effect == StatusEffects.boss);
            if (boss == null) return;

            if (boss.type == citadel) {
                control.bossMusic = Seq.with(livingSteam);
            } else if (boss.type == legion) {
                control.bossMusic = Seq.with(uncountable);
            } else {
                control.bossMusic = vBoss;
            }
        });
        //this should hopefully reset the music back to vanilla
        Events.on(StateChangeEvent.class, e -> {
            if (curPlanet == uxerd || curPlanet == lumoni) return;

            control.ambientMusic = vAmbient;
            control.darkMusic = vDark;
            control.bossMusic = vBoss;
        });
    }

    void reload() {
        uxerdAmbient = Seq.with(dive);
        lumoniAmbient = Seq.with(abandoned, scavenger, slowdown);

        vAmbient = control.ambientMusic;
        vDark = control.darkMusic;
        vBoss = control.bossMusic;
    }

    @Override
    public void loadContent() {
        FOSMusic.load();
    }
}
