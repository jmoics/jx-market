package org.jcs.esjp.util;

import java.util.HashMap;
import java.util.Map;

public class Settings
{
    public enum FactorySettings
    {
        ASTILLERO("-----Astillero"),
        MUELLE("-----Muelle"),
        ESTACION_COMERCIAL("-----Estación comercial"),
        FACTORIA("-----Factoría"),
        MENA("-----Mena"),
        SILICIO("-----Obleas de Silicio"),
        NVIDIUM("-----Nividium"),
        HIELO("-----Hielo"),
        VACIO("-----");

        private final String key;

        private FactorySettings(final String _key)
        {
            this.key = _key;
        }

        public String getKey()
        {
            return this.key;
        }
    }

    public enum RaceSettings
    {
        ARGON("Argon", "src/main/resources/images/argon.png"),
        TERRAN("Terran", "src/main/resources/images/terran.png"),
        BORON("Boron", "src/main/resources/images/boron.png"),
        TELADI("Teladi", "src/main/resources/images/teladi.png"),
        PARANID("Paranid", "src/main/resources/images/paranid.png"),
        SPLIT("Split", "src/main/resources/images/split.png"),
        PIRATAS("Piratas", "src/main/resources/images/pirata.png"),
        XENON("Xenon", "src/main/resources/images/xenon.png"),
        KHAAK("Kha'ak", "src/main/resources/images/khaak.png"),
        YAKI("Yaki", "src/main/resources/images/yaki.png"),
        UNKNOWN("Desconocida", "src/main/resources/images/desconocido.png"),
        GONER("Goner", "src/main/resources/images/goner.png");

        private final String key;
        private final String color;

        private RaceSettings(final String _key,
                             final String _color)
        {
            this.key = _key;
            this.color = _color;
        }

        public String getKey()
        {
            return this.key;
        }

        public String getColor()
        {
            return this.color;
        }
    }

    public static Map<String, String> RACE2COLOR = new HashMap<String, String>();
    static {
        Settings.RACE2COLOR.put("Argon", "src/main/resources/images/argon.png");
        Settings.RACE2COLOR.put("Terran", "src/main/resources/images/terran.png");
        Settings.RACE2COLOR.put("Boron", "src/main/resources/images/boron.png");
        Settings.RACE2COLOR.put("Teladi", "src/main/resources/images/teladi.png");
        Settings.RACE2COLOR.put("Paranid", "src/main/resources/images/paranid.png");
        Settings.RACE2COLOR.put("Split", "src/main/resources/images/split.png");
        Settings.RACE2COLOR.put("Piratas", "src/main/resources/images/pirata.png");
        Settings.RACE2COLOR.put("Xenon", "src/main/resources/images/xenon.png");
        Settings.RACE2COLOR.put("Kha'ak", "src/main/resources/images/khaak.png");
        Settings.RACE2COLOR.put("Yaki", "src/main/resources/images/yaki.png");
        Settings.RACE2COLOR.put("Goner", "src/main/resources/images/goner.png");
        Settings.RACE2COLOR.put("Desconocida", "src/main/resources/images/desconocido.png");
    }

    public enum GateSettings
    {
        NORTH("NO"),
        SOUTH("SO"),
        EAST("EA"),
        WEST("WE");

        private final String key;

        private GateSettings(final String _key)
        {
            this.key = _key;
        }

        public String getKey()
        {
            return this.key;
        }
    }
}