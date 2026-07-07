package sk.uniza.fri.sklad.osoby;

import sk.uniza.fri.sklad.miestnosti.MalySklad;
import sk.uniza.fri.sklad.predmety.Tovar;

/**
 * Zakaznik je potomok Osoby, ktory obsahuje dalsie atributy a metody specificke pre zakaznika.
 * @author Juraj
 */
public class Zakaznik extends Osoba {
    private final MalySklad prijemTovaru;   //miestnost na prijem tovaru
    private final MalySklad vydajTovaru;    //miestnost na vydaj tovaru

    /**
     * Vytvara zakaznika s menom, priezivskom, prijmomTovaru a vydajomTovaru z parametra.
     * @param meno meno zakaznika
     * @param priezvisko priezvisko zakaznika
     * @param prijemTovaru miestnost na prijem tovaru
     * @param vydajTovaru miestnost na vydaj tovaru
     */
    public Zakaznik(String meno, String priezvisko, MalySklad prijemTovaru, MalySklad vydajTovaru) {
        super(meno, priezvisko);
        this.prijemTovaru = prijemTovaru;
        this.vydajTovaru = vydajTovaru;
    }

    /**
     * Ulozi tovar z parametra do miestnosti urcenej na prijem tovaru.
     * @param tovar tovar na ulozenie
     */
    public void ulozTovar(Tovar tovar) {
        this.prijemTovaru.getRegal().ulozTovar(tovar);
    }

    /**
     * Vyzdvihne tovar z parametra z miestnosti urcenej na vydaj tovaru.
     * @param tovar tovar na vyzdvihnutie
     */
    public void vyzdvihniTovar(Tovar tovar) {
        this.vydajTovaru.getRegal().odoberTovar(tovar.getId());
    }


}
