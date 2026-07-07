package sk.uniza.fri.sklad.pracaSoSuborom;

import sk.uniza.fri.sklad.Sklad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Trieda CitacSuboru sluzi k nacitaniu konfiguracie skladu z textoveho suboru v ktorom je ulozena.
 * @author Juraj
 */
public class CitacSuboru {

    public CitacSuboru() {
    }

    /**
     * Pokusi sa nacitat konfiguraciu skladu z textoveho suboru. Ak subor neexistuje, vytvori novy subor s pociatocnou konfiguraciou.
     * @return nacitany sklad
     */
    public Sklad nacitaj() {
        try {
            File file = new File("sklad.txt");
            if (!file.exists()) {
                this.inicializujSubor(file);
            }
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            Sklad sklad = (Sklad)oi.readObject();

            oi.close();
            fi.close();

            return sklad;

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sluzi k vytvoreniu noveho textoveho suboru s pociatocnou konfiguraciou skladu.
     * @param subor subor do ktoreho sa ma konfig ulozit
     */
    private void inicializujSubor(File subor) {

        Sklad sklad = new Sklad();

        try {
            FileOutputStream f = new FileOutputStream(subor);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(sklad);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error initializing stream");
        }

    }


}
