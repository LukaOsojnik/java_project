package hr.javafx.realestate.javafxmanagementsystem.enum1;

public enum County {

    GRAD_ZAGREB("Grad Zagreb"),
    BJELOVARSKO_BILOGORSKA_ZUPANIJA("Bjelovarsko-bilogorska"),
    BRODSKO_POSAVSKA_ZUPANIJA("Brodsko-posavska"),
    DUBROVACKO_NERETVANSKA_ZUPANIJA("Dubrovačko-neretvanska"),
    ISTARSKA_ZUPANIJA("Istarska"),
    KARLOVACKA_ZUPANIJA("Karlovačka"),
    KOPRIVNICKO_KRIZEVAC_ZUPANIJA("Koprivničko-križevačka"),
    KRAPINSKO_ZAGORSKA_ZUPANIJA("Krapinsko-zagorska"),
    LICKO_SENJSKA_ZUPANIJA("Ličko-senjska"),
    MEDIMURSKA_ZUPANIJA("Međimurska"),
    OSJECKO_BARANJSKA_ZUPANIJA("Osječko-baranjska"),
    POZESKO_SLAVONSKA_ZUPANIJA("Požeško-slavonska"),
    PRIMORSKO_GORANSKA_ZUPANIJA("Primorsko-goranska"),
    SIBENSKO_KNINSKA_ZUPANIJA("Šibensko-kninska"),
    SISACKO_MOSLAVACKA_ZUPANIJA("Sisačko-moslavačka"),
    SPLITSKO_DALMATINSKA_ZUPANIJA("Splitsko-dalmatinska"),
    VARAZDINSKA_ZUPANIJA("Varaždinska"),
    VIROVITICKO_PODRAVSKA_ZUPANIJA("Virovitičko-podravska"),
    VUKOVARSKO_SRIJEMSKA_ZUPANIJA("Vukovarsko-srijemska"),
    ZADARSKA_ZUPANIJA("Zadarska"),
    ZAGREBACKA_ZUPANIJA("Zagrebačka");

    private final String naziv;

    County(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}

