package com.sanjin.entity.mobentity;

import com.sanjin.entity.AbstractPeasantEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MalePeasantEntity extends AbstractPeasantEntity {

    private static final String[] MALE_FIRST_NAMES = {
            "Alaric","Aldwin","Ansel","Archibald","Armand","Arnold","Athelstan","Auberon","Augustin","Baldric",
            "Bartram","Basil","Benedict","Berenger","Bernard","Bertram","Blaise","Boniface","Cadoc","Cedric",
            "Charles","Clement","Cuthbert","Cyric","Darwin","Denholm","Dietrich","Dorian","Dunstan","Eadric",
            "Edgar","Edmund","Edward","Edwin","Elric","Emeric","Enguerrand","Ethelred","Everard","Faramond",
            "Fenton","Ferdinand","Fitzroy","Francis","Frederick","Fulbert","Gareth","Gaston","Gavin","Geoffrey",
            "Gerald","Gervase","Gideon","Gilbert","Godfrey","Godric","Goswin","Gregory","Griffith","Guillaume",
            "Gunnar","Hadrian","Halbert","Hamlin","Hardwin","Harold","Hartwin","Hawkin","Hector","Helmut",
            "Henry","Herbert","Hereward","Herve","Hubert","Hugh","Humphrey","Hywel","Ivor","Ivo","Jacques",
            "Jasper","Jerome","Joachim","Joel","John","Jonas","Jordan","Joris","Joscelin","Julian",
            "Kenelm","Kennard","Lambert","Lancelot","Laurence","Leofric","Leonard","Leopold","Lionel","Lorcan",
            "Lothar","Lucian","Lucius","Ludwig","Magnus","Manfred","Marcel","Martin","Matthias","Maurice",
            "Maximilian","Miles","Milo","Mortimer","Nathaniel","Nicodemus","Nigel","Norbert","Odo","Oliver",
            "Orson","Osbert","Oscar","Osric","Oswald","Otbert","Otto","Pascal","Percival","Peter",
            "Philbert","Philip","Quentin","Rainier","Randolph","Raymond","Reginald","Reinhold","Remy","Reynard",
            "Richard","Robert","Robin","Roland","Rollo","Rorik","Rudiger","Rufus","Rupert","Samson",
            "Samuel","Siegfried","Sigmund","Simon","Stephen","Tancred","Theobald","Theodoric","Theodore","Thierry",
            "Thomas","Timothy","Tristan","Tybalt","Ulric","Uther","Valerian","Victor","Vincent","Vivian",
            "Walter","Warin","Wendell","Wilbert","Wilfred","Wilhelm","William","Wulfram","Xavier","Yorick"
    };

    private static final String[] MALE_LAST_NAMES = {
            "Abbott","Aldridge","Ashford","Ashworth","Atwood","Bailey","Bancroft","Barker","Barlow","Barrett",
            "Beaumont","Bedford","Bennett","Berkeley","Bertram","Bishop","Blackwood","Blake","Blanchard","Bourne",
            "Bradford","Bray","Brennan","Brett","Briggs","Bright","Bristol","Brock","Brook","Browning",
            "Burton","Butler","Byrne","Carver","Chamberlain","Chandler","Chapman","Chester","Clarke","Clifford",
            "Clive","Cobb","Cohen","Cole","Collier","Colton","Compton","Conrad","Cooke","Cooper",
            "Cornell","Corwin","Crawford","Cromwell","Cross","Dalton","Davenport","Davies","Dayne","De Clare",
            "De La Mare","Devereux","Dickens","Draper","Dudley","Dunbar","Durham","Easton","Eaton","Ellis",
            "Emerson","Fairfax","Farley","Farrar","Fenton","Fielding","Fisk","Fitzgerald","Fletcher","Flint",
            "Forbes","Foster","Fox","Francis","Franklin","Fraser","Frost","Garland","Garner","Gates",
            "Godwin","Goodman","Granger","Graves","Greenwood","Grey","Griffin","Griffith","Grover","Gurney",
            "Hale","Hammond","Harcourt","Harding","Harlan","Harrington","Hart","Hastings","Hawkins","Hayward",
            "Hazel","Heron","Hewitt","Hilton","Hobbes","Hollis","Holmes","Howard","Hudson","Hughes",
            "Hunt","Hunter","Hyde","Ingram","Irving","Jarvis","Jennings","Kemp","Kenward","Kingsley",
            "Kirk","Langley","Langston","Lawrence","Leclair","Leicester","Lennox","Lester","Levingston","Lightfoot",
            "Lloyd","Lockwood","Long","Lowell","Lynch","Lyndon","Mace","Maddox","Mallory","Manning",
            "March","Marsh","Marshall","Martin","Mason","Maynard","Melrose","Mercer","Milburn","Milton",
            "Montague","Montfort","Moore","Morley","Morton","Neville","Norman","North","Oakley","Osborne",
            "Osgood","Overton","Parker","Parr","Payne","Penrose","Peverell","Pike","Porter","Poyntz",
            "Prescott","Prichard","Quincy","Radcliffe","Ravenwood","Redfern","Remington","Renard","Rhodes","Rivers",
            "Rochester","Rowan","Roxley","Rush","Rutland","Salisbury","Sawyer","Selwyn","Sheldon","Shelley",
            "Shepherd","Sherwood","Shirley","Sinclair","Slade","Sloane","Somerset","Spencer","Stafford","Stanley",
            "Stanton","Stark","Stone","Stratford","Sutton","Swann","Tanner","Thatcher","Thorpe","Tilbury",
            "Tilling","Townsend","Trask","Tremaine","Trent","Trevino","Tucker","Turpin","Tyrell","Underhill"
    };

    private static final String[] MALE_TEXTURES = {};


    public MalePeasantEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected String[] getDefaultTexturePaths() {
        return MALE_TEXTURES;
    }

    @Override
    protected boolean isSlimDefault() {
        return false;
    }

    @Override
    protected String[] getRandomFirstNameOptions() {
        return MALE_FIRST_NAMES;
    }

    @Override
    protected String[] getRandomLastNameOptions() {
        return MALE_LAST_NAMES;
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
    protected void handlePlayerInteraction(Player player) {
        player.displayClientMessage(
                Component.literal("Hello! I'm " + this.getPeasantName() + ", a local peasant farmer."), false);
        this.targetPlayer = player;
    }
}
