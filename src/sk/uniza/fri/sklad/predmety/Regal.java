package sk.uniza.fri.sklad.predmety;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Trieda Regal vytvara regal so zadanou kapacitou, ktory uschovava tovar.
 * @author Juraj
 */
public class Regal implements Serializable {
    private final Tovar[] zoznamTovaru;   //zoznam tovaru v regali

    /**
     * Vytvori regal so zadanou kapacitou, bez tovaru.
     * @param kapacita kapacita regala
     */
    public Regal(int kapacita) {
        this.zoznamTovaru = new Tovar[kapacita];
        Arrays.fill(this.zoznamTovaru, null);
    }

    /**
     * Vracia zoznam tovaru v regali.
     * @return zoznam tovaru
     */
    public Tovar[] getZoznamTovaru() {
        return this.zoznamTovaru;
    }

    /**
     * Vracia konkretny tovar s danym ID.
     * @param id id tovaru
     * @return tovar
     */
    public Tovar getTovar (String id) {
        for (Tovar tovar : this.zoznamTovaru) {
            if (tovar != null && tovar.getId().equals(id)) {
                return tovar;
            }
        }
        System.out.println("Tovar nebol najdeny.");
        return null;
    }


    /**
     * Ulozi tovar z parametra do regalu, ak to je mozne.
     * @param tovar tovar na ulozenie
     */
    public void ulozTovar(Tovar tovar) {
        if (tovar == null) {
            System.out.println("Nemam ziaden tovar.");
            return;
        }
        for (int i = 0; i < this.zoznamTovaru.length; i++) {
            if (this.zoznamTovaru[i] == null) {
                this.zoznamTovaru[i] = tovar;
                System.out.println("Tovar bol ulozeny.");
                return;
            }
        }
        System.out.println("Regal je plny.");
    }

    /**
     * Odoberie tovar so zadanym ID z regalu.
     * @param id id tovaru
     */
    public void odoberTovar(String id) {
        for (int i = 0; i < this.zoznamTovaru.length; i++) {
            if (this.zoznamTovaru[i] != null && this.zoznamTovaru[i].getId().equals(id)) {
                this.zoznamTovaru[i] = null;
                System.out.println("Tovar bol odobrany.");
                return;
            }
        }
        System.out.println("Tovar nebol najdeny.");
    }

    /**
     * Zisti ci ma regal volne miesto
     * @return boolean
     */
    public boolean maVolneMiesto() {
        for (Tovar tovar : this.zoznamTovaru) {
            if (tovar == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Zisti ci je regal prazdny.
     * @return boolean
     */
    public boolean jePrazdny() {
        for (Tovar tovar : this.zoznamTovaru) {
            if (tovar != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vrati string obsahujuci informacie o tovare v regali.
     * @return string zoznam tovaru v regali
     */
    @Override
    public String toString() {
        String out = "Regal(kapacita " + this.zoznamTovaru.length + "), zoznam tovaru:" + '\n';
        StringBuilder zoznam = new StringBuilder();
        for (int i = 0; i < this.zoznamTovaru.length; i++) {
            if (this.zoznamTovaru[i] != null) {
                zoznam.append(i + 1).append(". ").append(this.zoznamTovaru[i].toString()).append('\n');
            } else {
                zoznam.append(i + 1).append(". ").append("[-]").append('\n');
            }
        }
        zoznam = new StringBuilder(zoznam.toString().indent(2));
        out = out + zoznam;
        return out;
    }
}
