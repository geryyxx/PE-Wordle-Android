package com.example.worlde;

public class WordList {

    public static String[] getWords(String language) {
        if (language.equals("en")) {
            return EN_WORDS;
        } else {
            return HU_WORDS;
        }
    }

    private static final String[] HU_WORDS = {
            "ALMÁK","KÖRTE","HÁZAK","VIRÁG","KUTYA",
            "FENYŐ","KAPUK","NAPOS","TÁBLA","KÖNYV",
            "GERGŐ","LÁMPA","AJTÓK","VÁROS","NYELV",
            "ERDŐK","FOLYÓ","FANNI","SZÉNA","TÓCSA",
            "CSIGA","MADÁR","HALAK","TEHÉN","LOVAK",
            "KERÉK","FEJEK","BARNI","MEGGY","BOGÁR",
            "KÉPEK","KÖRÖM","ABLAK","KANÁL","HÍDAK",
            "POHÁR","VILLA","NYÁRI","PAJTA","FOLYÓ",
            "KŐFAL","ANTIK","ARANY","EZÜST","BETEG",
            "ÁLLAT","ÁPOLÓ","BARNA","PATAK","CUKOR"
    };

    private static final String[] EN_WORDS = {
            "APPLE","HOUSE","PLANT","TRAIN","WATER",
            "CHAIR","BREAD","LIGHT","MOUSE","GREEN",
            "TABLE","PHONE","MUSIC","NIGHT","WORLD",
            "SMILE","LAUGH","CRANE","STONE","RIVER",
            "MONEY","POWER","SWEET","SUGAR","SALTY",
            "EARTH","OCEAN","CLOUD","STORM","WINDY",
            "FLAME","GRASS","LEMON","BERRY","PEACH",
            "GRAPE","MANGO","CHAOS","PEACE","BRAVE",
            "SMART","QUICK","HEART","HAPPY","ANGRY",
            "TIRED","FOCUS","DREAM","WRITE","READY"
    };
}
