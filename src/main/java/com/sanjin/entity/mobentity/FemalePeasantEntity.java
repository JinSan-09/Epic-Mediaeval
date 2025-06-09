package com.sanjin.entity.mobentity;

import com.sanjin.entity.AbstractPeasantEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FemalePeasantEntity extends AbstractPeasantEntity {

    private static final String[] FEMALE_FIRST_NAMES = {
            "Adelaide","Agnes","Aldith","Alice","Alianor","Amice","Anabel","Annis","Arabella","Aveline",
            "Avicia","Beatrice","Berengaria","Bertha","Blanche","Brunhild","Cecily","Clarice","Constance", "Cristina",
            "Dameta","Diana","Dulcia","Edelina","Edith","Edyth","Ela","Eleanor","Elfreda","Elisabeth",
            "Ellyn","Emeline","Emma","Ermentrude","Estrild","Ethel","Etheldreda","Eva","Evelina","Felice",
            "Fiona","Flora","Freya","Galiena","Gillian","Gisela","Giselle","Gundred","Gunnhild","Gwenllian",
            "Hadewisa","Hawise","Helen","Heloise","Hildegard","Idonea","Ida","Isabella","Isolde","Itta",
            "Jacoba","Jane","Joan","Johanna","Judith","Juliana","Justina","Katherine","Lavinia","Leofgyth",
            "Leticia","Lora","Loretta","Lucia","Lucretia","Lucy","Mabel","Magdalena","Mahtild","Margaret",
            "Margery","Marian","Marion","Matilda","Maud","Melisende","Millicent","Muriel","Nesta","Nicola",
            "Nora","Odilia","Oliva","Olwen","Orabel","Oriana","Osanne","Pavia","Petronilla","Philippa",
            "Prudence","Rafaela","Ragnhild","Richeza","Richenda","Rohese","Rolanda","Rosamund","Rosalind","Rowena",
            "Sabina","Salvia","Sancia","Sibilla","Sibylla","Sigelgiefu","Sigourney","Silva","Solene","Stella",
            "Sunniva","Swanhild","Sybil","Tabitha","Tamsin","Theda","Theodora","Thora","Thyra","Tilda",
            "Tisbe","Trina","Ula","Ulrica","Ursula","Vera","Verena","Viola","Violette","Vita",
            "Walda","Walburga","Wendy","Wenna","Wilhelmina","Winifred","Wulfhild","Ygraine","Ylva","Ysabel",
            "Ysolde","Yvette","Zelma","Zena","Zenobia","Zilla","Zora","Zuleika","Aenor","Aisley",
            "Amelot","Anemone","Arietta","Arlette","Audrey","Avice","Baldith","Benedetta","Bess","Betha",
            "Burgunda","Cateline","Celestria","Clemence","Criseyde","Damiana","Desdemona","Donata","Drusilla","Elvina",
            "Engeltrude","Ermina","Ethna","Euphemia","Flandrina","Gaetana","Griselda","Helisent","Herleva","Hilaria",
            "Hodierna","Hughonia","Imeltrude","Jocelyn","Lettice","Margot","Meliora","Melusine","Mirabel","Mireille"
    };
    private static final String[] FEMALE_LAST_NAMES = {
            "Abell","Ashdown","Atwood","Bailey","Baker","Barclay","Beaumont","Bennet","Berwick","Blackwood",
            "Blake","Blount","Bolton","Boswell","Bracken","Bray","Briar","Bright","Brun","Buchanan",
            "Butler","Calvert","Camden","Campbell","Carlyle","Carver","Chandler","Chapman","Chester","Clarke",
            "Clayton","Clemens","Colby","Colville","Compton","Conyers","Cooper","Cornwall","Crawford","Cresswell",
            "Croft","Curzon","Darcy","Darwin","De Clare","De Neville","De Vere","Denholm","Devlin","Digby",
            "Draper","Dunstan","Durant","Eaton","Eldridge","Ellery","Elwood","Essex","Fairfax","Falkner",
            "Farley","Fawcett","Ferguson","Ferrers","Fisher","FitzAlan","FitzRoy","Fletcher","Flint","Forbes",
            "Forester","Foster","Fox","Frampton","Gardener","Garland","Gifford","Godfrey","Golding","Goodwin",
            "Goring","Grant","Grantham","Graves","Green","Greenwood","Grey","Griffin","Grimsby","Grosvenor",
            "Grover","Hadley","Hale","Hall","Harcourt","Harding","Hargrave","Harman","Harper","Hawke",
            "Hayward","Helmsley","Hemming","Heron","Hewitt","Hill","Hilton","Holden","Holt","Hood",
            "Hooper","Hornby","Howard","Howe","Hudson","Hughes","Humphrey","Hunt","Hurst","Hyde",
            "Inglewood","Jagger","Jarvis","Jenner","Jennings","Jervis","Keats","Keller","Kemp","Kenway",
            "Kerr","Kingsley","Knight","Lacey","Landon","Langley","Langston","Leigh","Leland","Lewin",
            "Lindsay","Lister","Lockhart","Longford","Lovelace","Loxley","Lytton","Maitland","Malory","Manning",
            "March","Marlowe","Marshall","Martel","Mason","Maxton","Medley","Mercer","Merton","Milburn",
            "Milford","Milton","Molyneux","Montagu","Montfort","Moore","Moray","Moreton","Mortimer","Mowbray",
            "Neville","Newbury","Norfolk","Norman","Northcott","Norton","Oakley","Ormsby","Osborne","Overton",
            "Paine","Palmer","Parker","Parr","Pate","Payne","Peel","Pelham","Peverell","Phipps",
            "Pickering","Piers","Porter","Poyntz","Prescott","Preston","Pryor","Radford","Raleigh","Ramsey"
    };
    private static final String[] FEMALE_TEXTURES = {
            "textures/entity/female_peasant_1",
            "textures/entity/female_peasant_2",
            "textures/entity/female_peasant_3"
    };

    public FemalePeasantEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public String[] getDefaultTexturePaths() {
        return FEMALE_TEXTURES;
    }

    @Override
    public boolean isSlimDefault() {
        return true;
    }

    @Override
    public String[] getRandomFirstNameOptions() {
        return FEMALE_FIRST_NAMES;
    }

    @Override
    public String[] getRandomLastNameOptions() {
        return FEMALE_LAST_NAMES;
    }

    @Override
    protected SoundEvent getHurtSoundEvent() {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSoundEvent() {
        return SoundEvents.PLAYER_DEATH;
    }

    @Override
    protected void handlePlayerInteraction(@NotNull Player player) {
        player.displayClientMessage(
                Component.literal("Greetings! I'm " + this.getPeasantName() + ", a peasant woman of this village."), false);
        this.targetPlayer = player;
    }
}
